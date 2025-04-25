/*
Coach Arjun is training a cricket team and is experimenting with a new fitness 
evaluation drill. He provided the fitness scores of N players in the team. As 
part of the drill, he asked the team manager to keep track and perform these 
two specific operations on the players' fitness scores:

1. bestFitness(start, end) - Report the maximum fitness score among the players 
   whose jersey numbers lie between the positions start and end inclusive.
2. improveFitness(index, newScore) - Update the fitness score of the player at 
   the specific index position with a new fitness score newScore.

Your task is to efficiently handle these requests by using a Segment Tree data structure.

Input Format:  
-------------
Line-1: Two integers N and Q, representing the number of players and the total 
        number of queries respectively.
Line-2: N space-separated integers representing the initial fitness scores of 
        the players.
The next Q lines: Each line contains three integers: 
    - First integer (option) specifies the type of query:
      - If option = 1, the next two integers (start, end) represent the range 
        of jersey numbers (inclusive) for which to report the maximum fitness.
      - If option = 2, the next two integers (index, newScore) represent the 
        player's index to update and their new fitness score.

Output Format:  
--------------
- Output an integer value for every bestFitness query, representing the maximum 
  fitness score within the specified range.

Sample Input:  
-------------

8 5
1 2 13 4 25 16 17 28
1 2 6        //bestFitness
1 0 7        //bestFitness
2 2 18       //improveFitness
2 4 36       //improveFitness
1 2 7        //bestFitness


Sample Output:  
--------------
25
28
36
*/

import java.util.*;
class SegmentTree{
    public int[] tree;
    public int n;
    
    public SegmentTree(int[] arr){
        n = arr.length;
        tree = new int[4*n];
        buildTree(arr, 0 ,0 ,n-1);
    }
    
    private void buildTree(int[] arr, int node, int start,int end){
        if(start==end){
            tree[node] = arr[start];
        }else{
            int mid = (start + end) / 2;
            buildTree(arr, 2 * node + 1, start, mid);
            buildTree(arr, 2 * node + 2, mid + 1, end);
            
            tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }
    
    public int rangeMaxQuery(int left, int right){
        return query(0,0,n-1,left,right);
    }
    
    private int query(int node, int start, int end,int l, int r){
        if(r < start || l > end){
            return Integer.MIN_VALUE;
        }
        if(l <= start && end <= r){
            return tree[node];
        }
        int mid = (start + end) / 2;
        int leftMax = query(2 * node + 1, start,mid,l,r);
        int rightMax = query(2 * node + 2, mid + 1,end,l,r);
        return Math.max(leftMax, rightMax);
    }
    
    public void update(int index, int newValue){
        updateTree(0,0,n-1,index,newValue);
    }
    
    private void updateTree(int node, int start, int end, int index, int newValue){
        if(start==end){
            tree[node] = newValue;
        }else{
            int mid = (start + end) / 2;
            if(index <= mid){
                updateTree(2 * node + 1, start, mid, index, newValue);
            }else{
                updateTree(2 * node + 2, mid + 1, end, index, newValue);
            }
            tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }
}
class Solution{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        int[] arr = new int[n];
        for(int i=0;i<n;i++) arr[i] = sc.nextInt();
        SegmentTree st = new SegmentTree(arr);
        for(int i=0;i<q;i++){
            int flag = sc.nextInt();
            int l = sc.nextInt();
            int r = sc.nextInt();
            if(flag == 1){
                System.out.println(st.rangeMaxQuery(l,r));
            }else{
                st.update(l, r);
            }
        }
    }
}
