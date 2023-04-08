package me.centrium.bossfight.utils.reflect;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface MethodAccessor<R> {
    
    @Nullable
    R invoke(@Nullable Object var1, @Nonnull Object... var2);

    boolean has(@Nonnull Class<?> var1);

    boolean has(@Nonnull Object var1);
}