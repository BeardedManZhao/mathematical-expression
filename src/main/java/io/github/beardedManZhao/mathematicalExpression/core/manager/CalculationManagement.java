package io.github.beardedManZhao.mathematicalExpression.core.manager;

import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Function;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.FunctionPackage;
import io.github.beardedManZhao.mathematicalExpression.exceptional.ExtractException;

import java.util.HashMap;
import java.util.Set;

/**
 * 计算管理者类，在框架中的所有计算组件既可以复用，也可以进行动态的实例化，获取计算组件与动态注册组件的操作都可以在这里进行。
 * <p>
 * For the computing manager class, all computing components in the framework can be reused or dynamically instantiated. The operations of obtaining computing components and dynamically registering components can be performed here.
 *
 * @author zhao
 */
public final class CalculationManagement {

    /**
     * 被其它组件所依赖的解析无括号数学表达式的组件名称，该组件由于被其它组件所依赖，加载的时候是一定会加载的，因此在进行获取组件的时候，可以直接引用该名称，也可以由您手动设置一个新的组件。
     * <p>
     * The name of a component that is relied on by other components to resolve parenthesis free mathematical expressions. Because this component is relied on by other components, it is bound to be loaded when loading. Therefore, this name can be directly referenced when obtaining components, or you can manually set a new component.
     */
    public static final String PREFIX_EXPRESSION_OPERATION_NAME = "PrefixExpressionOperation_inner";
    public static final String BRACKETS_CALCULATION_2_NAME = "BracketsCalculation2_inner";

    public static final String FUNCTION_FORMULA_CALCULATION_2_NAME = "FunctionFormulaCalculation2_inner";

    private static final HashMap<String, Calculation> STRING_CALCULATION_HASH_MAP = new HashMap<>();
    private static final HashMap<String, Function> STRING_FUNCTION_HASH_MAP = new HashMap<>();

    /**
     * 根据名字，在哈希集合中获取到一个以该名称命名的计算组件
     *
     * @param CalculationName 计算组件的名字
     * @return 计算组件对象
     */
    public static Calculation getCalculationByName(String CalculationName) {
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
        final String calculationName = calculation.getName();
        if (judge && STRING_CALCULATION_HASH_MAP.containsValue(calculation)) {
            return false;
        } else {
            STRING_CALCULATION_HASH_MAP.put(calculationName, calculation);
            return true;
        }
    }

    public static boolean register(Calculation calculation) {
        return register(calculation, false);
    }

    /**
     * 将一个函数组件注册到管理者中，如果注册成功会返回true
     * <p>
     * Register a function component to the administrator, and return true if the registration is successful
     *
     * @param function 需要被注册的函数，这里的对象要求是function的子类实现
     *                 <p>
     *                 Functions that need to be registered. The object here is required to be a subclass implementation of function
     * @return 注册函数是否成功，如果返回false，那么代表函数名称已经被其它函数使用，需要更换函数名称
     * <p>
     * Whether the function is registered successfully. If false is returned, it means that the function name has been used by other functions, and the function name needs to be changed
     */
    public static boolean register(Function function) {
        final String name = function.getName();
        if (STRING_FUNCTION_HASH_MAP.containsKey(name)) {
            return false;
        } else {
            STRING_FUNCTION_HASH_MAP.put(name, function);
            return true;
        }
    }

    /**
     * 将一个函数包注册到管理者中，这将会导致函数包中的所有函数被注册。
     * <p>
     * Registering a function package with the manager will result in all functions in the package being registered.
     *
     * @param functionPackage 包含需要被注册的所有函数的函数包
     *                        <p>
     *                        A function package containing all the functions that need to be registered
     */
    public static void register(FunctionPackage functionPackage) {
        STRING_FUNCTION_HASH_MAP.putAll(functionPackage.getFunctionMap());
    }

