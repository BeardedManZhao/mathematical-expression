package io.github.beardedManZhao.mathematicalExpression.core.calculation;

/**
 * 共享池计算组件，带有共享池的计算组件需要继承该抽象类
 * <p>
 * A shared pool computing component. A computing component with a shared pool needs to inherit this abstract class
 *
 * @author zhao
 */
public interface SharedCalculation extends Calculation {

    /**
     * @return 该组件是否有启动共享池，一个布尔值，如果返回true代表共享池已经启动
     */
    boolean isStartSharedPool();

    /**
     * 设置共享池开关，共享池是在JavaApi中，1.1版本衍生出来的一种新特性，由于计算量的需求，引入该功能
     * <p>
     * Set the shared pool switch. The shared pool is a new feature derived from version 1.1 in JavaApi. Due to the requirement of computing load, this function is introduced
     *
     * @param startSharedPool 共享池如果设置为true，代表被打开，将会共享检查与
     */
    void setStartSharedPool(boolean startSharedPool);

    /**
     * 是否已经缓存了指定字符串的数据
     *
     * @param name 要检查的字符串
     * @return 如果已经缓存则返回true，否则返回false
     */
    boolean isCache(String name);

    /**
     * 清理缓存数据
     */
    void clearCache();
}
