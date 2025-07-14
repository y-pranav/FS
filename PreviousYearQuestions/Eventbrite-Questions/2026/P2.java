import java.util.*;

public class P2 {

    /*
     * Complete the 'getNumPairs' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER serverNodes
     *  2. INTEGER_ARRAY serverFrom
     *  3. INTEGER_ARRAY serverTo
     *  4. INTEGER_ARRAY serverWeight
     *  5. INTEGER signalSpeed
     */

    public static List<Integer> getNumPairs(int n,
                                            List<Integer> serverFrom,
                                            List<Integer> serverTo,
                                            List<Integer> serverWeight,
                                            int signalSpeed) {
        // 1‑indexed adjacency list: (neighbor, weight)
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < serverFrom.size(); i++) {
            int u = serverFrom.get(i);
            int v = serverTo.get(i);
            int w = serverWeight.get(i);
            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w});
        }

        List<Integer> result = new ArrayList<>();
        // For each server j = 1..n
        for (int j = 1; j <= n; j++) {
            boolean[] seen = new boolean[n+1];
            Queue<int[]> q = new LinkedList<>();
            // push (node, dist from j)
            q.offer(new int[]{j, 0});
            seen[j] = true;
            int reachableCount = 0;

            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int node = cur[0], dist = cur[1];
                if (node != j && dist % signalSpeed == 0) {
                    reachableCount++;
                }
                for (int[] e : adj.get(node)) {
                    int nxt = e[0], w = e[1];
                    if (!seen[nxt]) {
                        seen[nxt] = true;
                        q.offer(new int[]{nxt, dist + w});
                    }
                }
            }

            // ordered pairs among reachableCount nodes:
            // k * (k - 1)
            result.add(reachableCount * (reachableCount - 1));
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nodes = sc.nextInt();
        int edges = sc.nextInt();             // should be nodes - 1
        List<Integer> from = new ArrayList<>();
        List<Integer> to   = new ArrayList<>();
        List<Integer> wt   = new ArrayList<>();

        for (int i = 0; i < edges; i++) {
            from.add(sc.nextInt());
            to.add(sc.nextInt());
            wt.add(sc.nextInt());
        }
        int speed = sc.nextInt();

        List<Integer> ans = getNumPairs(nodes, from, to, wt, speed);
        for (int x : ans) {
            System.out.println(x);
        }
        sc.close();
    }
}
