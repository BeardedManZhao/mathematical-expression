package core;

import core.calculation.Calculation;
import core.calculation.function.Function;
import core.calculation.function.FunctionPackage;
import core.manager.CalculationManagement;

/**
 * 数学表达式解析库的门户类，在该类中能够直接获取到需要的计算组件并进行相对应的函数注册操作。
 * <p>
 * The portal class of the mathematical expression parsing library, in which the required calculation components can be directly obtained and corresponding function registration operations can be performed.
 *
 * @author 赵凌宇
 * 2023/2/20 15:30
 */
public enum Mathematical_Expression {

    bracketsCalculation2 {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.BracketsCalculation2.getInstance(calculationName);
        }
    }, cumulativeCalculation {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.CumulativeCalculation.getInstance(calculationName);
        }
    },
    fastMultiplyOfIntervalsBrackets {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.FastMultiplyOfIntervalsBrackets.getInstance(calculationName);
        }
    }, fastSumOfIntervalsBrackets {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.FastSumOfIntervalsBrackets.getInstance(calculationName);
        }
    },
    functionFormulaCalculation {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.FunctionFormulaCalculation.getInstance(calculationName);
        }
    }, functionFormulaCalculation2 {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.FunctionFormulaCalculation2.getInstance(calculationName);
        }
    },
    prefixExpressionOperation {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.PrefixExpressionOperation.getInstance(calculationName);
        }
    }, booleanCalculation2 {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.bool.BooleanCalculation2.getInstance(calculationName);
        }
    };

    /**
     * 使用枚举的方式获取到一个计算组件对象。
     * <p>
     * Obtain a calculated component object by enumerating.
     *
     * @param calculation 计算组件对象的类型。您可以直接传递枚举累中的各个选项选择出您需要使用的计算组件。
     *                    <p>
     *                    Calculate the type of component object. You can directly pass the options in the enumeration to select the calculation components you need to use.
     * @return 计算组件对象。
     * <p>
     * Calculate Component Object.
     */
    public static Calculation getInstance(Mathematical_Expression calculation) {
        return getInstance(calculation, calculation.toString());
    }

    /**
     * 注册一个函数到函数库中，使得所有需要使用函数计算的组件都可以获取到函数对象的数据类型。
     * <p>
     * Register a function into the function library, so that all components that need to use the function calculation can obtain the data type of the function object.
     *
     * @param function 函数实现类所示例化出来的对象。
     *                 <p>
     *                 The object instantiated by the function implementation class.
     * @return 如果返回true 则代表函数注册操作成功!!!
     * <p>
     * If true is returned, the function registration operation is successful!!!
     */
    public static boolean register_function(Function function) {
        return CalculationManagement.register(function);
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
    public static void register_function(FunctionPackage functionPackage) {
        CalculationManagement.register(functionPackage);
    }

    /**
     * 将一个已经注册的函数从函数库中注销注册，释放其所占用的内存空间。
     * <p>
     * Unregister a registered function from the function library to free the memory space it occupies.
     *
     * @param functionName 函数的名称。
     *                     <p>
     *                     The name of the function.
     * @return 如果返回 true 代表函数注销的操作执行成功!!!
     * <p>
     * If true is returned, the function logout operation is successful!!!
     */
    public static boolean unregister_function(String functionName) {
        return CalculationManagement.unregisterF(functionName);
    }

    /**
     * 将一个已经注册的函数从函数库中注销注册，释放其所占用的内存空间。
     * <p>
     * Unregister a registered function from the function library to free the memory space it occupies.
     *
     * @param function 函数的实现类对象。
     *                 <p>
     *                 The implementation class object of the function.
     * @return 如果返回 true 代表函数注销的操作执行成功!!!
     * <p>
     * If true is returned, the function logout operation is successful!!!
     */
    public static boolean unregister_function(Function function) {
        return unregister_function(function.getName());
    }

    /**
     * 传递一个名称，获取到一个计算组件，您不需要调用计算组件的 getInstance 函数。
     * <p>
     * Pass a name to get a calculation component. You don't need to call the getInstance function of the calculation component。
     *
     * @param calculation     计算组件的类型
     * @param calculationName 计算组件的名称.
     *                        <p>
     *                        Name of calculation component.
     * @return 计算组件对象。
     * <p>
     * Calculate component objects.
     */
    public static Calculation getInstance(Mathematical_Expression calculation, String calculationName) {
        return calculation.getInstance(calculationName);
    }

    /**
     * 获取到一个指定名称的计算组件。
     *
     * @param calculationName 计算组件的名称，该参数将会被用于内存地址中的编号映射，能够通过名称获取到指定的实例化号的计算组件对象。
     * @return 计算组件对象
     */
    public abstract Calculation getInstance(String calculationName);
}
