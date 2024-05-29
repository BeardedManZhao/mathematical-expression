import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.CompileCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.NameExpression;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MAIN {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 获取到计算组件
        final CompileCalculation instance = (CompileCalculation) Mathematical_Expression.getInstance(Mathematical_Expression.bracketsCalculation2);
        // 准备一个路径对象
        final Path path = Paths.get("C:\\Users\\zhao\\Downloads\\表达式.me");

        // 将表达式对象保存到磁盘
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            // 编译一个表达式对象
            final NameExpression compile = instance.compile(" 1 + (20 * 3 - 3 + (10 -4)) + (30 -2)  /2 + 10", true);
            // 将表达式对象输出到磁盘
            objectOutputStream.writeObject(compile);
        }

        // 再将表达式从磁盘读取进来
        try (final ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
            // 从磁盘中将表达式重新加载到内存
            final NameExpression expression = (NameExpression) objectInputStream.readObject();
            // 查看表达式的信息
            System.out.println("表达式来源：" + expression.getCalculationName());
            System.out.println("表达式的格式：" + expression.getExpressionStr());
            System.out.println("表达式支持的模式：" + (expression.isBigDecimal() ? "【高精度 √】 " : "【高精度 ×】 ") + (expression.isUnBigDecimal() ? "【非精度 √】 " : "【非精度 ×】 "));
            System.out.println(">>> 开始为表达式对象添加多精度支持");
            expression.convertToMultiPrecisionSupported();
            System.out.println("表达式支持的模式：" + (expression.isBigDecimal() ? "【高精度 √】 " : "【高精度 ×】 ") + (expression.isUnBigDecimal() ? "【非精度 √】 " : "【非精度 ×】 "));
            System.out.println("计算结果：" + expression.calculation(false));
        }
    }
}