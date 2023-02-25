# ![image](https://user-images.githubusercontent.com/113756063/203919312-dcec4a61-2136-4af2-a361-66b2ed4e6a54.png) 数学表达式

- Switch to [English Document](https://github.com/BeardedManZhao/mathematical-expression/blob/main/README.md)

## 介绍

本框架是一种针对数学公式解析的有效工具，能够解析包含嵌套函数，包含函数，数列步长累加等数学公式，返回值是一个数值的结果对象，同时也可以进行比较运算的操作，再进行比较的时候，返回值是一个布尔值结果对象。

- Maven依赖坐标

  您可以直接使用maven将本框架导入到项目中使用，能够高效的使用该功能

```xml

<dependencies>
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>mathematical-expression</artifactId>
        <version>1.2.2</version>
    </dependency>
</dependencies>
```

您也可以直接通过gradle将“mathematical-expression”载入到您的框架中，使用下面的依赖即可。

```
dependencies {
    implementation 'io.github.BeardedManZhao:mathematical-expression:1.2.1'
}
```

## 框架架构

### 通过 mathematical-expression 库直接获取到计算组件并进行计算

```java
package utils;

import core.Mathematical_Expression;
import core.calculation.number.NumberCalculation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 构建需要计算的两种表达式
        String s1 = "1 + 20 - 2 + 4", s2 = "1 + 20 - (2 + 4)";
        // 通过库获取到计算无括号表达式的计算组件
        NumberCalculation prefixExpressionOperation = Mathematical_Expression.getInstance(
                Mathematical_Expression.prefixExpressionOperation, "prefixExpressionOperation"
        );
        // 通过库获取到计算有括号表达式的计算组件
        NumberCalculation bracketsCalculation2 = Mathematical_Expression.getInstance(
                Mathematical_Expression.bracketsCalculation2, "bracketsCalculation2"
        );
        // 将第一个公式传递给无括号表达式的计算组件
        prefixExpressionOperation.check(s1);
        CalculationNumberResults calculation1 = prefixExpressionOperation.calculation(s1);
        // 打印出第一个表达式的计算结果
        System.out.println("计算层数：" + calculation1.getResultLayers() + "\n计算结果：" + calculation1.getResult() +
                "\n计算来源：" + calculation1.getCalculationSourceName());


        // 将第二个公式传递给无括号表达式的计算组件
        bracketsCalculation2.check(s2);
        CalculationNumberResults calculation2 = bracketsCalculation2.calculation(s2);
        // 打印出第二个表达式的计算结果
        System.out.println("计算层数：" + calculation2.getResultLayers() + "\n计算结果：" + calculation2.getResult() +
                "\n计算来源：" + calculation2.getCalculationSourceName());
    }
}
```

- 运行结果

  通过导入包可以获取到各个计算组件的模块对象，能够有效的减少代码导包代码。

```
计算层数：1
计算结果：23.0
计算来源：prefixExpressionOperation
计算层数：2
计算结果：15.0
计算来源：bracketsCalculation2
```

### 计算管理者

- 类组件：core.manager.CalculationManagement
- 介绍：

  管理者是一个为了同时使用单例与动态对象而设计的一个组件，管理者的存在可以使得每一个组件能够被名字所获取到，相同名字的组件，在内存中的存储地址也是一样的，避免了冗余组件的调用，同时针对需要使用到动态成员的组件，也可以通过一个新名字获取到一个新组件。
- API使用示例

```java
package utils;

import core.calculation.number.FunctionFormulaCalculation;
import core.calculation.number.PrefixExpressionOperation;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) {
        // 实例化三个计算组件
        // TODO getInstance会自动从管理者中获取，如果没有获取到，就会创建并注册然后再返回数据 这个方法即可以创建也可以访问管理者
        PrefixExpressionOperation a = PrefixExpressionOperation.getInstance("a");
        FunctionFormulaCalculation b = FunctionFormulaCalculation.getInstance("b");
        PrefixExpressionOperation a1 = PrefixExpressionOperation.getInstance("a1");
        // 注册一个名为“a”的无括号解析组件
        CalculationManagement.register(a);
        // 注册一个名为“b”的函数解析组件
        CalculationManagement.register(b);
        // 注册一个名为“a1”的无括号解析组件
        CalculationManagement.register(a1);
        // 打印我们示例化的 与 从管理者获取到的组件的内存数据是否一致
        System.err.println(a + "  " + CalculationManagement.getCalculationByName("a"));
        System.err.println(b + "  " + CalculationManagement.getCalculationByName("b"));
        System.err.println(a1 + "  " + CalculationManagement.getCalculationByName("a1"));
    }
}
```

- 运行结果

  最后三行就是内存数据的比较，实例化出来的组件与管理者中的组件在内存中是一样的，但是不同名称的组件是不同的。

```
[INFO][Calculation Management][22-11-14:11]] : +============================== Welcome to [mathematical expression] ==============================+
[INFO][Calculation Management][22-11-14:11]] : + 	Start time Mon Nov 14 11:45:13 CST 2022
[INFO][Calculation Management][22-11-14:11]] : + 	Calculation component manager initialized successfully
[INFO][Calculation Management][22-11-14:11]] : + 	For more information, see: https://github.com/BeardedManZhao/mathematical-expression.git
[INFO][Calculation Management][22-11-14:11]] : +--------------------------------------------------------------------------------------------------+
[INFO][Calculation Management][22-11-14:11]] : A computing component is registered a
[INFO][Calculation Management][22-11-14:11]] : A computing component is registered PrefixExpressionOperation
[INFO][Calculation Management][22-11-14:11]] : A computing component is registered BracketsCalculation2
[INFO][Calculation Management][22-11-14:11]] : A computing component is registered b
[INFO][Calculation Management][22-11-14:11]] : A computing component is registered a1
[INFO][Calculation Management][22-11-14:11]] : A computing component is registered a
[INFO][Calculation Management][22-11-14:11]] : A computing component is registered b
[INFO][Calculation Management][22-11-14:11]] : A computing component is registered a1
[INFO][Calculation Management][22-11-14:11]] : Get the [a] component from the manager
[INFO][Calculation Management][22-11-14:11]] : Get the [b] component from the manager
[INFO][Calculation Management][22-11-14:11]] : Get the [a1] component from the manager
core.calculation.number.PrefixExpressionOperation@8ad73b  core.calculation.number.PrefixExpressionOperation@8ad73b
core.calculation.number.FunctionFormulaCalculation@762604  core.calculation.number.FunctionFormulaCalculation@762604
core.calculation.number.PrefixExpressionOperation@41e737  core.calculation.number.PrefixExpressionOperation@41e737
```

## 计算组件介绍

### 无括号表达式

- 类组件：core.calculation.number.PrefixExpressionOperation
- 介绍

  针对一个没有括号，但是有加减乘除以及取余等运算操作的数学表达式而设计的组件，该组件可以实现带有优先级计算的功能，其中通过前缀表达式解析计算，将操作数与操作符一同存储到栈，在存储的同时配有计算优先级比较，如果当下的优先级较小，就先将上一个操作数与操作符与当前操作数进行运算，形成一个新的数值，然后再入栈。
- API使用示例

  该组件支持的运算符有： a+b a-b a*b a/b a%b

```java
package utils;

import core.calculation.number.PrefixExpressionOperation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取一个计算无括号表达式的函数
        PrefixExpressionOperation prefixExpressionOperation = PrefixExpressionOperation.getInstance("p");
        // 创建一个表达式
        String s = "1 + 2 + 4 * 10 - 3";
        // 检查表达式是否有错误
        prefixExpressionOperation.check(s);
        // 开始计算结果
        CalculationNumberResults calculation = prefixExpressionOperation.calculation(s);
        // 打印结果数值
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\n计算结果：" + calculation.getResult() +
                        "\n计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- 运行结果

  在API调用中，对函数的运行结果进行了打印，可以看到，组件计算的返回值是一个结果集对象，在该对象中存储的就是很多有关计算结果相关的信息。

```
计算层数：2
计算结果：40.0
计算来源：p
```

### 嵌套括号表达式

- 类组件：core.calculation.number.BracketsCalculation2
- 介绍：

  嵌套括号表达式解析组件，能够针对带有多个括号的数学表达式进行解析与结果计算，针对嵌套括号进行优先级的解析与计算，该组件依赖于“core.calculation.number.PrefixExpressionOperation”，在该组件中采用递归进行括号的解析，然后将最内层面的表达式提供给“core.calculation.number.PrefixExpressionOperation”进行计算。
- API使用示例

  该组件支持的运算符有： a+b a-b a*b a/b a%b ( )

```java
package utils;

import core.calculation.number.BracketsCalculation2;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取一个计算无括号表达式的函数
        BracketsCalculation2 bracketsCalculation = BracketsCalculation2.getInstance("BracketsCalculation");
        // 创建一个表达式
        String s = "1 + 2 + 4 * (10 - 3)";
        // 检查表达式是否有错误
        bracketsCalculation.check(s);
        // 开始计算结果
        CalculationNumberResults calculation = bracketsCalculation.calculation(s);
        // 打印结果数值
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\n计算结果：" + calculation.getResult() +
                        "\n计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- 运行结果

  在API调用中，对表达式的计算结果进行了打印，可以看到，组件计算的返回值是一个数值结果对象，在该对象中存储的就是很多有关计算结果相关的信息。

```
计算层数：2
计算结果：31.0
计算来源：BracketsCalculation
```

### 数学比较表达式

- 类组件：core.calculation.bool.BooleanCalculation2
- 介绍

  使用比较运算符两个括号表达式是否相互成立的一个组件，返回值是一个布尔类型的结果对象，该组件能够比较两个数值的大小等，也可以比较两个表达式之间的大小等关系，依赖于组件“core.calculation.bool.BooleanCalculation2”
- API使用示例

  该组件支持的运算符如API中演示

```java
package utils;

import core.calculation.bool.BooleanCalculation2;
import core.container.CalculationBooleanResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取一个计算数学比较表达式的组件
        BooleanCalculation2 booleanCalculation2 = BooleanCalculation2.getInstance("Bool");
        // 创建3个表达式
        String s1 = "1 + 2 + 4 * (10 - 3)";
        String s2 = "2 + 30 + (2 * 3) - 1";
        String s3 = "1 + 3 * 10";
        extracted(booleanCalculation2, s1 + " > " + s2);// false	
        extracted(booleanCalculation2, s1 + " < " + s2);// true	
        extracted(booleanCalculation2, s1 + " = " + s3);// true	
        extracted(booleanCalculation2, s1 + " == " + s3);// true	
        extracted(booleanCalculation2, s1 + " != " + s3);// false	
        extracted(booleanCalculation2, s1 + " <> " + s3);// false	
        extracted(booleanCalculation2, s1 + " <= " + s3);// true	
        extracted(booleanCalculation2, s1 + " >= " + s3);// true	
        extracted(booleanCalculation2, s1 + " != " + s2);// true	
        extracted(booleanCalculation2, s1 + " <> " + s2);// true	
    }

    private static void extracted(BooleanCalculation2 booleanCalculation2, String s) throws WrongFormat {
        // 检查表达式是否有错误
        booleanCalculation2.check(s);
        // 开始计算结果
        CalculationBooleanResults calculation = booleanCalculation2.calculation(s);
        // 打印结果数值
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- 运行结果

```
计算层数：4	计算结果：false	计算来源：Bool
计算层数：4	计算结果：true	计算来源：Bool
计算层数：3	计算结果：true	计算来源：Bool
计算层数：3	计算结果：true	计算来源：Bool
计算层数：3	计算结果：false	计算来源：Bool
计算层数：3	计算结果：false	计算来源：Bool
计算层数：3	计算结果：true	计算来源：Bool
计算层数：3	计算结果：true	计算来源：Bool
计算层数：4	计算结果：true	计算来源：Bool
计算层数：4	计算结果：true	计算来源：Bool
```

### 区间累加表达式

- 类组件：core.calculation.number.CumulativeCalculation
- 介绍

  在数学表达式中，往往有这样的一种公式，公式内容如下图所示，可以看到需要进行累加的数列操作，那么在这种公式的需求下，您可以通过上面的类组件去达到您所需要的目的。
  ![img_1](https://user-images.githubusercontent.com/113756063/201575828-5b76af88-6040-430d-a54c-61faf5905594.png)
- API使用示例

  语法层面于其他组件几乎一致，数学表达式的撰写于组件的计算示例就如下面所示，在这里展示的就是一个累加数学公式的计算。

```java
package utils;

import core.calculation.number.CumulativeCalculation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取一个计算累加数学表达式的组件
        CumulativeCalculation cumulativeCalculation = CumulativeCalculation.getInstance("zhao");
        // 构建一个数学表达式，这里的"n[1,10,1]"就类似数学中的累加符号，n会在这个区间内不断增加，每增加一次都会被带入公式中计算一次
        // 其中[1,10,1]中的最后一个1 代表增加步长，能够实现区间内不同等差值的累加
        String s = "n[1,10,1] 2 * (n + 1)";
        // 检查数学表达式
        cumulativeCalculation.check(s);
        // 计算结果
        CalculationNumberResults calculation = cumulativeCalculation.calculation(s);
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- 运行结果

```
计算层数：21	计算结果：130.0	计算来源：zhao
```

### 函数运算表达式

- 类组件：core.calculation.number.FunctionFormulaCalculation
- 介绍

  针对一些函数的操作，在该框架中也有支持，可以使用上面的类进行这中需要函数的数学表达式的书写，需要注意的是，一切在表达式中使用到的函数都需要在“CalculationManagement”中进行逻辑注册，使得计算的时候可以访问到函数
- API使用示例

```java
package utils;

import core.calculation.function.ManyToOneNumberFunction;
import core.calculation.number.FunctionFormulaCalculation;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 实例化一个函数 名为DoubleValue 用于将一个数值乘2
        ManyToOneNumberFunction myFunction = new ManyToOneNumberFunction("DoubleValue") {
            /**
             * 函数的运行逻辑实现
             *
             * @param numbers 这里是函数的数据输入对象，由框架向这里传递数据输入参数
             * @return 这里是数据经过函数转换之后的数据
             */
            @Override
            public double run(double... numbers) {
                // 在这里的参数中，第一个参数就是被FunctionFormulaCalculation所传入的参数
                return numbers[0] * 2;
            }
        };
        // 将函数注册到管理者中
        CalculationManagement.register(myFunction);
        // 获取一个计算累加数学表达式的组件
        FunctionFormulaCalculation functionFormulaCalculation = FunctionFormulaCalculation.getInstance("zhao");
        // 构建一个数学表达式，表达式中使用到了函数 DoubleValue
        String s = "2 * DoubleValue(2 + 3) + 1";
        // 检查数学表达式
        functionFormulaCalculation.check(s);
        // 计算结果
        CalculationNumberResults calculation = functionFormulaCalculation.calculation(s);
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- 运行结果

```
[INFO][zhao][22-11-14:11]] : Find and prepare the startup function: DoubleValue
计算层数：1	计算结果：21.0	计算来源：BracketsCalculation2
```

### 多参函数运算表达式

- 类组件：core.calculation.number.FunctionFormulaCalculation2
- 介绍

  针对一些在表达式中使用了函数的表达式计算，可以使用上面的类进行操作，它是“core.calculation.number.FunctionFormulaCalculation”类的升级版，从1.1版本开始出现，同时也是它的一个子类拓展实现。

  相较于父类，本组件弥补了父类只能解析带有一个参数函数表达式的不足，在该组件中，可以使用很多的实参进行函数的运算，例如sum(1,2,3)
  这类函数，就是一个多参函数，接下来请看API的使用示例，在此示例中，展示了多惨函数表达式的计算与结果。

```java
package utils;

