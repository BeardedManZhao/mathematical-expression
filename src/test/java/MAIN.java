
import core.Mathematical_Expression;
import core.calculation.Calculation;
import exceptional.WrongFormat;

public class MAIN {

    public static void main(String[] args) throws WrongFormat {
        // 将 f 函数注册进来
        Mathematical_Expression.register_function("f(x) = x * x");
        // 准备要计算的表达式
        final String data = "1 + f(20) + 3";
        // 获取到计算组件
        final Calculation instance = Mathematical_Expression.getInstance(Mathematical_Expression.functionFormulaCalculation2);
        // 检查与计算
        instance.check(data);
        System.out.println(instance.calculation(data));
    }
}