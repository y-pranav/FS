import java.util.*;

public class SuffixTreeOperations {

    static class SuffixTrieNode {
        static final int MAX_CHAR = 26;
        SuffixTrieNode[] children = new SuffixTrieNode[MAX_CHAR];
        boolean isLeaf;

        SuffixTrieNode() {
            Arrays.fill(children, null);
            isLeaf = false;
        }

        // Insert a suffix starting at this node
        void insertSuffix(String s) {
            if (s.length() == 0) {
                this.isLeaf = true;
                return;
            }

            int index = s.charAt(0) - 'a';
            if (children[index] == null)
                children[index] = new SuffixTrieNode();

            children[index].insertSuffix(s.substring(1));
        }

        // Search for a substring
        boolean search(String pattern) {
            if (pattern.length() == 0) return true;

            int index = pattern.charAt(0) - 'a';
            if (children[index] == null) return false;

            return children[index].search(pattern.substring(1));
        }

        // Count nodes (for counting distinct substrings)
        int countNodes() {
            int count = 0;
            for (SuffixTrieNode child : children) {
                if (child != null)
                    count += child.countNodes();
            }
            return 1 + count;
        }

        // Print all substrings from this node
        void collectSubstrings(String prefix, List<String> list) {
            if (isLeaf) list.add(prefix);

            for (int i = 0; i < MAX_CHAR; i++) {
                if (children[i] != null) {
                    char nextChar = (char) (i + 'a');
                    children[i].collectSubstrings(prefix + nextChar, list);
                }
            }
        }

        // Longest repeated substring (DFS)
        String longestRepeated(String path) {
            String result = "";
            int childCount = 0;
            int lastChild = -1;

            for (int i = 0; i < MAX_CHAR; i++) {
                if (children[i] != null) {
                    childCount++;
                    lastChild = i;
                }
            }

            if (childCount >= 2) {
                for (int i = 0; i < MAX_CHAR; i++) {
                    if (children[i] != null) {
                        String res = children[i].longestRepeated(path + (char) (i + 'a'));
                        if (res.length() > result.length()) {
                            result = res;
                        }
                    }
                }
                return result.length() > path.length() ? result : path;
            } else if (childCount == 1) {
                return children[lastChild].longestRepeated(path + (char) (lastChild + 'a'));
            } else {
                return "";
            }
        }
    }

    static class SuffixTrie {
        SuffixTrieNode root;

        SuffixTrie(String s) {
            root = new SuffixTrieNode();
            for (int i = 0; i < s.length(); i++) {
                root.insertSuffix(s.substring(i));
            }
        }

        boolean search(String pattern) {
            return root.search(pattern);
        }

        int countDistinctSubstrings() {
            return root.countNodes() - 1; // Exclude root
        }

        List<String> getAllSubstrings() {
            List<String> substrings = new ArrayList<>();
            root.collectSubstrings("", substrings);
            return substrings;
        }

        String longestRepeatedSubstring() {
            return root.longestRepeated("");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a lowercase string:");
        String input = sc.nextLine().toLowerCase();

        SuffixTrie trie = new SuffixTrie(input);

        System.out.println("1. Search for substring:");
        String pattern = sc.nextLine().toLowerCase();
        System.out.println("Found? " + trie.search(pattern));

        System.out.println("2. Count of distinct substrings:");
        System.out.println(trie.countDistinctSubstrings());

        System.out.println("3. All substrings:");
        for (String s : trie.getAllSubstrings()) {
            System.out.println(s);
        }

        System.out.println("4. Longest repeated substring:");
        System.out.println(trie.longestRepeatedSubstring());
    }
}
