package ru.t_trusty.pgiraffe.structure;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import javax.persistence.EntityManagerFactory;

public class SimpleStoreEngine implements  StoreEngine {

    private final EntityManagerFactory emf;

    public SimpleStoreEngine(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    @Override
    public void saveEdge(Edge edge) {

    }

    @Override
    public void saveVertex(Vertex vertex) {
        // but what is vertex?
        // we can ensure every prop it has
        // or we can ensure only label
    }
}
