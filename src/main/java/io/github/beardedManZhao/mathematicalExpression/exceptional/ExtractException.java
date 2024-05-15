package io.github.beardedManZhao.mathematicalExpression.exceptional;

/**
 * 提取异常，在进行提取操作的时候发生的运行时异常
 */
public final class ExtractException extends RuntimeException {
    public ExtractException() {
        super();
    }

    public ExtractException(String message) {
        super(message);
    }

    public ExtractException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtractException(Throwable cause) {
        super(cause);
    }
}
