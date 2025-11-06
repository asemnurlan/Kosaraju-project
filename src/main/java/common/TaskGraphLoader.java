package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

public class TaskGraphLoader {

    public static class RawEdge {
        public Object from;
        public Object to;
        public Double weight;
    }

    public static class RawGraph {
        public List<Object> nodes;
        public List<RawEdge> edges;
    }

    public static class Loaded {
        public final Graph graph;
        public final Map<Object, Integer> id;
        public final List<Object> rev;

        public Loaded(Graph graph, Map<Object, Integer> id, List<Object> rev) {
            this.graph = graph;
            this.id = id;
            this.rev = rev;
        }
    }

    public static Loaded load(File file) throws Exception {
        ObjectMapper om = new ObjectMapper();
        RawGraph rg = om.readValue(file, RawGraph.class);

        Map<Object, Integer> id = new LinkedHashMap<>();
        List<Object> rev = new ArrayList<>();
        for (Object label : rg.nodes) {
            if (!id.containsKey(label)) {
                id.put(label, id.size());
                rev.add(label);
            }
        }

        Graph g = new Graph(id.size());
        for (RawEdge e : rg.edges) {
            Integer u = id.get(e.from);
            Integer v = id.get(e.to);
            if (u == null || v == null) {
                throw new IllegalArgumentException("Unknown node in edge: " + e.from + " -> " + e.to);
            }
            g.addEdge(u, v);
        }

        return new Loaded(g, id, rev);
    }
}
