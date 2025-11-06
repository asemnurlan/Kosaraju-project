package scc;

import common.Graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CondensationBuilder {
    public static class Condensation {
        public final Graph dag;
        public final List<List<Integer>> compVertices;

        public Condensation(Graph dag, List<List<Integer>> compVertices) {
            this.dag = dag;
            this.compVertices = compVertices;
        }
    }

    public static Condensation build(Graph g, int[] compOf, int compsCount) {
        Graph dag = new Graph(compsCount);

        @SuppressWarnings("unchecked")
        Set<Integer>[] seen = new HashSet[compsCount];
        for (int i = 0; i < compsCount; i++) seen[i] = new HashSet<>();

        for (int u = 0; u < g.n(); u++) {
            int cu = compOf[u];
            for (int v : g.neighbors(u)) {
                int cv = compOf[v];
                if (cu != cv && seen[cu].add(cv)) {
                    dag.addEdge(cu, cv);
                }
            }
        }

        List<List<Integer>> compVertices = new ArrayList<>(compsCount);
        for (int i = 0; i < compsCount; i++) {
            compVertices.add(new ArrayList<Integer>());
        }
        for (int v = 0; v < g.n(); v++) {
            compVertices.get(compOf[v]).add(v);
        }

        return new Condensation(dag, compVertices);
    }
}

