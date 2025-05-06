package io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * 字节码存储
 */
public class InMemoryJavaFileObject extends SimpleJavaFileObject {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    InMemoryJavaFileObject(String name, Kind kind) {
        super(URI.create("mem://" + name.replace('.', '/') + kind.extension), kind);
    }

    @Override
    public OutputStream openOutputStream() {
        return byteArrayOutputStream;
    }

    byte[] getBytes() {
        return byteArrayOutputStream.toByteArray();
    }
}