# 1.3.6 -> 1.4.0 版本更新日志

### 更新时间：2024年05月15日 【稳定版本发布】

==Java==

- 项目所有功能重新自检完毕！项目模块包更换完毕
- 序列化操作修复完毕

- ✅ 您目前使用的是 1.4.0 版本！！！请注意包名换了哦~

```java
import io.github.beardedManZhao.mathematicalExpression.core.Mathematical_Expression;
import io.github.beardedManZhao.mathematicalExpression.core.calculation.Calculation;
import io.github.beardedManZhao.mathematicalExpression.exceptional.WrongFormat;

public class MAIN {

    public static void main(String[] args) throws WrongFormat {
        final Calculation instance = Mathematical_Expression.getInstance(
                // Select the different computing components you want to use here
                Mathematical_Expression.bracketsCalculation2
        );
        // If you ensure the correctness of the expression, you can skip checking
        instance.check("(1+2)*3");
        System.out.println(instance.calculation("(1+2)*3"));
    }
}
```
