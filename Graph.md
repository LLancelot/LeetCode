# Graph 图

## 399. Evaluate Division

> Equations are given in the format `A / B = k`, where `A` and `B` are variables represented as strings, and `k` is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return `-1.0`.
>
> **Example:**
> Given `a / b = 2.0, b / c = 3.0.`
> queries are: `a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .`
> return `[6.0, 0.5, -1.0, 1.0, -1.0 ].`
>
> The input is: `vector> equations, vector& values, vector> queries `, where `equations.size() == values.size()`, and the values are positive. This represents the equations. Return `vector`.
>
> According to the example above:
>
> ```
> equations = [ ["a", "b"], ["b", "c"] ],
> values = [2.0, 3.0],
> queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ]. 
> ```
>
>  
>
> The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.

题目意思就是给你一些分数型的等式，然后你要去query任意给出的两个变量之间是否存在一个分数关系，例如a/b=2, b/c=3，那么你可以知道a/c=6, b/a=1/2, c/b=1/3，等等

**思路**

- 构建一个**graph**，有向图的边权重为**两数之商**，沿着边相乘可以得到最终两个数之间的商，例如A->B = 2.0, B->C = 3.0，那么 A->C = 2.0 * 3.0 = 6.0

  ![image-20200603194353694.png](https://github.com/LLancelot/LeetCode/blob/work/images/image-20200603194353694.png?raw=true)

- 通过深度优先BFS + 队列deque 遍历一遍图，用visited的集合来保存每次循环时访问过的结点，以保证不会重复查找。

- 对题目给定的```queries```中的每个 query 进行```find_path(query)```，将函数的返回结果依次添加到 list 即可。

**代码实现**

```python
import collections
class Solution(object):
    def calcEquation(self, equations, values, queries):
        """
        :type equations: List[List[str]]
        :type values: List[float]
        :type queries: List[List[str]]
        :rtype: List[float]
        """
        graph = {}              # graph[f] = [(g1, v1), (g2, v2), (g3, v3)...]

        def build_graph(equations, values):
            def add_edges(f, g, value):
                if f in graph:
                    graph[f].append((g, value))
                else:
                    graph[f] = [(g, value)]

            for vertices, val in zip(equations, values):
                f, g = vertices
                add_edges(f, g, val)
                add_edges(g, f, 1/val)      # add its reciprocal value

            # end def build_graph()

        def find_path(query):
            begin, end = query

            if begin not in graph or end not in graph:
                return -1.0

            # BFS 
            queue = collections.deque([(begin, 1.0)])
            visited = set()

            while queue:
                find, curr_product = queue.popleft()
                if find == end:
                    return curr_product
                visited.add(find)
                for neighbor, value in graph[find]:         # search for its neighbors
                    if neighbor not in visited:
                        queue.append((neighbor, curr_product * value))	# multiply 

            # finally, if not found
            return -1.0

        build_graph(equations, values)
        return [find_path(q) for q in queries]		# result
```

## 269. Alien Dictionary

#### [题目](https://leetcode.com/problems/alien-dictionary/)

There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You receive a list of **non-empty** words from the dictionary, where **words are sorted lexicographically by the rules of this new language**. Derive the order of letters in this language.

**Example 1:**

```
Input:
[
  "wrt",
  "wrf",
  "er",
  "ett",
  "rftt"
]

Output: "wertf"
```

**Example 2:**

```
Input:
[
  "z",
  "x"
]

Output: "zx"
```

**Example 3:**

```
Input:
[
  "z",
  "x",
  "z"
] 

Output: "" 

Explanation: The order is invalid, so return "".
```

#### 思路

- 利用拓扑排序，将words中出现的字符放到 Graph 中，即```Map<Character, Set<Character>>``` 记录每个字母对应的 set 集合，这个集合存放的是 words 中比自己低级的字母，比如 example 1 中的 “wrt” 和 “wrf” ，我们就能知道 t 比 f 优先级高，那么我们就把 f 加到 t 的集合中。
- 其次，我们还需要记录每个字母的入度，即 ```int[] inDegree = new int[26]``` ，便于在 BFS 中的队列操作。

#### 代码

```java
class Solution {
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>();
        int[] inDegree = new int[26];
        buildGraph(graph, inDegree, words);
        return bfs(graph, inDegree);
    }
    
    private void buildGraph(Map<Character, Set<Character>> graph, int[] inDegree, String[] words)
    {
        for (String str: words) {
            for (char ch : str.toCharArray()) {
                graph.putIfAbsent(ch, new HashSet<>());
            }
        }
        
        // compare words[i] and words[i + 1]
        for (int i = 1; i < words.length; i++) {
            String first = words[i - 1];
            String second = words[i];
            int minLen = Math.min(first.length(), second.length());
            for (int j = 0; j < minLen; j++) {
                char c_out = first.charAt(j);
                char c_in = second.charAt(j);
                // find the first different char
                if (c_out != c_in) {
                    if (!graph.get(c_out).contains(c_in)) {
                        graph.get(c_out).add(c_in);
                        inDegree[c_in - 'a']++;
                    }
                    break;
                }
                
                if (j + 1 == minLen && first.length() > second.length()) {
                    graph.clear();
                    return;
                }
            }
        }
    }
    
    private String bfs(Map<Character, Set<Character>> graph, int[] inDegree) {
        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        for (char c : graph.keySet()) {
            if (inDegree[c - 'a'] == 0) {
                queue.offer(c);
            }
        }
        
        while (!queue.isEmpty()) {
            char out = queue.poll();
            sb.append(out);
            for (char in : graph.get(out)) {
                inDegree[in - 'a']--;
                if (inDegree[in - 'a'] == 0) {
                    queue.offer(in);
                }
            }
        }
        
        return sb.length() == graph.size() ? sb.toString() : "";
    }
}
```

## 277. Find the Celebrity

https://leetcode.com/problems/find-the-celebrity/

> Suppose you are at a party with `n` people (labeled from `0` to `n - 1`) and among them, there may exist one celebrity. The definition of a celebrity is that all the other `n - 1` people know him/her but he/she does not know any of them.
>
> Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information of whether A knows B. You need to find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).
>
> You are given a helper function `bool knows(a, b)` which tells you whether A knows B. Implement a function `int findCelebrity(n)`. There will be exactly one celebrity if he/she is in the party. Return the celebrity's label if there is a celebrity in the party. If there is no celebrity, return `-1`.
>
>  
>
> **Example 1:**
>
> ![img](https://assets.leetcode.com/uploads/2019/02/02/277_example_1_bold.PNG)
>
> ```
> Input: graph = [
>   [1,1,0],
>   [0,1,0],
>   [1,1,1]
> ]
> Output: 1
> Explanation: There are three persons labeled with 0, 1 and 2. graph[i][j] = 1 means person i knows person j, otherwise graph[i][j] = 0 means person i does not know person j. The celebrity is the person labeled as 1 because both 0 and 2 know him but 1 does not know anybody.
> ```
>
> **Example 2:**
>
> ![img](https://assets.leetcode.com/uploads/2019/02/02/277_example_2.PNG)
>
> ```
> Input: graph = [
>   [1,0,1],
>   [1,1,0],
>   [0,1,1]
> ]
> Output: -1
> Explanation: There is no celebrity.
> ```
>
>  
>
> **Note:**
>
> 1. The directed graph is represented as an adjacency matrix, which is an `n x n` matrix where `a[i][j] = 1` means person `i` knows person `j` while `a[i][j] = 0` means the contrary.
> 2. Remember that you won't have direct access to the adjacency matrix.

### 代码

```java
/* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */

public class Solution extends Relation {
    public int findCelebrity(int n) {
        
        int candidate = 0;
        
        // 第一趟：找到candidate
        for(int i = 1; i < n; i++){
            if(knows(candidate, i))
                candidate = i;
        }
        
        // 第二趟：验证candidate是否符合条件，不符合条件的情况返回-1
        // 不符合条件的情况：候选人知道别人，或者有人不知道候选人
        for(int i = 0; i < n; i++){
            if(i != candidate && (knows(candidate, i) || !knows(i, candidate))) return -1;
        }
        return candidate;
    }
}
```

## 684. Redundant Connection

https://leetcode.com/problems/redundant-connection/

**Example 1:**

```
Input: [[1,2], [1,3], [2,3]]
Output: [2,3]
Explanation: The given undirected graph will be like this:
  1
 / \
2 - 3
```



**Example 2:**

```
Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
Output: [1,4]
Explanation: The given undirected graph will be like this:
5 - 1 - 2
    |   |
    4 - 3
```

### 代码

- DFS, O(n^2) time, O(n) space

```python
class Solution(object):
    def findRedundantConnection(self, E):
        """
        :type edges: List[List[int]]
        :rtype: List[int]
        """
        return solve(E)
        
def solve(E):
    graph = defaultdict(list)
    for e in E:
        u, v = e[0], e[1]
        visited = set()
        if dfs(u, v, graph, visited):
            return e
        else:
            graph[u].append(v)
            graph[v].append(u)
    return []

def dfs(curr, dst, graph, visited):
    if curr == dst:
        # find a road
        return True
    visited.add(curr)
    if not graph[curr] or not graph[dst]:
        return False
    for nb in graph[curr]:
        if nb in visited: continue
        if dfs(nb, dst, graph, visited): return True
    return False
```

- Union-Find, O(n) time and space

```python
class Solution(object):
    def findRedundantConnection(self, ed):
        """
        :type edges: List[List[int]]
        :rtype: List[int]
        """
        return unionFind(ed)
        
def unionFind(ed):
    n = len(ed)
    parent = [0] * (n + 1)
    size = [1] * (n + 1)
    
    def find(node):
        while parent[node] != node:
            parent[node] = parent[parent[node]]
            node = parent[node]
        return node
    
    for u, v in ed:
        # add self cycle as parent
        if parent[u] == 0: parent[u] = u
        if parent[v] == 0: parent[v] = v
        pu = find(u)
        pv = find(v)
        if pu == pv:
            return [u, v]
        if size[pv] > size[pu]:
            u, v = v, u
        parent[pv] = pu
        size[pu] += size[pv]
    
    return []
```

