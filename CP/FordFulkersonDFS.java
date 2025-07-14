import java.util.*;

public class FordFulkersonDFS {
    private final int V;
    private final int[][] residual;
    private boolean[] visited;

    public FordFulkersonDFS(int V) {
        this.V = V;
        this.residual = new int[V][V];
    }

    /**
     * Depth-first search to find an augmenting path.
     * @param u     current node
     * @param t     sink node
     * @param flow  the flow we can still push
     * @return      amount of flow actually pushed (0 if none)
     */
    private int dfs(int u, int t, int flow) {
        if (u == t) 
            return flow;
        visited[u] = true;

        for (int v = 0; v < V; v++) {
            if (!visited[v] && residual[u][v] > 0) {
                // here pushed is bottle neck capacity or path flow.
                int pushed = dfs(v, t, Math.min(flow, residual[u][v])); 
                if (pushed > 0) {
                    // update residual capacities
                    residual[u][v] -= pushed;
                    residual[v][u] += pushed;
                    return pushed;
                }
            }
        }
        return 0;
    }

    /**
     * Computes max-flow using DFS-based Ford–Fulkerson.
     * @param graph adjacency matrix of original capacities
     * @param s     source index
     * @param t     sink index
     * @return      maximum flow value
     */
    public int maxFlow(int[][] graph, int s, int t) {
        // initialize residual graph
        for (int u = 0; u < V; u++)
            for (int v = 0; v < V; v++) 
                residual[u][v] = graph[u][v];

        int maxFlow = 0;
        while (true) {
            visited = new boolean[V];
            int pushed = dfs(s, t, Integer.MAX_VALUE);
            if (pushed == 0) // no more augmenting paths found, ie no more flow possible.
                break;
            maxFlow += pushed;
        }
        return maxFlow;
    }

    public static void main(String[] args) {
        // Example: nodes 0=S,1=A,2=B,3=D,4=C,5=T
        int V = 6;
        int[][] graph = {
            /*S A B D C T*/
            /*S*/ { 0, 8, 0, 3, 0, 0},
            /*A*/ { 0, 0, 9, 0, 0, 0},
            /*B*/ { 0, 0, 0, 0, 7, 2},
            /*D*/ { 0, 0, 0, 0, 4, 0},
            /*C*/ { 0, 0, 0, 0, 0, 5},
            /*T*/ { 0, 0, 0, 0, 0, 0},
        };

        FordFulkersonDFS ff = new FordFulkersonDFS(V);
        int s = 0, t = 5;
        int result = ff.maxFlow(graph, s, t);
        System.out.println("Maximum flow = " + result);
    }
}
