/*
In Hyderabad after a long pandemic gap, the Telangana Youth festival is 
Organized at HITEX. In HITEX, there are a lot of programs planned. During 
the festival in order to maintain the rules of Pandemic, they put a 
constraint that one person can only attend any one of the programs in 
one day according to planned days.

Now it’s your aim to implement the "Solution" class in such a way that 
you need to return the maximum number of programs you can attend according 
to given constraints.

Explanation:
You have a list of programs 'p' and days 'd', where you can attend only 
one program on one day. Programs [p] = [first day, last day], 
p is the program's first day and the last day.


Input Format:
-------------
Line-1: An integer N, number of programs.
Line-2: N comma separated pairs, each pair(f_day, l_day) is separated by space.

Output Format:
--------------
An integer, the maximum number of programs you can attend.


Sample Input-1:
---------------
4
1 2,2 4,2 3,2 2

Sample Output-1:
----------------
4

Sample Input-2:
---------------
6
1 5,2 3,2 4,2 2,3 4,3 5

Sample Output-2:
----------------
5 
*/

import java.util.*;

class SegmentTree {
    int size;
    int[] tree;
    int[] arr;
    SegmentTree(int[] inp){
        arr=inp;
        size=arr.length;
        tree=new int[4*size];
        build(0,0,size-1);
    }
    void build(int node, int start, int end){
        if(start==end){
            tree[node]=arr[start];
        }
        else{
            int mid=(start+end)/2;
            build(2*node+1,start,mid);
            build(2*node+2,mid+1,end);
            tree[node]=Math.min(tree[2*node+1],tree[2*node+2]); 
        }
    }
    int query(int l, int r){
        return querycal(0,0,size-1,l,r);
    }
    int querycal(int node, int start, int end, int l, int r){
        if(r<start || l>end){
            return Integer.MAX_VALUE;
        }
        if(l<=start && r>=end){
            return tree[node];
        }
        int mid=(start+end)/2;
        return Math.min(querycal(2*node+1, start, mid, l, r),querycal(2*node+2, mid+1, end, l, r));
    }
    void update(int ind, int val){
        updateutil(0,0,size-1,ind,val);
    }
    void updateutil(int node, int start, int end, int ind, int val){
        if(start==end){
            tree[node]=val;
            return;
        }
        int mid=(start+end)/2;
        if(ind<=mid){
            updateutil(2*node+1, start, mid, ind, val);
        }
        else{
            updateutil(2*node+2, mid+1, end, ind, val);
        }
        tree[node]=Math.min(tree[2*node+1],tree[2*node+2]);
    }
}

class Solution{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        sc.nextLine();
        String[] inp=sc.nextLine().split(",");
        int[][] ranges=new int[n][2];
        int d=0;
        for(int i=0;i<n;i++){
            String[] curr=inp[i].split(" ");
            int start=Integer.parseInt(curr[0]);
            int end=Integer.parseInt(curr[1]);
            ranges[i]=new int[]{start,end};
            d=Math.max(d,end);
        }
        Arrays.sort(ranges, Comparator.comparingInt(a->a[1]));
        int[] days=new int[d+1];
        SegmentTree st=new SegmentTree(days);
        int res=0;
        for(int i=0;i<n;i++){
            int[] curr=ranges[i];
            for(int day=curr[0];day<=curr[1];day++){
                if(st.query(day,day)==0){
                    st.update(day,1);
                    res+=1;
                    break;
                }
            }
        }
        System.out.println(res);
    }
}
