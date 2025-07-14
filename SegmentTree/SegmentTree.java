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

    public int rangeQuery(int l, int r) {
        return query(0, n - 1, l, r,  0);
    }

    private int query(int start, int end, int l, int r, int pos) {
        if (l <= start && r >= end) {
            return tree[pos]; 
        }
        if (l > end || r < start) {
            return 0; 
        }
        int mid = (start + end) / 2;
        return query(start, mid, l, r, 2 * pos + 1) +
            query(mid + 1, end, l, r, 2 * pos + 2); 
    }

    public void update(int index, int newValue) {
        update(index, newValue, 0, n - 1, 0);
    }

    private void update(int index, int newValue, int start, int end, int pos) {
        if (start == end) {
            tree[pos] = newValue;
            return;
        }
        int mid = (start + end) / 2;
        if (index <= mid) {
            update(index, newValue, start, mid, 2 * pos + 1);
        } else {
            update(index, newValue, mid + 1, end, 2 * pos + 2);
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
