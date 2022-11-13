package core.formula;

/**
 * 表达式接口，其中包含的就是一个表达式，每一个表达式都可以直接计算出来结果数值
 * <p>
 * Expression interface, which contains an expression. Each expression can directly calculate the result value
 *
 * @param <result>             表达式计算结果的数据类型
 *                             <p>
 *                             Data type of expression calculation result
 *                             <p>
 *                             For comparison of operation priority in expressions, when there are multiple expressions, the sorting function can be realized through this interface.
 * @param <ImplementationType> 该表达式的具体类型，用于进行子类与基类之间的转换
 *                             <p>
 *                             The specific type of the expression, which is used for conversion between subclasses and base classes
 * @author zhao
 */
public interface Formula<result, ImplementationType> extends Comparable<ImplementationType> {

    /**
     * @return 拓展本类至其子类实现类，能够通过该函数，实现父类与子类之间的切换
     * <p>
     * Expand this class to its subclass implementation class, and switch between parent class and subclass through this function
     */
    ImplementationType extendClass();

    /**
     * 两个表达式之间进行求和运算
     * <p>
     * Sum between two expressions
     *
     * @param Formula 被运算的表达式
     *                <p>
     *                The expression to be evaluated
     * @return 表达式1 + 表达式2 的结果 返回值还是一个表达式
     * <p>
     * The return value of the result of "Expression 1+Expression 2" is still an expression
     */
    ImplementationType _ADD_(ImplementationType Formula);

    /**
     * 两个表达式之间进行求差运算
     * <p>
     * Difference between two expressions
     *
     * @param Formula 被运算的表达式
     *                <p>
     *                The expression to be evaluated
     * @return 表达式1 + 表达式2 的结果 返回值还是一个表达式
     * <p>
     * The return value of the result of "Expression 1+Expression 2" is still an expression
     */
    ImplementationType _SUB_(ImplementationType Formula);

    /**
     * 两个表达式之间进行求和运算
     * <p>
     * Multiplication between two expressions
     *
     * @param Formula 被运算的表达式
     *                <p>
     *                The expression to be evaluated
     * @return 表达式1 + 表达式2 的结果 返回值还是一个表达式
     * <p>
     * The return value of the result of "Expression 1+Expression 2" is still an expression
     */
    ImplementationType _MULTIPLY_(ImplementationType Formula);

    /**
     * 两个表达式之间进行求和运算
     * <p>
     * Quotient between two expressions
     *
     * @param Formula 被运算的表达式
     *                <p>
     *                The expression to be evaluated
     * @return 表达式1 + 表达式2 的结果 返回值还是一个表达式
     * <p>
     * The return value of the result of "Expression 1+Expression 2" is still an expression
     */
    ImplementationType _DIVIDE_(ImplementationType Formula);

    /**
     * @return 该表达式中的所有参与计算的形参，这是一个数组，其中就是所有的形参
     * <p>
     * All the formal parameters involved in the calculation in the expression. This is an array, in which all the formal parameters are
     */
    result[] getCalculatedMember();

    /**
     * 获取到该表达式的结果数据对象，表达式的计算结果
     * <p>
     * Get the result data object of the expression, and the calculation result of the expression
     *
     * @return 表达式的结果
     * <p>
     * the calculation result of the expression
     */
    result getResult();
}
