import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.core.container.LogResults;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;
import top.lingyuzhao.varFormatter.core.VarFormatter;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取到一个有括号计算组件 您可以根据需求更换组件
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.bracketsCalculation2);
        // 然后进行一个简单的检查 这里我们要查询 1 + 2 ^ 4 - 2 * 3 + 2 的执行过程
        final String s = "1 + 2 ^ (2 + (10 - 7)) * 3 + 2";
        instance.check(s);
        // 我们可以通过 explain 获取到执行过程 它会返回一个对象 这个对象中有一个 result 字段 这个字段就是计算出来的结果
        final LogResults explain = instance.explain(s, true);
        System.out.println("计算结果：" + explain.getResult());
        // 事实上 LogResults 更大的作用是进行执行过程可视化 下面就是一个例子
        // 设置输出图的时候不拼接名字，因为在这里有很多的变量 需要进行关联的！拼接名字就不好关联了
        explain.setNameJoin(false);
        // 通过我们引入的 VarFormatter 可以很方便地进行格式化 我们在这里格式化为 MERMAID 图 代码
        System.out.println("graph LR");
        System.out.println(VarFormatter.MERMAID.getFormatter(true).format(explain));
    }
}