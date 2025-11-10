package graph;
import java.util.*;

public class PathResult {
    private final int n;
    private final long[] dist;
    private final int[] parent;
    private final int source;


    public static final long INF = Long.MAX_VALUE / 4;
    public static final long NINF = Long.MIN_VALUE / 4;


    public PathResult(int n, int source, long[] dist, int[] parent){
        this.n = n;
        this.source = source;
        this.dist = dist;
        this.parent = parent;
    }


    public long[] distances(){ return dist; }
    public int[] parents(){ return parent; }
    public int source(){ return source; }
    public int n(){ return n; }

    public List<Integer> pathTo(int v){
        if (v < 0 || v >= n) throw new IllegalArgumentException("v out of range");
        if (dist[v] >= INF/2 || dist[v] <= NINF/2) return Collections.emptyList();
        LinkedList<Integer> stack = new LinkedList<>();
        for (int cur = v; cur != -1; cur = parent[cur]) stack.addFirst(cur);
        if (stack.getFirst() != source) return Collections.emptyList();
        return stack;
    }
}
