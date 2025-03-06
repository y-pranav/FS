/*
Imagine you're an archivist in the ancient Kingdom of Numeria, tasked with 
recording the royal lineage on a sacred scroll. Every monarch is identified 
by a unique number, and the family tree unfolds in a very specific way. 
Your mission is to transcribe this dynasty using a method that adheres 
to the following customs:

Royal Inscription:  
- Each monarch’s unique number is written as their signature on the scroll.

Enclosing Descendants:  
- If a monarch has offspring, their descendants must be recorded inside parentheses 
  immediately following the monarch’s number.
    - The first-born (or primary heir) is enclosed in its own set of parentheses.
    - If there is a second-born, their number is similarly enclosed, following 
      the first-born’s parentheses.

Omitting Redundant Markings:  
- Empty pairs of parentheses are generally left off the scroll to keep the record neat.
- However, if a monarch has a second-born but first-born is no more, you must include 
  an empty pair of parentheses to clearly mark the absence of a primary heir—ensuring 
  the lineage is accurately mapped.

Your task is to create such an inscription that perfectly reflects the 
hierarchical structure of the royal lineage.

Example 1:
Input=
1 2 3 4
Output=
1(2(4))(3)

Explanation:
        1
       / \
      2   3
     /    
    4

- Monarch 1 is the founding ruler. He has two heirs: Monarch 2 (first-born) and 
  Monarch 3 (second-born).
- Monarch 2 has a single descendant, Monarch 4, recorded as his first-born.
- Since empty markings for non-existent descendants are omitted (because they 
  don't add any information), the inscription is kept concise.
- Thus, the final transcription on the sacred scroll is: "1(2(4))(3)"

Example 2:
Input=
1 2 3 -1 4
Output=
1(2()(4))(3)

Explanation:
        1
       / \
      2   3
       \
        4

- Monarch 1 is the founding ruler with two heirs: Monarch 2 and Monarch 3.
- In this dynasty, Monarch 2 first-born is no more alive but does have a younger 
  descendant, Monarch 4.
- To ensure the record accurately reflects the gap in succession for Monarch 2, 
  an empty pair of parentheses is added before representing Monarch 4. 
  This explicitly marks the absence of a first-born heir.
- Therefore, the recorded inscription becomes: "1(2()(4))(3)"

Example 3:
input=
1 2 3 4 -1 5 6 -1 7
output=
1(2(4()(7)))(3(5)(6))
 
*/

import java.util.*;

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


class P17 {
    public static TreeNode buildTree(int[] a) {
        if (a.length == 0 || a[0] == -1) return null;
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


    public static void solve(TreeNode root, StringBuilder sb) {
        if (root == null) return;
        sb.append('(');
        sb.append(root.val);
        if ((root.left == null && root.right != null)) {
            sb.append("()");
        }
        solve(root.left, sb);
        solve(root.right, sb);
        sb.append(")");
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
        StringBuilder sb = new StringBuilder();
        solve(root, sb);
        System.out.print(sb.toString().substring(1, sb.length() - 1));
    }
}


