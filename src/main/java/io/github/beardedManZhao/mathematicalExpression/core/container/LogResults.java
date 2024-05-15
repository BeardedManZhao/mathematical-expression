package io.github.beardedManZhao.mathematicalExpression.core.container;

import top.lingyuzhao.varFormatter.core.VarFormatter;
import top.lingyuzhao.varFormatter.utils.DataObj;

/**
 * 日志结果对象，此对象返回的一般是执行过程的流程。
 *
 * @author zhao
 */
public class LogResults extends DataObj implements CalculationResults {

    private static final long serialVersionUID = "LogResults".hashCode();

    private Object result;

    /**
     * 当前数据对象的构造函数
     * <p>
     * The constructor of the current data object
     *
     * @param name     当前数据对象的名称，可能会被格式化器用作根节点或其它部分。
     *                 <p>
     *                 The name of the current data object may be used by the formatter as a root node or other part.
     * @param dataObjs 当前数据对象内部需要存储的子数据对象
     *                 <p>
     *                 The child data objects that need to be stored inside the current data object
     */
    public LogResults(String name, DataObj... dataObjs) {
        super(name, dataObjs);
    }

    @Override
    public int getResultLayers() {
        throw new UnsupportedOperationException("LogResults currently does not support obtaining the number of layers");
    }

    @Override
    public String getCalculationSourceName() {
        return this.getName();
    }

    /**
     * @return 如果当前的记录中包含结果对象 则会直接返回结果对象，反之返回 null
     * <p>
     * If the current record contains a result object, it will directly return the result object, otherwise it will return null
     */
    @Override
    public Object getResult() {
        return this.result;
    }

    /**
     * 设置结果
     *
     * @param result 结果
     */
    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String explain(VarFormatter varFormatter) {
        return varFormatter.getFormatter(true).format(this);
    }
}
