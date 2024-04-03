import core.Mathematical_Expression;
import core.calculation.function.Functions;
import core.calculation.function.ManyToOneNumberFunction;
import exceptional.WrongFormat;

// 准备一个数学函数 x 的阶乘 + 1
@Functions("f(x) = x! + 1")
public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 将 MAIN 注解的函数注册 并进行使用
        Mathematical_Expression.register_function(MAIN.class);
        // 提取出 f(x) = x! + 1 的函数对象 我们知道这个函数的名字就是 f
        final ManyToOneNumberFunction f = Mathematical_Expression.getFunction("f");
        // 单独使用 f 进行计算
        final double run = f.run(3);
        System.out.println(run);
    }
}