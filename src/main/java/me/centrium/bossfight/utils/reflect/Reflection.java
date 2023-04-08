package me.centrium.bossfight.utils.reflect;


import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Reflection {

    @SneakyThrows
    public static Field getField(@Nonnull Class<?> clazz, @Nonnull String field) {
        try {
            return clazz.getDeclaredField(field);
        } catch (Throwable var3) {
            throw var3;
        }
    }

    public static Field getField(@Nonnull Object obj, @Nonnull String field) {
        try {
            return getField(obj.getClass(), field);
        } catch (Throwable var3) {
            throw var3;
        }
    }

    public static Field[] getFields(@Nonnull Class<?> clazz) {
        try {
            return clazz.getDeclaredFields();
        } catch (Throwable var2) {
            throw var2;
        }
    }

    public static Field[] getFields(@Nonnull Object obj) {
        try {
            return getFields(obj.getClass());
        } catch (Throwable var2) {
            throw var2;
        }
    }

    @SneakyThrows
    public static void setValue(@Nonnull Field field, @Nullable Object instance, @Nullable Object value) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            field.set(instance, value);
        } catch (Throwable var4) {
            throw var4;
        }
    }

    public static void setValue(@Nonnull String field, @Nonnull Object instance, @Nullable Object value) {
        try {
            setValue(getField(instance, field), instance, value);
        } catch (Throwable var4) {
            throw var4;
        }
    }

    @SneakyThrows
    public static <T> T getValue(@Nonnull Field field, @Nullable Object instance) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            return (T) field.get(instance);
        } catch (Throwable var3) {
            throw var3;
        }
    }

    public static <T> T getValue(@Nonnull String field, @Nonnull Object instance) {
        try {
            return getValue(getField(instance, field), instance);
        } catch (Throwable var3) {
            throw var3;
        }
    }

    @SneakyThrows
    public static Method getMethod(@Nonnull Class<?> clazz, @Nonnull String method, @Nonnull Class<?>... params) {
        try {
            return clazz.getDeclaredMethod(method, params);
        } catch (Throwable var4) {
            throw var4;
        }
    }

    public static Method getMethod(@Nonnull Object object, @Nonnull String method, @Nonnull Class<?>... params) {
        try {
            return getMethod(object.getClass(), method, params);
        } catch (Throwable var4) {
            throw var4;
        }
    }

    public static Method[] getMethods(@Nonnull Class<?> clazz) {
        try {
            return clazz.getDeclaredMethods();
        } catch (Throwable var2) {
            throw var2;
        }
    }

    public static Method[] getMethods(@Nonnull Object obj) {
        try {
            return getMethods(obj.getClass());
        } catch (Throwable var2) {
            throw var2;
        }
    }

    @SneakyThrows
    public static <T> T invokeMethod(@Nonnull Method method, @Nullable Object instance, @Nonnull Object... params) {
        try {
            return (T) method.invoke(instance, params);
        } catch (Throwable var4) {
            throw var4;
        }
    }

    public static <T> T invokeMethod(@Nonnull String method, @Nonnull Object instance, @Nonnull Object... params) {
        try {
            return invokeMethod(getMethod(instance, method), instance, params);
        } catch (Throwable var4) {
            throw var4;
        }
    }

    @SneakyThrows
    public static Class<?> getClass(String path) {
        try {
            return Class.forName(path);
        } catch (Throwable var2) {
            throw var2;
        }
    }

    public static Class<?>[] getClasses(Class<?> clazz) {
        try {
            return clazz.getClasses();
        } catch (Throwable var2) {
            throw var2;
        }
    }

    public static <T> FieldAccessor<T> fieldAccessor(@Nonnull Field field) {
        return new FieldAccessorImpl(field);
    }

    @SneakyThrows
    public static <R> MethodAccessor<R> methodAccessor(@Nonnull Method method) {
        return new MethodAccessorImpl(method);
    }

    private Reflection() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static class MethodAccessorImpl<R> implements MethodAccessor<R> {
        @Nonnull
        private final Method method;

        @Nullable
        public R invoke(@Nullable Object instance, @Nonnull Object... params) {
            return Reflection.invokeMethod(this.method, instance, params);
        }

        public boolean has(@Nonnull Class<?> clazz) {
            return Arrays.asList(Reflection.getMethods(clazz)).contains(this.method);
        }

        public boolean has(@Nonnull Object instance) {
            return this.has(instance.getClass());
        }

        public MethodAccessorImpl(@Nonnull Method method) {
            if (method == null) {
                throw new NullPointerException("method is marked non-null but is null");
            } else {
                this.method = method;
            }
        }
    }

    static class FieldAccessorImpl<T> implements FieldAccessor<T> {
        @Nonnull
        private final Field field;

        @Nullable
        public T get(@Nullable Object instance) {
            return Reflection.getValue(this.field, instance);
        }

        public void set(@Nullable Object instance, @Nullable T value) {
            Reflection.setValue(this.field, instance, value);
        }

        public boolean has(@Nonnull Object instance) {
            return this.has(instance.getClass());
        }

        public boolean has(@Nonnull Class<?> clazz) {
            return Arrays.asList(Reflection.getFields(clazz)).contains(this.field);
        }

        public FieldAccessorImpl(@Nonnull Field field) {
            if (field == null) {
                throw new NullPointerException("field is marked non-null but is null");
            } else {
                this.field = field;
            }
        }
    }
}
