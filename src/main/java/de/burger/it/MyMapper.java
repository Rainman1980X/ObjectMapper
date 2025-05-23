package de.burger.it;

public class MyMapper {

    public <S> FromTypeBinding<S> forType(Class<S> sourceClass) {
        return new FromTypeBinding<>(sourceClass);
    }

    public <T> TypeBinding<T> forSingleType(Class<T> clazz) {
        return new TypeBinding<>(clazz);
    }
}