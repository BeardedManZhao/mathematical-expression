package core.calculation.number;

import core.calculation.Calculation;
import core.calculation.function.ManyToOneNumberFunction;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import core.manager.ConstantRegion;
import exceptional.ExtractException;
import exceptional.WrongFormat;

import java.util.Stack;

/**
 * 一个支持函数运算操作的计算组件，在该组件中，支持使用函数进行表达式的运算，需要注意，本类由于是针对数值的计算，因此仅仅支持” ManyToOneNumberFunction “类型的函数进行计算，需要注意的是，在此类中的函数参数只能有一个！
 * <p>
 * A calculation component that supports function operation. In this component, it supports the operation of expressions using functions. Note that this class only supports the calculation of functions of the type "ManyToOneNumberFunction" because it is for numerical calculation
 * <p>
 * 更新：当前组件只能运算具有一个函数形参的数学表达式，针对函数的多参运算，请您使用新版API“FunctionFormulaCalculation2”
 * Update: The current component can only operate on mathematical expressions with one function parameter. For multi parameter operation of functions, please use the new API "FunctionFormulaCalculation2"
 *
 * @author zhao
 * @see core.calculation.number.FunctionFormulaCalculation2 新版函数运算组件 New version of function operation component
 */
public class FunctionFormulaCalculation extends NumberCalculation {

    public final static BracketsCalculation2 BRACKETS_CALCULATION_2 = BracketsCalculation2.getInstance(CalculationManagement.BRACKETS_CALCULATION_2_NAME);

    protected FunctionFormulaCalculation(String name) {
        super(name);
    }

    /**
     * 从管理者中获取到指定名称的计算组件，注意 如果管理者中不存在该名称的组件，那么该组件就会自动的注册
     * <p>
     * Get the calculation component with the specified name from the manager. Note that if there is no component with this name in the manager, the component will be registered automatically
     *
     * @param CalculationName 组件的名字
     * @return 解析括号类计算公式的计算组件
     */
    public static FunctionFormulaCalculation getInstance(String CalculationName) {
        if (CalculationManagement.isRegister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof FunctionFormulaCalculation) {
                return (FunctionFormulaCalculation) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于 FunctionFormulaCalculation 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            FunctionFormulaCalculation FunctionFormulaCalculation = new FunctionFormulaCalculation(CalculationName);
            CalculationManagement.register(FunctionFormulaCalculation, false);
            return FunctionFormulaCalculation;
        }
    }

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算
     * <p>
     * Format a formula so that it can be calculated by the calculation component
     *
     * @param string 数学运算公式
     * @return 格式化之后的数学运算公式
     */
    @Override
    public String formatStr(String string) {
        return string.replaceAll("\\s+", "");
    }

