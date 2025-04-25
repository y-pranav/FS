import java.util.*;

public class Main {

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
