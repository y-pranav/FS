import java.util.*;

public class P1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] items = new int[n];
        int[] rooms = new int[k];
        for (int i = 0; i < n; i++) {
            items[i] = sc.nextInt();
        }
        for (int i = 0; i < k; i++) {
            rooms[i] = sc.nextInt();
        }
        Arrays.sort(items);
        
    }
}