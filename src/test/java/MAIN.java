import core.calculation.function.ExpressionFunction;
import exceptional.WrongFormat;

class MAIN {
    public static void main(String[] args) throws WrongFormat {
        final ExpressionFunction parse = ExpressionFunction.parse("f(zhao, x, y) = x *00 */*/ - y + zhao");
        final String explain = parse.explain(1, 2, 3);
        System.out.println(explain);
    }
}