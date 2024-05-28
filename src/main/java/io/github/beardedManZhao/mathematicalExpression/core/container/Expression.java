package io.github.beardedManZhao.mathematicalExpression.core.container;

import java.io.Serializable;

/**
 * 数学计算表达式类，当我们在计算组件中调用了 compile 函数之后，会将计算表达式存储到当前的计算表达式类中。
 * <p>
 * Mathematical calculation expression class. When we call the compile function in the calculation component, the calculation expression will be stored in the current calculation expression class.
 */
public interface Expression extends Serializable {

    /**
     * 获取到当前计算表达式对象的字符串对象。
     * <p>
     * Get the string object of the current calculation expression object.
     *
     * @return 当前计算表达式的字符串对象！
     * <p>
     * The string object for the current calculation expression!
     */
    String getExpressionStr();

    /**
     * 获取到当前计算组件的名称
     * <p>
     * Obtain the name of the current computing component
     *
     * @return 当前的计算表达式对象的编译者的名称。
     * The name of the compiler of the current computed expression object.
     */
    String getCalculationName();

    /**
     * 获取当前计算表达式是否为 BigDecimal 类型的计算表达式
     * <p>
     * Get whether the current calculation expression is a BigDecimal type calculation expression
     *
     * @return 当前计算表达式是否为 BigDecimal 类型的计算表达式
     * <p>
     * Whether the current calculation expression is a BigDecimal type calculation expression
     */
    boolean isBigDecimal();

    /**
     * 获取缓存的计算结果，如果没有缓存则返回null
     *
     * @return 缓存的计算结果，如果没有缓存则返回null
     */
    CalculationResults getCache();

    /**
     * 将一个计算结果进行缓存
     *
     * @param calculationResults 计算结果
     */
    void setCache(CalculationResults calculationResults);

    /**
     * 将当前计算表达式类的结果计算出来！
     * <p>
     * Calculate the result of the current calculation expression class!
     *
     * @param isCopy 在计算操作进行的时候，如果您期望可以重负使用用此表达式的编译的类的所有数据，您需要在这里设置为 true；
     *               若不设置为 true 则表达式内部的一些栈数据将会消失，这可能导致此表达式的一些操作失去作用。
     *               <p>
     *               When performing a calculation operation, if you expect to be able to load all the data of the compiled class using this expression, you need to set it to true here;
     *               If not set to true, some stack data inside the expression will disappear, which may cause some operations of this expression to become ineffective.
     * @return 当前计算表达式类的结果对象！
     * <p>
     * The result object of the current calculation expression class!
     */
    CalculationResults calculation(boolean isCopy);

    /**
     * 将当前计算表达式类的结果计算出来！
     * <p>
     * Calculate the result of the current calculation expression class!
     *
     * @param isCopy 在计算操作进行的时候，如果您期望可以重负使用用此表达式的编译的类的所有数据，您需要在这里设置为 true；
     *               若不设置为 true 则表达式内部的一些栈数据将会消失，这可能导致此表达式的一些操作失去作用。
     *               <p>
     *               When performing a calculation operation, if you expect to be able to load all the data of the compiled class using this expression, you need to set it to true here;
     *               If not set to true, some stack data inside the expression will disappear, which may cause some operations of this expression to become ineffective.
     * @return 当前计算表达式类的结果对象！
     * <p>
     * The result object of the current calculation expression class!
     */
    CalculationResults calculationBigDecimals(boolean isCopy);


    /**
     * 带有缓存的方式对当前的计算表达式进行计算
     *
     * @param isCopy 在计算操作进行的时候，如果您期望可以重负使用用此表达式的编译的类的所有数据，您需要在这里设置为 true；
     *               若不设置为 true 则表达式内部的一些栈数据将会消失，这可能导致此表达式的一些操作失去作用。
     *               <p>
     *               When performing a calculation operation, if you expect to be able to load all the data of the compiled class using this expression, you need to set it to true here;
     *               If not set to true, some stack data inside the expression will disappear, which may cause some operations of this expression to become ineffective.
     * @return 当前计算表达式类的结果对象！
     * <p>
     * The result object of the current calculation expression class!
     */
    default CalculationResults calculationCache(boolean isCopy) {
        final CalculationResults cache = this.getCache();
        if (cache == null) {
            final CalculationResults calculation = this.calculation(isCopy);
            this.setCache(calculation);
            return calculation;
        }
        return cache;
    }

    /**
     * 带有缓存的方式对当前的计算表达式进行计算
     *
     * @param isCopy 在计算操作进行的时候，如果您期望可以重负使用用此表达式的编译的类的所有数据，您需要在这里设置为 true；
     *               若不设置为 true 则表达式内部的一些栈数据将会消失，这可能导致此表达式的一些操作失去作用。
     *               <p>
     *               When performing a calculation operation, if you expect to be able to load all the data of the compiled class using this expression, you need to set it to true here;
     *               If not set to true, some stack data inside the expression will disappear, which may cause some operations of this expression to become ineffective.
     * @return 当前计算表达式类的结果对象！
     * <p>
     * The result object of the current calculation expression class!
     */
    default CalculationResults calculationBigDecimalsCache(boolean isCopy) {
        final CalculationResults cache = this.getCache();
        if (cache == null) {
            final CalculationResults calculation = this.calculationBigDecimals(isCopy);
            this.setCache(calculation);
            return calculation;
        }
        return cache;
    }

    /**
     * @return 当前表达式是否可用，如果不可用，代表一些函数可能无法调用！
     * <p>
     * Is the current expression available? If not, it means that some functions may not be called!
     */
    @SuppressWarnings("")
    boolean isAvailable();

}
