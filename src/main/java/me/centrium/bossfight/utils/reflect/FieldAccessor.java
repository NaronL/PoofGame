package me.centrium.bossfight.utils.reflect;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface FieldAccessor<T> {

    @Nullable
    T get(@Nullable Object var1);

    void set(@Nullable Object var1, @Nullable T var2);

    boolean has(@Nonnull Object var1);

    boolean has(@Nonnull Class<?> var1);
}
