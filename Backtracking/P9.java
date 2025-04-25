/*
In the ancient land of Palindoria, wizards write magical spells as strings of 
lowercase letters. However, for a spell to be effective, each segment of the 
spell must read the same forward and backward.

Given a magical spell 'spell', your task is to partition it into sequences of 
valid magical spell segments. 
Your goal is to help the wizard discover all valid combinations of magical spell 
segmentations.

Example 1:
----------
Input:  
aab
  
Output:  
[[a, a, b], [aa, b]]

Example 2:

Input:  
a  
Output:  
[[a]]

Spell Constraints:

- The length of the spell is between 1 and 16 characters.  
- The spell contains only lowercase English letters.   
*/

import java.util.*;

class Solution {
    public static ArrayList<ArrayList<String>> res;
    public static boolean check(String s) {
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) return false;
        }
        return true;
    }
    public static void backtrack(String s, ArrayList<String> temp) {
        if (s.length() == 0) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 1; i <= s.length(); i++) {
            if (!check(s.substring(0, i))) continue;
            temp.add(s.substring(0, i));
            backtrack(s.substring(i), temp);
            temp.remove(temp.size() - 1);
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        res = new ArrayList<>();
        backtrack(s, new ArrayList<String>());
        System.out.println(res);
        sc.close();
    }
}