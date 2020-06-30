import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Comparator;
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
        SuffixArray2 solver = new SuffixArray2();
        solver.solve(1, in, out);
        out.close();
    }

    static class SuffixArray2 {
        public void solve(int testNumber, FastReader in, PrintWriter out) {
            String s = in.next();
            s += '$';
            char c[] = s.toCharArray();
            int n = s.length();
            int equiclass[] = new int[n], p[] = new int[n];
            pp[] a = new pp[n];
            for (int i = 0; i < n; ++i) a[i] = new pp(c[i], i);
            Arrays.sort(a, new Comparator<pp>() {

                public int compare(pp o1, pp o2) {
                    if (o1.c == o2.c) return o1.i - o2.i;
                    return o1.c - o2.c;
                }
            });
            for (int i = 0; i < n; i++) p[i] = a[i].i;
            equiclass[p[0]] = 0;
            for (int i = 1; i < n; ++i) {
                if (a[i].c == a[i - 1].c) equiclass[p[i]] = equiclass[p[i - 1]];
                else equiclass[p[i]] = equiclass[p[i - 1]] + 1;
            }
            int k = 0;
            while ((1 << k) < n) {
                for (int i = 0; i < n; ++i) p[i] = (p[i] - (1 << k) + n) % n;
                p = countSort(p, equiclass);
                int equiclass_new[] = new int[n];
                equiclass_new[p[0]] = 0;
                for (int i = 1; i < n; ++i) {
                    Pair now = new Pair(equiclass[p[i]], equiclass[(p[i] + (1 << k)) % n]);
                    Pair prev = new Pair(equiclass[p[i - 1]], equiclass[(p[i - 1] + (1 << k)) % n]);
                    if (now.x == prev.x && now.y == prev.y) equiclass_new[p[i]] = equiclass_new[p[i - 1]];
                    else equiclass_new[p[i]] = equiclass_new[p[i - 1]] + 1;
                }
                equiclass = equiclass_new;
                k++;
            }
            for (int e : p) out.print(e + " ");
        }

        public static int[] countSort(int[] p, int[] equiclass) {
            int n = p.length;
            int[] cnt = new int[n], pos = new int[n], p_new = new int[n];
            for (int e : equiclass) cnt[e]++;
            pos[0] = 0;
            for (int i = 1; i < n; ++i) pos[i] = pos[i - 1] + cnt[i - 1];
            for (int e : p) {
                int ind = equiclass[e];
                p_new[pos[ind]] = e;
                pos[ind]++;
            }
            return p_new;
        }

        public class pp {
            char c;
            int i;

            public pp(char c, int i) {
                this.c = c;
                this.i = i;
            }

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

