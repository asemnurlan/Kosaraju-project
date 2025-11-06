package scc;

import common.Graph;
import common.Metrics;
import common.Stopwatch;
import java.util.*;

public class KosarajuSCC {
    private final Graph g;
    private final Metrics metrics;

    public KosarajuSCC(Graph g, Metrics metrics) {
        this.g = g;
        this.metrics = metrics;
    }

    public static class Result {
        public final List<List<Integer>> components;
        public final int[] compOf;

        public Result(List<List<Integer>> components, int[] compOf) {
            this.components = components;
            this.compOf = compOf;
        }
    }

    public Result run() {
        try (Stopwatch sw = new Stopwatch(metrics)) {
            int n = g.n();
            boolean[] vis = new boolean[n];
            Deque<Integer> order = new ArrayDeque<>();

            for (int v = 0; v < n; v++) if (!vis[v]) dfs1(v, vis, order);

            Graph gr = g.reversed();
            Arrays.fill(vis, false);
            List<List<Integer>> comps = new ArrayList<>();
            int[] compOf = new int[n];
            Arrays.fill(compOf, -1);

            while (!order.isEmpty()) {
                int v = order.pop();
                if (vis[v]) continue;
                List<Integer> cur = new ArrayList<>();
                dfs2(gr, v, vis, cur);
                int id = comps.size();
                for (int x : cur) compOf[x] = id;
                comps.add(cur);
            }
            return new Result(comps, compOf);
        }
    }

    private void dfs1(int v, boolean[] vis, Deque<Integer> order) {
        vis[v] = true;
        metrics.dfsVisits++;
        for (int to : g.neighbors(v)) {
            metrics.edgesSeen++;
            if (!vis[to]) dfs1(to, vis, order);
        }
        order.push(v);
    }

    private void dfs2(Graph gr, int v, boolean[] vis, List<Integer> cur) {
        vis[v] = true;
        cur.add(v);
        metrics.dfsVisits++;
        for (int to : gr.neighbors(v)) {
            metrics.edgesSeen++;
            if (!vis[to]) dfs2(gr, to, vis, cur);
        }
    }
}
