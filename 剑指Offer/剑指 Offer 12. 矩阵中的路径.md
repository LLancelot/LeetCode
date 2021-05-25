## 剑指 Offer 12. 矩阵中的路径

https://leetcode-cn.com/problems/ju-zhen-zhong-de-lu-jing-lcof/

给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。

单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。

 

例如，在下面的 3×4 的矩阵中包含单词 "ABCCED"（单词中的字母已标出）。

 ![img](https://assets.leetcode.com/uploads/2020/11/04/word2.jpg)

示例 1：

输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"

输出：true



示例 2：

输入：board = [["a","b"],["c","d"]], word = "abcd"

输出：false



提示：

1 <= board.length <= 200

1 <= board[i].length <= 200

board 和 word 仅由大小写英文字母组成



注意：本题与主站 79 题相同：https://leetcode-cn.com/problems/word-search/



来源：力扣（LeetCode）

链接：https://leetcode-cn.com/problems/ju-zhen-zhong-de-lu-jing-lcof

著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。



### 代码

- 经典的DFS 回溯，注意还原现场（每次遍历需要修改`g[x][y]`或者设置一个`visited[x][y]`记录当前位置已被访问，遍历完成后需要还原成原来的状态，即`g[x][y] = ch`）。

```java
class Solution {
    public int[] dirs = {1, 0, -1, 0, 1}; // DLUR
    public int n, m;
    public char[][] g;
    public String word;
    public boolean exist(char[][] board, String w) {
        g = board;
        n = board.length;
        m = board[0].length;
        word = w;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (dfs(i, j, 0))
                    return true;
            }
        }
        return false;
    }
    public boolean dfs(int x, int y, int k) {
        if (k == word.length()) return true;
        if (x < 0 || y < 0 || x >= n || y >= m || g[x][y] != word.charAt(k))
            return false;
        
        boolean res = false;
        char ch = g[x][y];
        g[x][y] = '.';
        
        for (int d = 0; d < 4; d++) {
            int nx = x + dirs[d], ny = y + dirs[d + 1];
            res = dfs(nx, ny, k + 1);
            if (res == true) 
                break;
        }

        g[x][y] = ch;
        return res;
    }
}
```

