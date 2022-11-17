package core.calculation.number;

import core.calculation.Calculation;
import core.container.CalculationNumberResults;
import core.manager.CalculationManagement;
import core.manager.ConstantRegion;
import exceptional.ExtractException;
import exceptional.WrongFormat;
import utils.StrUtils;

/**
 * 累加计算公式解析组件，支持使用未知形参，以及其区间作为公式进行累加加过的计算，例如传入公式 “n [0, 10, 2] (1 + n * n)” 就是 (1 + 0 * 0) + (1 + 2 * 2) + ... + (1 + 10 * 10)
 * <p>
 * The cumulative calculation formula analysis component supports the use of unknown formal parameters and their intervals as formulas for cumulative calculation. For example, the formula "n [0, 10, 2] (1+n * n)" passed in is (1+0 * 0)+(1+2 * 2)+...+(1+10 * 10)
 *
 * @author zhao
 */
public class CumulativeCalculation extends BracketsCalculation2 {
    protected CumulativeCalculation(String name) {
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
    public static CumulativeCalculation getInstance(String CalculationName) {
        if (CalculationManagement.isRegister(CalculationName)) {
            final Calculation calculationByName = CalculationManagement.getCalculationByName(CalculationName);
            if (calculationByName instanceof CumulativeCalculation) {
                return (CumulativeCalculation) calculationByName;
            } else {
                throw new ExtractException(
                        "您需要的计算组件[" + CalculationName + "]被找到了，但是它似乎不属于  CumulativeCalculation 类型\n请您重新定义一个名称。"
                );
            }
        } else {
            CumulativeCalculation CumulativeCalculation = new CumulativeCalculation(CalculationName);
            CalculationManagement.register(CumulativeCalculation, false);
            return CumulativeCalculation;
        }
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
        // 判断是否存在区间
        final String[] split = string.trim().split("[\\[\\]]");
        if (split.length == 3) {
            // 如果满足上述条件代表有累加符号 有区间 有公式 所以现在判断区间内是否缺少东西
            if (split[1].split(",").length == 3) {
                // 满足上述条件代表区间也没有问题，下面就是将需要计算的公式交给父类去检查
                super.check(split[2].replaceAll(split[0], "0"));
            } else {
                throw new WrongFormat("检查到公式的错误，区间的配置不正确哦！正确的区间配置：自变量名称[起始值，终止值，等差值]\nThe formula error is detected, and the interval configuration is incorrect! Correct interval configuration: argument name [start, end, equalDifference]" +
                        "Wrong interval configuration => " + split[1]);
            }
        } else {
            throw new WrongFormat("检查到公式的错误，公式的格式似乎不正确，正确示例：n[0,10,1](1+(n)*n)\nAn error is detected in the formula. The format of the formula seems incorrect. A correct example: n[0,10,1](1+(n)*n)\n" +
                    "Wrong interval configuration => " + string);
        }
    }

    /**
     * 格式化一个公式 使得其可以被该计算组件进行运算，这里是将字符串格式化成为能够被括号解析组件计算的公式
     *
     * @param string 带有闭区间的数学运算公式  例如 n[0, 10, 1] (1 + n) 代表 n属于0到10，在累加的时候，每一次n都加1
     * @return 格式化之后的数学运算公式
     */
    @Override
    public String formatStr(String string) {
        final String[] split = string.trim().split("[\\[\\]]");
        // 获取到累加所属符号
        final String f = split[0];
        // 获取到区间的起始，终止，等差值
        final String[] s = split[1].split(",");
        final double start = StrUtils.stringToDouble(s[0]);
        final double end = StrUtils.stringToDouble(s[1]);
        final double equalDifference = StrUtils.stringToDouble(s[2]);
        // 获取公式位
        final String format = split[2];
        // 开始构造累加公式
        final StringBuilder stringBuilder = new StringBuilder((int) (format.length() * Math.abs(end - start)) + 1);
        for (double v = start; v <= end; v += equalDifference) {
            // 将指定位置的累加符号，变更为当前数值
            stringBuilder
                    .append(ConstantRegion.LEFT_BRACKET)
                    .append(format.replaceAll(f, String.valueOf(v)))
                    .append(ConstantRegion.RIGHT_BRACKET)
                    .append(ConstantRegion.PLUS_SIGN);
        }
        return super.formatStr(stringBuilder.toString());
    }

    /**
     * 解析带有括号的公式，并将每一个括号的计算结果进行汇总，然后将结果对象返回
     * <p>
     * Parse the formula with brackets, summarize the calculation results of each bracket, and then return the result object
     *
     * @param Formula        带有嵌套括号的公式
     *                       <p>
     *                       Formulas with nested parentheses
     * @param formatRequired 是否需要格式化，在此处进行格式化的设置
     *                       <p>
     *                       Whether format is required, set the format here
     * @return 返回一个结果对象
     * <p>
     * Returns a result object
     */
    @Override
    public CalculationNumberResults calculation(String Formula, boolean formatRequired) {
        return super.calculation(formatRequired ? formatStr(Formula) : Formula, false);
    }
}
