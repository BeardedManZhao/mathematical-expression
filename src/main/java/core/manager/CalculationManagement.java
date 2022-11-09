package core.manager;

import core.calculation.Calculation;

import java.util.HashMap;

/**
 * 计算管理者类
 */
public final class CalculationManagement {
    public static final String PREFIX_EXPRESSION_OPERATION_NAME = "PrefixExpressionOperation";
    private static final HashMap<String, Calculation> STRING_CALCULATION_HASH_MAP = new HashMap<>();

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
     *
     * @param calculation 需要被注册的计算组件
     * @return 是否注册成功，如果注册失败，意味着在管理者中很肯恩和已经存在一个该名称的数值了，所以不允许再一次注册。
     */
    public static boolean register(Calculation calculation) {
        if (STRING_CALCULATION_HASH_MAP.containsValue(calculation)) {
            return false;
        } else {
            return STRING_CALCULATION_HASH_MAP.put(calculation.getName(), calculation) == null;
        }
    }

    public static boolean unregister(String CalculationName) {
        return STRING_CALCULATION_HASH_MAP.remove(CalculationName) != null;
    }

    /**
     * 判断某个计算组件是否被成功注册
     *
     * @param Name 被判断的组件
     * @return 注册情况 true代表已经注册
     */
    public static boolean isregister(String Name) {
        return STRING_CALCULATION_HASH_MAP.containsKey(Name);
    }
}
