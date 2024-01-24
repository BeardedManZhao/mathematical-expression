package core.calculation.function;

import core.manager.CalculationManagement;
import utils.NumberUtils;

import java.util.HashMap;

/**
 * 函数包类，在这里包含一些内置的函数包，您可以直接将这里的内置函数包选择性的注册到计算组件中！
 * <p>
 * Function package class, which includes some built-in function packages, you can selectively register the built-in function packages here into the calculation component!
 */
public enum FunctionPackage {

    /**
     * 数学函数包
     */
    MATH("Math",
            // 求和函数
            new ManyToOneNumberFunction("sum") {
                @Override
                public double run(double... numbers) {
                    double temp = 0;
                    for (double number : numbers) {
                        temp += number;
                    }
                    return temp;
                }
            },
            // 求方函数
            new ManyToOneNumberFunction("pow") {
                @Override
                public double run(double... numbers) {
                    return Math.pow(numbers[0], numbers[1]);
                }
            },
            // 求根函数
            new ManyToOneNumberFunction("sqrt") {
                @Override
                public double run(double... numbers) {
                    return Math.sqrt(numbers[0]);
                }
            },
            // 求 ! 函数
            new ManyToOneNumberFunction("factorial") {
                @Override
                public double run(double... numbers) {
                    return NumberUtils.factorial(numbers[0]);
                }
            },
            // 求均值函数
            new ManyToOneNumberFunction("avg") {
                @Override
                public double run(double... numbers) {
                    final ManyToOneNumberFunction sum = CalculationManagement.getFunctionByNameNoCheck("sum");
                    if (sum == null) {
                        throw FunctionPackage.throwException("sum", "double[]", "double", "求均值前的求和函数;");
                    }
                    return sum.run(numbers) / numbers.length;
                }
            },
            // 求模长函数
            new ManyToOneNumberFunction("moduleLength") {
                @Override
                public double run(double... numbers) {
                    final ManyToOneNumberFunction pow = CalculationManagement.getFunctionByNameNoCheck("pow");
                    if (pow == null) {
                        throw FunctionPackage.throwException("pow", "double[]{需要被计算的数值，需要的次方数}", "int/double", "求某个数的N次方函数;");
                    }
                    final ManyToOneNumberFunction sqrt = CalculationManagement.getFunctionByNameNoCheck("sqrt");
                    if (sqrt == null) {
                        throw FunctionPackage.throwException("sqrt", "double[]{需要被计算的数值}", "double", "求某个数的 2 次方根函数;");
                    }
                    double temp = 0;
                    for (double number : numbers) {
                        temp += pow.run(number);
                    }
                    return sqrt.run(temp);
                }
            },
            // 求sin
            new ManyToOneNumberFunction("sin") {
                @Override
                public double run(double... numbers) {
                    return Math.sin(numbers[0]);
                }
            },
            // 求cos
            new ManyToOneNumberFunction("cos") {
                @Override
                public double run(double... numbers) {
                    return Math.cos(numbers[0]);
                }
            },
            // 求tan
            new ManyToOneNumberFunction("tan") {
                @Override
                public double run(double... numbers) {
                    return Math.tan(numbers[0]);
                }
            },
            // 求cot
            new ManyToOneNumberFunction("cot") {
                @Override
                public double run(double... numbers) {
                    return 1 / Math.tan(numbers[0]);
                }
            },
            // 求sec
            new ManyToOneNumberFunction("sec") {
                @Override
                public double run(double... numbers) {
                    return 1 / Math.cos(numbers[0]);
                }
            },
            // 求csc
            new ManyToOneNumberFunction("csc") {
                @Override
                public double run(double... numbers) {
                    return 1 / Math.sin(numbers[0]);
                }
            }
    ),

    /**
     * 日期函数包
     */
    DATE("Date",
            // 毫秒转秒
            new ManyToOneNumberFunction("msToS") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] / 1000;
                }
            },
            // 秒转毫秒
            new ManyToOneNumberFunction("sToMs") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] * 1000;
                }
            },
            // 毫秒转分钟
            new ManyToOneNumberFunction("msToMin") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] / 60000;
                }
            },
            // 分钟转毫秒
            new ManyToOneNumberFunction("minToMs") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] * 60000;
                }
            },
            // 毫秒转小时
            new ManyToOneNumberFunction("msToHour") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] / 3600000;
                }
            },
            // 小时转毫秒
            new ManyToOneNumberFunction("hourToMs") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] * 3600000;
                }
            },
            // 毫秒转天
            new ManyToOneNumberFunction("msToDay") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] / 86400000;
                }
            },
            // 天转毫秒
            new ManyToOneNumberFunction("dayToMs") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] * 86400000;
                }
            },
            // 毫秒转周
            new ManyToOneNumberFunction("msToWeek") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] / 604800000;
                }
            },
            // 周转毫秒
            new ManyToOneNumberFunction("weekToMs") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] * 604800000;
                }
            },
            // 毫秒转月
            new ManyToOneNumberFunction("msToMonth") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] / 2592000000L;
                }
            },
            // 月转毫秒
            new ManyToOneNumberFunction("monthToMs") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] * 2592000000L;
                }
            },
            // 毫秒转年
            new ManyToOneNumberFunction("msToYear") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] / 31536000000L;
                }
            },
            // 年转毫秒
            new ManyToOneNumberFunction("yearToMs") {
                @Override
                public double run(double... numbers) {
                    return numbers[0] * 31536000000L;
                }
            }
    );

    private final String name;
    private final HashMap<String, Function> functionMap = new HashMap<>();

    FunctionPackage(String name, Function... function) {
        this.name = name;
        for (Function function1 : function) {
            functionMap.put(function1.getName(), function1);
        }
    }

    /**
     * 当函数包中没有该函数时，抛出异常
     *
     * @param needName       需要的第三方函数
     * @param needParamsType 函数接收的参数的类型
     * @param returnType     函数返回的类型
     * @param info           对于函数的描述
     * @return 返回异常对象
     */
    private static UnsupportedOperationException throwException(String needName, String needParamsType, String returnType, String info) {
        // 首先判断这个函数在哪
        String w = needName + " 函数似乎没有被 Mathematical_Expression 所注册或实现，不用担心，您可以现在去实现一个!\nThe function " + needName + " seems to have not been mathematically defined_ You can now implement an expression that has been registered or implemented!";
        for (FunctionPackage value : FunctionPackage.values()) {
            final Function functionByName = value.getFunctionByNameNoCheck(needName);
            if (functionByName != null) {
                w = needName + " 函数位于 " + value.getName() + " 函数包中! 将这个函数包注册到 Mathematical_Expression 中就可以了!";
            }
        }
        return new UnsupportedOperationException("操作错误，您需要将 一个名为 " + needName + " 的函数注册到管理者中，它接收的是一个" + needParamsType + " 类型，返回的是一个 " + returnType + " 类型，它的作用是：" + info + "\n--- describe ---\n" + w);
    }

    /**
     * @return 当前函数所属的函数包的名字
     */
    public String getName() {
        return name;
    }

    /**
     * 根据函数名获取函数
     *
     * @param needName 需要的函数名
     * @return 指定的函数名字对应的函数对象
     */
    public Function getFunctionByNameNoCheck(String needName) {
        return this.functionMap.get(needName);
    }

    /**
     * @return 当前函数包中的所有函数的Map key 是函数的名字 value 是函数的值
     */
    public HashMap<String, Function> getFunctionMap() {
        return this.functionMap;
    }

}
