package core.container;

/**
 * 数值计算结果存储对象，在该类中存储的都是来自计算组件的计算结果，以及运算级别层数等信息
 * <p>
 * Numerical calculation result storage object. In this class, the calculation results from calculation components, as well as the number of operation levels and other information are stored
 *
 * @author zhao
 */
public class CalculationBooleanResults implements CalculationResults {
    private final boolean result;
    private final String source;
    private final int Layers;
    private final double left;
    private final double right;

    /**
     * @param result 已经计算出结果数值的情况下，使用该形参进行赋值
     * @param source 来源，表明该结果对象的计算来源。
     * @param layers 计算层次
     * @param left   计算左值
     * @param right  计算右值
     */
    public CalculationBooleanResults(boolean result, String source, int layers, double left, double right) {
        this.result = result;
        this.source = source;
        Layers = layers;
        this.left = left;
        this.right = right;
    }

    @Override
    public int getResultLayers() {
        return this.Layers;
    }

    @Override
    public String getCalculationSourceName() {
        return this.source;
    }

    public boolean getResult() {
        return this.result;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }
}
