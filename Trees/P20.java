/*
Imagine you’re decoding a secret message that outlines the hierarchical structure 
of a covert spy network. The message is a string composed of numbers and parentheses. 
Here’s how the code works:

- The string always starts with an agent’s identification number, this is the 
  leader of the network.
- After the leader’s ID, there can be zero, one, or two segments enclosed in 
  parentheses. Each segment represents the complete structure of one subordinate 
  network.
- If two subordinate networks are present, the one enclosed in the first (leftmost) 
  pair of parentheses represents the left branch, and the second (rightmost) 
  represents the right branch.

Your mission is to reconstruct the entire spy network hierarchy based on this 
coded message.

Example 1:
Input: 4(2(3)(1))(6(5))
Output: [4, 2, 6, 3, 1, 5]

Spy network:
        4
       / \
      2   6
     / \  /
    3   1 5

Explanation:
Agent 4 is the leader.
Agent 2 (with its own subordinates 3 and 1) is the left branch.
Agent 6 (with subordinate 5) is the right branch.

Example 2:
Input: 4(2(3)(1))(6(5)(7))
Output: [4, 2, 6, 3, 1, 5, 7]

Spy network:
         4
       /   \
      2     6
     / \   / \
    3   1 5   7

Explanation:
Agent 4 leads the network.
Agent 2 with subordinates 3 and 1 forms the left branch.
Agent 6 with subordinates 5 and 7 forms the right branch.
 
*/