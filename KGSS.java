import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Pranay2516
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastReader in = new FastReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        KGSS solver = new KGSS();
        solver.solve(1, in, out);
        out.close();
    }

    static class KGSS {
        public void solve(int testNumber, FastReader in, PrintWriter out) {
            int n = in.nextInt(), a[] = in.readIntArray(n);
            int q = in.nextInt();
            Pair[] tree = new Pair[4 * n + 1];
            for (int i = 0; i < 4 * n + 1; ++i) tree[i] = new Pair(0, 0);
            b(tree, a, 1, 0, n - 1);
            while (q-- > 0) {
                String s = in.next();
                int sum = 0;
                if (s.equals("Q")) {
                    int x = in.nextInt() - 1, y = in.nextInt() - 1;
                    int ind1 = q(tree, 1, 0, n - 1, x, y).y;
                    int val1 = q(tree, 1, 0, n - 1, x, y).x;
                    u(tree, 1, 0, n - 1, ind1, -1000);
                    int ind2 = q(tree, 1, 0, n - 1, x, y).y;
                    int val2 = q(tree, 1, 0, n - 1, x, y).x;
                    u(tree, 1, 0, n - 1, ind1, val1);
                    out.println(val1 + val2);
                } else {
                    int i = in.nextInt() - 1, x = in.nextInt();
                    u(tree, 1, 0, n - 1, i, x);
                    a[i] = x;
                }
            }
        }

        public static void b(Pair[] tree, int a[], int index, int s, int e) {
            if (s > e) return;
            if (s == e) {
                tree[index].x = a[s];
                tree[index].y = s;
                return;
            }
            int mid = (s + e) / 2;
            b(tree, a, 2 * index, s, mid);
            b(tree, a, 2 * index + 1, mid + 1, e);
            if (tree[2 * index].x > tree[2 * index + 1].x) tree[index] = tree[2 * index];
            else tree[index] = tree[1 + 2 * index];
        }

        public static Pair q(Pair[] tree, int index, int s, int e, int x, int y) {
            if (y < s || x > e) return new Pair(-10000, -1);
            else if (x <= s && y >= e) return tree[index];
            int mid = (s + e) / 2;
            Pair lans = q(tree, 2 * index, s, mid, x, y);
            Pair rans = q(tree, 2 * index + 1, mid + 1, e, x, y);
            if (lans.x > rans.x) return lans;
            else return rans;
        }

        public static void u(Pair[] tree, int index, int s, int e, int i, int v) {
            if (i < s || i > e) return;
            if (s == e) {
                tree[index].x = v;
                tree[index].y = s;
                return;
            }
            int mid = (s + e) / 2;
            u(tree, 2 * index, s, mid, i, v);
            u(tree, 2 * index + 1, mid + 1, e, i, v);
            if (tree[2 * index].x > tree[2 * index + 1].x) tree[index] = tree[2 * index];
            else tree[index] = tree[2 * index + 1];
        }

    }

    static class FastReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private FastReader.SpaceCharFilter filter;

        public FastReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1) throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            while (!isSpaceChar(c));
            return res * sgn;
        }

        public String next() {
            int c = read();
            while (isSpaceChar(c)) c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            }
            while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public int[] readIntArray(int size) {
            int[] array = new int[size];
            for (int i = 0; i < size; i++) array[i] = nextInt();
            return array;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }

    static class Pair {
        public int x;
        public int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
}

