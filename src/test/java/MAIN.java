import core.Mathematical_Expression;
import core.calculation.Calculation;
import core.calculation.function.FunctionPackage;
import core.calculation.function.Functions;
import core.container.CalculationResults;
import exceptional.WrongFormat;

// 准备一个数学函数 x 的阶乘 + 1
@Functions("f(x) = x! + 1")
public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 您可以将我们内置的函数进行导入，这样就可以使用一些内置函数了，如 sum
        // 注册内置的函数库 - 数学库
        Mathematical_Expression.register_function(FunctionPackage.MATH);
        // 当然 您还可以使用自定义函数的方式 将您自己的函数 注册进去
        Mathematical_Expression.register_function("fTwo(x, y) = x + y");
        // 您也可以使用注解批量的 将 MAIN 注解的所有函数注册 并进行使用
        Mathematical_Expression.register_function(MAIN.class);
        // 在下面就可以开始进行计算了 首先是获取到计算组件
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        // 然后进行一个简单的检查
        instance.check("1 + sum(1,2,3,4) + f(3) * fTwo(1, 2)");
        // 然后直接进行计算 您的表达式中完全是可以使用函数的哦~~~
        final CalculationResults calculation = instance.calculation("1 + sum(1,2,3,4) + f(3) * fTwo(1, 2)");
        // 直接打印就可以啦~
        System.out.println(calculation.getResult());
    }
}