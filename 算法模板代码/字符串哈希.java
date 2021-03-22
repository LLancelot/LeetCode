// 作者：acw_林少
// 链接：https://www.acwing.com/activity/content/code/content/994452/
// 来源：AcWing
// 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static long[] h, p;
    public static int P = 131;

    public static long query(int l, int r) {
        return h[r] - h[l - 1] * p[r - l + 1];
    }

    public static void main(String[] args) throws IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] ip1 = bf.readLine().split(" ");
        String str = bf.readLine();
        int n = Integer.parseInt(ip1[0]);
        int m = Integer.parseInt(ip1[1]);

        h = new long[n + 1];
        p = new long[n + 1];
        p[0] = 1;
        for (int i = 1; i <= n; i++) {
            h[i] = (h[i - 1] * P + (str.charAt(i - 1) - 'a' + 1)) % Long.MAX_VALUE;
            p[i] = p[i - 1] * P;
        }

        while (m -- > 0) {
            int l1, r1, l2, r2;
            String[] line = bf.readLine().split(" ");
            l1 = Integer.parseInt(line[0]);
            r1 = Integer.parseInt(line[1]);
            l2 = Integer.parseInt(line[2]);
            r2 = Integer.parseInt(line[3]);

            if (query(l1, r1) == query(l2, r2)) System.out.println("Yes");
            else System.out.println("No");
        }
    }
}
