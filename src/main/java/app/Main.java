package app;

import common.*;
import scc.*;
import topo.TopoKahn;
import java.io.File;
import java.util.List;
import common.TaskGraphLoader;
import common.TaskGraphLoader.Loaded;


public class Main {
    public static void main(String[] args) throws Exception {
        String jsonPath = (args.length > 0) ? args[0] : "src/main/resources/test.json";
        String key = (args.length > 1) ? args[1] : "small_1";

        System.out.println("CWD = " + System.getProperty("user.dir"));
        System.out.println("Loading key: " + key + " from " + jsonPath);

        Loaded loaded = TaskGraphLoader.load(new File(jsonPath), key);
        Graph g = loaded.graph;

        System.out.println("Loaded graph: n=" + g.n() + ", m=" + g.edgesCount());

        Metrics sccMetrics = new Metrics();
        KosarajuSCC kos = new KosarajuSCC(g, sccMetrics);
        KosarajuSCC.Result scc = kos.run();

        System.out.println("SCC count: " + scc.components.size());
        for (int i = 0; i < scc.components.size(); i++) {
            System.out.println("  comp#" + i + " size=" + scc.components.get(i).size());
        }
        System.out.println("SCC metrics: " + sccMetrics);

        CondensationBuilder.Condensation cond =
                CondensationBuilder.build(g, scc.compOf, scc.components.size());
        Graph dag = cond.dag;
        System.out.println("Condensation DAG: nodes=" + dag.n() + ", edges=" + dag.edgesCount());

        Metrics topoMetrics = new Metrics();
        TopoKahn kahn = new TopoKahn(dag, topoMetrics);
        List<Integer> topo = kahn.order();
        System.out.println("Topo order of components: " + topo);
        System.out.println("Topo metrics: " + topoMetrics);

        System.out.print("Order of original tasks: [");
        boolean first = true;
        for (int cid : topo) {
            List<Integer> vs = cond.compVertices.get(cid);
            vs.sort(Integer::compareTo);
            for (int v : vs) {
                if (!first) System.out.print(", ");
                first = false;
                System.out.print(loaded.rev.get(v));
            }
        }
        System.out.println("]");
    }
}
