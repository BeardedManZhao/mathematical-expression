import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.calculation.function.FunctionPackage;
import core.container.CalculationResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取到一个无括号计算组件 您可以根据需求更换组件
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.prefixExpressionOperation);
        // 导入数学包
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        // 然后进行一个简单的检查 这里我们要计算 1 + 2 * 2 * 2 * 2 + 2
        instance.check("1 + 2 ^ 4 + 2");
        // 然后直接进行计算 您的表达式中完全是可以使用函数的哦~~~
        final CalculationResults calculation = instance.calculation("1 + 2 ^ 4 + 2");
        // 直接打印就可以啦~
        System.out.println(calculation);
    }
}