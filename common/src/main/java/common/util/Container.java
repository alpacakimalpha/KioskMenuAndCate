package common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 사실 좋은 구현 방법이 아니라는 걸 알지만 뭐...
 */
public class Container {
    private static final Map<Class<?>, ?> CLASS_MAP = new HashMap<>();

    public <T> void put(Class<T> clazz, T instance) {

    }
}
