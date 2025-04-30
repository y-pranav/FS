/*
A digital advertising company is setting up electronic billboards across the city. 
Each billboard screen has dimensions of rows x cols, indicating how many lines (rows) 
and how many characters per line (cols) the screen can display. The company has 
prepared an advertising slogan consisting of several words, provided as a list of strings. 
The slogan must repeatedly appear on the billboard, word by word, maintaining the 
exact original order. Each word must fit entirely on a single line without breaking. 
Consecutive words on the same line must be separated by exactly one blank space.

Determine how many complete times the given advertising slogan can be displayed 
fully on the billboard screen.

Input format:
-------------
Line 1: Space seperated words, slogon
Line 2: Two space separated integers, rows & cols


Output format:
--------------
An integer, number of times the given advertising slogan can be displayed fully on the billboard screen.


Example 1:
----------
Input=
fast cars
2 8

Output=
1

Explanation:  
fast----  
cars----  
(The character '-' represents empty spaces on the screen.)


Example 2:
----------
Input=
win big now
3 7

Output=
2

Explanation:  
win-big  
now-win  
big-now  
(The character '-' represents empty spaces on the screen.)


Example 3:
----------
Input=
eat fresh daily
4 6

Output=1
 
Explanation:  
eat---  
fresh-  
daily-  
eat---  
(The character '-' represents empty spaces on the screen.)


Constraints:

- 1 <= slogan.length <= 1000
- Each word in slogan consists only of lowercase English letters.
- 1 <= rows, cols <= 2 *10^4 
*/

import java.util.*;

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] words = sc.nextLine().split(" ");
        int r = sc.nextInt();
        int c = sc.nextInt();

        int wi = 0;
        int pos = 0;
        int col = 0;
        int cnt = 0;
        while (col < r) {
            int len = words[wi].length();
            if (len + pos <= c) {
                pos += len + 1;
                wi++;
            } else {
                pos = 0;
                col++;
            }
            if (wi == words.length) {
                cnt++;
                wi = 0;
            }
        }
        System.out.println(cnt);
        sc.close();
    }
}