    /**
     * 检查公式格式是否正确，如果不正确就会抛出一个异常
     * <p>
     * Check whether the formula format is correct. If not, an exception will be thrown
     *
     * @param string 需要被判断格式的数学运算公式
     *               <p>
     *               Mathematical operation formula of the format to be judged
     */
    @Override
    public void check(String string) throws WrongFormat {
        // 将所有函数进行提取
        final Stack<Integer> start = new Stack<>();
        final Stack<Integer> end = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();
        // 创建一个标记，标记是否进入函数
        boolean b = false;
        int count = 0;
        final char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (((aChar >= ConstantRegion.BA_ASCII && aChar <= ConstantRegion.BZ_ASCII) || (aChar >= ConstantRegion.SA_ASCII && aChar <= ConstantRegion.SZ_ASCII))) {
                if (!b) {
                    // 如果是刚刚进入函数，就将当前索引添加到栈
                    b = true;
                    start.push(i);
                }
                // 如果当前是函数的名字，就将函数起始索引继续移动，将函数名字部分的索引去除
                start.push(start.pop() + 1);
            } else if (b && aChar == ConstantRegion.LEFT_BRACKET) {
                count += 1;
            } else if (b && aChar == ConstantRegion.RIGHT_BRACKET && --count == 0) {
                // 如果是函数结束，就将函数的终止点索引添加到栈
                b = false;
                end.push(i);
            } else if (!b && aChar != ConstantRegion.EMPTY) {
                stringBuilder.append(aChar);
            }
        }
        // 迭代所有的函数公式，判断是否有错误
        int size = start.size();
        int size1 = end.size();
        if (size == size1) {
            for (int i = 0; i < size; i++) {
                super.check(string.substring(start.pop() + 2, end.pop()));
                stringBuilder.append('0');
            }
            // 检查最终公式
            super.check(stringBuilder.toString());
        } else {
            throw new WrongFormat("函数可能缺少起始或结束括号，没有正常的闭环。\nThe function may lack a start or end bracket, and there is no normal closed loop\nMissing function bracket logarithm: " + Math.abs(size - size1));
        }
    }

    /**
     * 计算一个数学表达式，并将计算细节与计算结果存储到数值结果集中。
     * <p>
     * Compute a mathematical expression and store the calculation details and results in the numerical result set.
     *
     * @param Formula        被计算的表达式，要求返回值是一个数值。
     *                       <p>
     *                       The returned value of the evaluated expression is required to be a numeric value.
     * @param formatRequired 是否需要被格式化，用于确保公式格式正确。
     *                       <p>
     *                       Whether it needs to be formatted to ensure that the formula format is correct.
     * @return 数值结果集对象，其中保存着每一步的操作数据，以及最终结果数值
     * <p>
     * Numerical result set object, which stores the operation data of each step and the final result value
     */
    @Override
    public CalculationNumberResults calculation(String Formula, boolean formatRequired) {
        // 创建一个公式缓冲区
        final StringBuilder stringBuilder = new StringBuilder();
        {
            final char[] chars = Formula.toCharArray();
            // 创建变量，负责记录函数的起始索引
            int start = 0;
            // 创建一个计数器，统计括号对数
            int count = 0;
            // 创建一个状态，统计是否进入函数
            boolean setOk = false;
            // 创建一个字符缓冲区，用于存储函数的名字
            final StringBuilder name = new StringBuilder();
            // 开始迭代公式，找到函数的索引值
            for (int i = 0; i < chars.length; ++i) {
                final char aChar = chars[i];
                if (((aChar >= ConstantRegion.BA_ASCII && aChar <= ConstantRegion.BZ_ASCII) || (aChar >= ConstantRegion.SA_ASCII && aChar <= ConstantRegion.SZ_ASCII))) {
                    // 如果是字母，代表当前就是函数的起始点，将这个函数的名字存储到临时缓冲区
                    if (!setOk) {
                        start = i;
                        setOk = true;
                    }
                    name.append(aChar);
                } else if (setOk && aChar == ConstantRegion.LEFT_BRACKET) {
                    // 如果是一个左括号 同时当前属于函数范围内，就为计数器加1
                    count += 1;
                } else if (setOk && aChar == ConstantRegion.RIGHT_BRACKET && --count == 0) {
                    // 如果当前区域是函数内，同时当前是一个右括号，而且该括号是与起始括号相对应的，代表函数结束
                    setOk = false;
                    // 通过函数名字获取到函数组件
                    ManyToOneNumberFunction functionByName = CalculationManagement.getFunctionByName(name.toString());
                    LOGGER.info(ConstantRegion.LOG_INFO_FIND_FUNCTION + functionByName);
                    // 使用括号计算组件，计算出函数实参，然后通过函数将函数内的公式计算出来
                    double run = functionByName.run(BRACKETS_CALCULATION_2.calculation(Formula.substring(start + name.length() + 1, i), formatRequired).getResult());
                    name.delete(0, name.length());
                    // 将当前的函数结果添加到公式缓冲区，这里判断了下run的精度，如果run是一个整数，就直接转换成整数添加
                    int run1 = (int) run;
                    if (run == run1) {
                        stringBuilder.append(run1);
                    } else {
                        stringBuilder.append(run);
                    }
                } else if (!setOk && aChar != ConstantRegion.EMPTY) {
                    // 如果是其他情况就直接将字符添加到公式中
                    stringBuilder.append(aChar);
                }
            }
        }
        // 将当前不包含函数的公式使用括号表达式解析计算出来
        return BRACKETS_CALCULATION_2.calculation(stringBuilder.toString(), formatRequired);
    }

    /**
     * 计算一个数学表达式，并将计算细节与计算结果存储到数值结果集中。
     * <p>
     * Compute a mathematical expression and store the calculation details and results in the numerical result set.
     *
     * @param Formula 被计算的表达式，要求返回值是一个数值。
     *                <p>
     *                The returned value of the evaluated expression is required to be a numeric value.
     */
    @Override
    public CalculationNumberResults calculation(String Formula) {
        return this.calculation(Formula, true);
    }
}
