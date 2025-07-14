/*
You are given n software modules, each with an integer difficulty di. You need to assign every module to exactly one of three servers (each server must get at least one module). After this assignment, an adversary will pick one module from each server, yielding three difficulties (d1, d2, d3). The deployment difficulty of that triple is defined as

|d1 − d2| + |d2 − d3|

Your goal is to make this adversary-forced difficulty as large as possible. In other words, choose a partition of the modules into three nonempty groups so that when the worst-case triple is extracted, its two-term sum of absolute differences is maximized. 
*/
import java.util.*;

public class P1 {

    public static int solve(int[] A) {
        int n = A.length;
        Arrays.sort(A);

        // 1) middle-block
        int D1 = A[n-1] - A[0];

        // 2) suffix-block
        int D2 = 0;
        for (int j = 2; j < n; j++) {
            // i=1 is best for each j ⇒ A[i-1]=A[0]
            D2 = Math.max(D2, 2 * A[j] - A[j-1] - A[0]);
        }

        // 3) prefix-block
        int D3 = 0;
        for (int i = 1; i + 1 < n; i++) {
            // j=n-1 is best for each i ⇒ A[j]=A[n-1]
            D3 = Math.max(D3, A[i] + A[n-1] - 2 * A[i-1]);
        }
        return Math.max(D1, Math.max(D2, D3));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = sc.nextInt();
        }
        sc.close();

        System.out.println(solve(A));
    }
}
