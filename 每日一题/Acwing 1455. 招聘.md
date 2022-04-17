### Acwing 1455. 招聘

题目链接：https://www.acwing.com/problem/content/1457/；参考约瑟夫环问题。

#### 代码

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static int[] A = new int[1010];
    
    public static void main(String[] args) throws IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bf.readLine());
        while (T -- > 0) {
            // input n, m, A1, A2,..., Am
            String[] S = bf.readLine().split(" ");
            int n = Integer.parseInt(S[0]), m = Integer.parseInt(S[1]);
            for (int i = 0; i < m; i++ ) {
                A[i] = Integer.parseInt(S[i+2]);
            }
            int res = 0;
            for (int i = 1, j = (n - 1) % m; i < n;) {
                i++;
                j = (j + m - 1) % m;
                res = (res + A[j]) % i;
            }
            System.out.println(res);
        }
    }
}
```