import core.calculation.function.ManyToOneNumberFunction;
import core.calculation.number.FunctionFormulaCalculation2;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 实现一个sum函数
        ManyToOneNumberFunction manyToOneNumberFunction = new ManyToOneNumberFunction("sum") {
            /**
             * 函数的运行逻辑实现
             *
             * @param numbers 这里是函数的数据输入对象，由框架向这里传递数据输入参数
             * @return 这里是数据经过函数转换之后的数据
             */
            @Override
            public double run(double... numbers) {
                double res = 0;
                for (double number : numbers) {
                    res += number;
                }
                return res;
            }
        };
        // 将该函数注册到管理者
        CalculationManagement.register(manyToOneNumberFunction);
        // 获取到新版本的函数计算组件
        FunctionFormulaCalculation2 functionFormulaCalculation2 = FunctionFormulaCalculation2.getInstance("zhao");
        // 构建我们需要计算的公式 TODO 在这个表达式中的函数sum形参，不只有1个，是多参的函数
        String s = "2 * (200 - sum(1 + 10.1, 2, 3)) + sum(10, 20)";
        // 启用共享池，能够加快计算的速度，计算的公式越复杂，该共享池的效果越显著
        functionFormulaCalculation2.setStartSharedPool(true);
        // 开始检查公式是否有错误
        functionFormulaCalculation2.check(s);
        // 获取到计算结果
        CalculationNumberResults calculation = functionFormulaCalculation2.calculation(s);
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- 运行结果

