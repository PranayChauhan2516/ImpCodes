import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.ArrayList;

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
        Task solver = new Task();
        solver.solve(1, in, out);
        out.close();
    }

    static class Task {
        public void solve(int testNumber, FastReader in, PrintWriter out) {
            int t = in.nextInt();
            boolean ans[] = func.SieveOfEratosthenes(100000001);
            while (t-- > 0) {
                int l = in.nextInt(), r = in.nextInt();
                boolean a[] = func.SegmentedSieve(l, r, ans);
                for (int i = 0; i < r - l + 1; ++i) if (a[i] && i + l != 1) out.println(i + l);
                out.println();
            }
        }

    }

    static class func {
        public static boolean[] SieveOfEratosthenes(int max) {
            boolean a[] = new boolean[max];
            for (int i = 3; i < max; i += 2) a[i] = true;
            for (int i = 3; i < max; ++i) if (a[i]) for (long j = (long) i * i; j < max; j += i) a[(int) j] = false;
            a[2] = true;
            a[1] = a[0] = false;
            return a;
        }

        public static boolean[] SegmentedSieve(long l, long r, boolean ans[]) {
            boolean b[] = new boolean[(int) (r - l + 1)];
            Arrays.fill(b, true);
            ArrayList<Integer> ban = new ArrayList<>();
            for (int i = 2; i < 1000001; ++i) if (ans[i]) ban.add(i);
            for (int i = 0; (long) ban.get(i) * ban.get(i) <= r; i++) {
                int cur = ban.get(i);
                long base = (l / cur) * cur;
                if (base < l) base = base + cur;
                for (long j = base; j <= r; j += cur) b[(int) (j - l)] = false;
                if (base == cur) b[(int) (base - l)] = true;
            }
            return b;
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

