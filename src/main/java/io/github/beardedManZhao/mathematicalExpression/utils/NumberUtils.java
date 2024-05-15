package io.github.beardedManZhao.mathematicalExpression.utils;

import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;
import io.github.beardedManZhao.mathematicalExpression.exceptional.AbnormalOperation;

/**
 * 数值工具类
 */
public final class NumberUtils {

    /**
     * 将一个数值的 1/10 倍计算出来
     *
     * @param number 需要被计算的数值
     * @return 数值的 1/10倍
     * @deprecated 项目中未使用过的函数，未来可能会移除
     */
    @Deprecated
    public static int divideByTen(int number) {
        return (number >> 1) / 5;
    }

    /**
     * 计算一个数值的阶乘
     *
     * @param number 运算数值
     * @return 计算出来的结果
     */
    public static double factorial(double number) {
        return number <= 1 ? number : number * factorial(number - 1);
    }

    /**
     * 计算一个数值的结果
     *
     * @param CalculationType 计算类型
     * @param an              运算数值1
     * @param bn              运算数值2
     * @return 运算结果
     */
    public static double calculation(char CalculationType, double an, double bn) {
        switch (CalculationType) {
            case ConstantRegion.PLUS_SIGN:
                return an + bn;
            case ConstantRegion.MINUS_SIGN:
                return an - bn;
            case ConstantRegion.MULTIPLICATION_SIGN:
                return an * bn;
            case ConstantRegion.DIVISION_SIGN:
                return an / bn;
            case ConstantRegion.REMAINDER_SIGN:
                return an % bn;
            case ConstantRegion.POW_SIGN:
                return Math.pow(an, bn);
            default:
                throw new AbnormalOperation("操作数计算异常，您的计算模式不存在，错误的计算模式 = [" + CalculationType + "]\n" +
                        "Operand calculation exception. Your calculation mode does not exist. Wrong calculation mode = [" + CalculationType + "]");
        }
    }

    /**
     * 将两个操作符级别进行比较
     *
     * @param s1 操作符1
     * @param s2 操作符2
     * @return s1操作符的优先级 是否小于 s2操作符的优先级
     */
    public static boolean PriorityComparison(char s1, char s2) {
        switch (s1) {
            case ConstantRegion.PLUS_SIGN:
            case ConstantRegion.MINUS_SIGN:
                switch (s2) {
                    case ConstantRegion.MULTIPLICATION_SIGN:
                    case ConstantRegion.DIVISION_SIGN:
                    case ConstantRegion.REMAINDER_SIGN:
                    case ConstantRegion.POW_SIGN:
                        return true;
                }
                break;
        }
        return false;
    }

    /**
     * 将两个数值进行比较
     *
     * @param ComparisonOperator 两个数值的比较运算符
     * @param left               左值
     * @param right              右值
     * @return 左值 与 右值 之间是否符合比较运算符的关系
     * <p>
     * Whether the left value and right value conform to the comparison operator
     */
    public static boolean ComparisonOperation(String ComparisonOperator, double left, double right) {
        switch (ComparisonOperator) {
            case ConstantRegion.LESS_THAN_SIGN:
                return left < right;
            case ConstantRegion.GREATER_THAN_SIGN:
                return left > right;
            case ConstantRegion.LESS_THAN_OR_EQUAL_TO_SIGN:
                return left <= right;
            case ConstantRegion.GREATER_THAN_OR_EQUAL_TO_SIGN:
                return left >= right;
            case ConstantRegion.EQUAL_SIGN1:
            case ConstantRegion.EQUAL_SIGN2:
                return left == right;
            case ConstantRegion.NOT_EQUAL_SIGN1:
            case ConstantRegion.NOT_EQUAL_SIGN2:
                return left != right;
            default:
                throw new AbnormalOperation("无法进行比较运算，因为有错误的运算符。\n" +
                        "The comparison operation cannot be performed because there is an incorrect operator.\n" +
                        "Bad comparison operator => " + ComparisonOperator);
        }
    }

    /**
     * 将区间内的所有数值进行累加
     *
     * @param start 区间起始数值
     * @param end   区间终止数值
     * @return 区间内所有数值的累加结果
     * @deprecated 项目中未使用过的函数，未来可能会移除
     */
    @Deprecated
    public static int sumOfRange(int start, int end) {
        if (start == end) {
            return start;
        }
        return ((start + end) * ((end - start) + 1)) >> 1;
    }

    /**
     * 将区间内的所有数值进行累加
     *
     * @param start 区间起始数值
     * @param end   区间终止数值
     * @return 区间内所有数值的累加结果
     */
    public static double sumOfRange(double start, double end) {
        if (start == end) {
            return start;
        }
        return (start + end) * (Math.abs(end - start) + 1) / 2;
    }

    /**
     * 带有步长的方式计算一个区间内所有数值的累加
     *
     * @param start 区间起始数值
     * @param end   区间终止数值
     * @param step  区间内每一个元素之间的步长
     * @return 区间内元素之和
     */
    public static double sumOfRange(double start, double end, double step) {
        if (step == 1) return sumOfRange(start, end);
        if (start == end) {
            return start;
        }
        double abs = Math.abs(end - start);
        end -= abs % step;
        abs = Math.abs(end - start);
        double n = 1 + (abs / step);
        return n * start + n * (n - 1) * (Math.max(step, 2)) / 2;
    }

    /**
     * 带有步长的方式计算一个区间内所有数值的累乘
     *
     * @param start 区间的起始数值
     * @param end   区间的终止数值
     * @param step  区间内每一个元素之间的等差
     * @return 区间内所有元素的累乘结果
     */
    public static double MultiplyOfRange(double start, double end, double step) {
        if (start == end) {
            return start;
        }
        double res = 1;
        end = end - (end - start) % step;
        while (start <= end) {
            res *= start;
            start += step;
        }
        return res;
    }
}
