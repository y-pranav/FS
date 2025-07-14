import java.util.*;

public class MaxOfAllSubarraysOfSizeK {

    // ✅ Deque-based O(n) solution
    public static int[] maxUsingDeque(int[] a, int k) {
        int n = a.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new LinkedList<>();
        int idx = 0;

        for (int i = 0; i < n; i++) {
            // Remove out-of-window elements
            while (!deque.isEmpty() && deque.peek() <= i - k) {
                deque.removeFirst();
            }

            // Remove smaller elements from the back
            while (!deque.isEmpty() && a[i] >= a[deque.peekLast()]) {
                deque.removeLast();
            }

            deque.addLast(i);

            // Add to result only after reaching window size
            if (i >= k - 1) {
                result[idx++] = a[deque.peek()];
            }
        }

        return result;
    }

    // ✅ PriorityQueue-based O(n log k) solution
    public static int[] maxUsingPriorityQueue(int[] a, int k) {
        int n = a.length;
        int[] result = new int[n - k + 1];
        int idx = 0;

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        int windowStart = 0;

        for (int windowEnd = 0; windowEnd < n; windowEnd++) {
            maxHeap.add(a[windowEnd]);

            if (windowEnd - windowStart + 1 == k) {
                result[idx++] = maxHeap.peek();

                // Remove the element going out of the window
                maxHeap.remove(a[windowStart]);

                windowStart++;
            }
        }

        return result;
    }

    public static void printArray(int[] arr) {
        for (int val : arr) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of elements:");
        int n = sc.nextInt();

        int[] a = new int[n];
        System.out.println("Enter array elements:");
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        System.out.println("Enter window size k:");
        int k = sc.nextInt();

        System.out.println("\n🔹 Using Deque (O(n)):");
        int[] resultDeque = maxUsingDeque(a, k);
        printArray(resultDeque);

        System.out.println("\n🔹 Using PriorityQueue (O(n log k)):");
        int[] resultPQ = maxUsingPriorityQueue(a, k);
        printArray(resultPQ);
    }
}
