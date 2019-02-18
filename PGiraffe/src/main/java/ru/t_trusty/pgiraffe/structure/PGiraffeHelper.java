package ru.t_trusty.pgiraffe.structure;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.util.ElementHelper;

public final class PGiraffeHelper {

    protected static Edge addEdge(final PGiraffeGraph graph, final PGiraffeVertex outVertex, final PGiraffeVertex inVertex, final String label, final Object... keyValues ) {
        ElementHelper.validateLabel(label);
        ElementHelper.legalPropertyKeyValueArray(keyValues);

        Object idValue = ElementHelper.getIdValue(keyValues).orElse(null);

        if (null != idValue) {
            if (graph.edgeExists(idValue)) {
                throw Graph.Exceptions.edgeWithIdAlreadyExists(idValue);
            } else {
                idValue = graph.nextId(PGiraffeGraph.StoreType.EDGE);
            }
        }
        final Edge edge = new PGiraffeEdge(idValue, outVertex, label, inVertex);
        ElementHelper.attachProperties(edge, keyValues);
        graph.saveEdge(edge);
        PGiraffeHelper.addOutEdge(outVertex, label, edge);
        PGiraffeHelper.addInEdge(inVertex, label, edge);
        return null;
    }

    private static void addInEdge(PGiraffeVertex inVertex, String label, Edge edge) {
//        edge.graph().
    }

    private static void addOutEdge(PGiraffeVertex outVertex, String label, Edge edge) {
    }
}
