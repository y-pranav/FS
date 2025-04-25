/*
A detective is investigating a case involving a secret message hidden within a 
longer note. The detective knows that the culprit rearranged the letters of a 
short code-word into multiple secret locations within a larger note.

Given two strings, note (the longer text) and codeWord (the short secret code), 
your task is to help the detective find all starting positions within the note 
where any rearrangement or shuffled of codeWord is located.

Input Format:
-------------
Single line space separated strings, two words.

Output Format:
--------------
Print the list of integers, indices.


Sample Input-1:
---------------
bacdgabcda abcd
 
Sample Output-1:
----------------
[0, 5, 6]

Explanation:
- At index 0: "bacd" is an anagram of "abcd"
- At index 5: "abcd" matches exactly
- At index 6: "bcda" is an anagram of "abcd"
Thus, the positions [0, 5, 6] are returned.

Sample Input-2:
---------------
bacacbacdcab cab

Sample Output-2:
----------------
[0, 3, 4, 5, 9]
 
*/

import java.util.*;

class Solution {
    public static boolean check(int[] freq1, int[] freq2) {
        for (int i = 0; i < 26; i++) {
            if (freq1[i] != freq2[i]) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String lt = sc.next();
        String cw = sc.next();
        int k = cw.length();
        int n = lt.length();
        int[] freq1 = new int[26];
        int[] freq2 = new int[26];
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            freq1[lt.charAt(i) - 'a']++;
            freq2[cw.charAt(i) - 'a']++;
        }
        if (check(freq1, freq2)) {
            res.add(0);
        }
        int left = 0;
        for (int i = k; i < n; i++) {
            freq1[lt.charAt(left) - 'a']--;
            freq1[lt.charAt(i) - 'a']++;
            if (check(freq1, freq2)) {
                res.add(left + 1);
            }
            left++;
        }
        System.out.println(res);
        sc.close();
    }
}