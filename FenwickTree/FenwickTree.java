import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of elements: ");
        int n = sc.nextInt();
        System.out.println("Enter " + n + " elements:");
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        
        FenwickTree FT = new FenwickTree(n);
        FT.build(a);

        while (true) {
            System.out.println("Enter queries -> x, y");
            int x = sc.nextInt();
            int y = sc.nextInt();
            System.out.println("Input array: " + Arrays.toString(a));
            System.out.println("BIT: " + FT.toString());
            System.out.println("Sum of elements from index " + x + " to index " + y + " = " + (FT.query(y) - FT.query(x - 1)));
        }
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
    public void build(int[] a) {
        for (int i = 1; i <= a.length; i++) {
            update(i, a[i - 1]);
        }
    }
    public String toString() {
        return Arrays.toString(bit);
    }
}

