# Dijkstra最短路-堆优化

给定一个n个点m条边的有向图，图中可能存在重边和自环，所有边权均为非负值。

请你求出1号点到n号点的最短距离，如果无法从1号点走到n号点，则输出-1。

#### 输入格式

第一行包含整数n和m。

接下来m行每行包含三个整数x，y，z，表示存在一条从点x到点y的有向边，边长为z。

#### 输出格式

输出一个整数，表示1号点到n号点的最短距离。

如果路径不存在，则输出-1。

#### 数据范围

1≤n,m≤1.5×10^5
图中涉及边长均不小于0，且不超过10000。

#### 输入样例：

```
3 3
1 2 2
2 3 1
1 3 4
```

#### 输出样例：

```
3
```

## 思路

堆优化版的dijkstra是对朴素版dijkstra进行了优化，在朴素版dijkstra中时间复杂度最高的寻找距离最短的点O(n^2)可以使用最小堆优化。
1. 一号点的距离初始化为零，其他点初始化成无穷大。
2. 将一号点放入堆中。
3. 不断循环，直到堆空。每一次循环中执行的操作为：
    弹出堆顶（与朴素版diijkstra找到S外距离最短的点相同，并标记该点的最短路径已经确定）。
    用该点更新临界点的距离，若更新成功就加入到堆中。

## 代码

- c++

```cpp
#include<iostream>
#include<cstring>
#include<queue>

using namespace std;

typedef pair<int, int> PII;

const int N = 100010; // 把N改为150010就能ac

// 稀疏图用邻接表来存
int h[N], e[N], ne[N], idx;
int w[N]; // 用来存权重
int dist[N];
bool st[N]; // 如果为true说明这个点的最短路径已经确定

int n, m;

void add(int x, int y, int c)
{
    w[idx] = c; // 有重边也不要紧，假设1->2有权重为2和3的边，再遍历到点1的时候2号点的距离会更新两次放入堆中
    e[idx] = y; // 这样堆中会有很多冗余的点，但是在弹出的时候还是会弹出最小值2+x（x为之前确定的最短路径），并
    ne[idx] = h[x]; // 标记st为true，所以下一次弹出3+x会continue不会向下执行。
    h[x] = idx++;
}

int dijkstra()
{
    memset(dist, 0x3f, sizeof(dist));
    dist[0] = 1;
    priority_queue<PII, vector<PII>, greater<PII>> heap; // 定义一个小根堆
    // 这里heap中为什么要存pair呢，首先小根堆是根据距离来排的，所以有一个变量要是距离，其次在从堆中拿出来的时    
    // 候要知道知道这个点是哪个点，不然怎么更新邻接点呢？所以第二个变量要存点。
    heap.push({ 0, 1 }); // 这个顺序不能倒，pair排序时是先根据first，再根据second，这里显然要根据距离排序
    while(heap.size())
    {
        PII k = heap.top(); // 取不在集合S中距离最短的点
        heap.pop();
        int ver = k.second, distance = k.first;

        if(st[ver]) continue;
        st[ver] = true;

        for(int i = h[ver]; i != -1; i = ne[i])
        {
            int j = e[i]; // i只是个下标，e中在存的是i这个下标对应的点。
            if(dist[j] > distance + w[i])
            {
                dist[j] = distance + w[i];
                heap.push({ dist[j], j });
            }
        }
    }
    if(dist[n] == 0x3f3f3f3f) return -1;
    else return dist[n];
}

int main()
{
    memset(h, -1, sizeof(h));
    scanf("%d%d", &n, &m);

    while (m--)
    {
        int x, y, c;
        scanf("%d%d%d", &x, &y, &c);
        add(x, y, c);
    }

    cout << dijkstra() << endl;

    return 0;
}

作者：optimjie
链接：https://www.acwing.com/solution/content/6554/
来源：AcWing
```

- Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main{
    static int N = 100010;
    static int n;

    static int[] h = new int[N];
    static int[] e = new int[N];
    static int[] ne = new int[N];
    static int[] w = new int[N];
    static int idx = 0;
    static int[] dist = new int[N];// 存储1号点到每个点的最短距离
    static boolean[] st = new boolean[N];
    static int INF = 0x3f3f3f3f;//设置无穷大
    public static void add(int a,int b,int c)
    {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx ++;
    }

    // 求1号点到n号点的最短路，如果不存在则返回-1
    public static int dijkstra()
    {
        //维护当前未在st中标记过且离源点最近的点
        PriorityQueue<PIIs> queue = new PriorityQueue<PIIs>();
        Arrays.fill(dist, INF);
        dist[1] = 0;
        queue.add(new PIIs(0,1));
        while(!queue.isEmpty())
        {
            //1、找到当前未在s中出现过且离源点最近的点
            PIIs p = queue.poll();
            int t = p.getSecond();
            int distance = p.getFirst();
            if(st[t]) continue;
            //2、将该点进行标记
            st[t] = true;
            //3、用t更新其他点的距离
            for(int i = h[t];i != -1;i = ne[i])
            {
                int j = e[i];
                if(dist[j] > distance + w[i])
                {
                    dist[j] = distance + w[i];
                    queue.add(new PIIs(dist[j],j));
                }
            }

        }
        if(dist[n] == INF) return -1;
        return dist[n];
    }

    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] str1 = reader.readLine().split(" ");
        n = Integer.parseInt(str1[0]);
        int m = Integer.parseInt(str1[1]);
        Arrays.fill(h, -1);
        while(m -- > 0)
        {
            String[] str2 = reader.readLine().split(" ");
            int a = Integer.parseInt(str2[0]);
            int b = Integer.parseInt(str2[1]);
            int c = Integer.parseInt(str2[2]);
            add(a,b,c);
        }
        System.out.println(dijkstra());
    }
}

class PIIs implements Comparable<PIIs>{
    private int first;//距离值
    private int second;//点编号

    public int getFirst()
    {
        return this.first;
    }
    public int getSecond()
    {
        return this.second;
    }
    public PIIs(int first,int second)
    {
        this.first = first;
        this.second = second;
    }
    @Override
    public int compareTo(PIIs o) {
        // TODO 自动生成的方法存根
        return Integer.compare(first, o.first);
    }
}


作者：小呆呆
链接：https://www.acwing.com/solution/content/6291/
来源：AcWing
```

