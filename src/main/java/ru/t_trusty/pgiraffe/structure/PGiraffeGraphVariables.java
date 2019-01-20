package ru.t_trusty.pgiraffe.structure;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.util.GraphVariableHelper;
import org.apache.tinkerpop.gremlin.structure.util.StringFactory;

/**
 * @author Alik Khilazhev (https://github.com/alikhil)
 */
public final class PGiraffeGraphVariables implements Graph.Variables {

    private final Map<String, Object> variables = new ConcurrentHashMap<>();

    public PGiraffeGraphVariables() {
    }

    @Override
    public Set<String> keys() {
        return this.variables.keySet();
    }

    @Override
    public <R> Optional<R> get(String key) {
        return Optional.ofNullable((R) this.variables.get(key));
    }

    @Override
    public void set(String key, Object value) {
        GraphVariableHelper.validateVariable(key, value);
        this.variables.put(key, value);
    }

    @Override
    public void remove(String key) {
        this.variables.remove(key);
    }

    @Override
    public String toString() {
        return StringFactory.graphVariablesString(this);
    }
}