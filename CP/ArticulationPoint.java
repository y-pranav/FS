import java.util.*;

public class ArticulationPoint {
    private int timer = 1;

    private void dfs(int node, int parent, int[] vis, int[] tin, int[] low, int[] isArticulation, ArrayList<ArrayList<Integer>> adj) {
        vis[node] = 1;
        tin[node] = low[node] = timer++;
        int children = 0;

        for (int nbr : adj.get(node)) {
            if (nbr == parent) continue;

            if (vis[nbr] == 0) {
                // Tree-edge
                dfs(nbr, node, vis, tin, low, isArticulation, adj);
                low[node] = Math.min(low[node], low[nbr]);

                // Non-root articulation check:
                // If no nbr node reaches any node above the current 'node', and the 'node' is not a parent node
                if (low[nbr] >= tin[node] && parent != -1) {
                    isArticulation[node] = 1;
                }
                children++;
            } else {
                // Back-edge
                low[node] = Math.min(low[node], tin[nbr]);
            }
        }

        // Root articulation check:
        if (parent == -1 && children > 1) {
            isArticulation[node] = 1;
        }
    }

    /**
     * Returns a list of all articulation points in the given undirected graph.
     * If there are none, returns a list containing only -1.
     *
     * @param n   number of vertices (0-indexed: 0..n-1)
     * @param adj adjacency list of size n
     */
    public ArrayList<Integer> articulationPoints(int n,
                                                 ArrayList<ArrayList<Integer>> adj) {
        int[] vis  = new int[n];   // visited flag
        int[] tin  = new int[n];   // discovery time
        int[] low  = new int[n];   // low-link value
        int[] mark = new int[n];   // 1 if articulation point

        // Run DFS from each unvisited node (to cover disconnected graphs)
        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                dfs(i, -1, vis, tin, low, mark, adj);
            }
        }

        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (mark[i] == 1) {
                ans.add(i);
            }
        }
        if (ans.isEmpty()) {
            ans.add(-1);
        }
        return ans;
    }

    // Example usage
    public static void main(String[] args) {
        int n = 5;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        // sample undirected graph
        adj.get(0).add(1); adj.get(1).add(0);
        adj.get(1).add(2); adj.get(2).add(1);
        adj.get(2).add(0); adj.get(0).add(2);
        adj.get(1).add(3); adj.get(3).add(1);
        adj.get(3).add(4); adj.get(4).add(3);

        ArticulationPoint sol = new ArticulationPoint();
        ArrayList<Integer> arts = sol.articulationPoints(n, adj);
        System.out.println("Articulation points: " + arts);
        // expected output: [1, 3]
    }
}
