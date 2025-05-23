
package de.burger.it;

public class FromTypeBinding<S> {
    private final Class<S> sourceClass;

    public FromTypeBinding(Class<S> sourceClass) {
        this.sourceClass = sourceClass;
    }

    public <T> CrossTypeBinding<S, T> forType(Class<T> targetClass) {
        return new CrossTypeBinding<>(sourceClass, targetClass);
    }

    public TypeBinding<S> asSingleType() {
        return new TypeBinding<>(sourceClass);
    }
}

