package core.container;

/**
 * 数值计算结果存储对象，在该类中存储的都是来自计算组件的计算结果，以及运算级别层数等信息
 * <p>
 * Numerical calculation result storage object. In this class, the calculation results from calculation components, as well as the number of operation levels and other information are stored
 *
 * @author zhao
 */
public class CalculationNumberResults implements CalculationResults {
    private final Double[] doubles;
    private final double result;
    private final String source;

    /**
     * @param doubles 所有的括号结果数值 优先级右边最高
     * @param result  已经计算出结果数值的情况下，使用该形参进行赋值
     * @param source  来源，表明该结果对象的计算来源。
     */
    public CalculationNumberResults(Double[] doubles, double result, String source) {
        this.doubles = doubles;
        this.result = result;
        this.source = source;
    }

    @Override
    public int getResultLayers() {
        return this.doubles.length;
    }

    @Override
    public String getCalculationSourceName() {
        return this.source;
    }

    public double getResult() {
        return this.result;
    }

    /**
     * @return 计算时候的聚合过程数值组，会将每一次计算的结果存储在这里，这里的数据具体意义，应在于计算组件对于该数据的存储操作，本类只存储，不计算
     */
    public Double[] getDoubles() {
        return doubles;
    }
}
