package core.manager;

import java.util.regex.Pattern;

/**
 * 静态资源池，其中的所有数值都是一个静态资源，被整个框架多次复用，也可以在此处进行框架的参数定制
 * <p>
 * Static resource pool, in which all values are a static resource and reused by the whole framework for many times. You can also customize the parameters of the framework here
 *
 * @author zhao
 */
public final class ConstantRegion {
    public final static float VERSION = 1.33F;
    public final static String STRING_NULL = "null";
    public final static char LEFT_BRACKET = '(';
    public final static char RIGHT_BRACKET = ')';
    public final static char DECIMAL_POINT = '.';
    public final static char EMPTY = ' ';
    public final static String NO_CHAR = "";
    public final static byte BA_ASCII = 0b1000001;
    public final static byte BZ_ASCII = 0b1011010;
    public final static byte SA_ASCII = 0b1100001;
    public final static byte SZ_ASCII = 0b1111010;
    public final static char PLUS_SIGN = '+';
    public final static char MINUS_SIGN = '-';
    public final static char MULTIPLICATION_SIGN = '*';
    public final static char DIVISION_SIGN = '/';
    public final static char REMAINDER_SIGN = '%';
    public final static char FACTORIAL_SIGN = '!';
    public final static char COMMA = ',';
    public final static String GREATER_THAN_SIGN = ">";
    public final static String LESS_THAN_SIGN = "<";
    public final static String EQUAL_SIGN1 = "=";
    public final static String EQUAL_SIGN2 = EQUAL_SIGN1 + EQUAL_SIGN1;
    public final static String NEGATE_SIGN = "!";
    public final static String MINUS_SIGN_STR = String.valueOf(ConstantRegion.MINUS_SIGN);
    public final static String NOT_EQUAL_SIGN1 = NEGATE_SIGN + EQUAL_SIGN1;
    public final static String NOT_EQUAL_SIGN2 = LESS_THAN_SIGN + GREATER_THAN_SIGN;
    public final static String GREATER_THAN_OR_EQUAL_TO_SIGN = GREATER_THAN_SIGN + EQUAL_SIGN1;
    public final static String LESS_THAN_OR_EQUAL_TO_SIGN = LESS_THAN_SIGN + EQUAL_SIGN1;
    public final static String REGULAR_COMPARISON_OPERATOR = LESS_THAN_OR_EQUAL_TO_SIGN + "|" +
            GREATER_THAN_OR_EQUAL_TO_SIGN + "|" + NOT_EQUAL_SIGN1 + "|" + NOT_EQUAL_SIGN2 + "|" + EQUAL_SIGN2 +
            "|[" + LESS_THAN_SIGN + EQUAL_SIGN1 + GREATER_THAN_SIGN + "]";
    public final static Pattern REGULAR_COMPARISON_OPERATOR_PATTERN = Pattern.compile(REGULAR_COMPARISON_OPERATOR);
    public final static String REGULAR_PURE_LETTER = "[a-zA-Z]+";
    public final static String REGULAR_ADDITION_SUBTRACTION_AMBIGUITY = "\\" + PLUS_SIGN + MINUS_SIGN;
    public final static String LOG_INFO_GET_FUNCTION = "Get a function component from the manager. => ";
    public final static String LOG_INFO_GET_COMPONENT = "Get a computing component from the manager. => ";
    public final static String LOG_INFO_FIND_FUNCTION = "Find and prepare the startup function: ";
    public final static String LOG_INFO_REGISTER_COMPONENT = "A computing component is registered ";
    public final static String LOG_INFO_register_FUNCTION = "A function is registered ";
    public final static String LOG_INFO_UNREGISTER_COMPONENT = "Preparing to unregister the compute component. Component name:";
    public final static String LOG_INFO_UNREGISTER_FUNCTION = "Prepare the logoff of a function. Function name:";
    public final static String LOG_INFO_SHARED_POOL = "Use shared pool data. The identity of the data is: ";
    public final static String LOG_INFO_SHARED_POOL_NO_USE = "No Use shared pool: ";

}
