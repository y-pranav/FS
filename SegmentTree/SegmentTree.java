public class SegmentTree {
    private int[] tree;
    private int n;

    public SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        build(arr, 0, n - 1, 0);
    }

    private void build(int[] arr, int start, int end, int pos) {
        if (start == end) {
            tree[pos] = arr[start];
            return;
        }
        int mid = (start + end) / 2;
        build(arr, start, mid, 2 * pos + 1);
        build(arr, mid + 1, end, 2 * pos + 2);
        tree[pos] = tree[2 * pos + 1] + tree[2 * pos + 2];
    }

    public int rangeQuery(int qlow, int qhigh) {
        return rangeQuery(qlow, qhigh, 0, n - 1, 0);
    }

    private int rangeQuery(int qlow, int qhigh, int low, int high, int pos) {
        if (qlow <= low && qhigh >= high) {
            return tree[pos];
        }
        if (qlow > high || qhigh < low) {
            return 0;
        }
        int mid = (low + high) / 2;
        return rangeQuery(qlow, qhigh, low, mid, 2 * pos + 1) +
               rangeQuery(qlow, qhigh, mid + 1, high, 2 * pos + 2);
    }

    public void update(int index, int newValue) {
        update(index, newValue, 0, n - 1, 0);
    }

    private void update(int index, int newValue, int low, int high, int pos) {
        if (low == high) {
            tree[pos] = newValue;
            return;
        }
        int mid = (low + high) / 2;
        if (index <= mid) {
            update(index, newValue, low, mid, 2 * pos + 1);
        } else {
            update(index, newValue, mid + 1, high, 2 * pos + 2);
        }
        tree[pos] = tree[2 * pos + 1] + tree[2 * pos + 2];
    }

    public void printTree() {
        for (int i = 0; i < tree.length; i++) {
            System.out.print(tree[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11};
        SegmentTree st = new SegmentTree(arr);
        
        System.out.println("Segment Tree (array representation):");
        st.printTree();

        int sum = st.rangeQuery(1, 3);
        System.out.println("Sum of values in range [1, 3]: " + sum);

        st.update(1, 10);
        System.out.println("After updating index 1 to 10:");
        st.printTree();
        sum = st.rangeQuery(1, 3);
        System.out.println("Sum of values in range [1, 3]: " + sum);
    }
}
