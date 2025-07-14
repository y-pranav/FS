import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Insert a word into the Trie
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null) {
                node.children[i] = new TrieNode();
            }
            node = node.children[i];
        }
        node.isEndOfWord = true;
    }

    // Search a word in the Trie
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null)
                return false;
            node = node.children[i];
        }
        return node.isEndOfWord;
    }

    public boolean delete(String word) {
        return delete(root, word, 0);
    }

    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfWord) return false; // word not found
            current.isEndOfWord = false;
            return isEmpty(current); // if no children, delete node
        }

        char ch = word.charAt(index);
        int i = ch - 'a';
        TrieNode node = current.children[i];
        if (node == null) return false; // word not found

        boolean shouldDeleteCurrentNode = delete(node, word, index + 1);

        if (shouldDeleteCurrentNode) {
            current.children[i] = null;
            return !current.isEndOfWord && isEmpty(current);
        }

        return false;
    }

    private boolean isEmpty(TrieNode node) {
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null)
                return false;
        }
        return true;
    }

    public ArrayList<int[]> indexPairs(String s, String[] words) {
        ArrayList<int[]> res = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            TrieNode cur = root;
            int j = i;
            while (j < s.length() && cur.children[s.charAt(j) - 'a'] != null) {
                cur = cur.children[s.charAt(j) - 'a'];
                if (cur.isEndOfWord) res.add(new int[] {i, j});
                j++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int n = sc.nextInt();
        sc.nextLine();
        String[] words = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = sc.nextLine();
            trie.insert(words[i]);
        }
        // System.out.println(trie.indexPairs(s, words));
        ArrayList<int[]> res = trie.indexPairs(s, words);
        for (int[] pair: res){
            System.out.println(Arrays.toString(pair));
        }
        // trie.insert("code");
        // trie.insert("coder");
        // trie.insert("coding");

        // System.out.println("Search 'coder': " + trie.search("coder")); // true

        // trie.delete("coder");
        // System.out.println("Search 'coder' after deletion: " + trie.search("coder")); // false
        // System.out.println("Search 'code': " + trie.search("code")); // true
        // System.out.println("Search 'coding': " + trie.search("coding")); // true

    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord;

    TrieNode() {
        isEndOfWord = false;
    }
}
