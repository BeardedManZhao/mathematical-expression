package io.github.beardedManZhao.mathematicalExpression.core.container;

import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.ManyToOneNumberFunction;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm.DynamicFunctionCompiler;
import io.github.beardedManZhao.mathematicalExpression.utils.StrUtils;

import java.util.ArrayList;

/**
 * 数学表达式逆运算 单 x 方程求解器。
 * <p>
 * 该类基于牛顿法与二分法提供方程求根功能，支持动态编译与自动区间检测。
 * 主要特性：
 * <ul>
 *     <li>支持牛顿法与二分法切换</li>
 *     <li>自动检测并抛出无解异常</li>
 *     <li>可配置精度、迭代次数与初始区间/猜测值</li>
 *     <li>JavaScript 引擎动态函数编译</li>
 * </ul>
 *
 * @author 赵凌宇
 */
@SuppressWarnings("unused")
public class EquationSolver extends NameExpression {

    protected static final String[] X = {"x"};

    private static final double DERIVE_STEP = 1e-6;
    protected final ManyToOneNumberFunction function;
    protected final double result;
    protected double newtonInitialX = 2;
    protected double bisectionLeft = 1;
    protected double bisectionRight = 3;
    protected double tol = 1e-12;
    protected int maxIter = 100;
    protected boolean useNewton = true;

    /**
     * 私有构造函数，通过表达式字符串与结果值初始化求解器。
     *
     * @param exprString 方程左侧表达式字符串（关于 x）
     * @param exprResult 方程右侧常数字符串
     * @param name       计算器名称，用于动态编译函数命名
     * @throws IllegalArgumentException 如果右侧值无法解析为数字
     */
    private EquationSolver(String exprString, String exprResult, String name) {
        this(exprString, Double.parseDouble(exprResult), name, DynamicFunctionCompiler.compile("EquationSolver", exprString, X));
        autoDetectInterval();
    }

    /**
     * 私有构造函数，通过表达式字符串与结果值初始化求解器。
     *
     * @param exprString 方程左侧表达式字符串（关于 x）
     * @param exprResult 方程右侧常数字符串
     * @param name       计算器名称，用于动态编译函数命名
     * @param function   已编译的函数 这个就是方程左边表达式的编译
     * @throws IllegalArgumentException 如果右侧值无法解析为数字
     */
    protected EquationSolver(String exprString, double exprResult, String name, ManyToOneNumberFunction function) {
        super(exprString, name);
        this.function = function;
        try {
            this.result = exprResult;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("方程右侧值必须为数字：" + exprResult, e);
        }
    }

    /**
     * 编译方程求解器实例。表达式必须为 "左侧表达式=右侧数值" 格式。
     *
     * @param expression      含有等号的方程字符串
     * @param calculationName 实例名称，用于内部函数编译
     * @return 已初始化并完成区间检测的 {@link EquationSolver} 实例
     * @throws IllegalArgumentException 方程格式不合法时抛出
     */
    public static EquationSolver compile(String expression, String calculationName) {
        ArrayList<String> parts = StrUtils.splitByChar(expression, '=');
        if (parts.size() != 2) {
            throw new IllegalArgumentException("方程表达式格式应为 '表达式=值'，实际：" + expression);
        }
        final String s = parts.get(0);
        return new EquationSolver(s, parts.get(1), calculationName);
    }

    /**
     * 在指定 x 值处评估方程左侧表达式。
     *
     * @param x 自变量值
     * @return 表达式在 x 处的计算结果
     */
    protected double eval(double x) {
        return function.run(x);
    }

    /**
     * 使用中心差分法计算函数在指定点的数值导数。
     *
     * @param x 自变量值
     * @return 近似导数 f'(x)
     */
    private double derivative(double x) {
        double h = DERIVE_STEP;
        return (eval(x + h) - eval(x - h)) / (2 * h);
    }

