package ru.t_trusty.pgiraffe.structure;

import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.structure.util.ElementHelper;
import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils;

import javax.persistence.EntityManagerFactory;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SimpleStoreEngine implements  StoreEngine {

    private final EntityManagerFactory emf;
    private Map<Object, PGiraffeEdge> edges = new ConcurrentHashMap<>();
    private Map<Object, PGiraffeVertex> vertices = new ConcurrentHashMap<>();
    private Map<Element, Map<String, Object>> properties = new ConcurrentHashMap<>();

    public SimpleStoreEngine(EntityManagerFactory entityManagerFactory) {

        this.emf = entityManagerFactory;
    }

    @Override
    public void saveEdge(PGiraffeEdge edge) {
        edges.put(edge.id(), edge);
    }

    @Override
    public void saveVertex(PGiraffeVertex vertex) {
        vertices.put(vertex.id(), vertex);

        // but what is vertex?
        // we can ensure every prop it has
        // or we can ensure only label
    }

    @Override
    public void removeVertex(PGiraffeVertex pGiraffeVertex) {
        // also should remove all properties of vertex
    }

    @Override
    public Iterator<Edge> getEdges(PGiraffeVertex pGiraffeVertex, Direction direction, String... edgeLabels) {
        return null;
    }

    @Override
    public <V> Iterator<VertexProperty<V>> getVertexProperties(PGiraffeVertex vertex, String... propertyKeys) {
        return null;
//        if (propertyKeys.length == 1) {
//            final List<VertexProperty> properties = this.properties.getOrDefault(propertyKeys[0], Collections.emptyList());
//            if (properties.size() == 1) {
//                return IteratorUtils.of(properties.get(0));
//            } else if (properties.isEmpty()) {
//                return Collections.emptyIterator();
//            } else {
//                return (Iterator) new ArrayList<>(properties).iterator();
//            }
//        } else
//            return (Iterator) this.properties
//                    .entrySet()
//                    .stream()
//                    .filter(entry -> ElementHelper.keyExists(entry.getKey(), propertyKeys))
//                    .flatMap(entry -> entry.getValue().stream())
//                    .collect(Collectors.toList())
//                    .iterator();
    }

    @Override
    public <V> void saveProperty(PGiraffeProperty property) {
        properties.putIfAbsent(property.element(), new ConcurrentHashMap<String, Object>());
        properties
                .get(property.element())
                .put(property.key(), property.value());

    }

    @Override
    public <V> void removeProperty(PGiraffeProperty property) {
        // seems like we don't need such method
        if (properties.containsKey(property.element())) {
            Map<String, Object> mp = properties.get(property.element());
            if (mp.containsKey(property.key())) {
                mp.remove(property.key());
            }
        }
    }

    @Override
    public void removeEdge(PGiraffeEdge edge) {

    }
}
