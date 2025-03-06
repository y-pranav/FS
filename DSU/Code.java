public class Code {
    private int[] parent;
    private int[] rank;  // alternatively, you could use size

    // Constructor: initialize parent and rank arrays
    public Code(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;   // every element is its own parent
            rank[i] = 0;     // initial rank is 0
        }
    }

    // Find with path compression
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression step
        }
        return parent[x];
    }

    // Union by rank
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) return; // already in the same set

        // Attach smaller rank tree under root of higher rank tree
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            // If ranks are equal, choose one as root and increment its rank
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
}

