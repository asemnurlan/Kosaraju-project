package java.scc;

import common.Graph;
import common.Metrics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import scc.KosarajuSCC;

public class KosarajuSCCTest {
    @Test
    void simpleCycleAndTail() {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 4);

        KosarajuSCC.Result res = new KosarajuSCC(g, new Metrics()).run();

        Assertions.assertEquals(3, res.components.size()); // {0,1,2}, {3}, {4}
        int c0 = res.compOf[0];
        int c1 = res.compOf[1];
        int c2 = res.compOf[2];
        Assertions.assertEquals(c0, c1);
        Assertions.assertEquals(c1, c2);
        Assertions.assertNotEquals(c0, res.compOf[3]);
        Assertions.assertNotEquals(res.compOf[3], res.compOf[4]);
    }
}