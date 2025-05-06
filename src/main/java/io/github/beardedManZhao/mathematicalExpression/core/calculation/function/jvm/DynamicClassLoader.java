package io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义加载器
 */
public class DynamicClassLoader extends ClassLoader {
    private final Map<String, InMemoryJavaFileObject> classes = new HashMap<>();

    DynamicClassLoader() {
        super(JvmExpressionFunction.class.getClassLoader());
    }

    void addClass(String name, InMemoryJavaFileObject jf) {
        classes.put(name, jf);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InMemoryJavaFileObject jf = classes.get(name);
        if (jf != null) {
            byte[] bytes = jf.getBytes();
            return defineClass(name, bytes, 0, bytes.length);
        }
        return super.findClass(name);
    }
}