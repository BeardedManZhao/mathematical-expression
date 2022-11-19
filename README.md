# mathematical-expression

- 切换至 [中文文档](https://github.com/BeardedManZhao/mathematical-expression/blob/main/README-Chinese.md)

## introduce

This framework is an effective tool for mathematical formula analysis. It can analyze mathematical formulas including
nested functions, including functions, and step accumulation of series. The return value is a numerical result object.
At the same time, it can also be used for comparison operations. When comparing again, the return value is a Boolean
result object.

- Maven depends on coordinates

  You can directly use Maven to import this framework into the project and use it effectively

```xml

<dependencies>
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>mathematical-expression</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
```

## Framework

### Calculation Manager

- Full class name：core.manager.CalculationManagement
- introduce：

  The manager is a component designed to use both singletons and dynamic objects. The existence of the manager enables
  each component to be obtained by name. Components with the same name have the same storage address in memory, avoiding
  the use of redundant components. At the same time, for components that need to use dynamic members, a new component
  can also be obtained by a new name.
- API Usage Example

```java
package utils;

import core.calculation.number.FunctionFormulaCalculation;
import core.calculation.number.PrefixExpressionOperation;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) {
        // Instantiate three components
        // TODO getInstance will be automatically obtained from the manager. 
        //  If it is not obtained, it will be created and registered, and then the data will be returned. 
        //  This method can be used to create or access the manager
        PrefixExpressionOperation a = PrefixExpressionOperation.getInstance("a");
        FunctionFormulaCalculation b = FunctionFormulaCalculation.getInstance("b");
        PrefixExpressionOperation a1 = PrefixExpressionOperation.getInstance("a1");
        // Register a bracketed expression calculation component with the name:"a"
        CalculationManagement.register(a);
        // Register a function expression calculation component named:"b"
        CalculationManagement.register(b);
        // Register a bracketed expression calculation component with the name:"a1"
        CalculationManagement.register(a1);
        // Print whether the memory data of the component we instantiated is consistent with that obtained from the manager
        System.err.println(a + "  " + CalculationManagement.getCalculationByName("a"));
        System.err.println(b + "  " + CalculationManagement.getCalculationByName("b"));
        System.err.println(a1 + "  " + CalculationManagement.getCalculationByName("a1"));
    }
}
```

- Running results

  The last three lines are the comparison of memory data. The instantiated components are the same as the components in
  the manager, but the components with different names are different.

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

## Calculation component introduce

### Bracketed expression

- Full class name：core.calculation.number.PrefixExpressionOperation
- introduce

  This component is designed for a mathematical expression without parentheses, but with operations such as addition,
  subtraction, multiplication, division and remainder. This component can realize the function with priority
  calculation, in which the prefix expression is used to parse and calculate, and the operand and operator are stored on
  the stack together with the calculation priority comparison If the current priority is low, first operate the previous
  operand and operator with the current operand to form a new value, and then put it on the stack.
- API Usage Example

  The operators supported by this component are： a+b a-b a*b a/b a%b

```java
package utils;

import core.calculation.number.PrefixExpressionOperation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // Gets the calculation component of a function that evaluates an expression without parentheses
        PrefixExpressionOperation prefixExpressionOperation = PrefixExpressionOperation.getInstance("p");
        // Create an expression
        String s = "1 + 2 + 4 * 10 - 3";
        // Check the expression for errors
        prefixExpressionOperation.check(s);
        // Start calculating results
        CalculationNumberResults calculation = prefixExpressionOperation.calculation(s);
        // Print result value
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\n计算结果：" + calculation.getResult() +
                        "\n计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- Running results

  In the API call, the Running results of the function are printed. It can be seen that the returned value calculated by
  the component is a result set object, in which a lot of information about the calculation results is stored.

```
计算层数：2
计算结果：40.0
计算来源：p
```

### Nested parenthesis expression

- Full class name：core.calculation.number.BracketsCalculation2
- introduce：

  Nested parenthesis expression parsing component, which can parse and calculate the results of mathematical expressions
  with multiple parentheses, and parse and calculate the priority of nested parentheses. This component relies on "core.
  calculation. number. PrefixExpressionOperation", and uses recursion to parse parentheses in this component, Then
  provide the innermost expression to "core. calculation. number. PrefixExpressionOperation" for calculation.

- API Usage Example

  The operators supported by this component are： a+b a-b a*b a/b a%b ( )

```java
package utils;

import core.calculation.number.BracketsCalculation2;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // Get a calculation component that evaluates nested parenthesis expressions
        BracketsCalculation2 bracketsCalculation = BracketsCalculation2.getInstance("BracketsCalculation");
        // Create an expression
        String s = "1 + 2 + 4 * (10 - 3)";
        // Check the expression for errors
        bracketsCalculation.check(s);
        // Start calculating results
        CalculationNumberResults calculation = bracketsCalculation.calculation(s);
        // Print result value
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\n计算结果：" + calculation.getResult() +
                        "\n计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- Running results

  In the API call, the calculation result of the expression is printed. It can be seen that the return value of the
  component calculation is a numerical result object, in which a lot of information about the calculation result is
  stored.

```
计算层数：2
计算结果：31.0
计算来源：BracketsCalculation
```

### Mathematical comparison expression

- Full class name：core.calculation.bool.BooleanCalculation2
- introduce

  A component that uses the comparison operator to determine whether two parenthesis expressions are mutually valid. The
  return value is a Boolean result object. This component can compare the size of two numeric values, or the
  relationship between two expressions, depending on the component "core. calculation. bool. BooleanCalculation2"
- API Usage Example

  The operators supported by this component are shown in the API

```java
package utils;

import core.calculation.bool.BooleanCalculation2;
import core.container.CalculationBooleanResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) {
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
        // Check the expression for errors
        booleanCalculation2.check(s);
        // Start calculating results
        CalculationBooleanResults calculation = booleanCalculation2.calculation(s);
        // Print result value
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- Running results

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

### Interval accumulation expression

- Full class name：core.calculation.number.CumulativeCalculation
- introduce

  In mathematical expressions, there is often such a formula. The content of the formula is shown in the following
  figure. You can see the number sequence operations that need to be accumulated.

  Then, you can use the Full class name above to achieve the purpose you need.

  ![img_1](https://user-images.githubusercontent.com/113756063/201575828-5b76af88-6040-430d-a54c-61faf5905594.png)

- API Usage Example

  The syntax level is almost the same as that of other components. The calculation example of the mathematical
  expression written in the component is shown below. What is shown here is the calculation of an accumulative
  mathematical formula.

```java
package utils;

import core.calculation.number.CumulativeCalculation;
import core.container.CalculationNumberResults;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // Get a component that calculates the cumulative mathematical expression
        CumulativeCalculation cumulativeCalculation = CumulativeCalculation.getInstance("zhao");
        // Construct a mathematical expression. Here, "n [1,10,1]" is similar to the accumulation symbol in mathematics. N will increase continuously in this interval. Every increase will be brought into the formula for calculation
        // Wherein, the last 1 in [1,10,1] represents the increase step, which can realize the accumulation of different equal difference values in the interval
        String s = "n[1,10,1] 2 * (n + 1)";
        // Check mathematical expressions
        cumulativeCalculation.check(s);
        // Calculation results
        CalculationNumberResults calculation = cumulativeCalculation.calculation(s);
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- Running results

```
计算层数：21	计算结果：130.0	计算来源：zhao
```

### Function operation expression

- Full class name：core.calculation.number.FunctionFormulaCalculation
- introduce

  The framework also supports the operation of some functions. You can use the above classes to write mathematical
  expressions that require functions. It should be noted that all functions used in expressions need to be logically
  registered in "Calculation Management" so that functions can be accessed during calculation
- API Usage Example

```java
package utils;

import core.calculation.function.ManyToOneNumberFunction;
import core.calculation.number.FunctionFormulaCalculation;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

public class MAIN {
    public static void main(String[] args) throws WrongFormat {
        // Instantiate a function named DoubleValue to multiply a value by 2
        ManyToOneNumberFunction myFunction = new ManyToOneNumberFunction("DoubleValue") {
            /**
             * 函数的运行逻辑实现
             *
             * @param numbers 这里是函数的数据输入对象，由框架向这里传递数据输入参数
             * @return 这里是数据经过函数转换之后的数据
             */
            @Override
            public double run(double... numbers) {
                // Among the parameters here, the first parameter is the parameter passed in by FunctionFormulaCalculation
                return numbers[0] * 2;
            }
        };
        // Register function to manager
        CalculationManagement.register(myFunction);
        // Get a component that calculates the cumulative mathematical expression
        FunctionFormulaCalculation functionFormulaCalculation = FunctionFormulaCalculation.getInstance("zhao");
        // Build a mathematical expression that uses the function DoubleValue
        String s = "2 * DoubleValue(2 + 3) + 1";
        // Check mathematical expressions
        functionFormulaCalculation.check(s);
        // Calculation results
        CalculationNumberResults calculation = functionFormulaCalculation.calculation(s);
        System.out.println(
                "计算层数：" + calculation.getResultLayers() + "\t计算结果：" + calculation.getResult() +
                        "\t计算来源：" + calculation.getCalculationSourceName()
        );
    }
}
```

- Running results

```
[INFO][zhao][22-11-14:11]] : Find and prepare the startup function: DoubleValue
计算层数：1	计算结果：21.0	计算来源：BracketsCalculation2
```

<hr>

More information

- date: 2022-11-14
- 切换至 [中文文档](https://github.com/BeardedManZhao/mathematical-expression/blob/main/README-Chinese.md)
- [mathematical-expression-py](https://github.com/BeardedManZhao/mathematical-expression-Python)
