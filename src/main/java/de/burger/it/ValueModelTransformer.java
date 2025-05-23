package de.burger.it;


@FunctionalInterface
public interface ValueModelTransformer<T> {

    /**
     * Führt eine Transformation auf dem gegebenen ValueModel aus
     * und gibt das (modifizierte oder neue) ValueModel zurück.
     */
    T apply(T model);

    /**
     * Verkettet diesen Transformer mit einem weiteren,
     * wobei das Ergebnis dieses Transformers an den nächsten übergeben wird.
     */
    default ValueModelTransformer<T> andThen(ValueModelTransformer<T> next) {
        return model -> next.apply(this.apply(model));
    }
}
