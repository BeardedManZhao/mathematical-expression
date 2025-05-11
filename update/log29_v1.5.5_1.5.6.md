# 1.5.3 -> 1.5.5 版本更新日志

### 更新时间：2025年05月07日

==Java==

- 更新版本号为 1.5.6
- 优化方程求解器第二代，其支持多x计算，支持x和数值的二次修改！

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.Functions;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.number.SingletonEquationSolvingTwo;
import io.github.beardedManZhao.mathematicalExpression.core.container.EquationSolverExpression;
import io.github.beardedManZhao.mathematicalExpression.core.container.JvmExpression;

import java.util.concurrent.atomic.AtomicInteger;

@Functions("sum(x, y) = x + y")
public class MAIN {

  public static void show(EquationSolverExpression expression) {
    // 获取导其中存储的 Jvm 表达式对象
    JvmExpression jvmExpression = expression.getJvmExpression();
    // 准备一个计数器 用于当作索引
    AtomicInteger count = new AtomicInteger(-1);
    // 迭代其中所有的参数
    jvmExpression.iterator().forEach(n -> {
      if (expression.indexIsUnKnownNumber(count.incrementAndGet())) {
        System.out.println(count.get() + " 索引位置：找到未知数：x");
      } else {
        System.out.println(count.get() + " 索引位置：找到操作数：" + n);
      }
    });
    // 计算
    System.out.println("结果：x 推导为 = " + expression.calculation(false).getResult());
    System.out.println("==================");
  }

  public static void main(String[] args) {
    Mathematical_Expression.register_jvm_function(MAIN.class);
    SingletonEquationSolvingTwo instance = (SingletonEquationSolvingTwo) Mathematical_Expression.getInstance(Mathematical_Expression.singleEquationSolving2);
    EquationSolverExpression compile = instance.compile("sum(x, x)+4=10", false);
    // 查看当前的参数结构
    show(compile);
    // 修改其中的 4 为 5  然后使用这个表达式组件重新计算
    compile.setKnownNumber(2, 5);
    show(compile);
    // 修改其中的第2个未知数为 1，这样的操作会将第二个 x 变为 1，且第二个x不参与运算！  然后使用这个表达式组件重新计算
    compile.setUnKnownNumber(1, 1);
    show(compile);
  }
}
```