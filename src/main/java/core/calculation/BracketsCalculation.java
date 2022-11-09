package core.calculation;

import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.AbnormalOperation;
import exceptional.ExtractException;
import utils.StrUtils;

import java.util.ArrayList;

/**
 * 括号解析算法计算一个公式，支持嵌套括号计算
 * 能够解析带有括号/嵌套括号的四则运算公式。
 * 例如
 */
public class BracketsCalculation implements Calculation {

    private final String Name;

    protected BracketsCalculation(String name) {
        Name = name;
    }

    /**
     * 从管理者中获取到指定名称的计算组件，注意 如果管理者中不存在该名称的组件，那么该组件就会自动的注册
     *
     * @param CalculationName 组件的名字
     * @return 解析括号类计算公式的计算组件
     */
    public static BracketsCalculation getInstance(String CalculationName) {
        if (CalculationManagement.isregister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof BracketsCalculation) {
                return (BracketsCalculation) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于 BracketsCalculation 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            BracketsCalculation bracketsCalculation = new BracketsCalculation(CalculationName);
            CalculationManagement.register(bracketsCalculation);
            return bracketsCalculation;
        }
    }

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算，这里是将字符串格式化成为能够被括号解析组件计算的公式
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
    public double calculation(String BinaryFormula) {
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

    @Override
    public String getName() {
        return this.Name;
    }

    /**
     * 解析带有括号的公式，并将每一个括号的计算结果进行汇总，然后将结果对象返回
     *
     * @param Formula 带括号的公式
     * @return 返回一个结果对象
     */
    public CalculationNumberResults calculationBrackets(String Formula, boolean formatRequired) {
        final String newFormula;
        if (formatRequired) {
            newFormula = formatStr(Formula);
        } else {
            newFormula = Formula;
        }
        ArrayList<Double> arrayList = new ArrayList<>(8);
        ArrayList<Integer> arrayList1 = new ArrayList<>(4);
        String[] split = newFormula.split("[()]+");
        for (String s : split) {
            // 如果是二元运算公式 就直接运算然后存储到arraylist中，如果不是就转换成存储到运算符集合中
            if (s.matches("\\d+[+\\-*/]\\d+") || s.matches("^(-?\\d+)(\\.\\d+)?$[+\\-*/]^(-?\\d+)(\\.\\d+)?$")) {
                arrayList.add(calculation(s));
            } else if (s.matches(".+[+\\-*/]")) {
                // 如果有一个数值和运算符，就将数值与运算符提取出来 提供给al
                int cfi = s.length() - 1; // 运算符索引数值
                arrayList.add(StrUtils.stringToDouble(s.substring(0, cfi)));
                switch (s.charAt(cfi)) {
                    case '+':
                        arrayList1.add(0);
                        break;
                    case '-':
                        arrayList1.add(1);
                        break;
                    case '*':
                        arrayList1.add(2);
                        break;
                    case '/':
                        arrayList1.add(3);
                        break;
                    default:
                        throw new AbnormalOperation("在进行运算时出现了异常，您的计算公式格式有错误哦！\n" +
                                "An exception occurred during the calculation. Your calculation formula format is incorrect!\n" +
                                "Bad character => " + s);
                }
            } else if (s.matches("[+\\-*/].+")) {
                // 如果有一个数值和运算符，就将数值与运算符提取出来 提供给al
                arrayList.add(StrUtils.stringToDouble(s.substring(1)));
                switch (s.charAt(0)) {
                    case '+':
                        arrayList1.add(0);
                        break;
                    case '-':
                        arrayList1.add(1);
                        break;
                    case '*':
                        arrayList1.add(2);
                        break;
                    case '/':
                        arrayList1.add(3);
                        break;
                    default:
                        throw new AbnormalOperation("在进行运算时出现了异常，您的计算公式格式有错误哦！\n" +
                                "An exception occurred during the calculation. Your calculation formula format is incorrect!\n" +
                                "Bad character => " + s);
                }
            } else if (s.matches("[+\\-*/]")) {
                // 如果就单单的是一个运算符，直接将运算符转换成运算编号提供给al1
                switch (s) {
                    case "+":
                        arrayList1.add(0);
                        break;
                    case "-":
                        arrayList1.add(1);
                        break;
                    case "*":
                        arrayList1.add(2);
                        break;
                    case "/":
                        arrayList1.add(3);
                        break;
                    default:
                        throw new AbnormalOperation("在进行运算时出现了异常，您的计算公式格式有错误哦！\n" +
                                "An exception occurred during the calculation. Your calculation formula format is incorrect!\n" +
                                "Bad character => " + s);
                }
            }
        }
        // 计算结果
        return new CalculationNumberResults(arrayList.toArray(new Double[0]), arrayList1, this.Name);
    }
}
