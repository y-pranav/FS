// Imagine you are a librarian organizing books on vertical shelves in a grand 
// library. The books are currently scattered across a tree-like structure, where 
// each book (node) has a position determined by its shelf number (column) and row 
// number (level).

// Your task is to arrange the books on shelves so that:
// 1. Books are placed column by column from left to right.
// 2. Within the same column, books are arranged from top to bottom (i.e., by row).
// 3. If multiple books belong to the same shelf and row, they should be arranged 
// from left to right, just as they appear in the original scattered arrangement.

// Sample Input:
// -------------
// 3 9 20 -1 -1 15 7

// Sample Output:
// --------------
// [[9],[3,15],[20],[7]]

// Explanation:
// ------------
//          3
//        /   \
//       9     20
//           /    \
//          15     7

// Shelf 1: [9]
// Shelf 2: [3, 15]
// Shelf 3: [20]
// Shelf 4: [7]


// Sample Input-2:
// ---------------
// 3 9 8 4 0 1 7

// Sample Output-2:
// ----------------
// [[4],[9],[3,0,1],[8],[7]]

// Explanation:
// ------------

//           3
//        /     \
//       9       8
//     /   \   /   \
//    4     0 1     7

// Shelf 1: [4]
// Shelf 2: [9]
// Shelf 3: [3, 0, 1]
// Shelf 4: [8]
// Shelf 5: [7]


import java.util.*;

class P15 {
    public static TreeNode buildTree(int[] a) {
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode root = new TreeNode(a[0]);
        q.offer(root);

        int i = 1, n = a.length;
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();

            if (cur.left == null && i < n && a[i] != -1) {
                cur.left = new TreeNode(a[i]);
                q.offer(cur.left);
            }
            i++;

            if (cur.right == null && i < n && a[i] != -1) {
                cur.right = new TreeNode(a[i]);
                q.offer(cur.right);
            }
            i++;
        }
        return root;
    }

    public static TreeMap<Integer, ArrayList<Integer>> solve(TreeNode root) {
        TreeMap<Integer, ArrayList<Integer>> mp = new TreeMap<>();
        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(root, 0));

        while (!q.isEmpty()) {
            Pair cur = q.poll();
            int curNodeVal = cur.node.val;
            int HD = cur.dist; // horizontal distance

            mp.putIfAbsent(HD, new ArrayList<>());
            mp.get(HD).add(curNodeVal);

            if (cur.node.left != null) {
                q.offer(new Pair(cur.node.left, HD - 1));
            }

            if (cur.node.right != null) {
                q.offer(new Pair(cur.node.right, HD + 1));
            }
        }
        return mp;
    } 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(input[i]);
        }
        
        TreeNode root = buildTree(a);
        TreeMap<Integer, ArrayList<Integer>> mp = solve(root);
        int shelf = 1;
        for (ArrayList<Integer> val: mp.values()) {
            System.out.println("Shelf " + shelf + " :" + val);
            shelf++;
        }
        sc.close();
    }
}

class TreeNode {
    TreeNode left;
    TreeNode right;
    int val;
    TreeNode(int val) {
        this.left = null;
        this.right = null;
        this.val = val;
    }
}

class Pair {
    TreeNode node;
    int dist;
    Pair(TreeNode node, int dist) {
        this.node = node;
        this.dist = dist;
    }
}