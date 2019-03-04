package ru.t_trusty.pgiraffe.structure;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.BaseConfiguration;

import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;
import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.structure.util.ElementHelper;
import org.apache.tinkerpop.gremlin.structure.util.StringFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.*;

/**
 * @author Alik Khilazhev (https://github.com/alikhil)
 */
@Graph.OptIn(Graph.OptIn.SUITE_STRUCTURE_STANDARD)
@Graph.OptIn(Graph.OptIn.SUITE_STRUCTURE_INTEGRATE)
// @Graph.OptIn(Graph.OptIn.SUITE_PROCESS_STANDARD)
// @Graph.OptIn(Graph.OptIn.SUITE_PROCESS_COMPUTER)
public final class PGiraffeGraph implements Graph {

    private static final Configuration EMPTY_CONFIGURATION = new BaseConfiguration() {
        {
            this.setProperty(Graph.GRAPH, PGiraffeGraph.class.getName());
        }
    };
    public static final String GREMLIN_PGIRAFFE_POSTGRES_URL = "gremlin.pgiraffe.postgres.url";
    public static final String GREMLIN_PGIRAFFE_GRAPH_NAME = "gremlin.pgiraffe.graph.name";

    private final PGiraffeFeatures features = new PGiraffeFeatures();
    private final EntityManagerFactory entityManagerFactory;
    private final IdManager idManager;
    private final StoreEngine engine;

    private String name;
    private Configuration configuration;

    private EntityManagerFactory getEntityManagerFactory(final Configuration configuration) {
        Map<String, Object> configOverrides = new HashMap<String, Object>();

        configOverrides.put("hibernate.hbm2ddl.auto", "create-drop");
        configOverrides.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        configOverrides.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
        configOverrides.put("hibernate.connection.username", "postgres");
        configOverrides.put("hibernate.connection.password", "1234");
        configOverrides.put("hibernate.connection.url", "jdbc:postgresql://localhost:5454/postgres");

        // manager 1 is hardcoded in persistence.xml
        return Persistence.createEntityManagerFactory("manager1", configOverrides);
    }

    private PGiraffeGraph(final Configuration configuration) {
        this.configuration = configuration;
        this.entityManagerFactory = getEntityManagerFactory(this.configuration);
        this.name = configuration.getString(GREMLIN_PGIRAFFE_GRAPH_NAME, "pgiraffe");
        this.idManager = new SimpleIdManager();
        this.engine = new SimpleStoreEngine(this.entityManagerFactory);
    }

    public static PGiraffeGraph open() {
        return open(EMPTY_CONFIGURATION);
    }

    public static PGiraffeGraph open(final Configuration configuration) {
        return new PGiraffeGraph(configuration);
    }

    @Override
    public Vertex addVertex(final Object... keyValues) {
        ElementHelper.legalPropertyKeyValueArray(keyValues);
        Object idValue = ElementHelper.getIdValue(keyValues).orElse(null);
        final String label = ElementHelper.getLabelValue(keyValues).orElse(Vertex.DEFAULT_LABEL);

        if (null != idValue) {
            if (this.vertexExists(idValue))
                throw Exceptions.vertexWithIdAlreadyExists(idValue);
        } else {
            idValue = nextId(StoreType.VERTEX);
        }

        final PGiraffeVertex vertex = new PGiraffeVertex(idValue, label, this);
        this.engine.saveVertex(vertex);

        ElementHelper.attachProperties(vertex, VertexProperty.Cardinality.list, keyValues);
        return vertex;
    }

    private boolean vertexExists(Object idValue) {
        // TODO: implement this
        return false;
    }


    @Override
    public <C extends GraphComputer> C compute(Class<C> graphComputerClass) throws IllegalArgumentException {
        return null;
    }

    @Override
    public GraphComputer compute() throws IllegalArgumentException {
        return null;
    }

    @Override
    public Iterator<Vertex> vertices(Object... vertexIds) {
        return null;
    }

    @Override
    public Iterator<Edge> edges(Object... edgeIds) {
        return null;
    }

    @Override
    public Transaction tx() {
        // TODO: add transactions support
        throw Exceptions.transactionsNotSupported();
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Variables variables() {
        // TODO: add variables support -
        // http://tinkerpop.apache.org/docs/current/reference/#graph-variables
        // low priority
        throw Exceptions.variablesNotSupported();
    }

    @Override
    public Configuration configuration() {
        return this.configuration;
    }

    @Override
    public Features features() {
        return features;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean edgeExists(Object idValue) {
        return false;
    }



    public IdManager getIdManager() {
        return idManager;
    }

    public Long nextId(StoreType store) {
        return idManager.getNextId(this, store);
    }


    public StoreEngine engine() {
        return engine;
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

        // // TODO: add default?
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

    public class PGiraffeEdgeFeatures implements Features.EdgeFeatures {
        // TODO: implement it all correctly
        public PGiraffeEdgeFeatures() {
        }
    }

    public class PGiraffeGraphFeatures implements Features.GraphFeatures {
        public PGiraffeGraphFeatures() {
        }
    }

    public enum StoreType {
        ID("ID"),
        EDGE("EDGE"),
        VERTEX("VERTEX"),
        PROPERTY("PROPERTY")
        ;

        private final String store;

        StoreType(final String store) {
            this.store = store;
        }

        @Override
        public String toString() {
            return this.store;
        }
    }

    public interface IdManager {
        Long getNextId(final PGiraffeGraph graph, StoreType type);
    }

    public static class SimpleIdManager implements IdManager {

        public static final String NamedQuery_nextId = "nextId";

        public SimpleIdManager() {
            super();
        }

        @Override
        public Long getNextId(PGiraffeGraph graph, StoreType type) {
            EntityManager em = graph.getEntityManager();

            StoredProcedureQuery query = em
                    .createStoredProcedureQuery(NamedQuery_nextId)
                    .registerStoredProcedureParameter("store", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("graph", String.class, ParameterMode.IN)
                    .setParameter("store", type.toString())
                    .setParameter("graph", graph.getName())
                    .setHint("org.hibernate.cacheable", "false");

            query.execute();

            Object result = query.getSingleResult();
            em.close(); // if you will forget to do it; there will be some problems with cache
            return ((BigInteger) result).longValue();
        }
    }
}