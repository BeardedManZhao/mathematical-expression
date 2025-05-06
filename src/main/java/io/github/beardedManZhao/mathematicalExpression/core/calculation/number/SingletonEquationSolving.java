package io.github.beardedManZhao.mathematicalExpression.core.calculation.number;

import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.CompileCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.CalculationNumberResults;
import io.github.beardedManZhao.mathematicalExpression.core.container.EquationSolver;
import io.github.beardedManZhao.mathematicalExpression.core.container.NameExpression;
import io.github.beardedManZhao.mathematicalExpression.core.manager.CalculationManagement;
import io.github.beardedManZhao.mathematicalExpression.exceptional.ExtractException;

/**
 * 求解数学表达式
 *
 * @author 赵凌宇
 */
public class SingletonEquationSolving extends NumberCalculation implements CompileCalculation {


    protected SingletonEquationSolving(String name) {
        super(name);
    }

    /**
     * 从管理者中获取到指定名称的计算组件，注意 如果管理者中不存在该名称的组件，那么该组件就会自动的注册
     * <p>
     * Get the calculation component with the specified name from the manager. Note that if there is no component with this name in the manager, the component will be registered automatically
     *
     * @param CalculationName 组件的名字
     * @return 解析括号类计算公式的计算组件
     */
    public static SingletonEquationSolving getInstance(String CalculationName) {
        if (CalculationManagement.isRegister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof SingletonEquationSolving) {
                return (SingletonEquationSolving) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于  DynamicEquationSolver 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            SingletonEquationSolving singletonEquationSolving = new SingletonEquationSolving(CalculationName);
            CalculationManagement.register(singletonEquationSolving, false);
            return singletonEquationSolving;
        }
    }

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
    @Override
    public EquationSolver compile(String Formula, boolean formatRequired) {
        return EquationSolver.compile(Formula, this.getName());
    }

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
    @Override
    public NameExpression compileBigDecimal(String Formula, boolean formatRequired) {
        throw new UnsupportedOperationException("不支持BigDecimal运算");
    }

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算
     * <p>
     * Format a formula so that it can be calculated by the calculation component
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    @Override
    public String formatStr(String string) {
        return string;
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
        return this.compile(Formula, formatRequired).calculation(false);
    }
}
