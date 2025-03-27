package io.github.beardedManZhao.mathematicalExpression.core.container;

import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.ManyToOneNumberFunction;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.FunctionFormulaCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.manager.CalculationManagement;
import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;
import io.github.beardedManZhao.mathematicalExpression.utils.StrUtils;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 函数表达式的表达式对象，其解析了函数的位置，提供了表达式中函数修改的功能！
 * <p>
 * The expression object of a function expression, which parses the position of the function and provides the function modification function in the expression!
 *
 * @author zhao
 */
public class FunctionExpression extends SubCompileExpression {

    private final static FunctionFormulaCalculation2 functionFormulaCalculation2 = FunctionFormulaCalculation2.getInstance(CalculationManagement.FUNCTION_FORMULA_CALCULATION_2_NAME);
    private final Stack<Integer> start, end;
    private final Stack<String> funNames;

    /**
     * 将一个函数表达式 转换为函数表达式的对象
     * <p>
     * Object that converts a function expression into a function expression
     *
     * @param expression      表达式的字符串形式
     *                        <p>
     *                        The string form of an expression
     * @param calculationName 函数表达式的名称
     *                        <p>
     *                        The name of the function expression
     * @param stack1          表达式中所有的函数的表达式起始索引标记栈，如果在外界有了更好的解析，并有了结果，可以直接传入，不需要在其中进行解析
     *                        <p>
     *                        The starting index marker stack for all functions in the expression can be passed in directly without the need for parsing if there is better parsing outside and results are available
     * @param stack2          表达式中所有的函数的表达式结束索引标记栈，如果在外界有了更好的解析，并有了结果，可以直接传入，不需要在其中进行解析
     *                        <p>
     *                        The ending index marker stack for all functions in the expression can be passed in directly without the need for parsing if there is better parsing outside and results are available
     * @param stack3          表达式中所有的函数的表达式名称标记栈，如果在外界有了更好的解析，并有了结果，可以直接传入，不需要在其中进行解析
     *                        <p>
     *                        The name marker stack for all functions in the expression can be passed in directly without the need for parsing if there is better parsing outside and results are available
     */
    public FunctionExpression(String expression, String calculationName, Stack<Integer> stack1, Stack<Integer> stack2, Stack<String> stack3) {
        super(expression, calculationName);
        this.start = stack1;
        this.end = stack2;
        this.funNames = stack3;
    }

    public static FunctionExpression compile(String expression, String calculationName) {
        final Stack<Integer> start = new Stack<>();
        final Stack<Integer> end = new Stack<>();
        final Stack<String> funNames = new Stack<>();
        FunctionFormulaCalculation2.FunctionParameterExtraction(expression, start, end, funNames);
        return new FunctionExpression(expression, calculationName, start, end, funNames);
    }

    /**
     * 此函数用于将当前函数表达式进行深层编译，经过深层编译操作之后返回的表达式将可以直接被用来进行括号表达式计算！
     * <p>
     * This function is used to deeply compile the current function expression, and the expression returned after the deep compilation operation can be directly used for parenthesis expression calculation!
     *
     * @param expression 表达式的字符串形式
     *                   <p>
     *                   The string form of an expression
     * @param start      表达式中所有的函数的表达式起始索引标记栈，如果在外界有了更好的解析，并有了结果，可以直接传入，不需要在其中进行解析
     *                   <p>
     *                   The starting index marker stack for all functions in the expression can be passed in directly without the need for parsing if there is better parsing outside and results are available
     * @param end        表达式中所有的函数的表达式结束索引标记栈，如果在外界有了更好的解析，并有了结果，可以直接传入，不需要在其中进行解析
     *                   <p>
     *                   The ending index marker stack for all functions in the expression can be passed in directly without the need for parsing if there is better parsing outside and results are available
     * @param funNames   表达式中所有的函数的表达式名称标记栈，如果在外界有了更好的解析，并有了结果，可以直接传入，不需要在其中进行解析
     *                   <p>
     *                   The name marker stack for all functions in the expression can be passed in directly without the need for parsing if there is better parsing outside and results are available
     * @return 函数表达式被编译为括号表达式的字符串
     * <p>
     * The string object that the function expression is compiled into a bracket expression
     */
    public static StringBuilder subCompile(String expression, Stack<Integer> start, Stack<Integer> end, Stack<String> funNames) {
        // 准备进行表达式编译操作
        final StringBuilder stringBuilder = new StringBuilder(expression);
        if (StrUtils.IsANumber(expression)) {
            return stringBuilder;
        }
        if (funNames.isEmpty()) {
            return stringBuilder;
        }
        // 开始计算，首先迭代所有函数的公式与函数的名字，计算出来函数的结果
        while (!start.isEmpty()) {
            int pop1 = start.pop();
            int pop2 = end.pop();
            String pop = funNames.pop();
            final ManyToOneNumberFunction functionByName = CalculationManagement.getFunctionByName(pop);
            // 通过函数索引获取实参
            // 提前计算substring，避免重复计算
            String subFormula = expression.substring(pop1, pop2);

            // 使用ArrayList收集结果，以便于添加和转换为数组
            ArrayList<Double> results = new ArrayList<>();

            for (String s : StrUtils.splitByCharWhereNoB(subFormula, ConstantRegion.COMMA)) {
                double result = functionFormulaCalculation2.calculation(s).getResult();
                results.add(result);
            }
            // 将ArrayList转换为double[]数组，这一步在内部已经优化过
            double[] resultArray = results.stream().mapToDouble(Double::doubleValue).toArray();

            // 最后替换字符串内容 functionByName
            stringBuilder.replace(pop1 - pop.length() - 1, pop2 + 1, String.valueOf(functionByName.run(resultArray)));
        }
        // 表达式字符串准备就绪 可以进行计算
        return stringBuilder;
    }