```
计算层数：2	计算结果：397.8	计算来源：BracketsCalculation2
```

### 快速区间求和计算组件（基于括号表达式）

- 类组件：mathematical_expression/core/calculation/number/fastSumOfIntervalsBrackets.py
- 介绍 1.15版本的新产物，区间快速求和组件，是针对一个等差为1的区间进行所有元素求和的快速组件，它将一个区间在逻辑上模拟成为一个数学数列，并通过求和公式进行快速的求和。

  该组件实现了共享池计算功能，将检查，计算，以及上一次结果记录实现，能够加快计算速度，具体API调用如下所示。

```java
package utils;

import core.calculation.number.FastSumOfIntervalsBrackets;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取到区间求和快计算组件
        FastSumOfIntervalsBrackets fast = FastSumOfIntervalsBrackets.getInstance("fast");
        // 构建一个需要计算的表达式 下面的表达式代表 从 11 = (1+10) 加到 13 = (20-(5+2)) 默认等差为2 结果应为 24
        String s = "1 + 10, 20 - (5 + 2)";
        // 检查表达式，共享池从1.2版本后，已经是默认启用的状态了！不需要手动设置了
        // fast.setStartSharedPool(true);
        fast.check(s);
        // 从1.2版本之后，累加组件支持设置步长参数，1.2.1版本中开始正式支持步长区间的求和
        fast.step = 2;
        // 开始计算
        CalculationNumberResults calculation = fast.calculation(s);
        // 打印计算结果
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- 运行结果 从上面代码中我们可以看到，快速区间求和计算的公式由被逗号分割的两个括号表达式组成

```
计算层数：3	计算结果：24.0	计算来源：fast
```

### 快速区间累乘计算组件（基于括号表达式）

- 类组件：core.calculation.number.FastMultiplyOfIntervalsBrackets
- 介绍 1.2版本的新产物，区间快速累乘组件，是针对一个等差为n的区间进行所有元素累乘的快速组件，它将一个区间在逻辑上模拟成为一个数学数列，并通过求和公式进行快速的累乘。

  该组件实现了共享池计算功能，将检查，计算，以及上一次结果记录实现，能够加快计算速度，具体API调用如下所示。

```java
package utils;

