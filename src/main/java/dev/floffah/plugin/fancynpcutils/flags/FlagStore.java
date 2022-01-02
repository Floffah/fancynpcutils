package dev.floffah.plugin.fancynpcutils.flags;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface FlagStore<Key> extends Iterable<Map.Entry<FlagKey<Key>, Boolean>> {
    public void set(Class<?> setter, Key key, Boolean value);

    public boolean hasSet(Class<?> setter, Key key);

    public boolean hasSet(Key key);

    public List<Boolean> get(Key key);

    @Nullable
    public Boolean get(Class<?> setter, Key key);

    @Override
    void forEach(Consumer<? super Map.Entry<FlagKey<Key>, Boolean>> action);
}
