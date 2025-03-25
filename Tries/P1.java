/*
Imagine you're a digital security analyst reviewing a suspicious email. The email’s 
content is a continuous string of characters, and you have a list of keywords 
commonly used in phishing scams. Your mission is to scan the email text and flag 
every segment that exactly matches one of these keywords. In other words, identify 
all index pairs [i, j] such that the substring from position i to j in the email 
text is one of the suspicious keywords. Return these pairs sorted by their starting 
index, and if two pairs share the same starting index, sort them by their ending index.

Input Format
------------
Line-1: string STR(without any space)
Line-2: space separated strings, suspicious keywords[]

Output Format
-------------
Print the pairs[i, j] in sorted order.


Example 1:
----------
Input:  
cybersecuritybreachalert
breach alert cyber

Output: 
0 4
13 18
19 23

Example 2:
----------
Input:  
phishphishingphish
phish phishing

Output:
0 4
5 9
5 12
13 17


Explanation: Notice that keywords can overlap—for instance, the word "phish" appears 
as part of the substring [5,9] in addition to the complete "phishing" substring [5,12].

Constraints:

- 1 <= emailText.length <= 100  
- 1 <= suspiciousWords.length <= 20  
- 1 <= suspiciousWords[i].length <= 50  
- emailText and each suspicious word consist of lowercase English letters.  
- All suspicious words are unique.
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

    // Search all matches starting from index `start` in the string `s`
    public List<int[]> searchFrom(String s, int start) {
        List<int[]> matches = new ArrayList<>();
        TrieNode node = root;
        for (int i = start; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            if (node.children[idx] == null) {
                break;
            }
            node = node.children[idx];
            if (node.isEndOfWord) {
                matches.add(new int[]{start, i});
            }
        }
        return matches;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine().trim();
        String[] words = sc.nextLine().trim().split(" ");

        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word);
        }

        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            result.addAll(trie.searchFrom(s, i));
        }

        result.sort((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        for (int[] pair : result) {
            System.out.println(pair[0] + " " + pair[1]);
        }
            sc.close();
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