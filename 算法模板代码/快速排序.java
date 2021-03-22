import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static int[] q;
    
    public static void quickSort(int[] q, int l, int r) {
        if (l >= r) return;
        int i = l - 1, j = r + 1, x = q[l];
        while (i < j) {
            do i++; while (q[i] < x);
            do j--; while (q[j] > x);
            if (i < j) {
                int t = q[i];
                q[i] = q[j];
                q[j] = t;
            }
        }
        quickSort(q, l, j);
        quickSort(q, j + 1, r);
    }
    
    public static void main(String[] args) throws IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] ip1 = bf.readLine().split(" ");
        int N = Integer.parseInt(ip1[0]);
        String[] ip2 = bf.readLine().split(" ");
        q = new int[N];
        for (int i = 0; i < N; i++) q[i] = Integer.parseInt(ip2[i]);
        quickSort(q, 0, N - 1);
        for (int i = 0; i < N; i++) System.out.printf("%d ", q[i]);
    }
}
