/*
You are a bird living in a vast forest. Every day, you fly across different locations 
to collect food and store it in various nests. However, you must return to your home 
nest before sunset to rest safely.  

Your objective is to collect as much food as possible within a given time limit 
while following the forest rules:
1. Each food location contains only one unit of food.  
2. The bird can carry only one unit of food at a time.  
3. The bird must deposit food into a nest before collecting more.  
4. Distance Calculation: The time taken to fly between two locations using 
the Euclidean distance formula:  d = sqrt{(x_2 - x_1)^2 + (y_2 - y_1)^2}
5. The total time spent is the sum of:  
   - Travel time between locations.  
   - Fixed time to deposit food in a nest (each nest has a different deposit 
   time).  
6. The bird must return to the home nest before the total time limit.  

Your goal is to determine the maximum number of food units that the bird
can collect and store in different nests before sunset.  

Input Format:
-------------
An integer representing the number of food locations.  
An integer representing the number of nests.  
A 2D array containing the coordinates of each food location as pairs (x, y) 
A 2D array containing the coordinates of each nest as pairs (x, y).  
A 1D array containing the starting coordinates (home nest) (x, y).  
A floating-point number representing the total time available (before sunset).  

Output Format:
---------------  
The function must return an integer, representing the maximum number of 
food units that can be collected and stored in nests within the given time.  

Sample Input:
--------------
2
2
3 3
4 6
5 5
6 1
1 4
13

Sample Output:
---------------
2

Explanation:
---------------
The bird starts at (1,4).
Moves to food location (3,3) (distance = sqrt(5)).
Deposits food at a nest (5,5) (distance = sqrt(8)).
Moves to food location (4,6) (distance = sqrt(2))
and collects another unit of food.
Deposits it at a nest (5,5) (distance = sqrt(2)). 
Returns to the starting point (1,4) (distance = sqrt(17)).
Total distance is: sqrt(5) + sqrt(8) + sqrt(2) + sqrt(2)+ sqrt(17) = 12.0160278526
Since the total distance is within the allowed time (13), 
the maximum food units collected are 2.


Sample Input:
--------------
4
1
3 3
5 7
7 8
8 4
7 7
1 5
22

Sample Output:
---------------
3 
*/