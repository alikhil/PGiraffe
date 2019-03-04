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
    private Map<Vertex, Set<Edge>> vertexEdges = new ConcurrentHashMap<>();

    public SimpleStoreEngine(EntityManagerFactory entityManagerFactory) {

        this.emf = entityManagerFactory;
    }

    @Override
    public void saveEdge(PGiraffeEdge edge) {
        edges.put(edge.id(), edge);

        vertexEdges.putIfAbsent(edge.inVertex(), new HashSet<>());
        vertexEdges.putIfAbsent(edge.outVertex(), new HashSet<>());

        vertexEdges.get(edge.inVertex()).add(edge);
        vertexEdges.get(edge.outVertex()).add(edge);
    }

    @Override
    public void saveVertex(PGiraffeVertex vertex) {
        vertices.put(vertex.id(), vertex);

        // but what is vertex?
        // we can ensure every prop it has
        // or we can ensure only label

        // p.s.
        // all props are ensured in time of their creation
    }

    @Override
    public void removeVertex(PGiraffeVertex vertex) {
        if (properties.containsKey(vertex)) {
            properties.remove(vertex);
        }
        // also should remove all properties of vertex
    }

    @Override
    public Iterator<Edge> getEdges(PGiraffeVertex vertex, Direction direction, String... edgeLabels) {
        return vertexEdges.getOrDefault(vertex, new HashSet<>())
                .stream()
                .filter(edge -> matchesDirection(vertex, edge, direction))
                .filter(edge -> Arrays.stream(edgeLabels).anyMatch(edge.label()::equals))
                .iterator();
    }

    private boolean matchesDirection(PGiraffeVertex vertex, Edge edge, Direction direction) {
        // todo: ensure that it's true
        return direction == Direction.BOTH
                || (edge.inVertex().id() == vertex.id() && direction == Direction.IN)
                || (edge.outVertex().id() == vertex.id() && direction == Direction.OUT);
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
        if (properties.containsKey(property.element())) {
            Map<String, Object> mp = properties.get(property.element());
            // todo: remove checks, they are redundant
            if (mp.containsKey(property.key())) {
                mp.remove(property.key());
            }
        }
    }

    @Override
    public void removeEdge(PGiraffeEdge edge) {
        // todo: finish writing this method
        vertexEdges.remove(edge.inVertex());
        vertexEdges.remove(edge.outVertex());
    }
}
