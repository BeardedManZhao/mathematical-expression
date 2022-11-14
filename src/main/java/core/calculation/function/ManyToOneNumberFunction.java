package core.calculation.function;

import exceptional.ExtractException;

/**
 * 多对一的数值型函数，在该函数中，可以传入很多参数，最终计算出来结果数值，是针对数值计算的有效函数，需要实现其中的run方法。
 * <p>
 * Many to one numerical function. In this function, many parameters can be passed in, and the final result value can be calculated. It is an effective function for numerical calculation, and the run method needs to be implemented.
 *
 * @author zhao
 */
public abstract class ManyToOneNumberFunction implements Function {

    protected final String Name;

    protected ManyToOneNumberFunction(String name) {
        if (name.matches("[a-zA-Z]+")) {
            Name = name;
        } else {
            throw new ExtractException("您的函数名称中只能包含字母，同时函数名称的长度不能为0，请您更改函数名称\nYour function name can only contain letters, and the length of the function name cannot be 0. Please change the function name\n" +
                    "ERROR NAME => " + name);
        }
    }

    /**
     * @return 函数的名称，每一个函数都必须要设置一个名称，便于在公式中使用函数
     * <p>
     * The name of a function. Each function must have a name set to facilitate the use of functions in formulas
     */
    @Override
    public String getName() {
        return this.Name;
    }

    /**
     * 函数的运行逻辑实现
     *
     * @param numbers 这里是函数的数据输入对象，由框架向这里传递数据输入参数
     * @return 这里是数据经过函数转换之后的数据
     */
    public abstract double run(double... numbers);

    @Override
    public String toString() {
        return this.Name;
    }
}
