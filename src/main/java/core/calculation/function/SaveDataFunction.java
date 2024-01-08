package core.calculation.function;

import core.manager.ConstantRegion;

import java.util.HashMap;

/**
 * @author zhao
 */
public abstract class SaveDataFunction extends ManyToOneNumberFunction {

    private HashMap<String, Object> hashMap = new HashMap<>();

    protected SaveDataFunction(String name) {
        super(name);
    }

    /**
     * 定义一个数据
     *
     * @param key   数据变量名字
     * @param value 数据变量值
     */
    public void saveData(String key, Object value) {
        if (key == null || key.equals(ConstantRegion.STRING_NULL)) {
            throw new IllegalArgumentException("The key cannot be null.");
        } else {
            hashMap.put(key, value);
        }
    }

    /**
     * 获取数据
     *
     * @param key 数据变量名字
     * @return 数据变量值 / null
     */
    public Object getData(String key) {
        if (key == null || key.equals(ConstantRegion.STRING_NULL)) {
            throw new IllegalArgumentException("The key cannot be null.");
        } else {
            return hashMap.get(key);
        }
    }
}
