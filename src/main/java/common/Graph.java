package common;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int n;
    private final List<List<Integer>> adj;

    public Graph(int n) {
        this.n = n;
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public int n() { return n; }
    public List<Integer> neighbors(int u) { return adj.get(u); }
    public void addEdge(int u, int v) { adj.get(u).add(v); }

    public Graph reversed() {
        Graph r = new Graph(n);
        for (int u = 0; u < n; u++) {
            for (int v : adj.get(u)) r.addEdge(v, u);
        }
        return r;
    }

    public int edgesCount() {
        int m = 0;
        for (List<Integer> list : adj) m += list.size();
        return m;
    }

    public List<List<Integer>> adjacency() { return adj; }
}
