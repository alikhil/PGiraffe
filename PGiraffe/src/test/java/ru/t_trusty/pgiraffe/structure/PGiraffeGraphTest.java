package ru.t_trusty.pgiraffe.structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alik Khilazhev (https://github.com/alikhil)
 */

public class PGiraffeGraphTest {

    @Test
    public void shouldBeCreatableTest() {
        PGiraffeGraph graph = PGiraffeGraph.open();
        Assert.assertNotEquals(null, graph);
    }

    // <property name="hibernate.dialect" value="org.hibernate.dialect.HSQLDialect"
    // />
    // <property name="hibernate.connection.driver_class"
    // value="org.hsqldb.jdbcDriver" />
    // <property name="hibernate.connection.url" value="jdbc:hsqldb:mem:testdb" />
    // <property name="hibernate.connection.username" value="sa" />
    // <property name="hibernate.connection.password" value="" />
    // <property name="hibernate.hbm2ddl.auto" value="update" />
    @Test
    public void configureConnectionToPG() {
        // how to override persistence.xml config
        // http://docs.jboss.org/hibernate/stable/entitymanager/reference/en/html/configuration.html
        Map<String, Object> configOverrides = new HashMap<String, Object>();

        configOverrides.put("hibernate.hbm2ddl.auto", "create-drop");
        configOverrides.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        configOverrides.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
        configOverrides.put("hibernate.connection.username", "postgres");
        configOverrides.put("hibernate.connection.password", "1234");
        configOverrides.put("hibernate.connection.url", "jdbc:postgresql://localhost:5454/postgres");

        // manager 1 is hardcoded in persistence.xml
        EntityManagerFactory programmaticEmf = Persistence.createEntityManagerFactory("manager1", configOverrides);
        EntityManager em = programmaticEmf.createEntityManager();

        // see examples
        // https://www.programcreek.com/java-api-examples/?class=javax.persistence.EntityManager&method=createQuery

        Person person = new Person();
        person.setAge(30);
        person.setId(1L);
        person.setName("John Doe");

        em.getTransaction().begin();
        em.persist(person);
        
        em.getTransaction().commit();
        
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE id=1", Person.class);
        List<Person> personList = query.getResultList();

        Assert.assertEquals(1, personList.size());
        Assert.assertEquals("John Doe", personList.get(0).getName());
        
        Person persisted = em.find(Person.class, 1L);

        Assert.assertNotNull(persisted);
    }

    @Test
    public void testSimpleIdManager() {
        PGiraffeGraph graph = PGiraffeGraph.open();
        PGiraffeGraph.IdManager manager = new PGiraffeGraph.SimpleIdManager();

        // assuming that first id is always 1
        Assert.assertEquals(new Long(1), manager.getNextId(graph, PGiraffeGraph.StoreType.EDGE));
        Assert.assertEquals(new Long(1), manager.getNextId(graph, PGiraffeGraph.StoreType.PROPERTY));
        Assert.assertEquals(new Long(2), manager.getNextId(graph, PGiraffeGraph.StoreType.EDGE));

    }

}