package io.github.beardedManZhao.mathematicalExpression.core.calculation.number;

import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.CompileCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.SharedCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.ManyToOneNumberFunction;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.FunctionExpression;
import io.github.beardedManZhao.mathematicalExpression.core.container.LogResults;
import io.github.beardedManZhao.mathematicalExpression.core.manager.CalculationManagement;
import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;
import io.github.beardedManZhao.mathematicalExpression.exceptional.ExtractException;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;
import io.github.beardedManZhao.mathematicalExpression.utils.StrUtils;
import top.lingyuzhao.varFormatter.utils.DataObj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * 新版函数数学表达式计算组件，该组件相对于父类，能够使用函数多参数进行数学表达式的计算，将多参函数的机制在此组件中使用了起来。
 * <p>
 * The new version of the function mathematical expression calculation component, which is relative to the parent class, can use multiple parameters of the function to calculate mathematical expressions, and uses the mechanism of multi parameter functions in this component.
 *
 * @author zhao
 */
public class FunctionFormulaCalculation2 extends FunctionFormulaCalculation implements SharedCalculation, Serializable, CompileCalculation {

    private final static long serialVersionUID = "FunctionFormulaCalculation2".hashCode();

    private final HashMap<String, FunctionExpression> ShareHashMap = new HashMap<>();
    private final HashMap<String, CalculationNumberResults> ShareResultsHashMap = new HashMap<>();
    private boolean StartSharedPool = true;

    protected FunctionFormulaCalculation2(String name) {
        super(name);
    }

    protected FunctionFormulaCalculation2() {
        this(Mathematical_Expression.functionFormulaCalculation2.name() + "_temp");
    }

    /**
     * 从管理者中获取到指定名称的计算组件，注意 如果管理者中不存在该名称的组件，那么该组件就会自动的注册
     * <p>
     * Get the calculation component with the specified name from the manager. Note that if there is no component with this name in the manager, the component will be registered automatically
     *
     * @param CalculationName 组件的名字
     * @return 解析括号类计算公式的计算组件
     */
    public static FunctionFormulaCalculation2 getInstance(String CalculationName) {
        if (CalculationManagement.isRegister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof FunctionFormulaCalculation2) {
                return (FunctionFormulaCalculation2) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于 FunctionFormulaCalculation2 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            FunctionFormulaCalculation2 FunctionFormulaCalculation2 = new FunctionFormulaCalculation2(CalculationName);
            CalculationManagement.register(FunctionFormulaCalculation2, false);
            return FunctionFormulaCalculation2;
        }
    }

    /**
     * @return 该组件是否有启动共享池，一个布尔值，如果返回true代表共享池已经启动
     */
    @Override
    public boolean isStartSharedPool() {
        return StartSharedPool;
    }

    /**
     * 设置共享池开关，共享池是在JavaApi中，1.1版本衍生出来的一种新特性，由于计算量的需求，引入该功能
     * <p>
     * Set the shared pool switch. The shared pool is a new feature derived from version 1.1 in JavaApi. Due to the requirement of computing load, this function is introduced
     *
     * @param startSharedPool 共享池如果设置为true，代表被打开，将会共享检查与
     */
    @Override
    public void setStartSharedPool(boolean startSharedPool) {
        StartSharedPool = startSharedPool;
    }

    /**
     * 是否已经缓存了指定字符串的数据
     *
     * @param name 要检查的字符串
     * @return 如果已经缓存则返回true，否则返回false
     */
    @Override
    public boolean isCache(String name) {
        return this.ShareHashMap.containsKey(name);
    }

    /**
     * 清理缓存数据
     */
    @Override
    public void clearCache() {
        this.ShareResultsHashMap.clear();
        this.ShareHashMap.clear();
    }

