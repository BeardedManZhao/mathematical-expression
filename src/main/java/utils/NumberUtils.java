package utils;

import exceptional.AbnormalOperation;

import java.util.HashMap;

public final class NumberUtils {

    public static final short ADDITION = 0;
    public static final short SUBTRACTION = 1;
    public static final short MULTIPLICATION = 2;
    public static final short DIVISION = 3;
    public static final short REMAINDER = 4;
    public static final short ERROR = -1;
    public static final HashMap<Character, Short> OPERATOR_SET = new HashMap<>(10);

    static {
        OPERATOR_SET.put('+', ADDITION);
        OPERATOR_SET.put('-', SUBTRACTION);
        OPERATOR_SET.put('*', MULTIPLICATION);
        OPERATOR_SET.put('/', DIVISION);
        OPERATOR_SET.put('%', REMAINDER);
    }

    /**
     * 将一个数值的十倍计算出来
     *
     * @param number 需要被计算的数值
     * @return 数值的十倍数值
     */
    public static int tenfold(int number) {
        return ((number << 2) << 1) + (number << 1);
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
     * @param number 需要被做乘法的数值
     * @param n 次方数量
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
    public static double calculation(int CalculationType, double an, double bn) {
        switch (CalculationType) {
            case ADDITION:
                return an + bn;
            case SUBTRACTION:
                return an - bn;
            case MULTIPLICATION:
                return an * bn;
            case DIVISION:
                return an / bn;
            case REMAINDER:
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
    public static boolean PriorityComparison(short s1, short s2) {
        return (s1 == 0 || s1 == 1) && (s2 == 2 || s2 == 3 || s2 == 4);
    }

    /**
     * 将两个数值进行比较
     *
     * @param ComparisonOperator 两个数值的比较运算符
     * @param left               左值
     * @param right              右值
     * @return 左值 与 右值 之间是否符合比较运算符的关系
     */
    public static boolean ComparisonOperation(String ComparisonOperator, double left, double right) {
        switch (ComparisonOperator) {
            case "<":
                return left < right;
            case ">":
                return left > right;
            case "<=":
                return left <= right;
            case ">=":
                return left >= right;
            case "=":
            case "==":
                return left == right;
            case "!=":
            case "<>":
                return left != right;
            default:
                throw new AbnormalOperation("无法进行比较运算，因为有错误的运算符。\n" +
                        "The comparison operation cannot be performed because there is an incorrect operator.\n" +
                        "Bad comparison operator => " + ComparisonOperator);
        }
    }
}
