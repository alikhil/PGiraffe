package ru.t_trusty.pgiraffe.structure;

import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import java.util.Iterator;

public interface StoreEngine {
    void saveEdge(PGiraffeEdge edge);

    void saveVertex(PGiraffeVertex vertex);

    void removeVertex(PGiraffeVertex pGiraffeVertex);

    Iterator<Edge> getEdges(PGiraffeVertex pGiraffeVertex, Direction direction, String... edgeLabels);

    <V> Iterator<VertexProperty<V>> getVertexProperties(PGiraffeVertex vertex, String... propertyKeys);

    <V> void saveProperty(PGiraffeProperty property);

    <V> void removeProperty(PGiraffeProperty property);

    void removeEdge(PGiraffeEdge edge);
}

