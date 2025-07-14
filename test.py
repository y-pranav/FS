import sys
import threading
def main():
    data = sys.stdin.read().split()
    it = iter(data)
    t = int(next(it))
    MOD = 10**9+7

    def solve():
        n = int(next(it))
        P = [int(next(it)) for _ in range(n)]
        B = [int(next(it)) for _ in range(n)]
        adj = [[] for _ in range(n)]
        root = 0
        for i, p in enumerate(P):
            if p == -1:
                root = i
            else:
                adj[p-1].append(i)
        dp = [[0, 0] for _ in range(n)]
        def dfs(u):
            even, odd = 0, -10**30
            for v in adj[u]:
                dfs(v)
                ne = max(even + dp[v][0], odd  + dp[v][1])
                no = max(even + dp[v][1], odd  + dp[v][0])
                even, odd = ne, no
            dp[u][0] = even
            dp[u][1] = max(odd, even + B[u])
        dfs(root)
        print(max(dp[root][0], dp[root][1]) % MOD)

    for _ in range(t):
        solve()

if __name__ == "__main__":
    threading.Thread(target=main).start()