    /**
     * 通过函数的名字获取到一个函数的对象
     * <p>
     * Get the object of a function through the name of the function
     *
     * @param FunctionName   需要获取的函数的名字
     *                       <p>
     *                       Name of the function to be obtained
     * @param <functionType> 函数的实现类型，需要注意在这里的实现类型如果与找到的函数不一致，那么这里会抛出"ExtractException"
     *                       <p>
     *                       The implementation type of the function. Note that if the implementation type here is inconsistent with the found function, an "ExtractException" will be thrown here
     * @return 通过函数名字获取到的函数对象
     * <p>
     * Function object obtained by function name
     */
    @SuppressWarnings("unchecked")
    public static <functionType extends Function> functionType getFunctionByName(String FunctionName) {
        final Function function = STRING_FUNCTION_HASH_MAP.get(FunctionName);
        if (function == null) {
            throw new ExtractException("您想要提取的函数似乎没有被注册到管理者中，请您先使用“register”进行函数的注册！\nIt seems that the function you want to extract has not been registered with the manager. Please use \"register\" to register the function first!\nERROR FUNCTION => " + FunctionName);
        } else {
            try {
                return (functionType) function;
            } catch (ClassCastException c) {
                throw new ExtractException("您要提取的函数被找到了，但是它不适用您指定的类型，请在泛型中对该函数的类型进行调整。\nThe function you want to extract has been found, but it does not apply to the type you specified. Please adjust the type of the function in the generic type.\nERROR FUNCTION => " + function.getName());
            }
        }
    }

    /**
     * 判断指定的函数是否存在
     * <p>
     * Determine whether the specified function exists
     *
     * @param FunctionName 指定的函数名字
     *                     <p>
     *                     The specified function name
     * @return 如果存在返回 true
     * <p>
     * If there is, return true
     */
    public static boolean isFunctionExist(String FunctionName) {
        return STRING_FUNCTION_HASH_MAP.containsKey(FunctionName);
    }

    /**
     * 获取到所有已注册的函数的名字
     * <p>
     * Get the names of all registered functions
     *
     * @return 所有已注册的函数的名字的集合
     * <p>
     * A collection of the names of all registered functions
     */
    public static Set<String> getFunctionMap() {
        return STRING_FUNCTION_HASH_MAP.keySet();
    }

    /**
     * 通过函数的名字获取到一个函数的对象
     * <p>
     * Get the object of a function through the name of the function
     *
     * @param FunctionName   需要获取的函数的名字
     *                       <p>
     *                       Name of the function to be obtained
     * @param <functionType> 函数的实现类型，需要注意在这里的实现类型如果与找到的函数不一致，那么这里会抛出"ExtractException"
     *                       <p>
     *                       The implementation type of the function. Note that if the implementation type here is inconsistent with the found function, an "ExtractException" will be thrown here
     * @return 通过函数名字获取到的函数对象
     * <p>
     * Function object obtained by function name
     */
    @SuppressWarnings("unchecked")
    public static <functionType extends Function> functionType getFunctionByNameNoCheck(String FunctionName) {
        return (functionType) STRING_FUNCTION_HASH_MAP.get(FunctionName);
    }

    /**
     * 取消一个函数的注册，将一个函数注销掉，与之相关的所有信息将会被清除
     * <p>
     * Cancel the registration of a function and log off a function. All information related to it will be cleared
     *
     * @param FunctionName 需要注销的函数名字
     *                     <p>
     *                     Function name to be unregistered
     * @return 注销是否成功，如果返回false 代表需要注销的函数不存在
     * <p>
     * Whether the logout is successful. If false is returned, the function to logout does not exist
     */
    public static boolean unregisterF(String FunctionName) {
        return STRING_FUNCTION_HASH_MAP.remove(FunctionName) != null;
    }

    /**
     * 将一个计算组件取消注册，如果取消注册成功会返回false 请注意，不建议注销所有被依赖的组件
     * Cancel the registration of a computing component. If the registration is cancelled successfully, false will be returned. Please note that it is not recommended unregistering all dependent components
     *
     * @param CalculationName 需要取消注册的计算组件的名称
     *                        <p>
     *                        The name of the calculation component that needs to be unregistered
     * @return 如果注销成功返回true
     * <p>
     * Returns true if the logout is successful
     */
    public static boolean unregister(String CalculationName) {
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
