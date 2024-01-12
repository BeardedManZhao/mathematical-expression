package utils;

import core.Mathematical_Expression;
import core.manager.ConstantRegion;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        System.out.println(ConstantRegion.VERSION);
        // 开始进行注册 TODO 我们在这里注册了一个叫做 mySum 的函数 它接收两个参数 输出的是两个参数的求和结果
        if (Mathematical_Expression.register_function("mySum(a, b) = a + b")) {
            System.out.println("函数注册成功!");
        }
    }
}