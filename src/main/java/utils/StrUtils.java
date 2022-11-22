package utils;

import core.manager.ConstantRegion;
import exceptional.AbnormalOperation;

import java.util.ArrayList;

public final class StrUtils {
    /**
     * 将一个字符串的子字符串提取出来
     *
     * @param string                需要被提取的字符串
     * @param PositiveSequenceFirst 正序开始的第一个字符
     * @param ReverseOrderFirst     倒叙开始的第一个字符
     * @return 正第一个字符与倒第一字符之间的字符串数据
     */
    public static String getSubinterval(String string, char PositiveSequenceFirst, char ReverseOrderFirst) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = string.toCharArray();
        int start = 0;
        int end = 0;
        // 正向迭代所有字符
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == PositiveSequenceFirst) {
                // 如果遇到正向首字符就将此字符记录
                start = i;
                break;
            }
        }
        // 逆向迭代所有字符
        for (int i = chars.length - 1; i >= 0; --i) {
            if (chars[i] == ReverseOrderFirst) {
                // 如果遇到逆向首字符就将此字符记录
                end = i;
                break;
            }
        }
        // 返回左右开区间内的字符串
        for (int i = start + 1; i < end; ++i) {
            stringBuilder.append(chars[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * 将一个字符串转换为浮点数值
     *
     * @param s 需要被转换的字符串
     * @return 字符串转换的浮点数值
     */
    public static double stringToDouble(String s) {
        int floatRes = 0;
        int intRes = 0;
        int intSize = 0;
        int floatSize = 0;
        boolean isInt = true;
        for (char c : s.toCharArray()) {
            if (c != ConstantRegion.DECIMAL_POINT && c != ConstantRegion.EMPTY) {
                // 如果当前不是小数点符号 就直接对数值进行位分配
                if (isInt) {
                    // 如果当前不是小数点符号 就直接将数值归为整数
                    intRes = NumberUtils.tenfold(intRes) + charToInteger(c);
                    intSize += 1;
                } else {
                    // 如果是小数点 就直接将数值归为小数
                    floatRes = NumberUtils.tenfold(floatRes) + charToInteger(c);
                    floatSize += 1;
                }
            } else if (c == ConstantRegion.DECIMAL_POINT) {
                // 如果是小数点 就判断是否发生精度问题，如果没有发生就切换添加状态
                if (!isInt) {
                    throw new AbnormalOperation("数值的浮点符号出现次数过多，无法计算" + s);
                } else if (intSize > 9) {
                    throw new AbnormalOperation("数值的整数部分数值位数过长，无法计算" + s);
                }
                isInt = false;
            }
        }
        if (floatSize > 9) {
            throw new AbnormalOperation("数值的小数部分数值位数过长，无法计算" + s);
        }
        // 计算出来数值本身
        double res = intRes + floatRes / (double) NumberUtils.PowerOfTen(10, floatSize);
        // 判断是否为负数，如果不是负数直接返回值
        return s.charAt(0) == ConstantRegion.MINUS_SIGN ? -res : res;
    }

    /**
     * 将一个数值字符传换成一个数值
     *
     * @param c 需要被转换的字符
     * @return 转换之后的数值
     */
    public static int charToInteger(char c) {
        switch (c) {
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case '0':
                return 0;
            default:
                throw new RuntimeException("您在进行字符与数值之间的转换时，由于字符的不正确导致无法成功转换，错误字符：" + c +
                        "\nWhen you are converting characters to numeric values, the conversion cannot be successful due to incorrect characters. Error characters:" + c);
        }
    }

    /**
     * 计算一个带有两个操作数 一个操作符的计算公式的结果
     *
     * @param a               第一个操作数
     * @param b               第二个操作数
     * @param CalculationType 计算模式
     * @return 计算结果
     */
    public static double calculation(String a, String b, char CalculationType) {
        // 将a 与 b 转换成为数值，进行运算
        return NumberUtils.calculation(CalculationType, StrUtils.stringToDouble(a), StrUtils.stringToDouble(b));
    }

    /**
     * 计算一个带有两个操作数 一个操作符的计算公式的结果
     *
     * @param a               第一个操作数
     * @param b               第二个操作数
     * @param CalculationType 计算模式 0=加法 1=减法 2=乘法 3=除法 4=取余
     * @return 计算结果 如果出现异常，将会直接提示操作符是 ?
     * @deprecated 该方法中使用到了数值，但是新版本中支持直接使用符号进行计算，这里的数值有一些冗余，将会在下一次版本中移除该方法
     */
    @Deprecated
    public static double calculation(String a, String b, int CalculationType) {
        switch (CalculationType) {
            case 0:
                return NumberUtils.calculation(ConstantRegion.PLUS_SIGN, StrUtils.stringToDouble(a), StrUtils.stringToDouble(b));
            case 1:
                return NumberUtils.calculation(ConstantRegion.MINUS_SIGN, StrUtils.stringToDouble(a), StrUtils.stringToDouble(b));
            case 2:
                return NumberUtils.calculation(ConstantRegion.MULTIPLICATION_SIGN, StrUtils.stringToDouble(a), StrUtils.stringToDouble(b));
            case 3:
                return NumberUtils.calculation(ConstantRegion.DIVISION_SIGN, StrUtils.stringToDouble(a), StrUtils.stringToDouble(b));
            case 4:
                return NumberUtils.calculation(ConstantRegion.REMAINDER_SIGN, StrUtils.stringToDouble(a), StrUtils.stringToDouble(b));
            default:
                return NumberUtils.calculation('?', StrUtils.stringToDouble(a), StrUtils.stringToDouble(b));
        }
    }

    /**
     * 判断一个字符是不是一个操作符
     *
     * @param c 需要被判断的字符
     * @return 如果是一个操作符就返回True
     */
    public static boolean IsAnOperator(char c) {
        return c == ConstantRegion.PLUS_SIGN || c == ConstantRegion.MINUS_SIGN ||
                c == ConstantRegion.MULTIPLICATION_SIGN || c == ConstantRegion.DIVISION_SIGN ||
                c == ConstantRegion.REMAINDER_SIGN;
    }

    /**
     * 将一个字符串按照某个字符进行拆分
     *
     * @param s 需要被拆分的字符串
     * @param c 需要被作为分割符的字符
     * @return 字符串被拆分之后的列表
     */
    public static ArrayList<String> splitByChar(String s, char c) {
        final ArrayList<String> arrayList = new ArrayList<>();
        final StringBuilder stringBuilder = new StringBuilder();
        for (char c1 : s.toCharArray()) {
            if (c1 == c) {
                arrayList.add(stringBuilder.toString().trim());
                stringBuilder.delete(0, stringBuilder.length());
            } else {
                stringBuilder.append(c1);
            }
        }
        arrayList.add(stringBuilder.toString().trim());
        return arrayList;
    }
}

