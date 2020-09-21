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
        CoinCombinationsII solver = new CoinCombinationsII();
        solver.solve(1, in, out);
        out.close();
    }

    static class CoinCombinationsII {
        public void solve(int testNumber, FastReader in, PrintWriter out) {
            int n = in.nextInt(), x = in.nextInt(), a[] = in.readIntArray(n), mod = (int) 1e9 + 7;
            int dp[][] = new int[n + 1][x + 1];
            for (int i = 1; i <= n; ++i) {
                for (int sum = 0; sum <= x; ++sum) {
                    if (sum == 0) dp[i][sum] = 1;
                    else {
                        int op1 = (a[i - 1] > sum) ? 0 : dp[i][sum - a[i - 1]];
                        int op2 = (i == 1) ? 0 : dp[i - 1][sum];
                        dp[i][sum] = (op1 + op2) % mod;
                    }
                }
            }
            out.println(dp[n][x]);
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

        public boolean isSpaceChar(int c) {
            if (filter != null) return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public int[] readIntArray(int size) {
            int[] array = new int[(int) size];
            for (int i = 0; i < size; i++) array[i] = nextInt();
            return array;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

