## 剑指 Offer 13. 机器人的运动范围

https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof/

地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？



> 示例 1：
>
> 输入：m = 2, n = 3, k = 1
> 输出：3
>
> 
>
> 示例 2：
>
> 输入：m = 3, n = 1, k = 0
> 输出：1
>
> 
>
> 提示：
>
> 1 <= n,m <= 100
> 0 <= k <= 20
>
> 
>
> 来源：力扣（LeetCode）
> 链接：https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof
> 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。



### 代码

```java
class Solution {
    public int[] dirs = {1, 0, -1, 0, 1};
    public boolean[][] visited;
    public int m, n, k;
    public int movingCount(int _m, int _n, int _k) {
        m = _m;
        n = _n;
        k = _k;
        visited = new boolean[m][n];
        return dfs(0, 0);
    }

    public boolean checkValid(int x, int y) {
        return x / 10 + x % 10 + y / 10 + y % 10 <= k;
    }

    public int dfs(int x, int y) {
        if (x < 0 || y < 0 || x >= m || y >= n || !checkValid(x, y) || visited[x][y]) 
            return 0;
        
        visited[x][y] = true;
        int res = 1;
        for (int d = 0; d < 4; d++) {
            int nx = x + dirs[d], ny = y + dirs[d + 1];
            res += dfs(nx, ny);
        }
        return res;
    }
}
```

