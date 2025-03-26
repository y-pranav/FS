/*
Imagine you're the chief engineer aboard a futuristic spaceship. The ship is 
powered by N series of fuel cells arranged in a row, where each fuel cell holds
a specific amount of energy measured in megajoules. Your job is to manage these 
fuel cells by performing two main operations:

Option 1: Calculate the total energy available in a consecutive block of fuel 
          cells between indices start and end (inclusive).
Option 2: Update the energy level with given 'newEnergy' of a specific 'index' 
          fuel cell when it's refilled.

Input Format:
-------------
Line-1: Two integers N and Q, where N is the number of fuel cells and Q is the number of operations.
Line-2: N space separated integers.
next Q lines: Three integers option, start/index and end/newEnergy.

Output Format:
--------------
An integer result, for every totalEnergy.


Example 1:
-----------
Input:
8 5
1 2 13 4 25 16 17 8
1 2 6		//totalEnerge
1 0 7		//totalEnerge
2 2 18		//recharge
2 4 17		//recharge
1 2 7		//totalEnerge
--------------------------
8 5
1 2 13 4 25 16 17 8
1 2 6		
1 0 7		
2 2 18		
2 4 17		
1 2 7		
Output:
75
86
80


Example 2:
----------
Input:
16 1
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
1 0 15

Output:
136


Constraints

- 1 <= N <= 3*10^4  
- -100 <= fuelCells[i] <= 100  
- 0 <= index < fuelCells.length  
- -100 <= newEnergy <= 100  
- 0 <= start <= end < fuelCells.length  
- At most 3*10^4 calls will be made to recharge and totalEnergy. 
*/

import java.util.*;

class P1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        int[] a = new int[n];
        FenwickTree FT = new FenwickTree(n);
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        FT.build(a);
        for (int i = 0; i < q; i++) {
            int option = sc.nextInt();
            if (option == 1) {
                int start = sc.nextInt();
                int end = sc.nextInt();
                System.out.println(FT.rangeQuery(start + 1, end + 1));
            } else {
                int index = sc.nextInt();
                int newEnergy = sc.nextInt();
                int delta = newEnergy - a[index];
                a[index] = newEnergy;
                FT.update(index + 1, delta);
            }
        }
        sc.close();
    }
}

class FenwickTree {
    public int[] bit;
    public int n;
    FenwickTree(int size) {
        this.n = size;
        bit = new int[n + 1];
    }    
    public void update(int i, int delta) {
        while (i <= n) {
            bit[i] += delta;
            i += i & -i;
        }
    }
    public int query(int i) {
        int sum = 0;
        while (i > 0) {
            sum += bit[i];
            i -= i & -i;
        }
        return sum;
    }
    public int rangeQuery(int x, int y) {
        return query(y) - query(x - 1);
    }
    public void build(int[] a) {
        for (int i = 1; i <= a.length; i++) {
            update(i, a[i - 1]);
        }
    }
    public String toString() {
        return Arrays.toString(bit);
    }
}

