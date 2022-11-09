package core.formula;

import exceptional.AbnormalOperation;
import utils.NumberUtils;
import utils.StrUtils;

/**
 * 二元数值类型的数学表达式对象
 */
public class BinaryNumberFormula extends BinaryFormula<Double> {

    public BinaryNumberFormula(Double a, Double b, Double resultNumber, int operatorNumber) {
        super(a, b, resultNumber, operatorNumber);
    }

    /**
     * 通过一个表达式的具体参数实例化出来表达式对象
     *
     * @param a              操作数1
     * @param b              操作数2
     * @param operatorNumber 操作符
     * @return 表达式对象
     */
    public static BinaryNumberFormula parse(Double a, Double b, int operatorNumber) {
        return new BinaryNumberFormula(a, b, NumberUtils.calculation(operatorNumber, a, b), operatorNumber);
    }

    /**
     * 通过一个表达式字符串实例化出来表达式对象
     *
     * @param Formula 需要被解析的表达式字符串
     * @return 表达式对象
     */
    public static BinaryNumberFormula parse(String Formula) {
        // 获取到操作符的索引 以及 操作符类型
        int operatorNumber = -1;
        int operatorIndex = -1;
        char[] chars = Formula.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            switch (aChar) {
                case '+':
                    operatorNumber = NumberUtils.ADDITION;
                    operatorIndex = i;
                    break;
                case '-':
                    operatorNumber = NumberUtils.SUBTRACTION;
                    operatorIndex = i;
                    break;
                case '*':
                    operatorNumber = NumberUtils.MULTIPLICATION;
                    operatorIndex = i;
                    break;
                case '/':
                    operatorNumber = NumberUtils.DIVISION;
                    operatorIndex = i;
                    break;
                case '%':
                    operatorNumber = NumberUtils.REMAINDER;
                    operatorIndex = i;
                    break;
            }
        }
        if (NumberUtils.ERROR == operatorNumber) {
            throw new AbnormalOperation("您提供的表达式，无法被我们解析，目前可以解析的表达式如下所示。\n" +
                    "The expression you provided cannot be parsed by us. The expressions that can be parsed are shown below.\n" +
                    "1 + 2    1 - 2    1 * 2    1 / 2    1 % 2 ");
        }
        // 获取到 a 的数值 与 b 的数值，并与操作符编号一起传递给表达式构造中
        return BinaryNumberFormula.parse(
                StrUtils.stringToDouble(Formula.substring(0, operatorIndex)),
                StrUtils.stringToDouble(Formula.substring(operatorIndex + 1)),
                operatorNumber
        );
    }

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
    @Override
    public BinaryFormula<Double> _ADD_(BinaryFormula<Double> Formula) {
        int operatorNumber1 = this.getOperatorNumber();
        int operatorNumber2 = Formula.getOperatorNumber();
        if (operatorNumber1 == NumberUtils.ADDITION && operatorNumber1 == operatorNumber2) {
            return BinaryNumberFormula.parse(
                    this.getA() + Formula.getA(),
                    this.getB() + Formula.getB(),
                    operatorNumber1
            );
        } else {
            throw new AbnormalOperation("操作符不同的两个表达式不允许进行表达式运算，在您进行“表达式1 _ADD_ 表达式2 的时候 要求表达式的操作运算符应为[NumberUtils.ADDITION]”\n" +
                    "\"Two expressions with different operators are not allowed to perform expression operations. When you perform\" Expression 1 _ADD_ Expression 2 \", the operator of the expression is required to be [NumberUtils. ADDITION]\"\n" +
                    "Expression1[" + this + "]\tExpression1[" + Formula + "]");
        }
    }

    /**
     * 两个表达式之间进行求差运算
     * <p>
     * Difference between two expressions
     *
     * @param Formula 被运算的表达式
     *                <p>
     *                The expression to be evaluated
     * @return 表达式1 + 表达式2 的结果 返回值还是一个表达式 这里返回的是表达式的每一个参数进行运算的数值
     * <p>
     * The return value of the result of "Expression 1+Expression 2" is still an expression
     */
    @Override
    public BinaryFormula<Double> _SUB_(BinaryFormula<Double> Formula) {
        int operatorNumber1 = this.getOperatorNumber();
        int operatorNumber2 = Formula.getOperatorNumber();
        if (operatorNumber1 == NumberUtils.SUBTRACTION && operatorNumber1 == operatorNumber2) {
            return BinaryNumberFormula.parse(
                    this.getA() - Formula.getA(),
                    this.getB() - Formula.getB(),
                    operatorNumber1
            );
        } else {
            throw new AbnormalOperation("操作符不同的两个表达式不允许进行表达式运算，在您进行“表达式1 _SUB_ 表达式2 的时候 要求表达式的操作运算符应为[NumberUtils.SUBTRACTION]”\n" +
                    "\"Two expressions with different operators are not allowed to perform expression operations. When you perform\" Expression 1 _SUB_ Expression 2 \", the operator of the expression is required to be [NumberUtils. ADDITION]\"\n" +
                    "Expression1[" + this + "]\tExpression1[" + Formula + "]");
        }
    }

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
    @Override
    public BinaryFormula<Double> _MULTIPLY_(BinaryFormula<Double> Formula) {
        int operatorNumber1 = this.getOperatorNumber();
        int operatorNumber2 = Formula.getOperatorNumber();
        if (operatorNumber1 == NumberUtils.MULTIPLICATION && operatorNumber1 == operatorNumber2) {
            return BinaryNumberFormula.parse(
                    this.getA() * Formula.getA(),
                    this.getB() * Formula.getB(),
                    operatorNumber1
            );
        } else {
            throw new AbnormalOperation("操作符不同的两个表达式不允许进行表达式运算，在您进行“表达式1 _MULTIPLY_ 表达式2 的时候 要求表达式的操作运算符应为[NumberUtils.MULTIPLICATION]”\n" +
                    "\"Two expressions with different operators are not allowed to perform expression operations. When you perform\" Expression 1 _MULTIPLY_ Expression 2 \", the operator of the expression is required to be [NumberUtils. ADDITION]\"\n" +
                    "Expression1[" + this + "]\tExpression1[" + Formula + "]");
        }
    }

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
    @Override
    public BinaryFormula<Double> _DIVIDE_(BinaryFormula<Double> Formula) {
        int operatorNumber1 = this.getOperatorNumber();
        int operatorNumber2 = Formula.getOperatorNumber();
        if (operatorNumber1 == NumberUtils.DIVISION && operatorNumber1 == operatorNumber2) {
            return BinaryNumberFormula.parse(
                    this.getA() / Formula.getA(),
                    this.getB() / Formula.getB(),
                    operatorNumber1
            );
        } else {
            throw new AbnormalOperation("操作符不同的两个表达式不允许进行表达式运算，在您进行“表达式1 _DIVIDE_ 表达式2 的时候 要求表达式的操作运算符应为[NumberUtils.ADDITION]”\n" +
                    "\"Two expressions with different operators are not allowed to perform expression operations. When you perform\" Expression 1 _DIVIDE_ Expression 2 \", the operator of the expression is required to be [NumberUtils. ADDITION]\"\n" +
                    "Expression1[" + this + "]\tExpression1[" + Formula + "]");
        }
    }

    /**
     * @return 该表达式中的所有参与计算的形参，这是一个数组，其中就是所有的形参
     * <p>
     * All the formal parameters involved in the calculation in the expression. This is an array, in which all the formal parameters are
     */
    @Override
    public Double[] getCalculatedMember() {
        return new Double[]{getA(), getB()};
    }

    /**
     * 获取到该表达式的结果数据对象，表达式的计算结果
     * <p>
     * Get the result data object of the expression, and the calculation result of the expression
     *
     * @return 表达式的结果
     * <p>
     * the calculation result of the expression
     */
    @Override
    public Double getResult() {
        return super.getResultNumber();
    }
}
