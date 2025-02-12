/*
Construct Tree from the given Level-order and In-order.
Compute the Depth and Sum of the Deepest nodes in the Binary tree

Input Format:
-------------
An integer N representing the number of nodes.
A space-separated list of N integers representing the in-order traversal.
A space-separated list of N integers representing the level-order traversal.

Output Format:
--------------
Print two values:
->The maximum number of levels.
->The sum of all node values at the deepest level.

Example:
-------------
Input:
11
7 8 4 2 5 9 11 10 1 6 3
1 2 3 4 5 6 7 9 8 10 11

Output:
6 11

Explanation:
The binary tree structure:
           1
         /   \
       2       3
      / \     /
     4   5   6
    /     \   
   7       9
    \       \
     8      10
            /
          11
Maximum Depth: 6
nodes at the Deepest Level (6): 11
Sum of nodes at Level 6: 11
 
*/

import java.util.*;

class P5 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int[] inorder = new int[n];
        int [] levelorder = new int[n];
        for(int i=0;i<n;i++) inorder[i] = sc.nextInt();
        for(int i=0;i<n;i++) levelorder[i] = sc.nextInt();
        TreeNode root = buildTree(inorder,levelorder);
        int[] res = findMaxDepthAndSum(root);
        System.out.println(res[0] + " " + res[1]);
        
    }
    
    private static TreeNode buildTree(int[] inorder, int[] levelorder){
        if(inorder.length==0|| levelorder.length==0) return null;
        HashMap<Integer,Integer> inorderMap = new HashMap<>();
        for(int i=0;i<inorder.length;i++){
            inorderMap.put(inorder[i],i);
        }
        
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode root = new TreeNode(levelorder[0]);
        q.offer(root);
        HashMap<TreeNode, int[]> ranges = new HashMap<>();
        ranges.put(root,new int[]{0,inorder.length-1});
        int i=1;
        while(!q.isEmpty() && i < levelorder.length){
            TreeNode temp = q.poll();
            
            int[] range = ranges.get(temp);
            int inorderIdx = inorderMap.get(temp.val);
            
            if(inorderIdx > range[0] && i<levelorder.length){
                TreeNode lchild = new TreeNode(levelorder[i++]);
                q.offer(lchild);
                temp.left = lchild;
                ranges.put(lchild, new int[]{range[0],inorderIdx-1});
            
            }
            if(inorderIdx < range[1] && i<levelorder.length){
                TreeNode rchild = new TreeNode(levelorder[i++]);
                q.offer(rchild);
                temp.right = rchild;
                ranges.put(rchild,new int[]{inorderIdx+1,range[1]});
            }
        }
        return root;
    }
    
    private static int[] findMaxDepthAndSum(TreeNode root){
        if(root==null){
            return new int[]{0,0};
        }
        
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int maxDepth = 0;
        int deepsum = 0;
        while(!q.isEmpty()){
            int size = q.size();
            maxDepth++;
            deepsum = 0;
            for(int i=0;i<size;i++){
                TreeNode temp = q.poll();
                deepsum+=temp.val;
                if(temp.left!=null) q.offer(temp.left);
                if(temp.right!=null) q.offer(temp.right);
                
            }
        }
        return new int[]{maxDepth,deepsum};
    }
}


class TreeNode{
    int val;
    TreeNode left, right;
    TreeNode(int val){
        this.val = val;
    }
}
