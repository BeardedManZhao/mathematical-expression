package io.github.beardedManZhao.mathematicalExpression.core.container;


/**
 * 支持克隆的表达式接口，这是为了有一些表达式在多线程中需要使用到克隆功能而产生的！
 *
 * @author 赵凌宇
 */
public interface CloneExpression {

    CloneExpression cloneExpression();

}
