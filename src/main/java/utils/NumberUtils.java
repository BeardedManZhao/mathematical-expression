package utils;

import core.manager.ConstantRegion;
import exceptional.AbnormalOperation;

public final class NumberUtils {

    /**
     * 将一个数值的十倍计算出来
     *
     * @param number 需要被计算的数值
     * @return 数值的十倍数值
     */
    public static int tenfold(int number) {
        return (number << 3) + (number << 1);
    }

    /**
     * 将一个数值的 1/10 倍计算出来
     *
     * @param number 需要被计算的数值
     * @return 数值的 1/10倍
     */
    public static int divideByTen(int number) {
        return (number >> 1) / 5;
    }

    /**
     * 将一个数值乘以10的n次方
     *
     * @param number 需要被做乘法的数值
     * @param n      次方数量
     * @return number * 10的n次方
     */
    public static int PowerOfTen(int number, int n) {
        int res = number;
        for (int i = 1; i < n; i++) {
            res = tenfold(res);
        }
        return res;
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
     * @return s1 是否小于 s2
     */
    public static boolean PriorityComparison(char s1, char s2) {
        return (s1 == ConstantRegion.PLUS_SIGN || s1 == ConstantRegion.MINUS_SIGN) &&
                (s2 == ConstantRegion.MULTIPLICATION_SIGN || s2 == ConstantRegion.DIVISION_SIGN || s2 == ConstantRegion.REMAINDER_SIGN);
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
     */
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
        if (start == end) {
            return start;
        }
        double v = Math.abs(end - start);
        System.out.println(v);
        end = end - v % step;
        return ((start + end) * (v + 1)) / (2 * step);
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
        int res = 1;
        end = end - (end - start) % step;
        while (start <= end) {
            res *= start;
            start += step;
        }
        return res;
    }

    /**
     * 将两个double数组进行合并
     *
     * @param ts1 数组1
     * @param ts2 数组2
     * @return 合并两个数组后的新数组
     */
    public static Double[] merge(Double[] ts1, Double[] ts2) {
        Double[] doubles = new Double[ts1.length + ts2.length];
        if (ts1.length == 0) {
            System.arraycopy(ts2, 0, doubles, 0, ts2.length);
            return doubles;
        } else if (ts2.length == 0) {
            System.arraycopy(ts1, 0, doubles, 0, ts1.length);
            return doubles;
        }
        // 构建双指针进行数据插入
        int P1 = 0;
        int P2 = 0;
        int NP2 = P2 + ts1.length;
        boolean P1BP2 = ts1.length > ts2.length;
        if (P1BP2) {
            // 如果第一个数组较长，这里就以第二个数组为迭代基准
            while (P2 < ts2.length) {
                // 将P2的数据插入到新数组中的指定位置
                doubles[NP2++] = ts2[P2++];
                // 将P1的数据插入到新数组中的指定位置
                doubles[P1] = ts1[P1++];
            }
            // 然后将剩余的一数组数据添加到ts1
            while (P1 < ts1.length) doubles[P1] = ts1[P1++];
        } else {
            // 如果第二个数组较长，这里就以第一个数组为迭代基准
            while (P1 < ts1.length) {
                // 将P2的数据插入到新数组中的指定位置
                doubles[NP2++] = ts2[P2++];
                // 将P1的数据插入到新数组中的指定位置
                doubles[P1] = ts1[P1++];
            }
            // 然后将剩余的二数组数据添加到ts1
            while (P2 < ts2.length) doubles[NP2++] = ts2[P2++];
        }
        return doubles;
    }
}
