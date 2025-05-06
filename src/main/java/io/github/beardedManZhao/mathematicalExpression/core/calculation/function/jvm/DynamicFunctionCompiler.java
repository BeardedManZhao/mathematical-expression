package io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm;

import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.ManyToOneNumberFunction;

import javax.tools.*;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class DynamicFunctionCompiler {
    private static final JavaCompiler COMPILER = ToolProvider.getSystemJavaCompiler();
    private static final StandardJavaFileManager STANDARD_FILE_MANAGER;

    static {
        STANDARD_FILE_MANAGER = COMPILER.getStandardFileManager(null, null, null);
    }

    /**
     * 动态生成函数
     *
     * @param funcName 函数名
     * @param expr     函数的表达式 要使用下面的参数名称列表来构建表达式！
     * @param params   函数的参数名称列表
     * @return 动态函数
     */
    public static ManyToOneNumberFunction compile(String funcName, String expr, String[] params) {
        try {
            // 构建动态库类
            String libPkg = "io.github.beardedManZhao.mathematicalExpression.core.calculation.function.jvm";
            String libCls = "FunctionLibrary";
            String functionLibClassName = libPkg + "." + libCls;
            StringBuilder libSrc = new StringBuilder();
            libSrc.append("package ").append(libPkg).append(";\n");
            libSrc.append("public class ").append(libCls).append(" {\n");
            for (Map.Entry<String, String> entry : JvmExpressionFunction.getRegisteredMethods().entrySet()) {
                libSrc.append("  ").append(entry.getValue()).append("\n");
            }
            libSrc.append("}\n");

            JavaFileObject libFile = new SimpleJavaFileObject(
                    URI.create("string:///" + functionLibClassName.replace('.', '/') + ".java"),
                    JavaFileObject.Kind.SOURCE) {
                @Override
                public CharSequence getCharContent(boolean ignore) {
                    return libSrc.toString();
                }
            };

            // 构建表达式类，注意静态导入库方法
            String cls = "ExprFunc_" + funcName + "_" + UUID.randomUUID().toString().replace("-", "_");
            String fullName = libPkg + "." + cls;
            StringBuilder funcSrc = new StringBuilder();
            funcSrc.append("package ").append(libPkg).append(";\n");
            funcSrc.append("import static ").append(functionLibClassName).append(".*;\n");
            funcSrc.append("import io.github.beardedManZhao.mathematicalExpression.core.calculation.function.ManyToOneNumberFunction;\n");
            funcSrc.append("public class ").append(cls).append(" extends ManyToOneNumberFunction {\n");
            funcSrc.append("  public ").append(cls).append("() { super(\"").append(funcName).append("\"); }\n");
            funcSrc.append("  @Override public double run(double... numbers) {\n");
            for (int i = 0; i < params.length; i++) {
                funcSrc.append("    double ").append(params[i]).append(" = numbers[").append(i).append("];");
            }
            funcSrc.append("    return ").append(expr).append(";\n  }\n}\n");
            JavaFileObject funcFile = new SimpleJavaFileObject(
                    URI.create("string:///" + fullName.replace('.', '/') + ".java"),
                    JavaFileObject.Kind.SOURCE) {
                @Override
                public CharSequence getCharContent(boolean ignore) {
                    return funcSrc.toString();
                }
            };

            DiagnosticCollector<JavaFileObject> dg = new DiagnosticCollector<>();
            DynamicClassLoader loader = new DynamicClassLoader();
            JavaFileManager fm = new ForwardingJavaFileManager<JavaFileManager>(STANDARD_FILE_MANAGER) {
                @Override
                public JavaFileObject getJavaFileForOutput(Location loc, String name,
                                                           JavaFileObject.Kind kind, FileObject sibling) {
                    InMemoryJavaFileObject out = new InMemoryJavaFileObject(name, kind);
                    loader.addClass(name, out);
                    return out;
                }
            };
            // 编译库类与表达式类
            COMPILER.getTask(null, fm, dg, null, null, Arrays.asList(libFile, funcFile)).call();
            if (!dg.getDiagnostics().isEmpty()) throw new RuntimeException("编译错误: " + dg.getDiagnostics());

            Class<?> clazz = loader.loadClass(fullName);
            return (ManyToOneNumberFunction) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("动态函数生成失败", e);
        }
    }
}