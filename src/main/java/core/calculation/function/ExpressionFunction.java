package core.calculation.function;

import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import core.manager.ConstantRegion;
import exceptional.WrongFormat;
import utils.StrUtils;

import java.util.ArrayList;
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
    private final ArrayList<Integer> indexList;
    private final int paramSize;

    /**
     * 构建一个函数对象
     *
     * @param functionFormulaCalculation 函数计算组件
     * @param name                       函数名字
     * @param expression                 函数的所有形参
     * @param paramSize                  函数的参数个数
     * @param indexList                  函数的参数索引 元素是形参索引，元素索引是在表达式中参数的顺序
     */
    protected ExpressionFunction(Calculation functionFormulaCalculation, String name, ArrayList<String> expression, int paramSize, ArrayList<Integer> indexList) {
        super(name);
        this.functionFormulaCalculation = functionFormulaCalculation;
        this.expression = expression;
        this.paramSize = paramSize;
        this.indexList = indexList;
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
        ArrayList<String> params = null;
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
                params = StrUtils.splitByChar(trim.substring(functionName.length() + 1, lastIndex), ',');
            }
        }
        // 检查表达式
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        final String string = arrayList.get(1);
        // 准备其它表达式容器
        final ArrayList<String> arrayList1 = new ArrayList<>();
        // params 形参参数
        // arrayList1 操作符列表
        // 开始查找所有的参数在公式中的排序位置 找到之后使用 位置做为索引 使用参数形参位置做为索引
        // 例如 f(x) = x + 1 * x 构建的就是 [0, 0]
        // 首先提取出所有的操作数
        final ArrayList<Integer> arrayList2 = new ArrayList<>();
        int backEnd = 0;
        if (params != null) {
            final Matcher matcher = pattern.matcher(string);
            // 查找到函数的形参参数位置
            while (matcher.find()) {
                // 找到之后就提取出来
                final String group = matcher.group();
                // 判断是否为参数位
                final int i = params.indexOf(group);
                if (i >= 0) {
                    // 将参数位之前的表达式追加到 list 中
                    final String substring = string.substring(backEnd, matcher.start()).trim();
                    backEnd = matcher.end();
                    arrayList1.add(substring);
                    // 代表找到了一个操作数 将这个形参位置追加到 arrayList2
                    arrayList2.add(i);
                } else if (!StrUtils.IsANumber(group)) {
                    // 不是数值且不是形参，看看是不是函数名字
                    if (matcher.end() >= string.length()) {
                        // 不用判断了 这个不是函数 因为长度不够 直接返回错误
                        throw new WrongFormat("Unknown formal parameter [" + group + "] comes from [" + string + "].");
                    }
                    final String s = group + string.charAt(matcher.end());
                    if (!CalculationManagement.isFunctionExist(s)) {
                        // 也不是函数名字 直接返回错误信息
                        throw new WrongFormat("Unknown formal parameter [" + group + " or " + s + "] comes from [" + string + "].");
                    }
                }
            }
            // 最后再追加一次
            final String trim1 = string.substring(backEnd).trim();
            arrayList1.add(trim1);
        } else {
            params = new ArrayList<>();
        }
        // 开始进行构建
        if (functionName == null) {
            throw new WrongFormat("您的函数名提取失败，可能是格式错误，正确的格式示例:sum(a, b)，您的格式：" + trim);
        }
        return new ExpressionFunction(instance, functionName, arrayList1, params.size(), arrayList2);
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
            for (Integer integer : this.indexList) {
                stringBuilder.append(this.expression.get(index++)).append(numbers[integer]);
            }
            if (this.expression.size() > index) {
                stringBuilder.append(this.expression.get(index));
            }
            // 开始替换参数
            s = stringBuilder.toString();
        }
        try {
            this.functionFormulaCalculation.check(s);
            return ((CalculationNumberResults) this.functionFormulaCalculation.calculation(s, false)).getResult();
        } catch (WrongFormat e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
