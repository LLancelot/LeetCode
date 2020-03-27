#  **Linked List 链表总结**

## 19. Remove Nth Node From End of List

>核心思想(One-pass一趟完成)：
>
>- 用快慢指针，将fast指针先移动到第n+1个位置。例如1-2-3-4-5，n = 2, 则将fast移动到“3”的位置上。
>
>- 然后，开始移动slow, 并且继续移动fast，直到fast == None，即移动到空value为止。
>
>- 此时只需要将slow.next给跳过，即slow.next = slow.next.next
>
>- 最终返回start.next。

```python
# one-pass
class Solution:
    def removeNthFromEnd(self, head: ListNode, n: int) -> ListNode:
        start = ListNode(-1)
        fast = start
        slow = start
        slow.next = head
        
        for i in range(1,n+2):
            fast = fast.next
        while fast != None:
            # let fast keep moving, till it ends
            fast = fast.next
            slow = slow.next
        
        # just skip the slow.next to .next.next
        slow.next = slow.next.next
        return start.next
```

## 141. Linked List Cycle

```java
    1. 用快慢指针，slower = faster = head
    2. while (faster.next != None and faster.next.next != None):
        slower = slower.next
        faster = faster.next.next
        if (slower == faster):
            return True
    3. return False
```

## 24. Swap nodes in pair

```
    input : 1-2-3-4
    output: 2-1-4-3

    ********************
    dummy.next = head
    current = dummy
    [d-1-2-3-4]
    1) current = dummy, first = 1, second = 2
        first.next = second.next
        1 - 3
        current.next = second
        head = 2
        current.next.next = first
        (head.next = first)
        2 - 1 - 3
        current = current.next.next
```

-------------

# **Tree**

## 111. Minimum Depth of Binary Tree

```python
class Solution(object):
    def minDepth(self, root):
        """
        :type root: TreeNode
        :rtype: int
        """
        if not root: return 0
        h = map(self.minDepth, [root.left, root.right]) if root else [-1]
        return 1 + (min(h) or max(h))
```

## 107. 层序遍历

```python
    1. res = queue = []
    2. queue.append(root)
    3. while len(queue) != 0:
        temp = []
        quesize = len(queue)
        for i in range(quesize):
            node = queue.pop(0)
            if node.left is not None:
                queue.append(node.left)
            if node.right is not None:
                queue.append(node.right)
            temp.append(node.val)
        res.append(temp)
    4. return res
```

## 98. Validate a BST

```
两种办法：
1. 递归。通过递归在分别对左子树和右子树进行如下检查：
    初始状态：(-∞ < root.value < ∞)
    对左子树： (-∞ < root.left.value < root.val)
    对左子树：保持下界不变，改变上界值为其父节点（它的根节点）的值，判断root.left.val 是否比根节点要小，check(root.left, low, root.val)

    对右子树： (root.val < root.right.value < ∞)
    对右子树：保持上界不变，改变下界值为父节点（它的根节点）的值，
            判断root.right.val 是否比根节点大，check(root.right, root.val, high)

2. 中序遍历，in-order traversal
```

代码如下:

```python
'''
    In-order Traversal
'''
class Solution(object):
    def isValidBST(self, root):
        """
        :type root: TreeNode
        :rtype: bool
        """
        if not root:
            return True
        res = []
        res1 = self.in_order_trv(root, res)
        for i in range(len(res1)-1):
            if res1[i+1] <= res1[i]:
                return False
        return True
        
        
    def in_order_trv(self, root, res):
        if root:
            res = self.in_order_trv(root.left, res)
            res.append(root.val)
            res = self.in_order_trv(root.right, res)
        return res
```

## 701. Insert into a BST

```python
if root:
    if target < root and not root.left:
        into(root.left)
    elif target > root and not root.right:
        into(root.right)
    else:
        if target < root:
            root.left = target
        else:
            root.right = target
```

## 112. Path Sum

<p>
Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.


Note: A leaf is a node with no children.
</p>

