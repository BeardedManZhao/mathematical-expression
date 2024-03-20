package utils;

import core.Mathematical_Expression;
import core.calculation.function.ExpressionFunction;
import core.calculation.function.ManyToOneNumberFunction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MAIN {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ManyToOneNumberFunction ff, f;
        try (final ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get("C:\\Users\\zhao\\Desktop\\fsdownload\\f.me")))) {
            // 在这里读取到函数对象（要注意这里和保存时的顺序一致哦！！）
            ff = ExpressionFunction.readFrom(objectInputStream);
            f = ExpressionFunction.readFrom(objectInputStream);
        }
        // 把函数注册回 Mathematical_Expression
        Mathematical_Expression.register_function(ff);
        Mathematical_Expression.register_function(f);
        // 也可以直接使用它
        final double run = ff.run(1024);
        System.out.println(run);
    }
}