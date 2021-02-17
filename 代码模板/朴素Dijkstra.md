# Dijkstra求最短路①

给定一个n个点m条边的有向图，图中可能存在重边和自环，所有边权均为正值。

请你求出1号点到n号点的最短距离，如果无法从1号点走到n号点，则输出-1。

#### 输入格式

第一行包含整数n和m。

接下来m行每行包含三个整数x，y，z，表示存在一条从点x到点y的有向边，边长为z。

#### 输出格式

输出一个整数，表示1号点到n号点的最短距离。

如果路径不存在，则输出-1。

#### 数据范围

1≤n≤500,

1≤m≤10^5,

图中涉及边长均不超过10000。

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

## 代码及注释

- 朴素版时间复杂度 $O(n^2)$

```cpp
#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

const int N = 510;
int n, m;
int g[N][N]; //稠密图一般使用邻接矩阵
int dist[N]; //记录每个节点距离起点的距离
bool st[N]; //True表示已经确定最短路 属于s集合

int dijkstra() {
    //所有节点距离起点的距离初始化为无穷大
    memset(dist, 0x3f, sizeof dist);
    //起点距离自己的距离为零
    dist[1] = 0;

    //迭代n次，每次可以确定一个点到起点的最短路
    for (int i = 0; i < n; ++i) {
        int t = -1;
        //t的作用？

        for (int j = 1; j <= n; ++j) {
            //不在s集合，并且
            //如果没有更新过，则进行更新， 或者发现更短的路径，则进行更新
            if (!st[j] && (t == -1 || dist[j] < dist[t])) {
                t = j;
            }
        }

        //加入到s集合中
        st[t] = true;

        //找到了距离最小的点t，并用最小的点t去更新其他的点到起点的距离
        for (int j = 1; j <= n; ++j) {
            dist[j] = min(dist[j], dist[t] + g[t][j]);
        }
    }

    // 如果起点到达不了n号节点，则返回-1
    if (dist[n] == 0x3f3f3f3f) return -1;
    // 返回起点距离n号节点的最短距离
    return dist[n];
}

int main() {
    cin >> n >> m;
    //所有节点之间的距离初始化为无穷大
    memset(g, 0x3f, sizeof g);
    // 0x3f 0x3f3f3f3f 的区别？

    while (m--) {
        int a, b, c;
        scanf("%d%d%d", &a, &b, &c);
        g[a][b] = min(g[a][b], c); //如果有重边，请保留权值最小的一条边
    }

    cout << dijkstra() << endl;

    return 0;
}

作者：松鼠爱葡萄
链接：https://www.acwing.com/solution/content/13602/
来源：AcWing
```

