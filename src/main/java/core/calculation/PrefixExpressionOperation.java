package core.calculation;

import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.ExtractException;
import utils.NumberUtils;
import utils.StrUtils;

import java.util.ArrayList;
import java.util.Stack;

public class PrefixExpressionOperation implements Calculation {


    private final String Name;

    private PrefixExpressionOperation(String name) {
        Name = name;
    }

    /**
     * 从管理者中获取到指定名称的计算组件，注意 如果管理者中不存在该名称的组件，那么该组件就会自动的注册
     *
     * @param CalculationName 组件的名字
     * @return 解析括号类计算公式的计算组件
     */
    public static PrefixExpressionOperation getInstance(String CalculationName) {
        if (CalculationManagement.isregister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof PrefixExpressionOperation) {
                return (PrefixExpressionOperation) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于 PrefixExpressionOperation 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            PrefixExpressionOperation PrefixExpressionOperation = new PrefixExpressionOperation(CalculationName);
            CalculationManagement.register(PrefixExpressionOperation);
            return PrefixExpressionOperation;
        }
    }


    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    @Override
    public String formatStr(String string) {
        return string.replaceAll(" +", "");
    }

    public CalculationNumberResults calculation(String Formula, boolean formatRequired) {
        String newFormula;
        if (formatRequired) {
            newFormula = formatStr(Formula);
        } else {
            newFormula = Formula.replaceAll(" +", "");
        }
        double res = 0;
        ArrayList<Double> temps = new ArrayList<>();
        short back = NumberUtils.ADDITION;
        // 创建操作符栈
        final Stack<Double> doubleStack = new Stack<>();
        // 创建操作数栈
        final Stack<Short> shortStack = new Stack<>();
        // 开始格式化，将符号与操作数进行分类
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : newFormula.toCharArray()) {
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {
                // 如果是操作符，就先将上一个数值计算出来
                double number = StrUtils.stringToDouble(stringBuilder.toString());
                short i = StrUtils.OperatorConversion(c);
                if (shortStack.isEmpty()) {
                    // 如果栈为空 直接将运算符添加到栈顶
                    shortStack.push(i);
                    // 将数值添加到数值栈顶
                    doubleStack.push(number);
                } else {
                    // 如果不为空就检查栈顶的与当前运算符的优先级
                    if (!NumberUtils.PriorityComparison(shortStack.peek(), i)) {
                        // 如果上一个优先级较大 就将上一个操作符取出
                        short top = shortStack.pop();
                        // 将上一个优先级高的值 与当下优先级较低的值进行运算，然后将当下的新数值和当下的操作符推到栈顶
                        doubleStack.push(NumberUtils.calculation(top, number, doubleStack.pop()));
                        shortStack.push(i);
                    } else {
                        // 反之就直接将当前的数值添加到缓冲区
                        stringBuilder.append(c);
                        // 将当前运算符提供到栈顶
                        shortStack.push(i);
                        doubleStack.push(number);
                    }
                }
                // 清理所有的字符缓冲
                stringBuilder.delete(0, stringBuilder.length());
            } else if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '.') {
                // 如果是数值的某一位，就将数值存储到变量中
                stringBuilder.append(c);
            }
        }
        doubleStack.push(StrUtils.stringToDouble(stringBuilder.toString()));
        System.err.println(shortStack + " " + doubleStack);

        res = doubleStack.pop();
        // 开始计算
        int size = doubleStack.size();
        for (int i = 0; i < size; i++) {
            // 更新操作符
            back = shortStack.pop();
            // 获取操作数
            double aDouble1 = doubleStack.pop();
            // 计算结果
            System.out.println(res + " " + back + " " + aDouble1);
            res = NumberUtils.calculation(back, res, aDouble1);
            temps.add(res);
        }
        // 返回结果
        return new CalculationNumberResults(temps.toArray(new Double[0]), res, this.Name);
    }

    /**
     * @return 计算组件的名称
     */
    @Override
    public String getName() {
        return this.Name;
    }
}
