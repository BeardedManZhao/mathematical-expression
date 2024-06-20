package io.github.beardedManZhao.mathematicalExpression.core.container;


import io.github.beardedManZhao.algorithmStar.operands.ComplexNumber;

/**
 * 数值计算结果存储对象，在该类中存储的都是来自计算组件的计算结果，以及运算级别层数等信息
 * <p>
 * Numerical calculation result storage object. In this class, the calculation results from calculation components, as well as the number of operation levels and other information are stored
 *
 * @author zhao
 */
public class CalculationComplexResults extends CalculationNumberResults {

    private final ComplexNumber complexNumber;

    /**
     * @param layers  结果的计算层数
     *                <p>
     *                Calculation level of results
     * @param result1 复数的实部
     *                <p>
     *                The real part of a complex number
     * @param result2 复数的虚部
     *                <p>
     *                The imaginary part of complex numbers
     * @param source  来源，表明该结果对象的计算来源。
     *                <p>
     *                Source indicates the calculation source of the result object.
     */
    public CalculationComplexResults(int layers, double result1, double result2, String source) {
        super(layers, result1, source);
        this.complexNumber = ComplexNumber.parse(result1, result2);
    }

    /**
     * 转换为复数对象!
     *
     * @return 当前表达式对被转换玩的复数对象 此函数返回的对象可以进行计算！
     */
    public ComplexNumber toComplexNumber() {
        return complexNumber;
    }

    @Override
    public String toString() {
        return this.complexNumber.toString();
    }
}
