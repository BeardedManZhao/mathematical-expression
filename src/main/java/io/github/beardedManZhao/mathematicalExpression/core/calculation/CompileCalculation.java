package io.github.beardedManZhao.mathematicalExpression.core.calculation;

import io.github.beardedManZhao.mathematicalExpression.core.container.PrefixExpression;

/**
 * 带有编译功能的所有数学表达式对象都实现了此接口，编译功能的灵活度很高，能够实现强大的表达式对象获取的功能！
 * <p>
 * All mathematical expression objects with compilation function have implemented this interface, and the flexibility of compilation function is high, which can achieve powerful function of obtaining expression objects!
 *
 * @author zhao
 */
public interface CompileCalculation {

    /**
     * 将一个数学表达式编译为对象，这有助于您更好地使用数学表达式。
     * <p>
     * Compile a mathematical expression into an object, which helps you better use mathematical expressions.
     *
     * @param Formula        被计算的表达式，要求返回值是一个数值。
     *                       <p>
     *                       The returned value of the evaluated expression is required to be a numeric value.
     * @param formatRequired 是否需要被格式化，用于确保公式格式正确。
     *                       <p>
     *                       Whether it needs to be formatted to ensure that the formula format is correct.
     * @return 数学表达式的编译对象。您可以使用编译对象进行一系列操作。
     * <p>
     * The compilation object of mathematical expressions. You can use compiled objects to perform a series of operations.
     */
    PrefixExpression compile(String Formula, boolean formatRequired);

    /**
     * 将一个数学表达式编译为对象，这有助于您更好地使用数学表达式。
     * <p>
     * Compile a mathematical expression into an object, which helps you better use mathematical expressions.
     *
     * @param Formula        被计算的表达式，要求返回值是一个数值。
     *                       <p>
     *                       The returned value of the evaluated expression is required to be a numeric value.
     * @param formatRequired 是否需要被格式化，用于确保公式格式正确。
     *                       <p>
     *                       Whether it needs to be formatted to ensure that the formula format is correct.
     * @return 数学表达式的编译对象。您可以使用编译对象进行一系列操作。
     * <p>
     * The compilation object of mathematical expressions. You can use compiled objects to perform a series of operations.
     */
    PrefixExpression compileBigDecimal(String Formula, boolean formatRequired);
}