import core.calculation.number.FastMultiplyOfIntervalsBrackets;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

/**
 * 测试用类
 */
public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // 获取到区间求和快计算组件
        FastMultiplyOfIntervalsBrackets fast = FastMultiplyOfIntervalsBrackets.getInstance("fast");
        // 构建一个需要计算的表达式 下面的表达式代表 从 11 = (1+10) 乘到 13 = (20-(5+2)) 默认等差为2
        // 结果应为 11 * 13 = 143
        String s = "1 + 10, 20 - (5 + 2)";
        // 检查表达式，共享池从1.2版本后，已经是默认启用的状态了！不需要手动设置了
        // fast.setStartSharedPool(true);
        fast.check(s);
        // 从1.2版本之后，累加组件支持设置步长参数，1.2.1版本中开始正式支持步长区间的求和
        fast.step = 2;
        // 开始计算
        CalculationNumberResults calculation = fast.calculation(s);
        // 打印计算结果
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- 运行结果

  从上面代码中我们可以看到，快速区间求和计算的公式由被逗号分割的两个括号表达式组成

```
计算层数：3	计算结果：143.0	计算来源：fast
```

<hr>

更多信息

- date: 2022-11-14
- Switch to [English Document](https://github.com/BeardedManZhao/mathematical-expression/blob/main/README.md)
- [mathematical-expression-py](https://github.com/BeardedManZhao/mathematical-expression-Py)
