package topo;

import common.Graph;
import common.Metrics;
import common.Stopwatch;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TopoKahn {
    private final Graph dag;
    private final Metrics metrics;

    public TopoKahn(Graph dag, Metrics metrics) {
        this.dag = dag;
        this.metrics = metrics;
    }

    public List<Integer> order() {
        try (Stopwatch sw = new Stopwatch(metrics)) {
            int n = dag.n();
            int[] indeg = new int[n];
            for (int u = 0; u < n; u++) {
                for (int v : dag.neighbors(u)) indeg[v]++;
            }

            Deque<Integer> q = new ArrayDeque<>();
            for (int i = 0; i < n; i++) if (indeg[i] == 0) {
                q.addLast(i);
                metrics.queuePushes++;
            }

            List<Integer> topo = new ArrayList<>(n);
            while (!q.isEmpty()) {
                int u = q.removeFirst();
                metrics.queuePops++;
                topo.add(u);
                for (int v : dag.neighbors(u)) {
                    indeg[v]--;
                    if (indeg[v] == 0) {
                        q.addLast(v);
                        metrics.queuePushes++;
                    }
                }
            }

            if (topo.size() != n) {
                throw new IllegalStateException("Condensation graph contains a cycle.");
            }
            return topo;
        }
    }
}
