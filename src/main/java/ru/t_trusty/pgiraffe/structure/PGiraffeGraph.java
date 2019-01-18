import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.apache.tinkerpop.gremlin.structure.util.StringFactory;

/**
 * @author Alik Khilazhev (https://github.com/alikhil)
 */
@Graph.OptIn(Graph.OptIn.SUITE_STRUCTURE_STANDARD)
@Graph.OptIn(Graph.OptIn.SUITE_STRUCTURE_INTEGRATE)
@Graph.OptIn(Graph.OptIn.SUITE_PROCESS_STANDARD)
@Graph.OptIn(Graph.OptIn.SUITE_PROCESS_COMPUTER)
public final class PGiraffeGraph implements Graph {

    private final PGiraffeFeatures features = new PGiraffeFeatures();
    
    public Vertex addVertex(Object... keyValues) {
        return null;
    }

    public <C extends GraphComputer> C compute(Class<C> graphComputerClass) throws IllegalArgumentException {
        return null;
    }

    public GraphComputer compute() throws IllegalArgumentException {
        return null;
    }

    public Iterator<Vertex> vertices(Object... vertexIds) {
        return null;
    }

    public Iterator<Edge> edges(Object... edgeIds) {
        return null;
    }

    public Transaction tx() {
        // TODO: add transactions support
        throw Exceptions.transactionsNotSupported()
    }

    public void close() throws Exception {

    }

	public Variables variables() {
        // TODO: add variables support - http://tinkerpop.apache.org/docs/current/reference/#graph-variables
        // low priority
        throw Exceptions.variablesNotSupported();
	}

	public Configuration configuration() {
		return null;
	}

    @Override
    public Features features() {
        return features;
    }

    public class PGiraffeFeatures implements Features {
        private final PGiraffeGraphFeatures graphFeatures = new PGiraffeGraphFeatures();
        private final PGiraffeEdgeFeatures edgeFeatures = new PGiraffeEdgeFeatures();
        private final PGiraffeVertexFeatures vertexFeatures = new PGiraffeVertexFeatures();

        private PGiraffeFeatures() {
        }

        @Override
        public GraphFeatures graph() {
            return graphFeatures;
        }
        @Override
        public EdgeFeatures edge() {
            return edgeFeatures;
        }

        @Override
        public VertexFeatures vertex() {
            return vertexFeatures;
        }

        @Override
        public String toString() {
            return StringFactory.featureString(this);
        }
    }

    public class PGiraffeVertexFeatures implements Features.VertexFeatures {
        
        private final PGiraffeVertexPropertyFeatures vertexPropertyFeatures = new PGiraffeVertexPropertyFeatures();

        private PGiraffeVertexFeatures() {
        }

        @Override
        public Features.VertexPropertyFeatures properties() {
            return vertexPropertyFeatures;
        }

        @Override
        public boolean supportsCustomIds() {
            // TODO: update this method or remove comment
            return false;
        }

        @Override
        public boolean willAllowId(final Object id) {
            // TODO: add true checking with some vertexIdManager
            return true;
        }

        // @Override
        // public VertexProperty.Cardinality getCardinality(final String key) {
        //     // TODO: add default?
        // }
    }

    public class PGiraffeVertexPropertyFeatures implements Features.VertexPropertyFeatures {
        private PGiraffeVertexPropertyFeatures() {
        }

        @Override
        public boolean supportsCustomIds() {
            return false;
        }

        @Override
        public boolean willAllowId(final Object id) {
             // TODO: add true checking with some vertexIdManager
             return true;
        }
    }
 }