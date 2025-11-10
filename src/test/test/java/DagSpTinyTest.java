import graph.DagLongestPath;
import graph.DagShortestPath;
import org.junit.Test;
import org.junit.jupiter.api.Test;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
public class DagSpTinyTest {
    @Test
    public void tinyShortest(){
        int n = 5, s = 0;
        List<DagShortestPath.Edge> E = List.of(
                new DagShortestPath.Edge(0,1,2),
                new DagShortestPath.Edge(0,2,5),
                new DagShortestPath.Edge(1,3,1),
                new DagShortestPath.Edge(2,3,1),
                new DagShortestPath.Edge(3,4,3)
        );
        var run = DagShortestPath.shortestPaths(n, E, s);
        long[] d = run.result.distances();
        assertEquals(0, d[0]);
        assertEquals(2, d[1]);
        assertEquals(5, d[2]);
        assertEquals(3, d[3]);
        assertEquals(6, d[4]);
        assertTrue(run.metrics.relaxCount > 0);
        assertTrue(run.metrics.timeNs >= 0);
        assertEquals(List.of(0,1,3,4), run.result.pathTo(4));
    }

    @Test
    public void tinyLongest(){
        int n = 4, s = 0;
        List<DagLongestPath.Edge> E = List.of(
                new DagLongestPath.Edge(0,1,1),
                new DagLongestPath.Edge(0,2,1),
                new DagLongestPath.Edge(1,3,5),
                new DagLongestPath.Edge(2,3,3)
        );
        var run = DagLongestPath.longestPaths(n, E, s);
        long[] d = run.result.distances();
        assertEquals(0, d[0]);
        assertEquals(1, d[1]);
        assertEquals(1, d[2]);
        assertEquals(6, d[3]);
        assertEquals(List.of(0,1,3), run.result.pathTo(3));
    }
}
