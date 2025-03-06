/*
In the world of secret codes and cryptography, you are entrusted with deciphering a hidden message. 
You have two encoded keys, key1 and key2, both of equal length. Each character 
in key1 is paired with the corresponding character in key2. 

This relationship follows the standard rules of an equivalence cipher:
• Self-Mapping: Every character inherently maps to itself.  
• Mutual Mapping: If a character from key1 maps to one in key2, then that 
  character in key2 maps back to the one in key1.  
• Chain Mapping: If character A maps to B, and B maps to C, then A, B, and C 
  are all interchangeable in this cipher.

Using this mapping, you must decode a cipherText, by replacing every character 
with the smallest equivalent character from its equivalence group. 
The result should be the relatively smallest possible decoded message.


Input Format:
-------------
Three space separated strings, key1 , key2 and cipherText

Output Format:
--------------
Print a string, decoded message which is relatively smallest string of cipherText.

Example 1: 
input=
attitude progress apriori
output=
aaogoog


Explanation: The mapping pairs form groups: [a, p], [o, r, t], [g, i], [e, u], 
[d, e, s]. By substituting each character in cipherText with the smallest from 
its group, you decode the message to "aaogoog".


Constraints:  
• 1 <= key1.length, key2.length, cipherText.length <= 1000  
• key1.length == key2.length  
• key1, key2, and cipherText consist solely of lowercase English letters.
 
*/

import java.util.*;

class DSU {
    public char[] parent;
    DSU() {
        parent = new char[26];
        for (int i = 0; i < 26; i++) {
            parent[i] = (char) (i + 'a');
        }
    }
    public char find(char x){
        if (parent[x - 'a'] != x) {
            parent[x - 'a'] = find(parent[x - 'a']);
        }
        return parent[x - 'a'];
    }    
    public void union(char x, char y) {
        char rootX = find(x);
        char rootY = find(y);
        if (rootX < rootY) {
            parent[rootY - 'a'] = rootX;
        } else {
            parent[rootX - 'a'] = rootY;
        }
    }
}

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String key1 = sc.next();
        String key2 = sc.next();
        String cipher = sc.next();
        DSU dsu = new DSU();
        int len = key1.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            dsu.union(key1.charAt(i), key2.charAt(i));
        }
        for (int i = 0; i < cipher.length(); i++) {
            sb.append(dsu.find(cipher.charAt(i)));    
        }
        System.out.println(sb.toString());
        sc.close();
    }
}