import graph.DagShortestPath;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
public class DagSpNegativeTest {
    @Test
    public void negativeWeightsAreFineInDag(){
        int n = 4, s = 0;
        List<DagShortestPath.Edge> E = List.of(
                new DagShortestPath.Edge(0,1,2),
                new DagShortestPath.Edge(1,2,-5),
                new DagShortestPath.Edge(2,3,1)
        );
        var run = DagShortestPath.shortestPaths(n, E, s);
        long[] d = run.result.distances();
        assertEquals(-2, d[3]);
        assertEquals(List.of(0,1,2,3), run.result.pathTo(3));
    }
}
