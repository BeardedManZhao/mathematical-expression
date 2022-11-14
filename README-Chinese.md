# 数学表达式

- Switch to [English Document](https://github.com/BeardedManZhao/mathematical-expression/blob/main/README.md)

## 介绍

本框架是一种针对数学公式解析的有效工具，能够解析包含嵌套函数，包含函数，数列步长累加等数学公式，返回值是一个数值的结果对象，同时也可以进行比较运算的操作，再进行比较的时候，返回值是一个布尔值结果对象。

- Maven依赖坐标

  您可以直接使用maven将本框架导入到项目中使用，能够高效的使用该功能

```xml

```

## 框架架构

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
    public static void main(String[] args) throws WrongFormat {
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
计算层数：11   计算结果：120.0    计算来源：zhao
```

### 函数运算表达式

- 类组件
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

<hr>

- date: 2022-11-14
- Switch to [English Document](https://github.com/BeardedManZhao/mathematical-expression/blob/main/README.md)