package ru.t_trusty.pgiraffe.structure;

import java.util.NoSuchElementException;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Property;

public final class PGiraffeProperty<V> implements Property<V> {

    @Override
    public String key() {
        return null;
    }

    @Override
    public V value() throws NoSuchElementException {
        return null;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public Element element() {
        return null;
    }

    @Override
    public void remove() {

    }

}