```python
class Solution:
    def hasPathSum(self, root: TreeNode, findSum: int) -> bool:
        res = []
        self.dfs(root,findSum,res)
        if True in res:
            return True
        return False
 
    def dfs(self, root, target, res):
        if root:
            if not root.left and not root.right:
                if root.val == target:
                    res.append(True)
            if root.left:
                remain = target - root.val
                self.dfs(root.left, remain, res)
            if root.right:
                remain = target - root.val
                self.dfs(root.right, remain, res)
        
                
```

## 437. Path Sum III (easy)

## 124. Binary Tree Maximum Path Sum

```java
public class Solution {
    int maxValue;
    
    public int maxPathSum(TreeNode root) {
        maxValue = Integer.MIN_VALUE;
        maxPathDown(root);
        return maxValue;
    }
    
    private int maxPathDown(TreeNode node) {
        if (node == null) return 0;
        int left = Math.max(0, maxPathDown(node.left));
        int right = Math.max(0, maxPathDown(node.right));
        maxValue = Math.max(maxValue, left + right + node.val);
        return Math.max(left, right) + node.val;
    }
}
```

## 105. Construct Binary Tree from Preorder and Inorder Traversal

```
Given preorder and inorder traversal of a tree, construct the binary tree.

For example, given

preorder = [3,9,20,15,7]
inorder = [9,3,15,20,7]

Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7
```

核心思路：

通过前序遍历，找到根节点，然后在中序遍历中把该根节点的下标找到，以root为中心，依次对中序遍历的左半部分和右半部分分别进行左子树和右子树的构建。

代码：

```python
class Solution(object):
    def buildTree(self, preorder, inorder):
        """
        :type preorder: List[int]
        :type inorder: List[int]
        :rtype: TreeNode
        """
           
        def build(preorder, inorder, pre_start, in_start, in_end, mp):
            # corner case
            if pre_start >= len(preorder) or in_start > in_end:
                return None
            root = TreeNode(preorder[pre_start])
            root_index = mp[preorder[pre_start]]
            # build left and right subtree
            root.left = build(preorder, inorder, pre_start+1, in_start, root_index-1, mp)
            root.right = build(preorder, inorder, pre_start + root_index - in_start+1,
                              root_index+1, in_end, mp)            
            return root
        
        mp = {}
        for i, num in enumerate(inorder):
            mp[num] = i
        return build(preorder, inorder, 0, 0, len(inorder)-1, mp)
```

## 306. Find Leaves of Binary Tree

> Given a binary tree, collect a tree's nodes as if you were doing this: Collect and remove all leaves, repeat until the tree is empty.
>
> **Example:**
>
> ```
> Input: [1,2,3,4,5]
>   
>           1
>          / \
>         2   3
>        / \     
>       4   5    
> 
> Output: [[4,5,3],[2],[1]]
> ```

```python
class Solution():
    def findLeaves(self, root):
        if root == None:
            return []
        def removeLeaves(root, lst):
            if root == None:
                return True
            if root.left == None and root.right==None:
                lst.append(root.val)
                return True
            if root.left and removeLeaves(root.left,lst):
                root.left = None
            if root.right and removeLeaves(root.right, lst):
                root.right = None
            return False
        
        res = []
        while root:
            lst = []
            if (removeLeaves(root,lst)):
                root = None
            res.append(lst)
            
```



---

# **DP (Dynamic Programming)**

## [**DP-1**] 198. House Robber

<p>
核心思想：
<li>
对于第N间房子，我们有两种选择，要么偷，还么不偷
</li>
<li>
所以动态规划转移方程money[N] = max(money[N-2] + house[N], money[N-1])
</li>
<li>
DP初始条件：money[0] = house[0], money[1] = max(house[:2])
</li>
</p>


```python
class Solution:
    def rob(self, A: List[int]) -> int:
        money = [-1]*len(A)
        if not A:
            return 0
        if len(A) == 1:
            return A[0]
        if len(A) == 2:
            return max(A[0],A[1])
        money[0] = A[0]
        money[1] = max(A[:2])
        for i in range(2, len(A)):
            money[i] = max(money[i-2]+A[i], money[i-1])
        return max(money)
```


