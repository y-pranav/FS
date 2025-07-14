import java.util.*;

public class Test1 {
    static final long MOD = 1_000_000_007L;

    public static int GetAnswer(int n, List<Integer> P, List<Integer> B) {
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        int root = 0;
        for (int i = 0; i < n; i++) {
            int p = P.get(i);
            if (p == -1) {
                root = i;
            } else {
                adj.get(p - 1).add(i);
            }
        }

        long[][] dp = new long[n][2];
        dfs(root, adj, B, dp);

        return (int)(Math.max(dp[root][0], dp[root][1]) % MOD);
    }

    private static void dfs(int u, List<List<Integer>> adj, List<Integer> B, long[][] dp) {
        long even = 0;
        long odd  = Long.MIN_VALUE / 2;
        for (int v : adj.get(u)) {
            dfs(v, adj, B, dp);
            long ne = Math.max(even + dp[v][0], odd  + dp[v][1]);
            long no = Math.max(even + dp[v][1], odd  + dp[v][0]);
            even = ne;
            odd  = no;
        }
        dp[u][0] = even;
        dp[u][1] = Math.max(odd, even + B.get(u));
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int T = Integer.parseInt(scan.nextLine());
        while (T-- > 0) {
            int N = Integer.parseInt(scan.nextLine().trim());
            List<Integer> P = new ArrayList<>(N);
            for (int j = 0; j < N; j++) {
                P.add(Integer.parseInt(scan.nextLine().trim()));
            }
            List<Integer> B = new ArrayList<>(N);
            for (int j = 0; j < N; j++) {
                B.add(Integer.parseInt(scan.nextLine().trim()));
            }
            int result = GetAnswer(N, P, B);
            System.out.println("OP" + result);
        }

    }
}
