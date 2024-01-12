package core.calculation.function;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import core.manager.ConstantRegion;
import exceptional.WrongFormat;
import utils.StrUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表达式函数，通过表达式操作解析出来的函数对象
 *
 * @author zhao
 */
public class ExpressionFunction extends ManyToOneNumberFunction {

    /**
     * 参数提取器
     */
    final static Pattern pattern = Pattern.compile(ConstantRegion.REGULAR_PURE_LETTER + "(?!\\()");
    private final Calculation functionFormulaCalculation;
    private final ArrayList<String> expression;
    private final int paramSize;

    protected ExpressionFunction(Calculation functionFormulaCalculation, String name, ArrayList<String> expression, int paramSize) {
        super(name);
        this.functionFormulaCalculation = functionFormulaCalculation;
        this.expression = expression;
        this.paramSize = paramSize;
    }

    /**
     * 解析表达式，得到函数对象
     *
     * @param expression 表达式字符串
     * @return 解析出来的函数对象
     * @throws WrongFormat 如果函数表达式的格式有错误，则抛出此异常
     */
    public static ExpressionFunction parse(String expression) throws WrongFormat {
        // 获取到函数的形参部分 以及 函数的表达式部分
        final ArrayList<String> arrayList = StrUtils.splitByChar(expression, '=');
        // 判断是否为函数格式
        if (arrayList.size() != 2) {
            throw new UnsupportedOperationException("您的表达式不属于函数，期望的格式：【函数名】(参数1,参数2) = 数学表达式");
        }
        // 解析函数名
        String functionName = null;
        // 解析参数名列表
        LinkedHashSet<String> params = null;
        // 获取参数名
        final String trim = arrayList.get(0);
        final int lastIndex = trim.length() - 1;
        for (int i = 0; i < trim.length(); i++) {
            final char c = trim.charAt(i);
            if (c == ConstantRegion.LEFT_BRACKET) {
                functionName = trim.substring(0, i);
                continue;
            }
            if (c == ConstantRegion.RIGHT_BRACKET) {
                if (functionName == null) {
                    throw new WrongFormat("请您将函数名字写上!!!");
                }
                params = new LinkedHashSet<>(StrUtils.splitByChar(trim.substring(functionName.length() + 1, lastIndex), ','));
            }
        }
        // 检查表达式
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        final String string = arrayList.get(1);
        // 准备其它表达式容器
        final ArrayList<String> arrayList1 = new ArrayList<>();
        int backEnd = 0;
        if (params != null) {
            final Matcher matcher = pattern.matcher(string);
            // 查找到函数的形参参数位置
            while (matcher.find()) {
                // 找到之后就提取出来
                final String group = matcher.group();
                // 判断是否为参数位
                if (params.contains(group)) {
                    // 将参数位之前的表达式追加到 list 中
                    final String substring = string.substring(backEnd, matcher.start()).trim();
                    backEnd = matcher.end();
                    arrayList1.add(substring);
                }
            }
            // 最后再追加一次
            final String trim1 = string.substring(backEnd).trim();
            arrayList1.add(trim1);
        } else {
            params = new LinkedHashSet<>();
        }
        // 开始进行构建
        if (functionName == null) {
            throw new WrongFormat("您的函数名提取失败，可能是格式错误，正确的格式示例:sum(a, b)，您的格式：" + trim);
        }
        return new ExpressionFunction(instance, functionName, arrayList1, params.size());
    }

    /**
     * 函数的运行逻辑实现
     *
     * @param numbers 这里是函数的数据输入对象，由框架向这里传递数据输入参数
     * @return 这里是数据经过函数转换之后的数据
     */
    @Override
    public double run(double... numbers) {
        if (numbers.length != this.paramSize) {
            throw new UnsupportedOperationException("参数不正确，期望参数数量为：" + this.paramSize + "，实际参数数量为：" + numbers.length);
        }
        String s;
        {
            // 开始进行参数替换
            StringBuilder stringBuilder = new StringBuilder();
            int index = 0;
            for (double number : numbers) {
                stringBuilder.append(this.expression.get(index++)).append(number);
            }
            s = stringBuilder.toString();
        }
        try {
            this.functionFormulaCalculation.check(s);
            return ((CalculationNumberResults) this.functionFormulaCalculation.calculation(s)).getResult();
        } catch (WrongFormat e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
