import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
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
        RecurString solver = new RecurString();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class RecurString {
        public void solve(int testNumber, FastReader in, PrintWriter out) {
            char c[] = in.next().toCharArray();
            HashSet<String> h = new HashSet<>();
            permut(c, 0, c.length - 1, h);
            String s[] = new String[h.size()];
            int i = 0;
            for (String e : h) s[i++] = e;
            Arrays.sort(s);
            for (i = 0; i < h.size(); ++i) out.print(s[i] + " ");
            out.println();
        }

        public void permut(char[] c, int l, int r, HashSet<String> h) {
            if (l == r) {
                String s = String.valueOf(c);
                if (h.contains(s)) return;
                h.add(s);
                return;
            }
            int i = 0;
            for (i = l; i <= r; ++i) {
                interchange(c, l, i);
                permut(c, l + 1, r, h);
                interchange(c, l, i);
            }
        }

        public void interchange(char[] c, int a, int b) {
            char t = c[a];
            c[a] = c[b];
            c[b] = t;
            return;
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

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

