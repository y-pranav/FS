import java.util.*;

public class BuildTree {
    public static Node buildTree(int[] a) {
        Queue<Node> q = new LinkedList<>();
        Node root = new Node(a[0]);
        q.offer(root);
        int i = 1, n = a.length;
        while (!q.isEmpty()) {
            Node cur = q.poll();
            if (i < n && a[i] != -1) {
                cur.left = new Node(a[i]);
                q.offer(cur.left);
            } 
            i++;
            if (i < n && a[i] != -1) {
                cur.right = new Node(a[i]);
                q.offer(cur.right);
            } 
            i++;
        }
        return root;
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(input[i]);
        }
        Node root = buildTree(a);
        // logic here
        
        sc.close();
    }
}
class Node {
    Node left;
    Node right;
    int val;
    Node(int val) {
        this.left = null;
        this.right = null;
        this.val = val;
    }
}