## [**DP-2**] 198. House Robber III (Medium)

```python
class Solution:

    def rob_subtree(self, root, Hashmap):
        if not root:
            return 0
        if root in Hashmap:
            return Hashmap.get(root)
        val = 0
        if root.left != None:
            val += self.rob_subtree(root.left.left, Hashmap) + self.rob_subtree(root.left.right, Hashmap)
        if root.right != None:
            val += self.rob_subtree(root.right.left, Hashmap) + self.rob_subtree(root.right.right, Hashmap)
            
        val = max(val + root.val, self.rob_subtree(root.left,Hashmap)
                  +self.rob_subtree(root.right,Hashmap))
        Hashmap[root] = val
                
        return val
    
    def rob(self, root: TreeNode) -> int:
        Hashmap = {}
        return self.rob_subtree(root, {})
```

## [**DP-3**] 256. Paint House

题目描述详见 [issue](https://github.com/LLancelot/LeetCode/issues)

> #### 核心思想：
>
> - 直接在costs修改``` costs[i][j]```
> - ```costs[n][0] = min(costs[n-1][1], costs[n-1][2]) + costs[n][0]```
> - ```costs[n][1] = min(costs[n-1][0], costs[n-1][2]) + costs[n][1]```
> - ```costs[n][2] = min(costs[n-1][0], costs[n-1][1]) + costs[n][2]```
>
> - 返回costs最后一间房子的最小值, ```min(costs[-1])```

```python
class Solution:
    def minCost(self, costs: List[List[int]]) -> int:
        if not costs:
            return 0
        if len(costs) == 1:
            return min(costs[0])
        
        for n in range(1, len(costs)):
            costs[n][0] = min(costs[n-1][1], costs[n-1][2]) + costs[n][0]
            costs[n][1] = min(costs[n-1][0], costs[n-1][2]) + costs[n][1]
            costs[n][2] = min(costs[n-1][0], costs[n-1][1]) + costs[n][2]

        return min(costs[-1])
```

## [**DP-4**] 265. Paint House 2 (Hard)


NOTE: _n houses, k colors, **costs**: n * k matrix_

```python
class Solution:
    def minCostII(self, costs: List[List[int]]) -> int:
        if not costs:
            return 0
        if len(costs) == 1:
            return min(costs[0])

        for i_house in range(1, len(costs)):
            for j_color in range(len(costs[0])):
                costs[i_house][j_color] = min(costs[i_house-1][:j_color]+costs[i_house-1][j_color+1:]) + costs[i_house][j_color]
        print(costs)
        return min(costs[-1])
```

## [**DP-5**] 221. Maximal Square (Medium)

思路1 (不降维)：

![alt text](https://github.com/LLancelot/LeetCode/blob/master/images/DP-5.png)

参考代码:

```python
class Solution:
    def maximalSquare(self, m: List[List[str]]) -> int:
        if len(m) == 0: 
            return 0
        rows = len(m)
        cols = len(m[0]) if rows > 0 else 0
        dp = [[0]*(cols+1) for _ in range(rows+2)]
        
        MAX_LEN = 0
        for i in range(1, rows+1):
            for j in range(1, cols+1):
                if m[i-1][j-1] == "1":
                    dp[i][j] = min(dp[i][j-1], dp[i-1][j], dp[i-1][j-1]) + 1
                    MAX_LEN = max(MAX_LEN, dp[i][j])

        return MAX_LEN * MAX_LEN
```

## [**DP-6**] 494.Target Sum (Medium)

You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.

Find out how many ways to assign symbols to make sum of integers equal to target S.

核心思路：

https://zxi.mytechroad.com/blog/?s=494

```java
// Java
// Runtime: 7 ms (83%)

class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        for (final int num : nums)
            sum += num;
        if (sum < S) return 0;
        final int kOffset = sum;
        final int kMaxN = sum * 2 + 1;
        int[] ways = new int[kMaxN];
        ways[kOffset] = 1;
        for (final int num : nums) {      
            int[] tmp = new int[kMaxN];      
            for (int i = num; i < kMaxN - num; ++i) {
                // 以当前数字为中心，分别找加上或减去num刚好在kMaxN范围内，从上一层的计算得到的ways[i].
                // ways[i] means the total ways to sum up N (-kMaxN ~ kMaxN)
                tmp[i + num] += ways[i];
                tmp[i - num] += ways[i];
            }
            // update ways
            ways = tmp;
        }
        return ways[S + kOffset];
    }
}

```

## [**DP-7**] 746. Min Cost Climbing Stairs

```java
// DP rules := dp[i] = Math.min(dp[i-2] + cost[i-2], dp[i-1] + cost[i-1])
// dp[i] means the min cost before climbing the "i"th stairs, two ways. 

class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int dp[] = new int[cost.length + 1];
        Arrays.fill(dp, 0);
        
        for (int i = 2; i<=cost.length; i++){
            dp[i] = Math.min(dp[i-2] + cost[i-2], dp[i-1] + cost[i-1]);
        }
        return dp[cost.length];
    }
}
```

## [**DP-8**] 279. Perfect Squares 

```java
class Solution {
    public int numSquares(int n) {
        int dp[] = new int[n+1];
        Arrays.fill(dp, n+1);
        dp[0] = 0;

        int top = (int)Math.sqrt(n);
        int squares[] = new int[top];
        for (int i = 0; i < top; i++){
            squares[i] = (i+1) * (i+1);
        }

        for (int number : squares){
            for (int i = number; i <= n; i++){
                dp[i] = Math.min(dp[i], dp[i-number]+1);
            }
        }

        return dp[n];
    }
}

```

## [**DP-9**] 63. Unique Path

```cpp
    方法1：动态规划问题，从下至上递推求解
    int uniquePaths(int m, int n) {
        if (m==0||n==0)
            return 0;
        auto f = vector<vector<int>>(n+1, vector<int>(m+1, 0));
        f[1][1] = 1;
        for (int y = 1; y <= n; y++){
            for (int x = 1; x <= m; x++){
                if (x==1 && y==1){
                    continue;              
                }
                else{
                    f[y][x] = f[y-1][x] + f[y][x-1];  
                }
            }
        }
        return f[n][m];
```

 ``` cpp  
    方法2： 记忆化递归求解，耗时较长
    public:
    int uniquePaths(int m, int n) {
        if (m<0 || n<0)
            return 0;
        if (m==1 && n==1)
            return 1;
        if (memo[m][n] > 0)
            return memo[m][n];
        int left_path = uniquePaths(m-1, n);
        int up_path = uniquePaths(m, n-1);
        memo[m][n] = left_path + up_path;
        return memo[m][n];
    }
    private:
        unordered_map<int, unordered_map<int, int>> memo;
    
    总结：基本思路一致，就是若要求得走到(m,n)的位置，即f[m][n]，只有从left和up两个方向进行考虑
    即f[m][n] = f[m-1][n] + f[m][n-1]，直到终止条件为起点。
 ```

## [**DP-10**] 322. Coin Change

与这题相似的解法题为 **[DP-8] Perfect Squares**

>我们定义dp[i]时，dp长度为amount+1, 初始化dp[0] = 0, 其余dp[1...N]为amount+1 (或者integer最大值)。

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int []dp = new int[amount+1];
        Arrays.fill(dp, amount+1);
        dp[0] = 0;
        
        for (int coinType : coins)
            for (int i = coinType; i<= amount; i++){
                dp[i] = Math.min(dp[i], dp[i - coinType]+1);
            }
        return dp[amount] < amount+1 ? dp[amount] : -1;
    }
}
```

## [**DP-11**] 518. Coin Change_2

> 与上一题不同，在coin change 2当中，我们定义dp[i]时，dp长度为amount+1, 初始化dp[0] = 1, 其余dp[1...N]为0。
>
> 因为这题求的是一共有多少种累加到amount的方法，所以dp[i]应该叠加。
>
> 与上一题不同，在每次更新dp[i]时，应该累加之前的结果，转换方程为：
> dp[i] += dp[i - coin]，意思就是每次遇到新的钱币类型($1, $2, $5)，时，都要叠加之前算过的结果，和上一题不同的是，上一题的dp[i]代表每次循环新的钱币类型时，都要先比较出一个最小值，再更新dp[i]  

python代码：

```python
class Solution(object):
    def change(self, amount, coins):
        """
        :type amount: int
        :type coins: List[int]
        :rtype: int
        """
        dp = [0]* (amount + 1)
        dp[0] = 1
        
        for coin in coins:
            for i in range(coin, amount+1):
                dp[i] += dp[i - coin]
                
        return dp[amount] if dp[amount] != 0 else 0

