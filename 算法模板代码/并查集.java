import java.util.*;

public class Main {
    static int[] p;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        p = new int[n + 10];
        for (int i = 1; i <= n; i ++) {
            p[i] = i; // 让每个元素的父亲是自己
        }
        while (m -- > 0) {
            String o = in.next();
            int a = in.nextInt();
            int b = in.nextInt();
            if ("M".equals(o)) {
                p[find(a)] = find(b);
            } else {
                if (find(a) == find(b)) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
            }
        }
    }
    private static int find(int x) {
        if (x != p[x]) { // 父亲不是自己说明还没到根节点
            p[x] = find(p[x]);
        }
        return p[x]; // 返回父亲
    }
}

作者：luyunix
链接：https://www.acwing.com/activity/content/code/content/49199/
来源：AcWing
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。