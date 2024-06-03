package io.github.beardedManZhao.mathematicalExpression.core.container;

/**
 * 带有深层编译的表达式对象，通常情况下，当我们的表达式对象需要1次以上的编译才可以运行的情况下，其就需要实现此类！
 * <p>
 * Expression objects with deep compilation typically need to be implemented when our expression object requires more than one compilation to run!
 *
 * @author zhao
 */
public abstract class SubCompileExpression extends NameExpression {

    public SubCompileExpression(String expression, String calculationName) {
        super(expression, calculationName);
    }

    /**
     * 开始进行深层编译操作，经过深层编译后返回的表达式对象将会被直接做为运行对象。
     * <p>
     * Start the deep compilation operation, and the expression object returned after deep compilation will be directly used as the runtime object.
     *
     * @param isCopy 在计算操作进行的时候，如果您期望可以重负使用用此表达式的编译的类的所有数据，您需要在这里设置为 true；
     *               若不设置为 true 则表达式内部的一些栈数据将会消失，这可能导致此表达式的一些操作失去作用。
     *               <p>
     *               When performing a calculation operation, if you expect to be able to load all the data of the compiled class using this expression, you need to set it to true here;
     *               If not set to true, some stack data inside the expression will disappear, which may cause some operations of this expression to become ineffective.
     * @return 经过深层编译后的表达式对象。
     * <p>
     * The expression object after deep compilation.
     */
    public abstract NameExpression subCompile(boolean isCopy);

    /**
     * 开始进行深层编译操作，经过深层编译后返回的表达式对象将会被直接做为运行对象。
     * <p>
     * Start the deep compilation operation, and the expression object returned after deep compilation will be directly used as the runtime object.
     *
     * @param isCopy 在计算操作进行的时候，如果您期望可以重负使用用此表达式的编译的类的所有数据，您需要在这里设置为 true；
     *               若不设置为 true 则表达式内部的一些栈数据将会消失，这可能导致此表达式的一些操作失去作用。
     *               <p>
     *               When performing a calculation operation, if you expect to be able to load all the data of the compiled class using this expression, you need to set it to true here;
     *               If not set to true, some stack data inside the expression will disappear, which may cause some operations of this expression to become ineffective.
     * @return 经过深层编译后的表达式对象。
     * <p>
     * The expression object after deep compilation.
     */
    public abstract NameExpression subCompileBigDecimals(boolean isCopy);

    @Override
    public CalculationResults calculation(boolean isCopy) {
        return this.subCompile(isCopy).calculationCache(isCopy);
    }

    @Override
    public CalculationResults calculationBigDecimals(boolean isCopy) {
        return this.subCompileBigDecimals(isCopy).calculationBigDecimalsCache(isCopy);
    }
}