```

## [**DP-12**] 486. Predict the Winner

方法一：递归所有子序列的所有可能，非常慢

```python
class Solution(object):
    def PredictTheWinner(self, nums):
        """
        :type nums: List[int]
        :rtype: bool
        """
        def getScore(nums, le, ri):
            if le == ri:
                return nums[le]
            return max(nums[le] - getScore(nums, le+1, ri),
                      nums[ri] - getScore(nums, le, ri-1))
        return getScore(nums, 0, len(nums)-1) >= 0
```

方法二：记忆化递归，高效（推荐）

```python
class Solution(object):
    def PredictTheWinner(self, nums):
        """
        :type nums: List[int]
        :rtype: bool
        """
        def getScore(nums, le, ri, m):
            if le == ri:
                return nums[le]
            m_pos = le * len(nums) + ri
            if m[m_pos] > 0:
                return m[m_pos]
            m[m_pos] = max(nums[le] - getScore(nums, le+1, ri, m),
                      nums[ri] - getScore(nums, le, ri-1, m))
            return m[m_pos]
        
        m = [0]*(len(nums)**2)
        return getScore(nums, 0, len(nums)-1, m) >= 0
```

----

# Others 更新中

## 1304. Find N Unique Integers Sum up to Zero

```
    input: n = 5 
    output: [-7, -1, 1, 3, 4]

    *******************
    n = 1, ans = [0]
    n = 2, ans = [-1, 1]
    n = 3, ans = [-1, 0, 1]   
