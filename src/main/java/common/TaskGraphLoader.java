package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;

public class TaskGraphLoader {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RawEdge {
        public Object from;
        public Object to;
        public Double weight; // опционально
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
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

    private static final ObjectMapper OM = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static Loaded load(File file, String key) throws Exception {
        Map<String, RawGraph> map = tryReadAsMap(file);
        if (map != null) {
            if (map.isEmpty()) {
                throw new IllegalArgumentException("JSON map is empty");
            }
            RawGraph rg;
            if (key != null) {
                rg = map.get(key);
                if (rg == null) {
                    throw new IllegalArgumentException("Key '" + key + "' not found. Available: " + map.keySet());
                }
            } else {
                rg = map.values().iterator().next(); // первый попавшийся
            }
            return buildLoaded(rg);
        }

        RawGraph rg = OM.readValue(file, RawGraph.class);
        return buildLoaded(rg);
    }

    private static Map<String, RawGraph> tryReadAsMap(File file) throws IOException {
        try {
            return OM.readValue(file, new TypeReference<Map<String, RawGraph>>() {});
        } catch (MismatchedInputException notAMap) {
            return null;
        }
    }

    private static Loaded buildLoaded(RawGraph rg) {
        if (rg == null) throw new IllegalArgumentException("Graph section is null");
        if (rg.nodes == null) rg.nodes = List.of();
        if (rg.edges == null) rg.edges = List.of();

        Map<Object, Integer> id = new LinkedHashMap<>();
        List<Object> rev = new ArrayList<>();

        for (Object label : rg.nodes) {
            if (!id.containsKey(label)) {
                id.put(label, id.size());
                rev.add(label);
            }
        }

        for (RawEdge e : rg.edges) {
            if (!id.containsKey(e.from)) { id.put(e.from, id.size()); rev.add(e.from); }
            if (!id.containsKey(e.to))   { id.put(e.to,   id.size()); rev.add(e.to);   }
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
