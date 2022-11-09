package core.container;

/**
 * 计算结果接口，其中一般用来存储有关计算结果的数据，结果数据不一定是一个数值，具体的操作请查阅子类实现
 */
public interface CalculationResults {
    /**
     * 计算结果中是经历了很多层计算才获得到的，这个函数可以获取到计算结果的层数量，也可以说是计算结果的计算次数。
     *
     * @return 计算结果的计算次数
     */
    int getResultLayers();

    /**
     * 结果是被计算组件计算出来的，在这里可以查询到计算该结果的组件名称，需要注意的是，这里返回的名称不会随着管理者中注册表的更改而变化
     *
     * @return 计算结果的来源
     */
    String CalculationSource();
}
