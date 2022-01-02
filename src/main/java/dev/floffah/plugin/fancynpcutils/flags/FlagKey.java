package dev.floffah.plugin.fancynpcutils.flags;

import java.util.Objects;

public class FlagKey<Key> {
    public Class<?> setter;
    public Key key;

    public FlagKey(Class<?> setter, Key key) {
        this.setter = setter;
        this.key = key;
    }

    public Class<?> getSetter() {
        return setter;
    }

    public Key getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlagKey<?> flagKey = (FlagKey<?>) o;
        return setter.equals(flagKey.setter) && key.equals(flagKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(setter, key);
    }
}
