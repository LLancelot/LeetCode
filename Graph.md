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