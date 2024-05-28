package io.github.beardedManZhao.mathematicalExpression.core.container;

/**
 * 包装表达式对象，用于将一个表达式对象进行包装，若有修改外部表达式对象中一部分数据需求的情况下，完全可以直接继承此类，因为此类的构造函数做了一个优化，来允许您传递一个表达式对象。
 * <p>
 * Wrap expression object, used to wrap an expression object. If there is a need to modify some data in the external expression object, it can be directly inherited from this class because the constructor of this class has been optimized to allow you to pass an expression object.
 *
 * @author zhao
 */
public class PackExpression extends NameExpression {

    private final NameExpression nameExpression;

    public PackExpression(NameExpression nameExpression, String packName) {
        super(nameExpression.getExpressionStr(), packName + ":{" + nameExpression.getCalculationName() + '}');
        this.nameExpression = nameExpression;
    }

    @Override
    public boolean isBigDecimal() {
        return nameExpression.isBigDecimal();
    }

    @Override
    public CalculationResults calculation(boolean isCopy) {
        final CalculationResults calculationResults = nameExpression.calculationCache(isCopy);
        calculationResults.setSource(this.getCalculationName());
        return calculationResults;
    }

    @Override
    public CalculationResults calculationBigDecimals(boolean isCopy) {
        final CalculationResults calculationResults = nameExpression.calculationBigDecimalsCache(isCopy);
        calculationResults.setSource(this.getCalculationName());
        return calculationResults;
    }

    /**
     * 将当前的包装对象还原成原来的表达式对象
     * <p>
     * Restore the current packaging object to the original expression object
     *
     * @return Restore the current packaging object to the original expression object
     */
    public NameExpression dismantling() {
        return this.nameExpression;
    }
}
