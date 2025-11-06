package java.topo;

import common.Graph;
import common.Metrics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import topo.TopoKahn;
import java.util.List;

public class TopoKahnTest {
    @Test
    void topoOrderForDAG() {
        Graph dag = new Graph(4);
        dag.addEdge(0, 1);
        dag.addEdge(0, 2);
        dag.addEdge(2, 3);

        TopoKahn kahn = new TopoKahn(dag, new Metrics());
        List<Integer> order = kahn.order();

        Assertions.assertEquals(4, order.size());
        int p0 = order.indexOf(0);
        int p1 = order.indexOf(1);
        int p2 = order.indexOf(2);
        int p3 = order.indexOf(3);
        Assertions.assertTrue(p0 < p1 && p0 < p2 && p2 < p3);
    }
}

