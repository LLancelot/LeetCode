import java.util.*;

public class Main{
    private static int N = 100010;
    private static int e[] = new int[N];
    private static int l[] = new int[N];
    private static int r[] = new int[N];
    private static int idx;

    //初始化第1个插入节点下标为2，所以操作第k个数时都要+1 ！！
    public static void init(){
        //左右边界指针,非节点
        r[0] = 1;  //指向第一个节点
        l[1] = 0;  //指向最后一个节点
        idx = 2;
    }
    public static void insertL(int x){
        e[idx] = x;
        r[idx] = r[0];
        l[idx] = 0;     //这个可不指，但反向遍历就需要，所以最好也指
        l[r[0]] = idx;
        r[0] = idx++;
    }
    //在右边界前插入时，新节点的右指针也要指向边界，否则无法遍历到边界（超时）！！
    public static void insertR(int x){
        e[idx] = x;
        l[idx] = l[1];
        r[idx] = 1;    //这个要指！！
        r[l[1]] = idx;
        l[1] = idx++;
    }
    public static void delete(int k){
        r[l[k]] = r[k];
        l[r[k]] = l[k];
    }
    public static void insertK(int k,int x){
        e[idx] = x;
        l[idx] = k;
        r[idx] = r[k];
        l[r[k]] = idx;
        r[k] = idx++;
    }
    public static void main(String[] args){
        init();
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        while(m-- > 0){
            String op = scanner.next();
            if("L".equals(op)){
                int x = scanner.nextInt();
                insertL(x);
            }else if("R".equals(op)){
                int x = scanner.nextInt();
                insertR(x);
            }else if("D".equals(op)){
                int k = scanner.nextInt();
                delete(k + 1);
            }else if("IL".equals(op)){
                int k = scanner.nextInt();
                int x = scanner.nextInt();
                insertK(l[k + 1],x);
            }else if("IR".equals(op)){
                int k = scanner.nextInt();
                int x = scanner.nextInt();
                insertK(k + 1,x);
            }
        }
        for(int i = r[0];i != 1;i = r[i]){
            System.out.print(e[i] +" ");
        }

    }
}
