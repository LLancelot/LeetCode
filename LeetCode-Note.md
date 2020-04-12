



#  Linked List 链表总结

## 160. Intersection of Two Linked Lists

>Write a program to find the node at which the intersection of two singly linked lists begins.
>
>For example, the following two linked lists:
>
>[![img](https://assets.leetcode.com/uploads/2018/12/13/160_statement.png)](https://assets.leetcode.com/uploads/2018/12/13/160_statement.png)
>
>begin to intersect at node c1.
>
> 
>
>**Example 1:**
>
>[![img](https://assets.leetcode.com/uploads/2018/12/13/160_example_1.png)](https://assets.leetcode.com/uploads/2018/12/13/160_example_1.png)
>
>```
>Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
>Output: Reference of the node with value = 8
>Input Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,0,1,8,4,5]. There are 2 nodes before the intersected node in A; There are 3 nodes before the intersected node in B.
>```
>
> 
>
>**Example 2:**
>
>[![img](https://assets.leetcode.com/uploads/2018/12/13/160_example_2.png)](https://assets.leetcode.com/uploads/2018/12/13/160_example_2.png)
>
>```
>Input: intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
>Output: Reference of the node with value = 2
>Input Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [0,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the intersected node in A; There are 1 node before the intersected node in B.
>```
>
> 
>
>**Example 3:**
>
>[![img](https://assets.leetcode.com/uploads/2018/12/13/160_example_3.png)](https://assets.leetcode.com/uploads/2018/12/13/160_example_3.png)

思路和代码 (reference: ***Cracking the Coding Interview***)：

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

public class Solution {
    public class Result {
        public ListNode tail;
        public int size;
        public Result(ListNode tail, int size){
            this.tail = tail;
            this.size = size;
        }
	}
    Result getTailAndSize(ListNode list){
        if (list == null) return null;
        int size = 1;
        ListNode current = list;
        while(current.next != null){
            size++;
            current = current.next;
        }
        return new Result(current,size);
}
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        Result resA = getTailAndSize(headA);
        Result resB = getTailAndSize(headB);
        // if different tail nodes, then there's no intersection
        if (resA.tail != resB.tail) return null;
        // set pointer to the start of each linked list
        ListNode shorter = resA.size < resB.size ? headA : headB;
        ListNode longer = resA.size < resB.size ? headB : headA;
        
        // Advance the pointer for the longer linked list by difference in lengths
        longer = getKthNode(longer, Math.abs(resA.size - resB.size));
        // move both pointers until you have a collision
        while (shorter != longer){
            shorter = shorter.next;
            longer = longer.next;
        }    
        return longer;
    }
    
    public ListNode getKthNode(ListNode head, int k){
        ListNode current = head;
        while (k>0 && current != null){
            current = current.next;
            k--;
        }
        return current;
    }
}
```



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

```python
class Solution:
    def swapPairs(self, head: ListNode) -> ListNode:
        dummy = ListNode(0)
        dummy.next = head
        current = dummy
        while current.next != None and current.next.next != None:
            first = current.next
            second = current.next.next
            first.next = second.next
            current.next = second
            current.next.next = first
            current = current.next.next
        
        return dummy.next
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

## 107. 层序遍历 Level Order Traversal

```python
res, queue = [], []
queue.append(root)
while len(queue) != 0:
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
return res
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

>Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
>
>**Note:** A leaf is a node with no children.
>
>**Example:**
>
>Given the below binary tree and `sum = 22`,
>
>```
>      5
>     / \
>    4   8
>   /   / \
>  11  13  4
> /  \      \
>7    2      1
>```
>
>return true, as there exist a root-to-leaf path `5->4->11->2` which sum is 22.

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

>You are given a binary tree in which each node contains an integer value.
>
>Find the number of paths that sum to a given value.
>
>The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).
>
>The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.
>
>**Example:**
>
>```
>root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
>
>      10
>     /  \
>    5   -3
>   / \    \
>  3   2   11
> / \   \
>3  -2   1
>
>Return 3. The paths that sum to 8 are:
>
>1.  5 -> 3
>2.  5 -> 2 -> 1
>3. -3 -> 11
>```

代码：

```python
class Solution:
    def pathSum(self, root: TreeNode, S: int) -> int:
        if not root:
            return 0
        ans = {"nums":0}
        sums = {0:1}

        def dfs(node, sums, currSum):
            if not node:
                return
            newSum = node.val + currSum
            if newSum - S in sums:
                ans['nums'] += sums[newSum - S]
            if not node.left and not node.right:
                return
            
            sums[newSum] = sums.get(newSum,0)+1
            dfs(node.left, sums, newSum)
            dfs(node.right, sums, newSum)
            sums[newSum] -= 1
            
        dfs(root, sums, 0)
        return ans['nums']
            
```



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

## [**DP-9**.1] 63. Unique Path

```java
    方法1：动态规划问题，从下至上递推求解
class Solution {
    public int uniquePaths(int m, int n) {
        if (m<=0||n<=0){
            return 0;
        }
        
        int [][]dp = new int[m][n];
        for (int row = 0; row < m ; row++){
            dp[row][0] = 1;
        }
        for (int col = 0; col < n; col++){
            dp[0][col] = 1;
        }
        for (int i = 1; i < m ; i++){
            for (int j = 1; j < n; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
}
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

## [DP-9.2] Unique Path 2 (with obstacle)

A robot is located at the top-left corner of a *m* x *n* grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

Now consider if some obstacles are added to the grids. How many unique paths would there be?

![img](https://assets.leetcode.com/uploads/2018/10/22/robot_maze.png)

An obstacle and empty space is marked as `1` and `0` respectively in the grid.

**Note:** *m* and *n* will be at most 100.

**Example 1:**

```
Input:
[
  [0,0,0],
  [0,1,0],
  [0,0,0]
]
Output: 2
Explanation:
There is one obstacle in the middle of the 3x3 grid above.
There are two ways to reach the bottom-right corner:
1. Right -> Right -> Down -> Down
2. Down -> Down -> Right -> Right
```

*****

与上一题的基本思路相似，可以采用记忆化递归求解，在这里需要注意的是：

- 在递归的时候，需要处理```grid[i-1][j-1] == 1```的情况，即碰到障碍物时，将```dp[i][j] = 0```.
- 边界条件, ```return 0```
- 记忆化处理：```if dp[i][j] != INT_MIN```, 直接返回求过的dp值, ```return dp[i][j]```

代码：

```python
class Solution(object):
    def uniquePathsWithObstacles(self, grid):
        row = len(grid)
        if row == 0:  return 0
        col = len(grid[0])
        # dp[i][j] = path(i,j), INT_MIN: not solved yet, unknown solution
        dp = [[-2**31]*(col+1) for _ in range(row+2)]
        
        def paths(dp,r,c,grid):
            if r<=0 or c<=0:    return 0
            # if r,c=1, reach the start point
            if r==1 and c==1:   return 1-grid[0][0]
            # if already solved, just return the answer.
            if dp[r][c] != -2**31:  return dp[r][c]
            # if there's an obstacle, set path to 0.
            if grid[r-1][c-1] == 1: 
                dp[r][c] = 0
            else:
                dp[r][c] = paths(dp,r,c-1,grid)+paths(dp,r-1,c,grid)
            return dp[r][c]
        
        return paths(dp,row,col,grid)
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

## [DP-13] 377. Combination Sum IV

>Given an integer array with all positive numbers and no duplicates, find the number of possible combinations that add up to a positive integer target.

**Example:**

```
nums = [1, 2, 3]
target = 4

The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)

Note that different sequences are counted as different combinations.

Therefore the output is 7.
```

```python
class Solution:
    def combinationSum4(self, nums, target):
        dp = [0] * (target+1)
        dp[0] = 1
        for i in range(1,target+1):
            for num in nums:
                if i - num>=0:
                    dp[i] += dp[i-num]
        return dp[target]
```



----


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

## 130. Surrounded Regions

>Given a 2D board containing `'X'` and `'O'` (**the letter O**), capture all regions surrounded by `'X'`.
>
>A region is captured by flipping all `'O'`s into `'X'`s in that surrounded region.
>
>**Example:**
>
>```
>X X X X
>X O O X
>X X O X
>X O X X
>```
>
>After running your function, the board should be:
>
>```
>X X X X
>X X X X
>X X X X
>X O X X
>```
>
>**Explanation:**
>
>Surrounded regions shouldn’t be on the border, which means that any `'O'` on the border of the board are not flipped to `'X'`. Any `'O'` that is not on the border and it is not connected to an `'O'` on the border will be flipped to `'X'`. Two cells are connected if they are adjacent cells connected horizontally or vertically.

基本思路:

- 先处理边界条件，对第一行、最后一行、第一列、最后一列进行遍历，找到为'O'的岛屿进行DFS，标记visited, 对这些岛屿**不进行反转**。
- 然后，遍历边界内部的区域，找到为'O'的岛屿进行**上下左右**的DFS，标记visited, 并且将```boolean change```设为```true```, 在DFS中针对这些值为```true```的岛屿做反转操作。

代码：

```python
class Solution(object):
    def solve(self, board):
        if not board or len(board) == 0:
            return
        row, col = len(board), len(board[0])
        visited = [[False]*col for _ in range(row)]
        # 先处理四条边的情况
        for j in range(col):
            # 第一行
            if board[0][j] == 'O':
                self.dfs(board, 0, j, visited, False)
            # 最后一行
            if board[row-1][j] == 'O':
                self.dfs(board, row-1, j, visited, False)
        for i in range(row):
            # 第一列
            if board[i][0] == 'O':
                self.dfs(board, i, 0, visited, False)
            # 最后一列
            if board[i][col-1] == 'O':
                self.dfs(board, i, col-1, visited, False)
                
        # 其余情况，对边界内部的岛屿进行dfs和反转操作        
        for i in range(1,row-1):
            for j in range(1, col-1):
                if board[i][j] == 'O':
                    self.dfs(board, i, j, visited, True)
        
    def dfs(self, board, row, col, visited, change):
		# 越界情况，return
        if row<0 or row>len(board)-1 or col<0 or col>len(board[0])-1:
            return
        if board[row][col] == 'X':
            return
        if visited[row][col] == True:
            return
        if change == True:
            board[row][col] = 'X'
        
        visited[row][col] = True
        self.dfs(board, row+1, col, visited, change)
        self.dfs(board, row-1, col, visited, change)
        self.dfs(board, row, col+1, visited, change)
        self.dfs(board, row, col-1, visited, change)
```

## 200. Numbers of Islands

>Given a 2d grid map of `'1'`s (land) and `'0'`s (water), count the number of islands. An island is surrounded by water  and is formed by connecting adjacent lands horizontally or vertically.  You may assume all four edges of the grid are all surrounded by water.
>
>**Example 1:**
>
>```
>Input:
>11110
>11010
>11000
>00000
>
>Output: 1
>```
>
>**Example 2:**
>
>```
>Input:
>11000
>11000
>00100
>00011
>
>Output: 3
>```

代码：

```java
class Solution {
    public int numIslands(char[][] board) {
        if (board == null || board.length == 0)   return 0;
        int row = board.length, col = board[0].length;
        int count = 0;
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                count += board[i][j] - '0';
                dfs(board, i, j);
            }
        }
        return count;
    }
    private void dfs(char[][] board, int i, int j){
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] == '0')
            return;
        board[i][j] = '0';
        dfs(board, i, j+1);
        dfs(board, i, j-1);
        dfs(board, i+1, j);
        dfs(board, i-1, j);
    }
}
```

## 547. Friend Circles

>There are **N** students in a class. Some of them are friends, while  some are not. Their friendship is transitive in nature. For example, if A is a **direct** friend of B, and B is a **direct** friend of C, then A is an **indirect** friend of C. And we defined a friend circle is a group of students who are direct or indirect friends.
>
>Given a **N\*N** matrix **M** representing the friend relationship between students in the class. If M[i][j] = 1, then the ith and jth students are **direct** friends with each other, otherwise not. And you have to output the total number of friend circles among all the students.
>
>**Example 1:**
>
>```
>Input: 
>[[1,1,0],
> [1,1,0],
> [0,0,1]]
>Output: 2
>Explanation:The 0th and 1st students are direct friends, so they are in a friend circle. The 2nd student himself is in a friend circle. So return 2.
>```
>
>
>
>**Example 2:**
>
>```
>Input: 
>[[1,1,0],
> [1,1,1],
> [0,1,1]]
>Output: 1
>Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends, so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.
>```
>
>
>
>**Note:**
>
>1. N is in range [1,200].
>2. M[i][i] = 1 for all students.
>3. If M[i][j] = 1, then M[j][i] = 1.

代码：

```python
class Solution:
    def findCircleNum(self, M: List[List[int]]) -> int:
        def dfs(M, curr, n):
            for i in range(n):
                if M[curr][i] == 1:
                    M[curr][i] = M[i][curr] = 0
                    # i是curr找到的朋友，因此处理完curr之后，继续对curr的朋友i进行dfs
                    dfs(M, i, n)
        n = len(M)
        ans = 0
        for i in range(n):
            if M[i][i] == 1:
                ans += 1
                dfs(M, i, n)
        return ans
```



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

## 

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

## 59. Spiral Matrix II

> Given a positive integer *n*, generate a square matrix filled with elements from 1 to *n*2 in spiral order.
>
> **Example:**
>
> ```
> Input: 3
> Output:
> [
>  [ 1, 2, 3 ],
>  [ 8, 9, 4 ],
>  [ 7, 6, 5 ]
> ]
> ```

```python
class Solution(object):
    def generateMatrix(self, nn):
        """
        :type n: int
        :rtype: List[List[int]]
        """
        matrix = []
        level = []
        res = []
        res1 = []
        for i in range(1,nn**2+1):
            level.append(i)
            if i%nn == 0:
                matrix.append(level)
                level = []
                
        m = len(matrix)
        n = m
        count, ub1, ub2 = 1, n, m-1
        i, j, di, dj = 0, 0, 0, 1
        for k in range(m*n):
            matrix[i][j] = k+1
            if count == ub1:
                count, di, dj = 0, dj, -di
                ub1, ub2 = ub2, ub1 - 1
            count += 1
            i += di
            j += dj
        # print(matrix)
        return matrix
```

## 73. Set Matrix Zeroes

>Given a *m* x *n* matrix, if an element is 0, set its entire row and column to 0. Do it [**in-place**](https://en.wikipedia.org/wiki/In-place_algorithm).
>
>**Example 1:**
>
>```
>Input: 
>[
>  [1,1,1],
>  [1,0,1],
>  [1,1,1]
>]
>Output: 
>[
>  [1,0,1],
>  [0,0,0],
>  [1,0,1]
>]
>```
>
>**Example 2:**
>
>```
>Input: 
>[
>  [0,1,2,0],
>  [3,4,5,2],
>  [1,3,1,5]
>]
>Output: 
>[
>  [0,0,0,0],
>  [0,4,5,0],
>  [0,3,1,0]
>]
>```

```python
class Solution:
    def setZeroes(self, matrix: List[List[int]]) -> None:
        """
        Do not return anything, modify matrix in-place instead.
        """
        flag, m, n = False, len(matrix), len(matrix[0])
        # first scan, find "0", if "0" in first col, flag=0
        # if "0" in other position(i,j), then set matrix[i][0] = matrix[0][j] = 0
        for i in range(m):
            if matrix[i][0] == 0: flag = True
            for j in range(1, n):
                if matrix[i][j] == 0:
                    matrix[i][0] = matrix[0][j] = 0
        # second scan, bottom-up
        for i in range(m-1, -1, -1):
            for j in range(n-1, 0, -1):
            # for each [i][1...j], check the header of col and row
                if matrix[i][0] == 0 or matrix[0][j] == 0:
                    matrix[i][j] = 0
            # for each num in header of row, check flag
            if flag == True:
                matrix[i][0] = 0
                
```


