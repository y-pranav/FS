/*
Mr PushpaRaj has N bottles of Spirit as bottles[], where ith-bottle contains 
bottle[i] ltrs of Spirit. He wants to make that all the bottles contains same
amount of Spirit. Generally, Spirit evaporates quickly, due to this, 
P percentage of Spirit will be evaporated while pouring the Spirit from one 
bottle to another bottle, i.e., if He poured M litres of Spirit from one bottle
to another bottle, the amount of Spirit evaporated is P% of M.

Your task is to help Mr PushpaRaj to find the maximum amount of Spirit 
in each bottle after making that all the bottles have same amount of Spirit. 

NOTE: Print the result within 10^(-5) of the actual result will be accepted.

Input Format:
-------------
Line-1: Two space separated integers, N and P.
Line-2: N space separated integers, bottles[].

Output Format:
--------------
Print a double value rounded to 5 decimals. 



Sample Input-1:
---------------
4 75
2 8 3 12

Sample Output-1:
----------------
4.00000

Explanation:
------------
::-The bottles contains {2,8,3,12},Pour 4 ltrs from bottles[1] to bottles[0].
4 * 75% = 3 ltrs are evaporated and bottles[0] only got 4 - 3 = 1 ltr of Spirit.
::-The bottles contains {3,4,3,12}, Pour 4 ltrs from bottles[3] to bottles[0].
4 * 75% = 3 ltrs are evaporated and bottles[0] only got 4 - 3 = 1 ltr of Spirit.
::-The bottles contains {4,4,3,8}, Pour 4 ltrs from bottles[3] to bottles[2].
4 * 75% = 3 ltrs are evaporated and bottles[0] only got 4 - 3 = 1 ltr of Spirit.
::-All the bottles have 4 ltrs of Spirit in them, so return 4.


Sample Input-2:
---------------
4 50
3 5 8 10

Sample Output-2:
----------------
5.66666
 
*/