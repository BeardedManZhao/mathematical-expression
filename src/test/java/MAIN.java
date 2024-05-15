import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.ManyToOneNumberFunction;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class MAIN {

    public static void main(String[] args) throws WrongFormat, IOException {
        // 将函数注册一下
        try (final FileInputStream fileInputStream = new FileInputStream("C:\\Users\\zhao\\Desktop\\fsdownload\\f.ME")) {
            // 直接在这里使用数据流来进行反序列化操作，这个数据流对应的文件包含的函数都会开始尝试注册
            final Map.Entry<Integer, Integer> integerIntegerEntry = Mathematical_Expression.register_function(fileInputStream);
            // 注册完毕之后在这里就可以查看到结果
            System.out.println("注册成功的数量：" + integerIntegerEntry.getKey());
            System.out.println("注册失败的数量：" + integerIntegerEntry.getValue());
        }
        // 然后我们就可以开始使用了 在这里的数据流中 包含的三个函数分别是 f ff fff
        final ManyToOneNumberFunction f = Mathematical_Expression.getFunction("f");
        final ManyToOneNumberFunction ff = Mathematical_Expression.getFunction("ff");
        final ManyToOneNumberFunction fff = Mathematical_Expression.getFunction("fff");
        System.out.println(f.run(10));
        System.out.println(ff.run(10));
        System.out.println(fff.run(10));
    }
}