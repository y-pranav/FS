/*
Imagine you are the curator of a historic library, where books are arranged in a 
unique catalog system based on their publication years. The library’s archive is 
structured like a hierarchical tree, with each book’s publication year stored at 
a node. You are given the nodes of this catalog tree starting with main node
and a list of query years.

For each query year, you need to find two publication years:
- The first is the latest year in the archive that is less than or equal to the 
  query year. If no such book exists, use -1.
- The second is the earliest year in the archive that is greater than or equal 
  to the query year. If no such book exists, use -1.

Display the results as an list of pairs, where each pair corresponds to a query.

Example 1:
----------
Input: 
2006 2002 2013 2001 2004 2009 2015 2014
2002 2005 2016

Output:
[[2002, 2002], [2004, 2006], [2015, -1]] 


Archive Structure:
          2006
         /    \
     2002     2013
     /   \     /   \
  2001  2004  2009  2015
                     /
                  2014
                  
Explanation:  
- For the query 2002, the latest publication year that is ≤ 2002 is 2002, and 
  the earliest publication year that is ≥ 2002 is also 2002.  
- For the query 2005, the latest publication year that is ≤ 2005 is 2004, and 
  the earliest publication year that is ≥ 2005 is 2006.  
- For the query 2016, the latest publication year that is ≤ 2016 is 2015, but 
  there is no publication year ≥ 2016, so we output -1 for the second value.

Example 2:
----------
Input:  
2004 2009
2003

Output:
[[-1, 2004]]

Explanation:  
- For the query 2003, there is no publication year ≤ 2003, while the earliest 
  publication year that is ≥ 2003 is 2004.

Constraints:
- The total number of books in the archive is in the range [2, 10^5].
- Each publication year is between 1 and 10^6.
- The number of queries n is in the range [1, 10^5].
- Each query year is between 1 and 10^6.
 
*/

import java.util.*;

class P5 {
    private static Node insert(Node root, int val) {
        if (root==null) {
            return new Node(val);
        }
        if (val<root.val) {
            root.left = insert(root.left,val);
        } 
        else if (val>root.val) {
            root.right = insert(root.right,val);
        }
        return root;
    }
     
    public static void findLess(Node root, int year, int[] cur) {
        if (root == null) return;
        if (root.val == year) {
            cur[0] = year;
            return;
        }
        if (root.val < year) {
            cur[0] = root.val;
            findLess(root.right, year, cur);
        } else {
            findLess(root.left, year, cur);
        }
    }

    public static void findMore(Node root, int year, int[] cur) {
        if (root == null) return;
        if (root.val == year) {
            cur[1] = year;
            return;
        }
        if (root.val > year) {
            cur[1] = root.val;
            findMore(root.left, year, cur);
        } else {
            findMore(root.right, year, cur);
        }
    }
    
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String[] inp1 = sc.nextLine().split(" ");
        String[] req = sc.nextLine().split(" ");
        Node root=null;
        for (int i1 = 0; i1<inp1.length; i1++) {
            root = insert(root, Integer.parseInt(inp1[i1]));
        }
        
        ArrayList<ArrayList<Integer>> res=new ArrayList<>();
        for(String y:req){
            ArrayList<Integer> curr=new ArrayList<>();
            int[] cur = {-1, -1};
            findLess(root, Integer.parseInt(y), cur);
            curr.add(cur[0]);
            findMore(root, Integer.parseInt(y), cur);
            curr.add(cur[1]);
            res.add(curr);
        }
        System.out.println(res);
    }
}

class Node{
    int val;
    Node left,right;
    Node(int v){
        val=v;
        left=null;
        right=null;
    }
}
