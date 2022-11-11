package core.container;

import java.util.ArrayList;

/**
 * 数值计算结果存储对象，在该类中存储的都是来自计算组件的计算结果，以及运算级别层数等信息
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

    /**
     * 初始化好结果数据容器，同时将结果运算出来
     *
     * @param doubles         所有的括号结果数值 优先级右边最高
     * @param OperatorNumbers 每两个括号结果值之间的运算符，符号动态累计计算，其中是一个整数的列表 每一个数值应属于[0,4]区间 代表加减乘除四个运算符 以及取余一个运算符
     * @param source          来源，表明该结果对象的计算来源。
     */
    public CalculationNumberResults(Double[] doubles, ArrayList<Integer> OperatorNumbers, String source) {
        this.doubles = doubles;
        this.source = source;
        double res = doubles[0];
        int offset = 0;
        for (int i = 1; i < doubles.length; ++i) {
            switch (OperatorNumbers.get(offset++)) {
                case 0:
                    res += doubles[i];
                    break;
                case 1:
                    res -= doubles[i];
                    break;
                case 2:
                    res *= doubles[i];
                    break;
                case 3:
                    res /= doubles[i];
                    break;
            }
        }
        this.result = res;
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
