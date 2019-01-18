
package ru.t_trusty.pgiraffe;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.AbstractGraphProvider;
import org.apache.tinkerpop.gremlin.LoadGraphWith.GraphData;
import org.apache.tinkerpop.gremlin.structure.Graph;

import ru.t_trusty.pgiraffe.structure.PGiraffeElement;
import ru.t_trusty.pgiraffe.structure.PGiraffeVertex;

/**
 * @author Alik Khilazhev (https://github.com/alikhil)
 */
public class PGiraffeGraphProvider extends AbstractGraphProvider {

    private static final Set<Class> IMPLEMENTATION = new HashSet<Class>() {{
        add(PGiraffeElement.class);
        add(PGiraffeVertex.class);
    }};

    public void clear(Graph graph, Configuration configuration) throws Exception {

    }

    public Set<Class> getImplementations() {
        return IMPLEMENTATION;
    }

	@Override
	public Map<String, Object> getBaseConfiguration(String graphName, Class<?> test, String testMethodName,
			GraphData loadGraphWith) {
		return new HashMap<string, Object>() {{
            put(Graph.GRAPH, PGiraffeGraph.class.getName());
        }};
	}

}