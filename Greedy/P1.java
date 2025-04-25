/*
Imagine a unique circular conveyor belt in a sushi restaurant, which serves 
exactly 26 distinct dishes labeled by 26 unique lowercase English letters 
('a' to 'z'). The dishes are arranged linearly along the conveyor belt, indexed 
from 0 to 25 according to the given order. Initially, a robotic serving arm is 
positioned at index 0.

Whenever a customer orders a specific dish, the robotic arm moves from its current 
position to the position of the desired dish along the belt. The robotic arm takes 
exactly one second per unit distance to move along the conveyor belt (the time 
taken from index i to index j is |i - j| seconds).

Given the conveyor belt layout (order of dishes) and a customer's order represented 
as a word (sequence of dishes), write a code to calculate the total time the robotic 
serving arm will take to serve the customer's entire order.


Input Format:
-------------
Line-1: A String, belt layout.
Line-2: A String, word: customer's order.

Output Format:
--------------
An integer T, time to serve.


Sample Output-1:
----------------
abcdefghijklmnopqrstuvwxyz
code

Sample Output-1:
----------------
26 
*/

import java.util.*;

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String layout = sc.nextLine();
        String code = sc.nextLine();
        int n = layout.length();
        int m = code.length();
        HashMap<Character, Integer> mp = new HashMap<>();
        for (int i = 0; i < n; i++) {
            mp.put(layout.charAt(i), i);
        }
        int res = mp.get(code.charAt(0));
        for (int i = 1; i < m; i++) {
            int diff = Math.abs(mp.get(code.charAt(i)) - mp.get(code.charAt(i - 1)));
            res += diff;
        }
        System.out.println(res);
        sc.close();
    }
}