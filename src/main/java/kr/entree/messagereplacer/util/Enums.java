package kr.entree.messagereplacer.util;

import kr.entree.messagereplacer.replacer.Named;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
@SuppressWarnings("unchecked")
public class Enums {
    public static <T extends Enum<T> & Named> Optional<T> get(Class<T> enumClass, String name) {
        try {
            Method method = enumClass.getMethod("values");
            T[] values = (T[]) method.invoke(null);
            for (T value : values) {
                if (value.getName().equalsIgnoreCase(name)) {
                    return Optional.of(value);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // Ignore
        }
        return Optional.empty();
    }
}
