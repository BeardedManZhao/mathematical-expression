package core.calculation.number;

import core.calculation.Calculation;
import core.calculation.SharedCalculation;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import core.manager.ConstantRegion;
import exceptional.ExtractException;
import exceptional.WrongFormat;
import utils.NumberUtils;
import utils.StrUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 快速的将一个等差区间中的所有数值之和计算出来，在该计算组件中的公式由两个组成，例如 "a+b,a+b+10"，会将a+b+1 + a+b+2 + a+b+3 +...+a+b+10 的结果计算出来。
 * <p>
 * Quickly calculate the sum of all values in an isochromatic interval. The formula in this calculation component consists of two components, such as "a+b, a+b+10". The result of a+b+1+a+b+2+a+b+3+...+a+b+10 will be calculated.
 *
 * @author zhao
 */
public class FastSumOfIntervalsBrackets extends BracketsCalculation2 implements SharedCalculation {

    public final static BracketsCalculation2 BRACKETS_CALCULATION_2 = BracketsCalculation2.getInstance(CalculationManagement.BRACKETS_CALCULATION_2_NAME);
    public int step;
    private boolean StartSharedPool = true;
    private String CurrentOwner;
    private String left;
    private String right;
    private CalculationNumberResults shareNumberCalculation;

    protected FastSumOfIntervalsBrackets(String name) {
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
    public static FastSumOfIntervalsBrackets getInstance(String CalculationName) {
        if (CalculationManagement.isRegister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof FastSumOfIntervalsBrackets) {
                return (FastSumOfIntervalsBrackets) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于 FastSumOfIntervalsBrackets 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            FastSumOfIntervalsBrackets FastSumOfIntervalsBrackets = new FastSumOfIntervalsBrackets(CalculationName);
            CalculationManagement.register(FastSumOfIntervalsBrackets, false);
            FastSumOfIntervalsBrackets.step = 1;
            return FastSumOfIntervalsBrackets;
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
        return string.trim();
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
        if (this.StartSharedPool && string.equals(this.CurrentOwner)) {
            // 如果共享池开启，同时共享池中的数据所属身份属于当前公式，并且有计算结果，就直接停止检查，因为这个公式之前检查过
            return;
        }
        // 在这里获取到前后两个公式
        ArrayList<String> arrayList = StrUtils.splitByChar(string, ConstantRegion.COMMA);
        int size = arrayList.size();
        if (size == 2) {
            String l = arrayList.get(0);
            String r = arrayList.get(1);
            FastSumOfIntervalsBrackets.BRACKETS_CALCULATION_2.check(l);
            if (!l.equals(r)) {
                FastSumOfIntervalsBrackets.BRACKETS_CALCULATION_2.check(r);
            }
            if (this.StartSharedPool) {
                // 如果检查无误，就将检查之后的结果存储到共享池
                this.left = l;
                this.right = r;
                this.CurrentOwner = string;
            }
        } else {
            throw new WrongFormat("区间求和表达式解析错误，在该组件中的表达式，需要两个以逗号分割的表达式组成累加区间中的两个边界值。\n" +
                    "Error parsing the interval summation expression. The expression in this component needs two expressions separated by commas to form two boundary values in the cumulative interval." +
                    "Number of expressions you provide => " + size);
        }
    }

    /**
     * 计算一个数学表达式，并将计算细节与计算结果存储到数值结果集中。
     * <p>
     * Compute a mathematical expression and store the calculation details and results in the numerical result set.
     *
     * @param Formula 被计算的表达式，要求返回值是一个数值。
     *                <p>
     *                The returned value of the evaluated expression is required to be a numeric value.
     * @return 数值结果对象，该返回值是一个专用于存储数值计算结果的包装类，其中有着计算数据的记录以及结果的保存，可以直接获取到
     * <p>
     * Numerical result object. The returned value is a wrapper class dedicated to storing numerical calculation results. It contains the records of calculation data and the saving of results, which can be directly obtained
     * @see CalculationNumberResults
     */
    @Override
    public CalculationNumberResults calculation(String Formula) {
        return this.calculation(Formula, true);
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
        final String start;
        final String end;
        final boolean equals = this.StartSharedPool && Formula.equals(this.CurrentOwner);
        if (equals) {
            LOGGER.info(ConstantRegion.LOG_INFO_SHARED_POOL + this.Name + ConstantRegion.DECIMAL_POINT + CurrentOwner);
            // 如果共享池开启，同时共享池中数据所属没有错误，就使用共享池数据进行计算
            if (this.shareNumberCalculation != null) {
                return this.shareNumberCalculation;
            }
            start = left;
            end = right;
        } else {
            // 如果是其他情况，代表共享池数据不可用，在这里获取出公式
            ArrayList<String> arrayList = StrUtils.splitByChar(Formula, ConstantRegion.COMMA);
            start = arrayList.get(0);
            end = arrayList.get(1);
        }
        // 计算出结果数值
        CalculationNumberResults calculationNumberResults = calculation(start, end);
        if (equals) {
            this.shareNumberCalculation = calculationNumberResults;
        }
        return calculationNumberResults;
    }

    /**
     * 将两个公式作为求和区间的起始与种植点，以此进行区间的求和，不进行共享池的相关判断
     * <p>
     * The two formulas are used as the starting point and planting point of the summation interval, so as to sum the interval without making relevant judgments on the shared pool
     *
     * @param f1 起始点计算公式
     * @param f2 终止点计算公式
     * @return 区间求和的结果对象
     * <p>
     * Result object of interval summation
     */
    public CalculationNumberResults calculation(String f1, String f2) {
        if (f1.equals(f2)) {
            return FastSumOfIntervalsBrackets.BRACKETS_CALCULATION_2.calculation(f1);
        }
        return calculation(
                FastSumOfIntervalsBrackets.BRACKETS_CALCULATION_2.calculation(f1),
                FastSumOfIntervalsBrackets.BRACKETS_CALCULATION_2.calculation(f2)
        );
    }

    /**
     * 将两个结果对象，作为需要求和区间的起始与终止数值，以此进行区间的求和，不进行公式的计算
     * <p>
     * Take the two result objects as the starting and ending values of the interval to be summed, so as to sum the interval, without calculating the formula
     *
     * @param start 起始点结果数值
     * @param end   终止点结果数值
     * @return 区间求和的结果对象
     * <p>
     * Result object of interval summation
     */
    public CalculationNumberResults calculation(CalculationNumberResults start, CalculationNumberResults end) {
        return new CalculationNumberResults(
                NumberUtils.merge(start.getDoubles(), end.getDoubles()),
                this.step == 1 ?
                        NumberUtils.sumOfRange(start.getResult(), end.getResult()) :
                        NumberUtils.sumOfRange(start.getResult(), end.getResult(), step),
                this.Name
        );
    }

    /**
     * @return 该组件是否有启动共享池，一个布尔值，如果返回true代表共享池已经启动
     */
    @Override
    public boolean isStartSharedPool() {
        return this.StartSharedPool;
    }

    /**
     * 设置共享池开关，共享池是在JavaApi中，1.1版本衍生出来的一种新特性，由于计算量的需求，引入该功能
     * <p>
     * Set the shared pool switch. The shared pool is a new feature derived from version 1.1 in JavaApi. Due to the requirement of computing load, this function is introduced
     *
     * @param startSharedPool 共享池如果设置为true，代表被打开，将会共享检查与
     */
    @Override
    public void setStartSharedPool(boolean startSharedPool) {
        this.StartSharedPool = startSharedPool;
    }

    /**
     * 是否已经缓存了指定字符串的数据
     *
     * @param name 要检查的字符串
     * @return 如果已经缓存则返回true，否则返回false
     */
    @Override
    public boolean isCache(String name) {
        return Objects.equals(this.CurrentOwner, name);
    }
}
