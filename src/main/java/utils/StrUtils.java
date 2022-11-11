package utils;

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
        boolean isInt = true;
        for (char c : s.trim().toCharArray()) {
            if (c != '.' && c != ' ') {
                // 如果当前不是小数点符号 就直接对数值进行位分配
                if (isInt) {
                    // 如果当前不是小数点符号 就直接将数值归为整数
                    intRes = NumberUtils.tenfold(intRes) + charToInteger(c);
                } else {
                    // 如果是小数点 就直接将数值归为小数
                    floatRes = NumberUtils.tenfold(floatRes) + charToInteger(c);
                }
            } else if (c == '.') {
                // 如果是小数点 就切换添加状态
                isInt = false;
            }
        }
        // 计算出来小数点的位数
        int count = floatRes == 0 ? 0 : floatRes - NumberUtils.tenfold(NumberUtils.DividebyTen(floatRes));
        // 计算出来数值本身
        double res = intRes + floatRes / (double) NumberUtils.PowerOfTen(10, count - 1);
        // 判断是否为负数，如果不是负数直接返回值
        return (intRes == 0 && floatRes == 0) ? 0 : s.charAt(0) == '-' ? -res : res;
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
     * @param CalculationType 计算模式 0=加 1=减 2=乘 3=除
     * @return 计算结果
     */
    public static double calculation(String a, String b, int CalculationType) {
        // 将a 与 b 转换成为数值
        double an = StrUtils.stringToDouble(a);
        double bn = StrUtils.stringToDouble(b);
        return NumberUtils.calculation(CalculationType, an, bn);
    }

    /**
     * 将一个字符类型的操作符 替换成数值编码
     *
     * @param c 字符类型的操作符
     * @return 数值编码
     */
    public static short OperatorConversion(char c) {
        Short aShort = NumberUtils.OPERATOR_SET.get(c);
        return aShort == null ? NumberUtils.ERROR : aShort;
    }
}