    /**
     * 使用牛顿迭代法求解方程根。
     *
     * @param tol     收敛容忍度，当 `abs(f(x)-result) 小于 tol` 时停止
     * @param maxIter 最大迭代次数
     * @return 长度为 2 的数组，包含根近似值与实际迭代次数：[x, iter]
     * @throws ArithmeticException 导数接近零或迭代未收敛时抛出
     */
    public double[] solveNewton(double tol, int maxIter) {
        double x = newtonInitialX;
        for (int i = 1; i <= maxIter; i++) {
            double fx = eval(x) - result;
            if (Math.abs(fx) < tol) {
                return new double[]{x, i};
            }
            double dfx = derivative(x);
            if (Math.abs(dfx) < 1e-12) {
                throw new ArithmeticException(
                        String.format("导数过小 (|f'(x)| < 1e-12) 在 x=%.6f 处停止迭代", x)
                );
            }
            x -= fx / dfx;
        }
        throw new ArithmeticException(
                String.format("牛顿法在 %d 次迭代后未收敛 (阈值 tol=%.2e)", maxIter, tol)
        );
    }

    /**
     * 使用二分法求解方程根。
     *
     * @param tol     收敛容忍度，当区间长度/2 小于 tol 或 abs(f(mid)-result) 小于 tol 时停止
     * @param maxIter 最大迭代次数
     * @return 长度为 2 的数组，包含根近似值与实际迭代次数：[mid, iter]
     * @throws IllegalArgumentException 初始区间两端函数值同号时抛出
     */
    public double[] solveBisection(double tol, int maxIter) {
        double a = bisectionLeft;
        double b = bisectionRight;
        double fa = eval(a) - result;
        double fb = eval(b) - result;
        if (fa * fb > 0) {
            throw new IllegalArgumentException(
                    String.format("二分法初始区间 f(%.6f)=%.6f 与 f(%.6f)=%.6f 同号，无法保证有根", a, fa, b, fb)
            );
        }
        double mid = a;
        for (int i = 1; i <= maxIter && (b - a) / 2 > tol; i++) {
            mid = (a + b) / 2;
            double fm = eval(mid) - result;
            if (Math.abs(fm) < tol) {
                return new double[]{mid, i};
            }
            if (fa * fm <= 0) {
                b = mid;
            } else {
                a = mid;
                fa = fm;
            }
        }
        return new double[]{mid, maxIter};
    }

    /**
     * 指示是否使用 BigDecimal 类型计算。
     *
     * @return 始终返回 false
     */
    @Override
    public boolean isBigDecimal() {
        return false;
    }

    /**
     * 指示是否使用非 BigDecimal 的普通双精度计算。
     *
     * @return 始终返回 true
     */
    @Override
    public boolean isUnBigDecimal() {
        return true;
    }

    /**
     * 将实例切换到多精度模式。当前不支持 BigDecimal，调用将抛出异常。
     *
     * @throws UnsupportedOperationException 总是抛出，不支持 BigDecimal
     */
    @Override
    public void convertToMultiPrecisionSupported() {
        throw new UnsupportedOperationException("不支持 BigDecimal 类型计算");
    }

    /**
     * 执行方程求解，根据配置选择 Newton 或 Bisection。
     *
     * @param isCopy 未使用参数，用于接口兼容
     * @return {@link CalculationNumberResults} 包含迭代次数、计算结果及名称
     */
    @Override
    public CalculationNumberResults calculation(boolean isCopy) {
        double[] res = useNewton
                ? solveNewton(tol, maxIter)
                : solveBisection(tol, maxIter);
        return new CalculationNumberResults((int) res[1], res[0], getCalculationName());
    }

    /**
     * 执行 BigDecimal 高精度方程求解，当前不支持。
     *
     * @param isCopy 未使用参数，用于接口兼容
     * @throws UnsupportedOperationException 总是抛出，不支持高精度
     */
    @Override
    public CalculationResults calculationBigDecimals(boolean isCopy) {
        throw new UnsupportedOperationException("不支持高精度计算");
    }

    /**
     * 获取当前是否使用牛顿法。
     *
     * @return true 表示使用牛顿法，否则使用二分法
     */
    public boolean isUseNewton() {
        return useNewton;
    }

    /**
     * 设置求解时是否使用牛顿法。
     *
     * @param useNewton true 切换到牛顿法， false 切换到二分法
     */
    public void setUseNewton(boolean useNewton) {
        this.useNewton = useNewton;
    }

