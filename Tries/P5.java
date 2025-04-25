/*
Mr Suresh is working with the plain text P, a list of words w[], 
He is converting P into C [the cipher text], C is valid cipher of P, 
if the following rules are followed:
	- The cipher-text C is a string ends with '$' character.
	- Every word, w[i] in w[], should be a substring of C, and 
	the substring should have $ at the end of it.

Your task is to help Mr Suresh to find the shortest Cipher C,  
and return its length.

Input Format:
-------------
Single line of space separated words, w[].

Output Format:
--------------
Print an integer result, the length of the shortest cipher.


Sample Input-1:
---------------
kmit it ngit

Sample Output-1:
----------------
10

Explanation:
------------
A valid cipher C is "kmit$ngit$".
w[0] = "kmit", the substring of C, and the '$' is the end character after "kmit"
w[1] = "it", the substring of C, and the '$' is the end character after "it"
w[2] = "ngit", the substring of C, and the '$' is the end character after "ngit"


Sample Input-2:
---------------
ace

Sample Output-2:
----------------
4

Explanation:
------------
A valid cipher C is "ace$".
w[0] = "ace", the substring of C, and the '$' is the end character after "ace"
 
*/

import java.util.*;

class P5 {
    static Trie trie;

    // O(NLOGN) SOLUTION
    public static int sol1(int n, String[] input) {
        int ans = 0;
        Arrays.sort(input, (a, b) -> b.length() - a.length());
        Set<String> uniq = new LinkedHashSet<>(Arrays.asList(input));
        // StringBuilder res = new StringBuilder();
        for (String word: uniq) {
            String rev = new StringBuilder(word).reverse().toString();
            if (!trie.startsWith(rev)) {
                trie.insert(rev);
                ans += word.length() + 1;
                // res.append(word + "$");
            }
        }
        // System.out.println(res.toString());
        return ans;
    }

    // O(SUM OF LENGTH OF ALL WORDS) SOLUTION
    public static int sol2(int n, String[] input) {
        for (int i = 0; i < n; i++) {
            String rev = new StringBuilder(input[i]).reverse().toString();
            if (!trie.startsWith(rev)) {
                trie.insert(rev);
            }
        }
        return dfs(trie.root, 0); // dfs(node, depth)
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(" ");
        int n = input.length;

        // O(NLOGN) SOLUTION
        trie = new Trie();
        System.out.println(sol1(n, input));

        // O(TOTAL CHARS) SOLUTION
        trie = new Trie();
        System.out.println(sol2(n, input));
        sc.close();
    }
    public static int dfs(TrieNode node, int depth) {
        int sum = 0;
        int children = 0;
        for (TrieNode next: node.children) {
            if (next != null) {
                children++;
                sum += dfs(next, depth + 1);
            }
        }
        if (children == 0) { // children == 0 implies the current node is a leaf node
            return depth + 1;  // we add the word length i.e depth + 1 for '$'
        }
        return sum;
    }
}

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
    boolean isEndOfWord;
    TrieNode() {
        children = new TrieNode[26];
        isEndOfWord = false;
    }
}