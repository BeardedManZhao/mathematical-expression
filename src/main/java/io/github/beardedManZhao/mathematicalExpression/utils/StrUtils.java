package io.github.beardedManZhao.mathematicalExpression.utils;

import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.manager.ConstantRegion;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 字符串工具类
 */
public final class StrUtils {

/*
    /**
     * 删除一个字符串中所有的空字符
     *
     * @param s 需要被删除的字符串
     * @return 删除后的字符串
     * /
    public static String removeEmpty(String s) {
        StringBuilder stringBuilder = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (c != ConstantRegion.EMPTY) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }
*/

    /**
     * 将一个字符串转换为浮点数值
     *
     * @param s 需要被转换的字符串
     * @return 字符串转换的浮点数值
     */
    public static double stringToDouble(String s) {
        final Boolean useCache = Mathematical_Expression.Options.isUseCache();
        int hash = 0;
        if (useCache) {
            hash = s.hashCode();
            final Number cacheCalculation = Mathematical_Expression.Options.getCacheCalculation(hash);
            if (cacheCalculation != null) {
                return cacheCalculation.doubleValue();
            }
        }
        final int lastIndex = s.length() - 1;
        double res;
        if (s.charAt(lastIndex) == ConstantRegion.FACTORIAL_SIGN) {
            // 代表要进行阶乘计算
            final double v = Double.parseDouble(s.substring(0, lastIndex));
            if (v < 0) {
                throw new UnsupportedOperationException("负数不支持计算阶乘:" + v);
            }
            res = NumberUtils.factorial(v);
            if (useCache) {
                // 缓存结果
                Mathematical_Expression.Options.cacheCalculation(hash, res);
            }
            return res;
        }
        res = Double.parseDouble(s);
        if (useCache) {
            // 缓存结果
            Mathematical_Expression.Options.cacheCalculation(hash, res);
        }
        return res;
    }

    /**
     * 将一个字符串转换为浮点数值
     *
     * @param s 需要被转换的字符串
     * @return 字符串转换的浮点数值
     */
    public static BigDecimal stringToBigDecimal(String s) {
        final Boolean useCache = Mathematical_Expression.Options.isUseCache();
        int hash = s.hashCode();
        if (useCache) {
            final Number cacheCalculation = Mathematical_Expression.Options.getCacheCalculation(hash);
            if (cacheCalculation instanceof BigDecimal) {
                return (BigDecimal) cacheCalculation;
            }
        }
        BigDecimal res;
        final int lastIndex = s.length() - 1;
        if (s.charAt(lastIndex) == ConstantRegion.FACTORIAL_SIGN) {
            // 代表要进行阶乘计算
            final BigDecimal v = new BigDecimal(s.substring(0, lastIndex));
            final double v1 = v.doubleValue();
            if (v1 < 0) {
                throw new UnsupportedOperationException("负数不支持计算阶乘:" + v);
            }
            res = BigDecimal.valueOf(NumberUtils.factorial(v1));
            if (useCache) {
                Mathematical_Expression.Options.cacheCalculation(hash, res);
            }
            return res;
        }
        res = new BigDecimal(s);
        if (useCache) {
            Mathematical_Expression.Options.cacheCalculation(hash, res);
        }
        return res;
    }

/*
    /**
     * 将一个数值字符传换成一个数值
     *
     * @param c 需要被转换的字符
     * @return 转换之后的数值
     * /
    public static int charToInteger(char c) {
        if (StrUtils.IsANumber(c)) {
            return c - 0x30;
        } else {
            throw new RuntimeException("您在进行字符与数值之间的转换时，由于字符的不正确导致无法成功转换，错误字符：" + c +
                    "\nWhen you are converting characters to numeric values, the conversion cannot be successful due to incorrect characters. Error characters:" + c);
        }
    }
*/

    /**
     * 判断一个字符是不是一个操作符
     *
     * @param c 需要被判断的字符
     * @return 如果是一个操作符就返回True
     */
    public static boolean IsAnOperator(char c) {
        switch (c) {
            case ConstantRegion.PLUS_SIGN:
            case ConstantRegion.MINUS_SIGN:
            case ConstantRegion.MULTIPLICATION_SIGN:
            case ConstantRegion.DIVISION_SIGN:
            case ConstantRegion.REMAINDER_SIGN:
            case ConstantRegion.POW_SIGN:
                return true;
        }
        return false;
    }

    /**
     * 判断一个字符串是不是一个数值
     *
     * @param c 需要被判断的自读
     * @return 如果是一个数值，这里返回True
     */
    public static boolean IsANumber(CharSequence c) {
        for (int i = 0; i < c.length(); i++) {
            if (!IsANumber(c.charAt(i))) {
                return false;
            }
        }
        return true;
    }

/*
    /**
     * 判断一个字符串是不是一个数值 允许阶乘
     *
     * @param c              需要被判断的自读
     * @param allowFactorial 允许阶乘则设置为 true
     * @return 如果是一个数值，这里返回True
     * /
    public static boolean IsANumber(CharSequence c, boolean allowFactorial) {
        final int i = c.length() - 1;
        if (allowFactorial && c.charAt(i) == ConstantRegion.FACTORIAL_SIGN) {
            return IsANumber(c.subSequence(0, c.length()));
        }
        return IsANumber(c);
    }
*/

    /**
     * 判断一个字符是不是一个数值
     *
     * @param c 需要被判断的自读
     * @return 如果是一个数值，这里返回True
     */
    public static boolean IsANumber(char c) {
        return c >= 48 && c <= 57;
    }

    /**
     * 将一个字符串按照某个字符进行拆分
     *
     * @param s 需要被拆分的字符串
     * @param c 需要被作为分割符的字符
     * @return 字符串被拆分之后的列表
     */
    public static ArrayList<String> splitByChar(String s, char c) {
        final int length = s.length();
        final ArrayList<String> arrayList = new ArrayList<>();
        final StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c1 = s.charAt(i);
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

/*
    /**
     * 替换字符串a中所有出现在字符集b中的字符为字符c。
     *
     * @param a          原始字符串
     * @param replaceMap 替换字典，会按照字典来进行替换操作
     * @return 新字符串，其中a中所有在b中存在的字符已被c替换
     * /
    public static String replaceCharsInString(String a, HashMap<Character, String> replaceMap) {
        // 创建一个字符集合，用于存储字符b中的字符
        StringBuilder result = new StringBuilder();
        for (char ch : a.toCharArray()) {
            // 检查当前字符是否在字符集中
            String c = replaceMap.get(ch);
            if (c != null) {
                // 如果存在，则替换为字符c
                result.append(c);
            } else {
                // 否则，保留原字符
                result.append(ch);
            }
        }
        return result.toString();
    }
*/
}

