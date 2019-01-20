package ru.t_trusty.pgiraffe.structure;

import org.apache.tinkerpop.gremlin.GraphProviderClass;
import org.apache.tinkerpop.gremlin.structure.StructureStandardSuite;
import org.junit.runner.RunWith;

import ru.t_trusty.pgiraffe.PGiraffeGraphProvider;

/**
 * Executes the Standard Gremlin Structure Test Suite using PGiraffeGraph.
 *
 * @author Alik Khilazhev (https://github.com/alikhil)
 */

@RunWith(StructureStandardSuite.class)
@GraphProviderClass(provider = PGiraffeGraphProvider.class, graph = PGiraffeGraph.class)
public class PGiraffeGraphStructureStandardTest {

}
