package io.github.beardedManZhao.mathematicalExpression.core.container;

import top.lingyuzhao.varFormatter.core.VarFormatter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 计算结果接口，其中一般用来存储有关计算结果的数据，结果数据不一定是一个数值，具体的操作请查阅子类实现
 * <p>
 * Calculation result interface, which is generally used to store data related to calculation results. The result data may not be a numerical value. Please refer to the subclass implementation for specific operations
 *
 * @author zhao
 */
public interface CalculationResults extends Serializable {
    /**
     * 计算结果中是经历了很多层计算才获得到的，这个函数可以获取到计算结果的层数量，也可以说是计算结果的计算次数。
     * <p>
     * The calculation result is obtained after many layers of calculation. This function can obtain the number of layers of the calculation result, or the number of calculations of the calculation result.
     *
     * @return 计算结果的计算次数
     */
    int getResultLayers();

    /**
     * 结果是被计算组件计算出来的，在这里可以查询到计算该结果的组件名称，需要注意的是，这里返回的名称不会随着管理者中注册表的更改而变化
     * <p>
     * The result is calculated by the calculation component. You can query the name of the component that calculates the result here. Note that the name returned here will not change with the change of the registry in the administrator
     *
     * @return 计算结果的来源
     */
    String getCalculationSourceName();

    /**
     * 获取计算结果，计算结果的类型在不同的子类中是不同的，所以这里返回的是Object类型
     * <p>
     * Obtain the calculation result. The type of the calculation result varies among different subclasses, so the object type is returned here
     *
     * @return 计算结果
     * <p>
     * Calculation results
     */
    Object getResult();

    /**
     * 获取计算结果，计算结果的类型在不同的子类中是不同的，所以这里返回的是Object类型
     * <p>
     * Obtain the calculation result. The type of the calculation result varies among different subclasses, so the object type is returned here
     *
     * @return 计算结果
     * <p>
     * Calculation results
     */
    default BigDecimal getBigDecimalResult() {
        throw new UnsupportedOperationException("This function is not supported getBigDecimalResult!!!");
    }

    /**
     * @param varFormatter 当您需要将计算出来的 日志流程图 的字符串获取到的时候，您可以使用此函数来进行格式化操作，您可以在这里指定您的格式化规则。
     *                     <p>
     *                     When you need to obtain the string of the calculated log flowchart, you can use this function to perform formatting operations, where you can specify your formatting rules.
     * @return 获取到当前计算结果对应的 流程图的代码，此函数不一定可以被成功调用，您需要确保您的结果类型中支持流程图代码的生成
     * <p>
     * Obtain the code for the flowchart corresponding to the current calculation result. This function may not be called successfully. You need to ensure that your result type supports the generation of flowchart code
     */
    default String explain(VarFormatter varFormatter) {
        throw new UnsupportedOperationException("There is no execution process record in this type!");
    }
}
