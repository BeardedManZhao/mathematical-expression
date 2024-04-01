package core;

import core.calculation.Calculation;
import core.calculation.function.*;
import core.manager.CalculationManagement;
import exceptional.WrongFormat;

import java.io.*;
import java.nio.file.Files;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * 数学表达式解析库的门户类，在该类中能够直接获取到需要的计算组件并进行相对应的函数注册操作。
 * <p>
 * The portal class of the mathematical expression parsing library, in which the required calculation components can be directly obtained and corresponding function registration operations can be performed.
 *
 * @author 赵凌宇
 * 2023/2/20 15:30
 */
public enum Mathematical_Expression {

    bracketsCalculation2 {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.BracketsCalculation2.getInstance(calculationName);
        }
    }, cumulativeCalculation {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.CumulativeCalculation.getInstance(calculationName);
        }
    },
    fastMultiplyOfIntervalsBrackets {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.FastMultiplyOfIntervalsBrackets.getInstance(calculationName);
        }
    }, fastSumOfIntervalsBrackets {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.FastSumOfIntervalsBrackets.getInstance(calculationName);
        }
    },
    functionFormulaCalculation {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.FunctionFormulaCalculation.getInstance(calculationName);
        }
    }, functionFormulaCalculation2 {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.FunctionFormulaCalculation2.getInstance(calculationName);
        }
    },
    prefixExpressionOperation {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.number.PrefixExpressionOperation.getInstance(calculationName);
        }
    }, booleanCalculation2 {
        @Override
        public Calculation getInstance(String calculationName) {
            return core.calculation.bool.BooleanCalculation2.getInstance(calculationName);
        }
    };

    /**
     * 使用枚举的方式获取到一个计算组件对象。
     * <p>
     * Obtain a calculated component object by enumerating.
     *
     * @param calculation 计算组件对象的类型。您可以直接传递枚举累中的各个选项选择出您需要使用的计算组件。
     *                    <p>
     *                    Calculate the type of component object. You can directly pass the options in the enumeration to select the calculation components you need to use.
     * @return 计算组件对象。
     * <p>
     * Calculate Component Object.
     */
    public static Calculation getInstance(Mathematical_Expression calculation) {
        return getInstance(calculation, calculation.toString());
    }

    /**
     * 注册一个函数到函数库中，使得所有需要使用函数计算的组件都可以获取到函数对象的数据类型。
     * <p>
     * Register a function into the function library, so that all components that need to use the function calculation can obtain the data type of the function object.
     *
     * @param function 函数的表达式，您可以使用数学中的格式来定义一个函数，例如 f(x) = 2 * x
     *                 <p>
     *                 The expression of a function, you can use mathematical formats to define a function, such as f (x)=2 * x
     * @return 如果返回true 则代表函数注册操作成功!!!
     * <p>
     * If true is returned, the function registration operation is successful!!!
     * @throws WrongFormat 函数的格式发生错误则会抛出此异常
     */
    public static boolean register_function(String function) throws WrongFormat {
        return CalculationManagement.register(ExpressionFunction.parse(function));
    }

    /**
     * 注册一个函数到函数库中，使得所有需要使用函数计算的组件都可以获取到函数对象的数据类型。
     * <p>
     * Register a function into the function library, so that all components that need to use the function calculation can obtain the data type of the function object.
     *
     * @param function 被 Functions 注解的类的实例，此示例中的 Functions 中所有的函数表达式将会被注册。
     *                 <p>
     *                 The instance of the class annotated by Functions, all function expressions in the Functions in this example will be registered.
     * @return 如果返回true 则代表函数注册操作成功!!!
     * <p>
     * If true is returned, the function registration operation is successful!!!
     * @throws WrongFormat 函数的格式发生错误则会抛出此异常
     */
    public static boolean register_function(Class<?> function) throws WrongFormat {
        final Functions annotation = function.getAnnotation(Functions.class);
        if (annotation != null) {
            for (String functionExpression : annotation.value()) {
                Mathematical_Expression.register_function(functionExpression);
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 注册一个函数到函数库中，使得所有需要使用函数计算的组件都可以获取到函数对象的数据类型。
     * <p>
     * Register a function into the function library, so that all components that need to use the function calculation can obtain the data type of the function object.
     *
     * @param function 函数的序列化文件，您可以在这里指定一个函数文件的路径，并将自动的注册进来
     *                 <p>
     *                 The expression of a function, you can use mathematical formats to define a function, such as f (x)=2 * x
     * @return 如果返回true 则代表函数注册操作成功!!!
     * <p>
     * If true is returned, the function registration operation is successful!!!
     * @throws IOException            针对函数文件的反序列化操作出现错误的时候会抛出此异常对象
     * @throws ClassNotFoundException Class of a serialized object cannot be found.
     */
    public static boolean register_function(File function) throws IOException, ClassNotFoundException {
        try (final ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(function.toPath()))) {
            final Object o = objectInputStream.readObject();
            if (o instanceof Function) {
                return CalculationManagement.register((Function) o);
            }
            return false;
        }
    }


    /**
     * 将一个数据流中所包含的函数对象的序列化数据进行读取并进行解析和注册，最终返回注册结果！
     *
     * @param inputStream 包含函数对象序列化数据的数据流对象。
     * @return 返回一个 二元组，其中 k 代表的就是注册成功的函数对象的数量，v 代表的是注册失败的函数对象的数量。
     * @throws WrongFormat 如果在格式化操作进行的时候出现了格式错误，则在这里打印出此异常。
     * @throws IOException 如果发生了 IO 错误直接抛出此异常。
     */
    public static Map.Entry<Integer, Integer> register_function(InputStream inputStream) throws WrongFormat, IOException {
        try (final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            int count = 0, errrorCount = 0;
            do {
                try {
                    final Object o = objectInputStream.readObject();
                    if (o instanceof Function && CalculationManagement.register((Function) o)) {
                        ++count;
                    } else {
                        ++errrorCount;
                    }
                } catch (EOFException e) {
                    break;
                }
            } while (true);
            return new AbstractMap.SimpleEntry<>(count, errrorCount);
        } catch (ClassNotFoundException e) {
            throw new WrongFormat("The data stream you provided has an unknown class and cannot continue parsing functions in the file!");
        }
    }

    /**
     * 注册一个函数到函数库中，使得所有需要使用函数计算的组件都可以获取到函数对象的数据类型。
     * <p>
     * Register a function into the function library, so that all components that need to use the function calculation can obtain the data type of the function object.
     *
     * @param function 函数实现类所示例化出来的对象。
     *                 <p>
     *                 The object instantiated by the function implementation class.
     * @return 如果返回true 则代表函数注册操作成功!!!
     * <p>
     * If true is returned, the function registration operation is successful!!!
     */
    public static boolean register_function(Function function) {
        return CalculationManagement.register(function);
    }

    /**
     * 将一个函数包注册到管理者中，这将会导致函数包中的所有函数被注册。
     * <p>
     * Registering a function package with the manager will result in all functions in the package being registered.
     *
     * @param functionPackage 包含需要被注册的所有函数的函数包
     *                        <p>
     *                        A function package containing all the functions that need to be registered
     */
    public static void register_function(FunctionPackage functionPackage) {
        CalculationManagement.register(functionPackage);
    }

    /**
     * 获取一个函数对象
     *
     * @param functionName 函数的名称。
     * @return 函数对应的函数对象
     */
    public static ManyToOneNumberFunction getFunction(String functionName) {
        return CalculationManagement.getFunctionByName(functionName);
    }

    /**
     * 获取到所有已注册的函数的名字
     * <p>
     * Get the names of all registered functions
     *
     * @return 所有已注册的函数的名字的集合
     * <p>
     * A collection of the names of all registered functions
     */
    public static Set<String> getFunctionMap() {
        return CalculationManagement.getFunctionMap();
    }

    /**
     * 将当前的函数对象输出并保存
     * <p>
     * Output and save the current function object
     *
     * @param manyToOneNumberFunction 需要被导出为序列化文件的函数对象。
     *                                <p>
     *                                The function object that needs to be exported as a serialized file.
     * @param file                    当前函数对象要保存到的目标位置
     *                                <p>
     *                                The target location to save the current function object to
     * @throws IOException IO异常
     *                     <p>
     *                     IO exception
     */
    public static void saveFunction(ManyToOneNumberFunction manyToOneNumberFunction, File file) throws IOException {
        try (final OutputStream outputStream = Files.newOutputStream(file.toPath())) {
            saveFunction(outputStream, manyToOneNumberFunction);
        }
    }

    /**
     * 将当前的函数对象输出并保存
     * <p>
     * Output and save the current function object
     *
     * @param outputStream            当序列化数据产生之后，需要通过此数据流来进行数据输出操作，因此您可以直接在这里传递外界已经建立好的数据流对象。
     *                                <p>
     *                                After the serialized data is generated, it is necessary to perform data output operations through this data stream, so you can directly pass the established data stream objects from the outside world here.
     * @param manyToOneNumberFunction 需要被导出为序列化文件的函数对象。
     *                                <p>
     *                                The function object that needs to be exported as a serialized file.
     * @throws IOException IO异常
     *                     <p>
     *                     IO exception
     */
    public static void saveFunction(OutputStream outputStream, ManyToOneNumberFunction... manyToOneNumberFunction) throws IOException {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            for (ManyToOneNumberFunction toOneNumberFunction : manyToOneNumberFunction) {
                if (toOneNumberFunction.AllowSerialization()) {
                    objectOutputStream.writeObject(toOneNumberFunction);
                    continue;
                }
                CalculationManagement.LOGGER.warn("The function " + toOneNumberFunction.getName() + " is not allowed to be serialized.");
            }
        }
    }

    /**
     * 将一个已经注册的函数从函数库中注销注册，释放其所占用的内存空间。
     * <p>
     * Unregister a registered function from the function library to free the memory space it occupies.
     *
     * @param functionName 函数的名称。
     *                     <p>
     *                     The name of the function.
     * @return 如果返回 true 代表函数注销的操作执行成功!!!
     * <p>
     * If true is returned, the function logout operation is successful!!!
     */
    public static boolean unregister_function(String functionName) {
        return CalculationManagement.unregisterF(functionName);
    }

    /**
     * 将一个已经注册的函数从函数库中注销注册，释放其所占用的内存空间。
     * <p>
     * Unregister a registered function from the function library to free the memory space it occupies.
     *
     * @param function 函数的实现类对象。
     *                 <p>
     *                 The implementation class object of the function.
     * @return 如果返回 true 代表函数注销的操作执行成功!!!
     * <p>
     * If true is returned, the function logout operation is successful!!!
     */
    public static boolean unregister_function(Function function) {
        return unregister_function(function.getName());
    }

    /**
     * 传递一个名称，获取到一个计算组件，您不需要调用计算组件的 getInstance 函数。
     * <p>
     * Pass a name to get a calculation component. You don't need to call the getInstance function of the calculation component。
     *
     * @param calculation     计算组件的类型
     * @param calculationName 计算组件的名称.
     *                        <p>
     *                        Name of calculation component.
     * @return 计算组件对象。
     * <p>
     * Calculate component objects.
     */
    public static Calculation getInstance(Mathematical_Expression calculation, String calculationName) {
        return calculation.getInstance(calculationName);
    }

    /**
     * 获取到一个指定名称的计算组件。
     *
     * @param calculationName 计算组件的名称，该参数将会被用于内存地址中的编号映射，能够通过名称获取到指定的实例化号的计算组件对象。
     * @return 计算组件对象
     */
    public abstract Calculation getInstance(String calculationName);
}
