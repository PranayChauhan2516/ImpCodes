import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
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
        AGGRCOWS solver = new AGGRCOWS();
        solver.solve(1, in, out);
        out.close();
    }

    static class AGGRCOWS {
        public void solve(int testNumber, FastReader in, PrintWriter out) {
            int t = in.nextInt();
            while (t-- > 0) {
                int n = in.nextInt(), c = in.nextInt();
                int x[] = in.readIntArray(n);
                Arrays.sort(x);
                out.println(binSearch(x, n, c));
            }
        }

        public long binSearch(int[] x, int n, int c) {
            long s = 0, e = x[n - 1], ans = 0;
            while (s <= e) {
                long mid = (s + e) / 2;
                if (isValid(x, n, c, mid)) {
                    ans = mid;
                    s = mid + 1;
                } else e = mid - 1;
            }
            return ans;
        }

        public boolean isValid(int[] x, int n, int c, long mid) {
            int cnt = 1, j = 1, i = 0;
            while (j < n && i < n) {
                if (x[j] - x[i] >= mid) {
                    cnt++;
                    i = j;
                    j = i + 1;
                } else j++;
            }
            return cnt >= c;
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

