package core.manager;

import core.calculation.Calculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;

/**
 * 计算管理者类，在框架中的所有计算组件既可以复用，也可以进行动态的实例化，获取计算组件与动态注册组件的操作都可以在这里进行。
 * <p>
 * For the computing manager class, all computing components in the framework can be reused or dynamically instantiated. The operations of obtaining computing components and dynamically registering components can be performed here.
 *
 * @author zhao
 */
public final class CalculationManagement {


    public static final Logger LOGGER;

    /**
     * 被其它组件所依赖的解析无括号数学表达式的组件名称，该组件由于被其它组件所依赖，加载的时候是一定会加载的，因此在进行获取组件的时候，可以直接引用该名称，也可以由您手动设置一个新的组件。
     * <p>
     * The name of a component that is relied on by other components to resolve parenthesis free mathematical expressions. Because this component is relied on by other components, it is bound to be loaded when loading. Therefore, this name can be directly referenced when obtaining components, or you can manually set a new component.
     */
    public static final String PREFIX_EXPRESSION_OPERATION_NAME = "PrefixExpressionOperation";
    public static final String BRACKETS_CALCULATION_2_NAME = "BracketsCalculation2";
    private static final HashMap<String, Calculation> STRING_CALCULATION_HASH_MAP = new HashMap<>();

    static {
        LOGGER = LoggerFactory.getLogger("Calculation Management");
        LOGGER.info("+=============== Welcome to [mathematical expression] ===============+");
        LOGGER.info("+ \tStart time " + new Date());
        LOGGER.info("+ \tCalculation component manager initialized successfully");
        LOGGER.info("+--------------------------------------------------------------------+");
    }

    /**
     * 根据名字，在哈希集合中获取到一个以该名称命名的计算组件
     *
     * @param CalculationName 计算组件的名字
     * @return 计算组件对象
     */
    public static Calculation getCalculationByName(String CalculationName) {
        LOGGER.info("Get the [" + CalculationName + "] component from the manager");
        return STRING_CALCULATION_HASH_MAP.get(CalculationName);
    }


    /**
     * 将一个计算组件注册到管理者中，如果注册成功会返回true，不允许重复注册
     * <p>
     * Register a calculation component to the administrator. If the registration is successful, true will be returned. Repeated registration is not allowed
     *
     * @param calculation 需要被注册的计算组件
     *                    <p>
     *                    Computing components to be registered
     * @param judge       如果设置为true代表需要检查判断情况 如果设置为false 代表直接注册
     * @return 如果注册成功返回true，如果注册失败，意味着在管理者中很肯恩和已经存在一个该名称的数值了，所以不允许再一次注册。
     * <p>
     * If the registration succeeds, it returns true. If the registration fails, it means that there is already a numerical value of the name in the manager, so it is not allowed to register again.
     */
    public static boolean register(Calculation calculation, boolean judge) {
        String calculationName = calculation.getName();
        if (judge && STRING_CALCULATION_HASH_MAP.containsValue(calculation)) {
            LOGGER.warn("An error occurred while registering the component, because the [" + calculationName + "] component has already been registered");
            return false;
        } else {
            LOGGER.info("A computing component is registered " + calculationName);
            return STRING_CALCULATION_HASH_MAP.put(calculationName, calculation) == null;
        }
    }

    /**
     * 将一个计算组件取消注册，如果取消注册成功会返回false 请注意，不建议注销所有被依赖的组件
     * Cancel the registration of a computing component. If the registration is cancelled successfully, false will be returned. Please note that it is not recommended to unregister all dependent components
     *
     * @param CalculationName 需要取消注册的计算组件的名称
     *                        <p>
     *                        The name of the calculation component that needs to be unregistered
     * @return 如果注销成功返回true
     * <p>
     * Returns true if the logout is successful
     */
    public static boolean unregister(String CalculationName) {
        LOGGER.info("Log off the [" + CalculationName + "] component from the manager");
        return STRING_CALCULATION_HASH_MAP.remove(CalculationName) != null;
    }

    /**
     * 判断某个计算组件是否被成功注册，因为相同的组件名称只能注册一个组件，所以在这里的函数可以帮助您避免这种覆盖组件的情况
     * <p>
     * Judge whether a computing component has been successfully registered. Because only one component can be registered with the same component name, the functions here can help you avoid this case of overwriting components
     *
     * @param Name 被判断的组件
     *             <p>
     *             Judged components
     * @return 如果组件名称已经被注册过了，该函数返回true
     * <p>
     * If the component name has been registered, this function returns true
     */
    public static boolean isRegister(String Name) {
        return STRING_CALCULATION_HASH_MAP.containsKey(Name);
    }
}
