package core;

import core.calculation.function.Function;
import core.calculation.number.NumberCalculation;
import core.manager.CalculationManagement;
import exceptional.ExtractException;

/**
 * 数学表达式解析库的门户类，在该类中能够直接获取到需要的计算组件并进行相对应的函数注册操作。
 * <p>
 * The portal class of the mathematical expression parsing library, in which the required calculation components can be directly obtained and corresponding function registration operations can be performed.
 *
 * @author 赵凌宇
 * 2023/2/20 15:30
 */
public enum Mathematical_Expression {

    bracketsCalculation2, cumulativeCalculation,
    fastMultiplyOfIntervalsBrackets, fastSumOfIntervalsBrackets,
    functionFormulaCalculation, functionFormulaCalculation2,
    prefixExpressionOperation;

    public static NumberCalculation getInstance(Mathematical_Expression calculation) {
        return getInstance(calculation, calculation.toString());
    }

    public static boolean register_function(Function function) {
        return CalculationManagement.register(function);
    }

    public static boolean unregister_function(String functionName) {
        return CalculationManagement.unregisterF(functionName);
    }

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
    public static NumberCalculation getInstance(Mathematical_Expression calculation, String calculationName) {
        switch (calculation) {
            case bracketsCalculation2:
                return core.calculation.number.BracketsCalculation2.getInstance(calculationName);
            case cumulativeCalculation:
                return core.calculation.number.CumulativeCalculation.getInstance(calculationName);
            case fastMultiplyOfIntervalsBrackets:
                return core.calculation.number.FastMultiplyOfIntervalsBrackets.getInstance(calculationName);
            case fastSumOfIntervalsBrackets:
                return core.calculation.number.FastSumOfIntervalsBrackets.getInstance(calculationName);
            case functionFormulaCalculation:
                return core.calculation.number.FunctionFormulaCalculation.getInstance(calculationName);
            case functionFormulaCalculation2:
                return core.calculation.number.FunctionFormulaCalculation2.getInstance(calculationName);
            case prefixExpressionOperation:
                return core.calculation.number.PrefixExpressionOperation.getInstance(calculationName);
            default:
                throw new ExtractException("UNKnow " + calculation);
        }
    }
}