    /**
     * 获取最大迭代次数。
     *
     * @return 最大迭代次数
     */
    public int getMaxIter() {
        return maxIter;
    }

    /**
     * 设置最大迭代次数。
     *
     * @param maxIter 新的最大迭代次数，必须为正整数
     */
    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }

    /**
     * 获取当前收敛容忍度。
     *
     * @return 收敛容忍度 tol
     */
    public double getTol() {
        return tol;
    }

    /**
     * 设置收敛容忍度。
     *
     * @param tol 新的 tol，必须大于 0
     */
    public void setTol(double tol) {
        this.tol = tol;
    }

    /**
     * 获取牛顿初始猜测值。
     *
     * @return 牛顿法初始 x
     */
    public double getNewtonInitialX() {
        return newtonInitialX;
    }

    /**
     * 设置牛顿法初始猜测值。
     *
     * @param newtonInitialX 初始 x 值
     */
    public void setNewtonInitialX(double newtonInitialX) {
        this.newtonInitialX = newtonInitialX;
    }

    /**
     * 获取二分法左端点。
     *
     * @return 二分法左边界 a
     */
    public double getBisectionLeft() {
        return bisectionLeft;
    }

    /**
     * 设置二分法左端点。
     *
     * @param bisectionLeft 新的左边界 a
     */
    public void setBisectionLeft(double bisectionLeft) {
        this.bisectionLeft = bisectionLeft;
    }

    /**
     * 获取二分法右端点。
     *
     * @return 二分法右边界 b
     */
    public double getBisectionRight() {
        return bisectionRight;
    }

    /**
     * 设置二分法右端点。
     *
     * @param bisectionRight 新的右边界 b
     */
    public void setBisectionRight(double bisectionRight) {
        this.bisectionRight = bisectionRight;
    }

    /**
     * 获取方程结果。
     *
     * @return 方程的右表达式
     */
    public double getResult() {
        return result;
    }

    /**
     * 自动检测解所在的初始区间并设置二分法与牛顿法相关参数。
     *
     * <p>算法分两阶段：
     * <ol>
     *   <li>指数级扩展搜索区间，快速定位符号变化区间</li>
     *   <li>线性细化已定位区间，找到首个符号变化点</li>
     * </ol>
     *
     * @throws NoRootFoundException 若在 [-1000.0, 1000.0] 区间未检测到根
     */
    protected void autoDetectInterval() {
        final double MAX_RANGE = 1000.0;
        final double BASE_STEP = 1.0;
        double prevX = 0.0, prevY = eval(prevX) - result;
        // 指数搜索
        for (double exp = BASE_STEP; exp <= MAX_RANGE; exp *= 2) {
            double currX = Math.copySign(exp, prevY);
            double currY = eval(currX) - result;
            if (prevY * currY <= 0) {
                refineInterval(prevX, currX, prevY, currY);
                return;
            }
            prevX = currX;
            prevY = currY;
        }
    }

    /**
     * 在给定区间内以固定步长线性细化，寻找首个符号变化点并设置相关参数。
     *
     * @param a  区间左端点
     * @param b  区间右端点
     * @param fa a 处函数值 f(a)-result
     * @param fb b 处函数值 f(b)-result
     * @throws NoRootFoundException 若未找到符号变化点
     */
    private void refineInterval(double a, double b, double fa, double fb) {
        final double STEP = 1.0;
        double x1 = a, y1 = fa;
        double x2 = a + STEP;
        while (x2 <= b) {
            double y2 = eval(x2) - result;
            if (y1 * y2 <= 0) {
                this.bisectionLeft = x1;
                this.bisectionRight = x2;
                this.newtonInitialX = (x1 + x2) / 2;
                return;
            }
            x1 = x2;
            y1 = y2;
            x2 += STEP;
        }
        throw new NoRootFoundException(
                String.format("线性细化在 [%.6f, %.6f] 未找到根", a, b)
        );
    }

    /**
     * 当自动检测区间失败时抛出。
     */
    public static class NoRootFoundException extends RuntimeException {
        public NoRootFoundException(String message) {
            super(message);
        }
    }
}
