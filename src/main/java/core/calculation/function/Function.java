package core.calculation.function;

/**
 * 函数计算组件的父类，其本身一定程度上也属于计算组件，因此也会会被管理者所管理，在使用函数的时候需要进行实现与注册
 * <p>
 * The parent class of the function calculation component itself belongs to the calculation component to a certain extent, so it will also be managed by the manager. When using the function, it needs to be implemented and registered
 *
 * @author zhao
 */
public interface Function {
    /**
     * @return 函数的名称，每一个函数都必须要设置一个名称，便于在公式中使用函数
     * <p>
     * The name of a function. Each function must have a name set to facilitate the use of functions in formulas
     */
    String getName();
}
