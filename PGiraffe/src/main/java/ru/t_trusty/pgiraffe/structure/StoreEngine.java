package ru.t_trusty.pgiraffe.structure;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public interface StoreEngine {
    void saveEdge(Edge edge);

    void saveVertex(Vertex vertex);
}

