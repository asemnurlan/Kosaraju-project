Assignment 4 — Task Graph Analysis (SCC → Condensation → DAG SP/LP)

🎯 Project Overview
This project analyses **directed task graphs** by computing:
1. **Strongly Connected Components (SCC)** using *Kosaraju’s algorithm*  
2. **Condensation DAG** (each SCC becomes a single node)  
3. **Topological Order** using *Kahn’s algorithm*  
4. **Shortest and Longest Paths** in the resulting DAG (critical path analysis)

Implemented in Java 17 using a modular structure:
- `graph.scc` – KosarajuSCC  
- `graph.topo` – TopoKahn  
- `graph.dagsp` – DAG shortest / longest path  
- `common` – Graph structure, Metrics, Stopwatch, Loader, CondensationBuilder  
- `app` – Main entry point  

---

Repository Structure
project-root/
│
├─ data/
│ ├─ tasks.json # All main datasets (small_1..large_3)
│ └─ student2/
│ ├─ small_2.json
│ ├─ medium_2.json
│ └─ large_2.json
│
├─ src/
│ ├─ main/java/
│ │ ├─ app/Main.java
│ │ ├─ common/{Graph,Metrics,Stopwatch,TaskGraphLoader,CondensationBuilder}.java
│ │ ├─ graph/scc/KosarajuSCC.java
│ │ ├─ graph/topo/TopoKahn.java
│ │ └─ graph/dagsp/{DagShortest.java,DagLongest.java}.java
│ └─ test/java/
│ ├─ graph/scc/KosarajuSCCTest.java
│ ├─ graph/topo/TopoKahnTest.java
│ └─ graph/dagsp/DagSpTest.java
│
└─ README.md

____

Algorithms
1️⃣ Strongly Connected Components — Kosaraju
Two-pass DFS with metrics counting:
dfsVisits, edgesSeen
timeNanos measured by Stopwatch

2️⃣ Condensation Builder
Builds DAG from SCCs, removing parallel edges and collecting minimal weights.

3️⃣ Topological Sort — Kahn’s Algorithm
Uses queue; counts queuePushes, queuePops
Ensures DAG validity (throws exception if cycle detected)

4️⃣ Shortest Paths in DAG (Student 2)
Process vertices in topological order
Relax all outgoing edges
Output: distance array, parent array, reconstructed path
Metrics: relaxations, timeNanos

5️⃣ Longest Paths in DAG (Student 2)
Computed by max-DP over topological order
Alternatively, inversion of weights (w' = −w)
Output: length of critical path and the path itself
