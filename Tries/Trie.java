import java.util.Scanner;

public class Trie {
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

    public static void main(String[] args) {
        Trie trie = new Trie();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of words:");
        int n = sc.nextInt();
        sc.nextLine();
        String[] words = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = sc.nextLine();
            trie.insert(words[i]);
        }   
        System.out.println("Enter prefixes:");     
        // while(true) {
        //     System.out.print(">");
        //     String prefix = sc.nextLine();
        //     if (prefix == "exit") break;
        //     boolean sw = trie.startsWith(prefix);
        //     System.out.println("prefix status -> " + sw);
        // }
        // while(true) {
        //     System.out.print(">");
        //     String word = sc.nextLine();
        //     if (word == "exit") break;
        //     boolean sw = trie.found(word);
        //     System.out.println("word status -> " + sw);
        // }
        sc.close();
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