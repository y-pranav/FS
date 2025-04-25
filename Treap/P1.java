/*
In a magical kingdom, each wizard carries a certain number of enchanted crystals. 
A pair of wizards is said to have a "dominant wizard" if the first wizard, who 
\arrived earlier at the royal gathering, possesses more than twice as many 
crystals as the second wizard, who arrived later.

Given an list of crystals, representing the number of enchanted crystals carried 
by each wizard in their order of arrival at the gathering, calculate the number 
of "dominant wizard" pairs.

A pair of wizards (x, y) is considered dominant if:

- 0 ≤ x < y < crystals.length and
- crystals[x] > 2 × crystals[y]

Example 1:
Input: 
1 3 2 3 1
Output: 2

Explanation: The dominant wizard pairs are:
- Wizard 1 (3 crystals) and Wizard 4 (1 crystal), since 3 > 2 × 1
- Wizard 3 (3 crystals) and Wizard 4 (1 crystal), since 3 > 2 × 1

Example 2:
Input:
2 4 3 5 1
Output: 3

Explanation: The dominant wizard pairs are:
- Wizard 1 (4 crystals) and Wizard 4 (1 crystal), since 4 > 2 × 1
- Wizard 2 (3 crystals) and Wizard 4 (1 crystal), since 3 > 2 × 1
- Wizard 3 (5 crystals) and Wizard 4 (1 crystal), since 5 > 2 × 1

Constraints:
- 1 ≤ crystals.length ≤ 5 × 10^4
- -2^31 ≤ crystals[i] ≤ 2^31 - 1 
*/

import java.util.*;

class TreapNode {
    int val, priority, count, size;
    TreapNode left, right;

    TreapNode(int val) {
        this.val = val;
        this.priority = new Random().nextInt();
        this.count = 1;
        this.size = 1;
    }
}

class Treap {
    TreapNode root;

    int getSize(TreapNode node) {
        return node == null ? 0 : node.size;
    }

    void updateSize(TreapNode node) {
        if (node != null)
            node.size = node.count + getSize(node.left) + getSize(node.right);
    }

    TreapNode rotateRight(TreapNode p) {
        TreapNode q = p.left;
        p.left = q.right;
        q.right = p;
        updateSize(p);
        updateSize(q);
        return q;
    }

    TreapNode rotateLeft(TreapNode p) {
        TreapNode q = p.right;
        p.right = q.left;
        q.left = p;
        updateSize(p);
        updateSize(q);
        return q;
    }

    TreapNode insert(TreapNode node, int val) {
        if (node == null) return new TreapNode(val);

        if (val == node.val) {
            node.count++;
        } else if (val < node.val) {
            node.left = insert(node.left, val);
            if (node.left.priority > node.priority)
                node = rotateRight(node);
        } else {
            node.right = insert(node.right, val);
            if (node.right.priority > node.priority)
                node = rotateLeft(node);
        }

        updateSize(node);
        return node;
    }

    void insert(int val) {
        root = insert(root, val);
    }

    int countLessThan(TreapNode node, long val) {
        if (node == null) return 0;

        if (val <= node.val) {
            return countLessThan(node.left, val);
        } else {
            return getSize(node.left) + node.count + countLessThan(node.right, val);
        }
    }

    int countLessThan(long val) {
        return countLessThan(root, val);
    }
}

public class P1 {
    public static int reversePairs(int[] nums) {
        Treap treap = new Treap();
        int count = 0;

        for (int i = nums.length - 1; i >= 0; i--) {
            count += treap.countLessThan((long) nums[i]);
            treap.insert((int)(2L * nums[i]));
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // int n = sc.nextInt();
        String[] input = sc.nextLine().split(" ");
        int n = input.length;
        int[] nums = new int[n];
        
        for (int i = 0; i < n; i++)
            nums[i] = Integer.parseInt(input[i]);

        System.out.println(reversePairs(nums));
    }
}
