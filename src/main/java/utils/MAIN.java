package utils;

import core.calculation.function.ExpressionFunction;
import exceptional.WrongFormat;

class MAIN {
    public static void main(String[] args) throws WrongFormat {
        final ExpressionFunction parse = ExpressionFunction.parse("f(a,b,c) = a + (c - b)");
        System.out.println("函数能够接收的参数数量：" + parse.getParamSize());
        // 使用函数进行计算
        System.out.println("f(10, 2, 3) = " + parse.run(10, 2, 3));
    }
}