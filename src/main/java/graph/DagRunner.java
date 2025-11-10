package graph;

import java.nio.file.*;
import java.util.*;
import com.google.gson.*;

public class DagRunner {
    static class GraphDto {
        int n;
        List<List<Long>> edges;
        String description;
        String density;
        String type;
    }

    public static void main(String[] args) throws Exception {
        String path = args.length > 0 ? args[0] : "data/student2/small2.json";
        System.out.println("== Running on: " + path);

        String json = Files.readString(Path.of(path));
        GraphDto g = new Gson().fromJson(json, GraphDto.class);

        List<DagShortestPath.Edge> E = new ArrayList<>();
        for (var e : g.edges) {
            int u = e.get(0).intValue();
            int v = e.get(1).intValue();
            long w = e.get(2);
            E.add(new DagShortestPath.Edge(u, v, w));
        }

        int source = 0;

        var sp = DagShortestPath.shortestPaths(g.n, E, source);
        System.out.println("[Shortest]");
        System.out.println("dist = " + Arrays.toString(sp.result.distances()));
        System.out.println("relaxCount = " + sp.metrics.relaxCount + ", timeNs = " + sp.metrics.timeNs);

        List<DagLongestPath.Edge> E2 = new ArrayList<>();
        for (var e : E) E2.add(new DagLongestPath.Edge(e.u, e.v, e.w));

        var lp = DagLongestPath.longestPaths(g.n, E2, source);
        System.out.println("[Longest / Critical]");
        long[] ld = lp.result.distances();
        System.out.println("dist = " + Arrays.toString(ld));
        int target = g.n - 1;
        System.out.println("critical length to " + target + " = " + ld[target]);
        System.out.println("critical path to " + target + " = " + lp.result.pathTo(target));
        System.out.println("relaxCount = " + lp.metrics.relaxCount + ", timeNs = " + lp.metrics.timeNs);

        System.out.println("\n[Summary]");
        System.out.printf("dataset=%s n=%d |E|=%d src=%d%n",
                Path.of(path).getFileName(), g.n, g.edges.size(), source);
        System.out.printf("shortest: relax=%d timeNs=%d%n", sp.metrics.relaxCount, sp.metrics.timeNs);
        System.out.printf("longest : relax=%d timeNs=%d%n", lp.metrics.relaxCount, lp.metrics.timeNs);
    }
}