```

代码如下：

```python
class Solution:
    def sumZero(self, n: int) -> List[int]:
        base = [-1,1]
        if n == 1:
            return [0]
        if n == 2:
            return base
        if n == 3:
            base.insert(1,0)
            return base
        else:
            if n % 2 == 0:
                ans = [x for x in range(-n//2, n//2 + 1)]
                ans.remove(0)
            else:
                return [x for x in range(-(n//2), n//2 + 1)]
        return ans
'''
n = 4, ans = [-2,-1,1,2], [x for x in range(-n//2, n//2 +1)]
ans.remove(0)

n = 5, ans = [-2,-1,0,1,2]

'''
```

## 706. Design HashMap


## 1311. Get watched videos by your friends

## 200. Numbers of Islands

***********************

```java
class Solution {
    // 首先定义四个方向的向量，方便计算矩阵上下左右的位置
    final static int [][]dirs = {{-1, 0},
                                 {1, 0},
                                 {0, -1},
                                 {0, 1}
                                 };
    public int numIslands(char[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }
        int count = 0;
        final int rows = grid.length;
        final int cols = grid[0].length;
        // 用DFS遍历所有的相邻'1'的位置
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++){
                if (grid[i][j] == '1'){
                    count++;
                    dfs(grid, i, j, rows, cols);
                }
            }
        return count;
    }
    
    public void dfs(char[][]grid, int x, int y, int rows, int cols){
        if (x < 0 || x >= rows || y < 0 || y >= cols || grid[x][y] == '0'){
            return;
        }
        grid[x][y] = '0';
        for (int []dir : dirs){
            int next_x = dir[0] + x;
            int next_y = dir[1] + y;
            dfs(grid, next_x, next_y, rows, cols);
        }
    }
}
```

***************************************



## 325. Maximum Size Subarray Sum Equals k

```java
    1. HashMap<int, int>;
    2. for (i, nums):
        sum = sum + nums[i]
        if (match) // if (sum == k)
            max = idx + 1 // 这一步是求当前match的数（包括自己在内）前面所有数字的长度
                          // 即nums[:idx+1]
        distance = sum - k
        if map.containsKey(distance):
            更新max = Math.max(max, idx - map.containsKey(distance))
        else (!map.containsKey(sum)):
            若当前的sum不在map中，则将对应的sum和下标放到map里面
    3. return max
```

## 395. Longest Substring with At Least K Repeating Characters

```python
    1. corner case:
        if len(s) < K:
            总长度小于目标K， 直接返回0
    
    2. min_freq_char = min(set(s), key = s.count)
        // 把string中频率最小的char取出。

        if s.count(min_freq_char) > K:
            如果最小频率已经大于目标值K， 则将整个string 返回
            return s
    
    3. return max(self.函数(sub, K = freq) for sub in s.split(min_freq_char))
        递归。对string进行切片，然后将切片后的每个substring进行递归。
```

--------------


## 209. Minimum Size Subarray Sum

```python
class Solution:
    def minSubArrayLen(self, k: int, nums: List[int]) -> int:
        left, total = 0, 0
        minlen = len(nums) + 1
        for right, num in enumerate(nums):
            total += num
            while total >= k:
                minlen = min(minlen, right - left + 1)
                total -= nums[left]
                left += 1
        return minlen if minlen <= len(nums) else 0

```


# Subset 子集问题

## 1) 78. Subset

Given a set of distinct integers, nums, return all possible subsets (the power set).

**_Note: The solution set must not contain duplicate subsets._**

```
Input: nums = [1,2,3]
Output:
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]

```

```python
class Solution():
    def subset(self, nums):
        res = [[]]
        if not nums:
            return res
        for num in nums:
            for le in range(len(res)):
                res.append(res[le] + [num])
        return res

```

## 2) 90. Subset

- Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).

- _Note: The solution set must not contain duplicate subsets._

```python
Input: [1,2,2]
Output:
[
  [2],
  [1],
  [1,2,2],
  [2,2],
  [1,2],
  []
]

class Solution(object):
    def subsetsWithDup(self, nums):
        nums, result, pos = sorted(nums), [[]], {}
        for n in nums:
            start, len_ = pos.get(n, 0), len(result)
            # start: if current num is new, then start = 0
            # if not, then start = len_
            for r in result[start:]:
                result += [r + [n]]
            pos[n] = len_
        return result
```

----

# DFS (Depth-first-search)

## 79. Word Search

Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.

```
Example:

board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

Given word = "ABCCED", return true.
Given word = "SEE", return true.
Given word = "ABCB", return false.
```

代码：

```java
class Solution {
    private int w;
    private int h;
    
    public boolean exist(char[][] board, String word) {
        if (board.length == 0)
            return false;
        h = board.length;
        w = board[0].length;
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                if (search(board, word, 0, i, j))
                    return true;
        return false;
    }
    
    public boolean search(char[][] board, String word, int pos, int x, int y){
        if (x<0 || x==w || y<0 || y==h || word.charAt(pos) != board[y][x])
            return false;
        if (pos == word.length() -1)
            return true;
        char cur = board[y][x];
        board[y][x] = 0;
        boolean find_every_dir = 
            search(board, word, pos+1, x+1, y)
            || search(board, word, pos+1, x-1, y)
            || search(board, word, pos+1, x, y+1)
            || search(board, word, pos+1, x, y-1);
        board[y][x] = cur;
        return find_every_dir;
    }
}
```
