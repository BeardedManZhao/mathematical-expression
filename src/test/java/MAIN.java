import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.JvmCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.JvmExpression;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

@Functions("sum(x,y) = x + y")
public class MAIN {

    public static void main(String[] args) throws WrongFormat {
        // 注册一个函数 TODO 注意 只有 注解 和 字符串 的函数注册才能对 JVM 生效哦！
        Mathematical_Expression.register_jvm_function(MAIN.class);
        // 获取到 jvm 计算器
        JvmCalculation jvm = (JvmCalculation) Mathematical_Expression.getInstance(Mathematical_Expression.jvmCalculation);
        // 编译表达式
        JvmExpression compile = jvm.compile("10 + 20 + sum(4, 5) + 40 * 3 - 1", true);
        // 查询编译好的表达式
        System.out.println("编译结果：" + compile.getExpressionStr());
        // 调用编译好的表达式
        // 注意不要使用缓存 因为这个表达式很特别 可以任意修改 为了演示修改参数 所以需要关闭缓存 避免2次计算出同样的结果
        System.out.println("计算结果1：" + compile.calculation(false).getResult());
        // 还可以修改参数 比如我们要修改第 2（索引为1） 个数值 为 30
        compile.setParamNumber(1, 30);
        // 再次调用编译好的表达式
        System.out.println("计算结果2：" + compile.calculation(false).getResult());
        // 获取参数 比如获取到第  2 个参数 也就是索引为 1 的参数
        System.out.println("第2个参数值：" + compile.getParamNumber(1));

        // 使用索引 迭代所有参数
        int length = compile.getLength();
        for (int i = 0; i < length; i++) {
            System.out.println("第" + (i + 1) + "个参数值：" + compile.getParamNumber(i));
        }
        // 也可以使用迭代器
        compile.iterator().forEach(System.out::println);
    }
}