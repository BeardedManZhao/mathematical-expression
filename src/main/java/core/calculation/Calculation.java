package core.calculation;

/**
 * 计算接口 其中提供不同计算组件针对数学公式的不同实现
 */
public interface Calculation {

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    String formatStr(String string);

    /**
     * @return 计算组件的名称
     */
    String getName();
}
