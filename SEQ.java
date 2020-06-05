import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
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
        Task solver = new Task();
        solver.solve(1, in, out);
        out.close();
    }

    static class Task {
        public static long mod = 1000000000;

        public void solve(int testNumber, FastReader in, PrintWriter out) {
            int t = in.nextInt();
            while (t-- > 0) {
                int k = in.nextInt();
                int b[] = in.readIntArray(k);
                int c[] = in.readIntArray(k);
                int n = in.nextInt();
                if (n <= k) {
                    out.println(b[n - 1]);
                } else {
                    long tr[][] = new long[k][k];
                    for (int i = 0; i < k; ++i) Arrays.fill(tr[i], 0);
                    for (int i = 0; i < k - 1; ++i) {
                        tr[i][i + 1] = 1;
                    }
                    for (int j = k - 1; j >= 0; j--) {
                        tr[k - 1][k - j - 1] = (long) c[j];
                    }
                    tr = power(tr, n - k, k);
                    long res = 0;
                    for (int i = 0; i < k; ++i) res = (res + tr[k - 1][i] * ((long) b[i])) % mod;
                    out.println(res);
                }
            }
        }

        public static long[][] power(long[][] a, int n, int k) {
            long t[][] = new long[k][k];
            for (int i = 0; i < k; ++i) Arrays.fill(t[i], 0);
            if (n == 1) return a;
            if (n % 2 == 1) return mul(a, power(a, n - 1, k), k);
            t = power(a, n / 2, k);
            return mul(t, t, k);
        }

        public static long[][] mul(long[][] a, long[][] b, int k) {
            long[][] m = new long[k][k];
            for (int i = 0; i < k; ++i) {
                for (int j = 0; j < k; ++j) {
                    for (int o = 0; o < k; ++o) {
                        m[i][j] = (m[i][j] + a[i][o] * b[o][j]) % mod;
                    }
                }
            }
            return m;
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

        public int[] readIntArray(int size) {
            int[] array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = nextInt();
            }
            return array;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

