import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

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
        GSS1 solver = new GSS1();
        solver.solve(1, in, out);
        out.close();
    }

    static class GSS1 {
        public void solve(int testNumber, FastReader in, PrintWriter out) {
            int n = in.nextInt();
            int a[] = new int[n];
            for (int i = 0; i < n; ++i) a[i] = in.nextInt();
            int m = in.nextInt();
            GSS1.node[] tree = new GSS1.node[4 * n + 1];
            for (int i = 0; i < 4 * n + 1; ++i) tree[i] = new GSS1.node();
            int s = 0, e = n - 1;
            buildTree(tree, a, 1, s, e);
            while (m-- > 0) {
                int x = in.nextInt() - 1, y = in.nextInt() - 1;
                out.println(queryUtils(tree, 1, s, e, x, y).max);
            }
        }

        public static void buildTree(GSS1.node[] tree, int[] a, int index, int s, int e) {
            if (s > e) return;
            if (s == e) {
                tree[index].sum = a[s];
                tree[index].pref = a[s];
                tree[index].suff = a[s];
                tree[index].max = a[s];
                return;
            }
            int mid = (s + e) / 2;
            buildTree(tree, a, 2 * index, s, mid);
            buildTree(tree, a, 2 * index + 1, mid + 1, e);
            tree[index] = merge(tree[2 * index], tree[2 * index + 1]);
        }

        public static GSS1.node merge(GSS1.node l, GSS1.node r) {
            GSS1.node parent = new GSS1.node();
            parent.pref = Math.max(l.pref, l.sum + r.pref);
            parent.suff = Math.max(r.suff, r.sum + l.suff);
            parent.sum = l.sum + r.sum;
            parent.max = Math.max(l.max, Math.max(r.max, l.suff + r.pref));
            return parent;
        }

        public static GSS1.node queryUtils(GSS1.node[] tree, int index, int s, int e, int x, int y) {
            GSS1.node res = new GSS1.node();
            res.sum = res.pref = res.suff = res.max = -100;
            if (e < x || s > y) return res;
            if (x <= s && e <= y) return tree[index];
            int mid = (s + e) / 2;
            if (x > mid) return queryUtils(tree, 2 * index + 1, mid + 1, e, x, y);
            if (y <= mid) return queryUtils(tree, 2 * index, s, mid, x, y);
            GSS1.node l = queryUtils(tree, 2 * index, s, mid, x, y);
            GSS1.node r = queryUtils(tree, 2 * index + 1, mid + 1, e, x, y);
            res = merge(l, r);
            return res;
        }

        public static class node {
            int sum;
            int pref;
            int suff;
            int max;
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

            if (numChars == -1)
                throw new InputMismatchException();

            if (curChar >= numChars) {

                curChar = 0;

                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }

                if (numChars <= 0)
                    return -1;
            }

            return buf[curChar++];
        }

        public int nextInt() {

            int c = read();

            while (isSpaceChar(c))
                c = read();

            int sgn = 1;

            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;

            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();

                res *= 10;
                res += c - '0';
                c = read();
            }
            while (!isSpaceChar(c));

            return res * sgn;
        }

        public boolean isSpaceChar(int c) {

            if (filter != null)
                return filter.isSpaceChar(c);

            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

