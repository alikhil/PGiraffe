package ru.t_trusty.pgiraffe.structure;

import java.util.NoSuchElementException;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.util.ElementHelper;
import org.apache.tinkerpop.gremlin.structure.util.StringFactory;

public final class PGiraffeProperty<V> implements Property<V> {

    private final Element element;
    private final String key;
    private final V value;
    private final PGiraffeGraph graph;

    public PGiraffeProperty(PGiraffeGraph graph, final Element elem, final String key, final V val) {
        this.element = elem;
        this.key = key;
        this.value = val;
        this.graph = graph;
        graph.engine().saveProperty(this);
    }

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public V value() throws NoSuchElementException {
        return this.value;
    }

    @Override
    public boolean isPresent() {

        return null != this.value;
    }

    @Override
    public Element element() {

        return this.element;
    }

    @Override
    public String toString() {
        return StringFactory.propertyString(this);
    }

    @Override
    public boolean equals(final Object object) {
        return ElementHelper.areEqual(this, object);
    }

    @Override
    public int hashCode() {
        return ElementHelper.hashCode(this);
    }

    @Override
    public void remove() {
        if (this.element instanceof Edge) {
            ((PGiraffeEdge) this.element).removeProperty(this.key);
//            TinkerHelper.removeIndex((PGiraffeEdge) this.element, this.key, this.value);
        } else {
            ((PGiraffeVertexProperty) this.element).removeProperty(this.key);
        }
//        this.graph.engine().removeProperty(this);
    }

}