/*
Imagine you're playing a fantasy video game where secret level codes unlock new 
worlds. These codes are strings made up of letters, and a level code is only 
considered valid if every shorter code formed by its leading characters has been
discovered along the journey. In other words, a code is unlockable only when all
of its prefixes are also present in your collection.

Given a list of strings representing the level codes you’ve collected, find the 
longest valid level code such that every prefix of that code is in the list. 
If there is more than one valid code of the same length, choose the one that 
comes first in alphabetical order. If no such code exists, return an empty string.

Input Format
-------------
Line1: Space separated codes (strings)
 
Output Format
--------------
string 


Example 1:
----------
Input:
m ma mag magi magic magici magicia magician magicw
Output: 
"magician"

Explanation: The code "magician" is unlockable because every 
prefix—"m", "ma", "mag", "magi", "magic", "magici", and "magicia"—is present in 
the list. Although "magicw" appears too, it fails since its prefix "magica" is missing.


Example 2:
----------
Input:
a banana app appl ap apply apple
Output: 
"apple"  

Explanation: Both "apple" and "apply" have every prefix in the list, but "apple" 
comes first in alphabetical order.

Example 3:
----------
Input: 
abc bc ab abcd
 Output: 
 ""
*/

import java.util.*;

class Trie {
    TrieNode root;
    Trie() {
        root = new TrieNode();
    }
    public void insert(String word) {
        TrieNode node = root;
        for (char ch: word.toCharArray()) {
            int idx = ch - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isEndOfWord = true;
    }
    public TrieNode search(String word) {
        TrieNode node = root;
        for (char ch: word.toCharArray()) {
            int idx = ch - 'a';
            if (node.children[idx] == null) {
                return null;
            }
            node = node.children[idx];
        }
        return node;
    }
    public boolean found(String word) {
        TrieNode node = search(word);
        return node != null && node.isEndOfWord;
    }
    public boolean startsWith(String prefix) {
        TrieNode node = search(prefix);
        return node != null;
    }
}
class TrieNode {
    TrieNode[] children;
    int idx;
    boolean isEndOfWord;
    TrieNode() {
        children = new TrieNode[26];
        idx = 0;
        isEndOfWord = false;
    }
}

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] words = sc.nextLine().split(" ");
        int n = words.length;
        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word);
        }

        List<String> result = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            boolean flag = true;
            for (int j = 1; j < words[i].length(); j++) {
                if (!trie.found(words[i].substring(0, j))) {
                    flag = false;
                    break;
                }
            }
            if (flag) result.add(words[i]);
        }

        Collections.sort(result, (a, b) -> {
            if (a.length() != b.length()) return Integer.compare(b.length(), a.length());
            return a.compareTo(b);
        });
        System.out.print(result.size() == 0 ? "" : result.get(0));
    }
}
