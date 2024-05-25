package io.github.beardedManZhao.mathematicalExpression.core.container;

/**
 * 带有名字的计算表达式对象
 *
 * @author zhao
 */
public abstract class NameExpression implements Expression {


    private final String Expression, calculationName;

    private boolean available = true;

    private CalculationResults cache;

    public NameExpression(String expression, String calculationName) {
        this.Expression = expression;
        this.calculationName = calculationName;
    }

    @Override
    public String getExpressionStr() {
        return this.Expression;
    }

    @Override
    public String getCalculationName() {
        return this.calculationName;
    }

    @Override
    public CalculationResults getCache() {
        return this.cache;
    }

    @Override
    public void setCache(CalculationResults calculationResults) {
        this.cache = calculationResults;
    }


    @Override
    public boolean isAvailable() {
        return available;
    }

    /**
     * 关闭可用状态！
     */
    protected void closeAvailable() {
        this.available = false;
    }

    @Override
    public CalculationResults calculationCache(boolean isCopy) {
        final CalculationResults calculationResults = Expression.super.calculationCache(isCopy);
        if (!isCopy) {
            this.closeAvailable();
        }
        return calculationResults;
    }

    @Override
    public CalculationResults calculationBigDecimalsCache(boolean isCopy) {
        final CalculationResults calculationResults = Expression.super.calculationBigDecimalsCache(isCopy);
        if (!isCopy) {
            this.closeAvailable();
        }
        return calculationResults;
    }
}
