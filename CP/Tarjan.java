import java.util.*;

class Tarjan {
    private int timer = 1;
    private void dfs(int node, int parent, int[] vis, List<List<Integer>> adj, int[] tin, int[] low, List<List<Integer>> bridges) {

        vis[node] = 1;
        tin[node] = low[node] = timer++;
        
        for (int nbr : adj.get(node)) {
            if (nbr == parent) {
                // case 3: don’t go straight back along the same edge
                // directly goes to the else block -> updates its minimum insertion time from its children nodes
                continue;
            }
            if (vis[nbr] == 0) {
                // case 1: tree-edge
                dfs(nbr, node, vis, adj, tin, low, bridges);

                // propagate up the low-link value
                low[node] = Math.min(low[node], low[nbr]);
                
                // bridge check
                if (low[nbr] > tin[node]) {
                    bridges.add(Arrays.asList(node, nbr));
                }
            } else {
                // case 2: back-edge
                low[node] = Math.min(low[node], tin[nbr]);
            }
        }
    }

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        // build adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (List<Integer> edge : connections) {
            int u = edge.get(0), v = edge.get(1);
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] vis = new int[n];    // visited flag
        int[] tin = new int[n];    // discovery times
        int[] low = new int[n];    // low-link values
        List<List<Integer>> bridges = new ArrayList<>();

        // run DFS from node 0 (or loop over all i=0..n-1 if graph may be disconnected)
        dfs(0, -1, vis, adj, tin, low, bridges);

        return bridges;
    }
}
