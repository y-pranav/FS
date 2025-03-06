/*
Cliff Shaw is working on the singly linked list.
He is given a list of boxes arranged as singly linked list,
where each box is printed a positive number on it.

Your task is to help Mr Cliff to find the given list is equivalent to 
the reverse of it or not. If yes, print "true", otherwise print "false"

Input Format:
-------------
Line-1: space separated integers, boxes as list.

Output Format:
--------------
Print a boolean a value.


Sample Input-1:
---------------
3 6 2 6 3

Sample Output-1:
----------------
true


Sample Input-2:
---------------
3 6 2 3 6

Sample Output-2:
----------------
false 
*/

import java.util.*;


class Node {
    int val;
    Node next;
    Node(int val) {
        this.val = val;
        this.next = null;
    }
}


class Solution {
    public static void buildLL(Node head, int[] a) {
        Node cur = head;
        for (int i = 1; i < a.length; i++) {
            cur.next = new Node(a[i]);
            cur = cur.next;
        }
        cur.next = null;
    }
    
    public static Node reverseLL(Node head) {
        Node prev = null, cur = head, temp = null;
        while (cur != null) {
            temp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = temp;
        }
        return prev;
    }
    
    public static boolean solve(Node head) {
        if (head == null || head.next == null) return true;
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        if (fast != null) slow = slow.next; // odd length palindrome
        
        // reverse second half
        slow = reverseLL(slow);
        fast = head;
        
        while (slow != null) {
            // System.out.println(slow.val + " " + fast.val);
            if (slow.val != fast.val) return false;
            slow = slow.next;
            fast = fast.next;
        }
        return true;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(input[i]);
        Node head = new Node(a[0]);
        buildLL(head, a);
        System.out.println(solve(head));

    }
}



