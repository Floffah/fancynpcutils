package dev.floffah.plugin.fancynpcutils.flags;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class BehaviourFlagStore<Key> implements FlagStore<Key> {
    Map<FlagKey<Key>, Boolean> store = new HashMap<>();

    @Override
    public void set(Class<?> setter, Key key, Boolean value) {
        this.store.put(new FlagKey<Key>(setter, key), value);
    }

    @Override
    public boolean hasSet(Class<?> setter, Key key) {
        return this.store.get(new FlagKey<>(setter, key)) != null;
    }

    @Override
    public boolean hasSet(Key key) {
        for (FlagKey<Key> lKey : this.store.keySet()) {
            if (lKey.key.equals(key)) return true;
        }

        return false;
    }

    @Override
    public List<Boolean> get(Key key) {
        List<Boolean> list = new ArrayList<>();

        for (Map.Entry<FlagKey<Key>, Boolean> entry : this.store.entrySet()) {
            if (entry.getKey().key.equals(key)) list.add(entry.getValue());
        }

        return list;
    }

    @Nullable
    @Override
    public Boolean get(Class<?> setter, Key key) {
        return this.store.get(new FlagKey<>(setter, key));
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<FlagKey<Key>, Boolean>> iterator() {
        return this.store.entrySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<FlagKey<Key>, Boolean>> action) {
        for (Map.Entry<FlagKey<Key>, Boolean> entry : this.store.entrySet()) {
            action.accept(entry);
        }
    }
}