    /**
     * 提取出来一个公式中所有函数的名称，以及其函数形参的起始与终止索引值
     *
     * @param string 需要被解析的数学运算公式
     * @param start  公式中所有包含函数实参公式的起始索引值
     * @param end    公式中所有包含函数实参公式的的终止索引值
     * @param names  公式中所有包含函数的名字
     */
    public static void FunctionParameterExtraction(String string, Stack<Integer> start, Stack<Integer> end, Stack<String> names) {
        // 创建一个标记，标记是否进入函数
        boolean b = false;
        int count = 0;
        // 创建函数名称缓冲区
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char aChar = string.charAt(i);
            if (((aChar >= ConstantRegion.BA_ASCII && aChar <= ConstantRegion.BZ_ASCII) || (aChar >= ConstantRegion.SA_ASCII && aChar <= ConstantRegion.SZ_ASCII))) {
                if (!b) {
                    // 如果是刚刚进入函数，就将当前索引添加到栈
                    b = true;
                    start.push(i + 1);
                }
                // 将当前函数名字字符添加到函数名称缓冲区
                stringBuilder.append(aChar);
                // 如果当前是函数的名字，就将函数起始索引继续移动，将函数名字部分的索引去除
                start.push(start.pop() + 1);
            } else if (b && aChar == ConstantRegion.LEFT_BRACKET) {
                count += 1;
            } else if (b && aChar == ConstantRegion.RIGHT_BRACKET && --count == 0) {
                // 如果是函数结束，就将函数的终止点索引添加到栈
                b = false;
                end.push(i);
                // 将函数的名称添加到栈
                names.push(stringBuilder.toString());
                // 清理名称缓冲区
                stringBuilder.delete(0, stringBuilder.length());
            }
        }
    }

    /**
     * 提取出来一个公式中所有函数的名称，以及其函数形参的起始与终止索引值
     *
     * @param string         需要被解析的数学运算公式
     * @param start          公式中所有包含函数实参公式的起始索引值
     * @param end            公式中所有包含函数实参公式的的终止索引值
     * @param names          公式中所有包含函数的名字
     * @param formulaBuilder 公式缓冲区，这个用于存储转换之后的公式，当需要进行公式检查的时候才会使用到该参数
     * @throws WrongFormat 如果公式格式不正确，就会抛出一个异常
     */
    public void FunctionParameterExtraction(String string, Stack<Integer> start, Stack<Integer> end, Stack<String> names, StringBuilder formulaBuilder) throws WrongFormat {
        // 创建一个标记，标记是否进入函数
        boolean b = false;
        int count = 0;
        // 创建函数名称缓冲区
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            final char aChar = string.charAt(i);
            if (((aChar >= ConstantRegion.BA_ASCII && aChar <= ConstantRegion.BZ_ASCII) || (aChar >= ConstantRegion.SA_ASCII && aChar <= ConstantRegion.SZ_ASCII))) {
                if (!b) {
                    // 如果是刚刚进入函数，就将当前索引添加到栈
                    b = true;
                    start.push(i + 1);
                    // 然后为缓冲的公式进行 0 的追加
                    formulaBuilder.append('0');
                } else if (count != 0) {
                    // 如果 count 不为0 代表找到了函数参数，但是参数里面有字母 所以肯定有问题
                    throw new WrongFormat("请勿在参数位使用函数嵌套，您可以在函数的表达式中使用嵌套，例如 f(x) = x + ff(x), 调用为f(1 + 2); \n但是不能在函数的形参中使用嵌套，例如 f(x, b) = x + b, 调用为f(1 + 2, ff(1 + 2)) 是不允许滴\n嵌套发生的位置 = " + string.substring(i) + " 来自于 " + string);
                }
                // 将当前函数名字字符添加到函数名称缓冲区
                stringBuilder.append(aChar);
                // 如果当前是函数的名字，就将函数起始索引继续移动，将函数名字部分的索引去除
                start.push(start.pop() + 1);
            } else if (b && aChar == ConstantRegion.LEFT_BRACKET) {
                count += 1;
            } else if (b && aChar == ConstantRegion.RIGHT_BRACKET && --count == 0) {
                // 如果是函数结束，就将函数的终止点索引添加到栈
                b = false;
                end.push(i);
                // 将函数的名称添加到栈
                names.push(stringBuilder.toString());
                // 清理名称缓冲区
                stringBuilder.delete(0, stringBuilder.length());
            } else if (!b && aChar != ConstantRegion.EMPTY) {
                // 其他情况就直接将字符添加到公式缓冲区中
                formulaBuilder.append(aChar);
            }
        }
    }

    /**
     * 检查公式格式是否正确，如果不正确就会抛出一个异常
     * <p>
     * Check whether the formula format is correct. If not, an exception will be thrown
     *
     * @param string 需要被判断格式的数学运算公式
     *               <p>
     *               Mathematical operation formula of the format to be judged
     */
    @Override
    @SuppressWarnings("unchecked")
    public void check(String string) throws WrongFormat {
        if (this.StartSharedPool && this.isCache(string)) {
            return;
        }
        // 准备函数元数据缓冲区
        Stack<Integer> start = new Stack<>();
        Stack<Integer> end = new Stack<>();
        Stack<String> names = new Stack<>();
        // 准备公式缓冲区
        StringBuilder stringBuilder = new StringBuilder(string.length());
        // 开始进行公式函数元数据提取
        FunctionParameterExtraction(string, start, end, names, stringBuilder);
        // 判断是否全部一致
        int size1 = start.size();
        int size2 = end.size();
        if (size1 == size2 && size1 == names.size()) {
            // 检查无误后，判断是否启动共享池，如果启动了的话，将格式化中的没有问题的数据提供给共享池
            if (StartSharedPool) {
                // 更新共享池所属公式 TODO 这里是需要将函数的元素复制下 因为共享池中的栈需要用来计算!
                ShareHashMap.put(string, new FunctionExpression(string, this.getName(), (Stack<Integer>) start.clone(), (Stack<Integer>) end.clone(), names));
            }
            while (!(start.isEmpty() || end.isEmpty())) {
                // 如果一致，就进行函数内部每一个公式的检查 这里首先将函数中的每一个公式切割出来
                for (String s : StrUtils.splitByChar(string.substring(start.pop(), end.pop()), ConstantRegion.COMMA)) {
                    // 将每一个公式进行检查
                    BRACKETS_CALCULATION_2.check(s);
                }
            }
            // 如果函数没有问题就检查整个公式
            BRACKETS_CALCULATION_2.check(stringBuilder.toString());
        } else {
            throw new WrongFormat("函数可能缺少起始或结束括号，没有正常的闭环。\nThe function may lack a start or end bracket, and there is no normal closed loop\nMissing function bracket logarithm: " + Math.abs(size2 - size1));
        }
    }

    /**
     * 计算一个数学表达式，并将计算细节与计算结果存储到数值结果集中。
     * <p>
     * Compute a mathematical expression and store the calculation details and results in the numerical result set.
     *
     * @param Formula        被计算的表达式，要求返回值是一个数值。
     *                       <p>
     *                       The returned value of the evaluated expression is required to be a numeric value.
     * @param formatRequired 是否需要被格式化，用于确保公式格式正确。
     *                       <p>
     *                       Whether it needs to be formatted to ensure that the formula format is correct.
     * @return 数值结果集对象，其中保存着每一步的操作数据，以及最终结果数值
     * <p>
     * Numerical result set object, which stores the operation data of each step and the final result value
     */
    @Override
    public CalculationNumberResults calculation(String Formula, boolean formatRequired) {
        boolean equals = this.StartSharedPool;
        FunctionExpression objects = null;
        if (equals) {
            final CalculationNumberResults calculationNumberResults = this.ShareResultsHashMap.get(Formula);
            if (calculationNumberResults != null) {
                LOGGER.info(ConstantRegion.LOG_INFO_SHARED_POOL + this.Name + ConstantRegion.LEFT_BRACKET + Formula + ConstantRegion.RIGHT_BRACKET);
                return calculationNumberResults;
            }
            objects = this.ShareHashMap.get(Formula);
            equals = objects != null;
        }
        // 准备编译对象
        FunctionExpression functionExpression;
        // 开始进行函数计算，首先判断是否启用了共享池 以及身份是否正确，确保两个公式是同一个
        if (equals) {
            functionExpression = objects;
            LOGGER.info(ConstantRegion.LOG_INFO_SHARED_POOL + this.Name + ConstantRegion.LEFT_BRACKET + Formula + ConstantRegion.RIGHT_BRACKET);
        } else {
            functionExpression = FunctionExpression.compile(Formula, this.Name);
            LOGGER.info(ConstantRegion.LOG_INFO_SHARED_POOL_NO_USE + this.Name + ConstantRegion.LEFT_BRACKET + Formula + ConstantRegion.RIGHT_BRACKET);
        }
        // 开始计算
        final CalculationNumberResults calculation = Mathematical_Expression.Options.isUseBigDecimal() ? functionExpression.calculationBigDecimalsCache(false) : functionExpression.calculationCache(false);
        if (this.StartSharedPool) {
            this.ShareResultsHashMap.put(Formula, calculation);
        }
        return calculation;
    }

    @Override
    public LogResults explain(String Formula, boolean formatRequired) {
        Stack<Integer> start;
        Stack<Integer> end;
        Stack<String> names;
        StringBuilder stringBuilder = new StringBuilder(Formula);
        // 开始进行函数计算，首先判断是否启用了共享池 以及身份是否正确，确保两个公式是同一个
        start = new Stack<>();
        end = new Stack<>();
        names = new Stack<>();
        FunctionParameterExtraction(Formula, start, end, names);
        // 启用标记
        final LogResults logResults = new LogResults("start");
        logResults.setNameJoin(false);
        logResults.setPrefix("start(\"" + Formula + "\")");
        // 开始计算，首先迭代所有函数的公式与函数的名字，计算出来函数的结果
        while (!start.isEmpty()) {
            int pop1 = start.pop();
            int pop2 = end.pop();
            String pop = names.pop();
            // 通过函数名字获取函数对象
            final ManyToOneNumberFunction functionByName = CalculationManagement.getFunctionByName(pop);
            // 通过函数索引获取实参
            // 提前计算substring，避免重复计算
            String subFormula = Formula.substring(pop1, pop2);
            final String s1 = functionByName.getName() + ConstantRegion.LEFT_BRACKET;
            DataObj dataObj = (DataObj) logResults.get(pop);
            if (dataObj == null) {
                dataObj = new DataObj(pop);
                logResults.put(pop, dataObj);
            }
            // 使用ArrayList收集结果，以便于添加和转换为数组
            ArrayList<Double> results = new ArrayList<>();

            StringBuilder stringBuilder1 = new StringBuilder();

            for (String s : StrUtils.splitByChar(subFormula, ConstantRegion.COMMA)) {
                double result = BRACKETS_CALCULATION_2.calculation(s).getResult();
                results.add(result);
                stringBuilder1.append(result).append(',');
            }

            // 将ArrayList转换为double[]数组，这一步在内部已经优化过
            double[] resultArray = results.stream().mapToDouble(Double::doubleValue).toArray();

            // 最后替换字符串内容 functionByName
            final String s = String.valueOf(functionByName.run(resultArray));
            stringBuilder.replace(pop1 - pop.length() - 1, pop2 + 1, s);
            final String s2 = pop + pop1 + '_' + pop2;
            final DataObj dataObj1 = new DataObj(s2);
            dataObj1.setPrefix(s2 + "(\"" + s1 + subFormula + ConstantRegion.RIGHT_BRACKET + "\")\n" + s2 + "_r(\"" + s1 + stringBuilder1 + ConstantRegion.RIGHT_BRACKET + "\")");
            dataObj1.put(s2 + "_r", s);
            dataObj.put(dataObj1);
        }
        final LogResults explain = BRACKETS_CALCULATION_2.explain(stringBuilder.toString(), false);
        logResults.put(explain);
        logResults.setResult(explain.getResult());
        return logResults;
    }

    @Override
    public FunctionExpression compile(String Formula, boolean formatRequired) {
        return FunctionExpression.compile(Formula, this.Name);
    }

    @Override
    public FunctionExpression compileBigDecimal(String Formula, boolean formatRequired) {
        return this.compile(Formula, formatRequired);
    }
}
