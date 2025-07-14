/*
A group of students is forming a tech club, and they are 
interviewing K × K students. Each student has two skill levels:
 - Mathematics (M)
 - Physics (P)
These skill levels range from 0 to K-1.

They want to form committees of size N under the following 
conditions:
 - All members of a committee must have different Mathematics 
   skill levels.
 - All members must have different Physics skill levels.
 - For any two students in the committee, the difference of their 
   Math skills must not equal the difference of their Physics skills, 
   i.e., |M1 - M2| != |P1 - P2|

Input Format:
-------------
input1: Integer N – desired committee size
input2: Integer K – number of skill levels for Math and Physics 
(total students = K × K)

Output Fromat:
--------------
An integer representing the number of valid committees of size N 
that can be formed from the K × K students. Return the result 
modulo 10⁹ + 7.

Sample Input: 
-------------
2
3

Sample Output: 
--------------
8

Explanation:
------------
Examples of some valid pairs:
(0,0) and (1,2) → |0-1| = 1, |0-2| = 2 ✅
(0,1) and (1,0) → |0-1| = 1, |1-0| = 1 ❌ (same diff → invalid)
(0,0) and (2,1) → |0-2| = 2, |0-1| = 1 ✅

8 Valid Pairs: 1. (0,0) - (1,2)
2. (0,0) - (2,1)
3. (0,1) - (2,0)
4. (0,2) - (1,0)
5. (0,2) - (2,1)
6. (1,0) - (2,2)
7. (1,2) - (0,0)
8. (1,2) - (2,0)

Sample Input: 
-------------
2
2

Sample Output: 
--------------
0
 
*/

import java.util.*;

class Solution{
    static int mod = (int)1e9 + 7;
    static int res = 0;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int k = sc.nextInt();
        
        List<int[]> students = new ArrayList<>();
        
        for(int i=0;i<k;i++){
            for(int j=0;j<k;j++){
                students.add(new int[]{i,j});
            }
        }
        
        backtrack(0, students, new ArrayList<>(), new HashSet<>(), new HashSet<>(), n, k);
        
        System.out.println(res);
    }
    
    private static void backtrack(int start, List<int[]> students, List<int[]> committee, Set<Integer> math, Set<Integer> phy, int n, int k){
        if(committee.size()==n){
            res = (res + 1) % mod;
            return;
        }
        
        for(int i=start;i<students.size();i++){
            int[] student = students.get(i);
            int m = student[0], p = student[1];
            if(math.contains(student[0]) || phy.contains(student[1])) continue;
            
            boolean isvalid = true;
            for(int[] s: committee){
                int diff1 = Math.abs(m - s[0]);
                int diff2 = Math.abs(p - s[1]);
                
                if(diff1 == diff2){
                    isvalid = false;
                    break;
                }
            }
            
            if(!isvalid) continue;
            
            committee.add(new int[]{m,p});
            math.add(m);
            phy.add(p);
            
            backtrack(i + 1, students, committee, math, phy, n, k);
            
            committee.remove(committee.size()-1);
            math.remove(m);
            phy.remove(p);
            
        }
    }
}
