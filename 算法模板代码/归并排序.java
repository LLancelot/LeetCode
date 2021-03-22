import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static int[] q, t;
    
    public static void mergeSort(int[] q, int l, int r) {
        if (l >= r) return;
        int mid = l + r >> 1;
        mergeSort(q, l, mid); mergeSort(q, mid + 1, r);
        int k = 0, i = l, j = mid + 1;
        while (i <= mid && j <= r) {
            if (q[i] < q[j]) t[k++] = q[i++];
            else t[k++] = q[j++];
        }
        while (i <= mid) t[k++] = q[i++];
        while (j <= r) t[k++] = q[j++];
        for (i = l, k = 0; i <= r; i++, k++)
            q[i] = t[k];
    }
    
    public static void main(String[] args) throws IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String ip1 = bf.readLine();
        String[] ip2 = bf.readLine().split(" ");
        int N = Integer.parseInt(ip1);
        q = new int[N];
        t = new int[N];
        for (int i = 0; i < N; i++) q[i] = Integer.parseInt(ip2[i]);
        mergeSort(q, 0, N - 1);
        for (int i = 0; i < N; i++) System.out.printf("%d ", q[i]);
    }
}
