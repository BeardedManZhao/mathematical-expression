# 1.2.3 -> 1.2.4 版本更新日志

### 更新时间：UTC2023-12-04 22:20

==Java==

In the old version, there were some exceptions in calculation operations such as `2 * -1` , so we have fixed it here.
This fix will not affect any operations or bring any adverse effects. The following is the code where the error occurred
in the old version

----

在旧版本中，针对 `2*-1` 这类的计算操作会发生一些异常，因此在这里我们进行了修复，本次修复不会影响任何操作，也不会带来任何不良影响，下面是旧版本中出现错误的代码

```java
import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.container.CalculationResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) {
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.bracketsCalculation2);
        final CalculationResults calculation = instance.calculation("1+2 * (1 - 2)");
        System.out.println(calculation);
    }
}
```
