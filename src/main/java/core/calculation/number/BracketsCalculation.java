package core.calculation.number;

import exceptional.AbnormalOperation;
import utils.StrUtils;

/**
 * 括号解析算法计算一个公式的计算组件的父类，其中的计算具体实现是一个抽象，等待实现
 * <p>
 * The bracket parsing algorithm calculates the parent class of the calculation component of a formula, in which the specific implementation of the calculation is an abstract, waiting to be implemented
 */
public abstract class BracketsCalculation extends NumberCalculation {
    protected BracketsCalculation(String name) {
        super(name);
    }

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算，这里是将字符串格式化成为能够被括号解析组件计算的公式
     * <p>
     * Format a formula so that it can be calculated by the calculation component. Here is to format the string into a formula that can be calculated by the bracket resolution component
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    @Override
    public String formatStr(String string) {
        final StringBuilder stringBuilder = new StringBuilder();
        int endBCount = 0;
        for (String s1 : string.replaceAll("\\s+", "").split("[()]+")) {
            if (s1.matches("\\d+[+\\-*/]\\d+")) {
                stringBuilder.append("(").append(s1).append(")");
            } else if (s1.matches("[+\\-*/]\\d+[+\\-*/]\\d+")) {
                stringBuilder.append(s1.charAt(0)).append("(").append(s1.substring(1)).append(")");
            } else if (s1.matches("\\d+[+\\-*/]\\d+[+\\-*/]")) {
                int ctf = s1.length() - 1;
                stringBuilder.append("(").append(s1, 0, ctf).append(")").append(s1.charAt(ctf));
            } else if (s1.matches("\\d+[+\\-*/]")) {
                stringBuilder.append("(").append(s1);
                ++endBCount;
            } else if (s1.matches("[+\\-*/]\\d+")) {
                stringBuilder.append(s1).append(")");
            } else {
                stringBuilder.append(s1);
            }
        }
        for (int i = 0; i < endBCount; i++) {
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    /**
     * 计算一个带有两个操作数 一个操作符的计算公式的结果
     *
     * @param BinaryFormula 公式 例如 1 + 2
     * @return 计算结果数值
     */
    public double calculation2(String BinaryFormula) {
        int CalculationType = -1; // 0=加 1=减 2=乘 3=除
        int Operatorposition = -1;
        char[] chars = BinaryFormula.toCharArray();
        // 判断计算模式 同时获取操作符索引
        for (int i = 0; i < chars.length; i++) {
            // 迭代每一个字符，直到遇到操作符
            switch (chars[i]) {
                case '+':
                    CalculationType = 0;
                    Operatorposition = i;
                    break;
                case '-':
                    CalculationType = 1;
                    Operatorposition = i;
                    break;
                case '*':
                    CalculationType = 2;
                    Operatorposition = i;
                    break;
                case '/':
                    CalculationType = 3;
                    Operatorposition = i;
                    break;
            }
        }
        // 判断是否有获取到操作符
        if (CalculationType > -1) {
            // 获取到操作符号就直接将a与b先提取出来
            String a = BinaryFormula.substring(0, Operatorposition);
            String b = BinaryFormula.substring(Operatorposition + 1, chars.length);
            // 返回计算结果
            return StrUtils.calculation(a, b, CalculationType);
        } else {
            throw new AbnormalOperation("您的公式格式错误，未解析成功，请您检查您的格式哦！\n" +
                    "The format of your formula is wrong, and it was not parsed successfully. Please check your format!\n" +
                    "error format => " + BinaryFormula);
        }
    }
}
