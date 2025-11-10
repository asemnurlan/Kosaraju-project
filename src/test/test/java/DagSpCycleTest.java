import graph.DagLongestPath;
import graph.DagShortestPath;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.Assert.assertThrows;
public class DagSpCycleTest {
    @Test
    public void throwsOnCycle(){
        int n = 3, s = 0;
        List<DagShortestPath.Edge> E = List.of(
                new DagShortestPath.Edge(0,1,1),
                new DagShortestPath.Edge(1,2,1),
                new DagShortestPath.Edge(2,1,1),
                new DagShortestPath.Edge(0,2,10)
        );
        assertThrows(IllegalArgumentException.class, () -> DagShortestPath.shortestPaths(n, E, s));
        assertThrows(IllegalArgumentException.class, () -> DagLongestPath.longestPaths(n,
                List.of(new DagLongestPath.Edge(0,1,1), new DagLongestPath.Edge(1,2,1), new DagLongestPath.Edge(2,1,1), new DagLongestPath.Edge(0,2,10)), s));
    }
}
