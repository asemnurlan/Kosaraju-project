package graph;
import java.util.*;
public class DagLongestPath {
    public static class Edge { public final int u,v; public final long w; public Edge(int u,int v,long w){ this.u=u; this.v=v; this.w=w; } }


    public static class Run {
        public final PathResult result;
        public final DagMetrics metrics;
        public Run(PathResult r, DagMetrics m){ this.result = r; this.metrics = m; }
    }

    public static Run longestPaths(int n, List<Edge> edges, int source){
        if (source < 0 || source >= n) throw new IllegalArgumentException("source out of range");
        long t0 = System.nanoTime();
        DagMetrics M = new DagMetrics();



        List<List<Edge>> adj = new ArrayList<>(n);
        int[] indeg = new int[n];
        for (int i=0;i<n;i++) adj.add(new ArrayList<>());
        for (Edge e: edges){
            if (e.u<0||e.u>=n||e.v<0||e.v>=n) throw new IllegalArgumentException("edge endpoint out of range");
            adj.get(e.u).add(e);
            indeg[e.v]++;
        }

        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i=0;i<n;i++) if (indeg[i]==0) q.add(i);
        int[] topo = new int[n]; int t=0;
        while(!q.isEmpty()){
            int u = q.removeFirst();
            topo[t++] = u;
            for (Edge e: adj.get(u)) if (--indeg[e.v]==0) q.add(e.v);
        }
        if (t != n) throw new IllegalArgumentException("Graph is not a DAG (cycle detected)");


        long[] dist = new long[n];
        int[] parent = new int[n];
        Arrays.fill(dist, PathResult.NINF);
        Arrays.fill(parent, -1);
        dist[source] = 0;


        for (int i=0;i<n;i++){
            int u = topo[i];
            if (dist[u] <= PathResult.NINF/2) continue;
            for (Edge e: adj.get(u)){
                long nd = dist[u] + e.w;
                M.relaxCount++;
                if (nd > dist[e.v]){
                    dist[e.v] = nd;
                    parent[e.v] = u;
                }
            }
        }


        M.timeNs = System.nanoTime() - t0;
        return new Run(new PathResult(n, source, dist, parent), M);
    }
}
