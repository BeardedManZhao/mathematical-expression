import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.CompileCalculation;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.BracketsCalculation2;
import io.github.beardedManZhao.mathematicalExpression.core.container.Expression;

public class MAIN {

    // 准备一个用于进行基准测试的表达式
    private static String s = "1 + 30 + (20 / 10)";

    static {
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += '+' + s;
        s += "+ 100000";
        System.out.println("您要计算的表达式：" + s);
    }

    public static void main(String[] args) {
        // 获取到计算表达式组件
        final BracketsCalculation2 instance = (BracketsCalculation2) Mathematical_Expression.getInstance(Mathematical_Expression.bracketsCalculation2);
        // 预热
        final Expression compile0 = instance.compileBigDecimal(s, true);
        System.out.println(compile0.calculationBigDecimalsCache(false));
        System.out.println("-------");

        // 开启缓存时非常快，能够有多快，取决于您的表达式中重复的子表达式的数量，数量越多 效果越明显
        Mathematical_Expression.Options.setUseCache(true);
        run(instance);
        System.out.println("-------");

        // 不开启缓存的计算速度就慢了许多
        Mathematical_Expression.Options.setUseCache(false);
        run(instance);
    }

    /**
     * 基准测试函数
     *
     * @param instance 需要使用的计算组件
     */
    public static void run(CompileCalculation instance) {
        final long l = System.currentTimeMillis();
        // 第一次进行编译
        final Expression compile = instance.compileBigDecimal(s, true);
        System.out.println(compile.calculationBigDecimalsCache(false));

        final long l1 = System.currentTimeMillis();
        System.out.println("第一次计算所用时间：" + (l1 - l));

        // 再一次进行编译 为了结果严谨，这里计算两次 且第二次表达式有所改动
        final Expression compile1 = instance.compileBigDecimal('(' + s + ") / 2", true);
        System.out.println(compile1.calculationBigDecimalsCache(false));

        System.out.println("第二次计算所用时间：" + (System.currentTimeMillis() - l1));
    }
}