    @Override
    public boolean isBigDecimal() {
        return true;
    }

    @Override
    public boolean isUnBigDecimal() {
        return true;
    }

    @Override
    public void convertToMultiPrecisionSupported() {

    }

    /**
     * @return 获取到当前表达式中，所有参与运算的表达式的栈，您可以在栈中查询到函数的名称和计算顺序，您可以使用 `setFunName` 来修改您表达式对象中的一些函数，它允许您干涉计算过程的函数调用。
     * <p>
     * Retrieve the stack of all expressions participating in the current expression, where you can query the names and calculation order of functions. You can use 'setFunName' to modify some functions in your expression object, which allows you to interfere with function calls during the calculation process
     */
    public Stack<String> getFunNames() {
        return this.getFunNames(true);
    }

    public void setFunName(int index, String name) {
        // 获取到当前指定函数名字
        final String s = this.funNames.get(index);
        this.funNames.set(index, name);
        // 找到当前函数名字的结束位置 栈存储的是表达式 不包含函数名字 因此在这里先获取到函数名字的结束位置
        int end = this.start.get(index) - 1;
        // 获取到当前指定函数名字的起始位置
        int start = end - s.length();
        // 通过引用修改表达式
        this.getExpressionStrBuilder().replace(start, end, name);
    }

    @Override
    public NameExpression subCompile(boolean isCopy) {
        if (this.notNeedSubCompile()) {
            throw new UnsupportedOperationException("此表达式不需要深度编译了！" + this.getExpressionStr());
        }
        return functionFormulaCalculation2.compile(
                FunctionExpression.subCompile(this.getExpressionStr(), this.getStart(isCopy), this.getEnd(isCopy), this.getFunNames(isCopy)).toString(), true
        );
    }

    @Override
    public NameExpression subCompileBigDecimals(boolean isCopy) {
        if (this.notNeedSubCompile()) {
            throw new UnsupportedOperationException("此表达式不需要深度编译了！" + this.getExpressionStr());
        }
        return functionFormulaCalculation2.compileBigDecimal(
                FunctionExpression.subCompile(this.getExpressionStr(), this.getStart(isCopy), this.getEnd(isCopy), this.getFunNames(isCopy)).toString(), true
        );
    }

    /**
     * @return 该表达式是否需要深度编译！
     */
    @Override
    public boolean notNeedSubCompile() {
        return this.funNames.isEmpty();
    }

    @SuppressWarnings("unchecked")
    protected Stack<Integer> getStart(boolean isCopy) {
        if (isAvailable()) {
            return isCopy ? (Stack<Integer>) start.clone() : start;
        }
        throw new UnsupportedOperationException("此计算表达式组件将不再可用，因为您在上一次计算的时候 isCopy 设置为了 false，导致计算表达式将不存在可复用性!\nThis calculation expression component will no longer be available because isCopy was set to false during the last calculation, resulting in the calculation expression being no longer reusable!\nerror => " + this.getExpressionStr());
    }

    @SuppressWarnings("unchecked")
    protected Stack<Integer> getEnd(boolean isCopy) {
        if (isAvailable()) {
            return isCopy ? (Stack<Integer>) end.clone() : end;
        }
        throw new UnsupportedOperationException("此计算表达式组件将不再可用，因为您在上一次计算的时候 isCopy 设置为了 false，导致计算表达式将不存在可复用性!\nThis calculation expression component will no longer be available because isCopy was set to false during the last calculation, resulting in the calculation expression being no longer reusable!\nerror => " + this.getExpressionStr());
    }

    @SuppressWarnings("unchecked")
    protected Stack<String> getFunNames(boolean isCopy) {
        if (isAvailable()) {
            return isCopy ? (Stack<String>) funNames.clone() : funNames;
        }
        throw new UnsupportedOperationException("此计算表达式组件将不再可用，因为您在上一次计算的时候 isCopy 设置为了 false，导致计算表达式将不存在可复用性!\nThis calculation expression component will no longer be available because isCopy was set to false during the last calculation, resulting in the calculation expression being no longer reusable!\nerror => " + this.getExpressionStr());
    }

    @Override
    public CalculationNumberResults calculationCache(boolean isCopy) {
        final CalculationNumberResults calculationNumberResults = (CalculationNumberResults) super.calculationCache(isCopy);
        calculationNumberResults.setSource(this.getCalculationName());
        return calculationNumberResults;
    }

    @Override
    public CalculationNumberResults calculationBigDecimalsCache(boolean isCopy) {
        final CalculationNumberResults calculationNumberResults = (CalculationNumberResults) super.calculationBigDecimalsCache(isCopy);
        calculationNumberResults.setSource(this.getCalculationName());
        return calculationNumberResults;
    }
}
