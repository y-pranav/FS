/*
Imagine a company where each employee has a performance score, and 
the organizational chart is structured as a binary tree with the CEO at the top. 
An employee is considered "outstanding" if, along the chain of command from the 
CEO down to that employee, no one has a performance score higher than that 
employee's score. Your task is to determine the total number of outstanding 
employees in the company.

Example 1:
Input: 3 1 4 3 -1 1 5
Output: 4

Chart formed:
         3
        / \
       1   4
      /   / \
     3   1   5

Explanation:
- The CEO (score 3) is automatically outstanding.
- The employee with score 4, whose chain is [3,4], is outstanding because 4 
  is higher than 3.
- The employee with score 5, following the path [3,4,5], is outstanding as 5 
  is the highest so far.
- The subordinate with score 3, along the path [3,1,3], is outstanding because 
  none of the managers in that chain have a score exceeding 3.

Example 2:
Input: 3 3 -1 4 2
Output: 3

Chart formed:
       3
      / 
     3
    / \
   4   2

Explanation:
- The CEO (score 3) is outstanding.
- The subordinate with score 3 (chain: [3,3]) is outstanding.
- The employee with score 2 (chain: [3,3,2]) is not outstanding because the 
  managers have higher scores.
 
*/

