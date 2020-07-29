# 207. Course Schedule

https://leetcode.com/problems/course-schedule/

>  返回 True/False，对于课程安排能否上完所有课。

### BFS 

```python
class Solution:
    def canFinish(self, n: int, prerequisites: List[List[int]]) -> bool:       
        G = [[] for _ in range(n)]
        degree = [0] * n
        for u, v in prerequisites:
            G[v].append(u)
            degree[u] += 1
            
        queue = [num for num in range(0, n) if degree[num] == 0]
        for u in queue:
            for v in G[u]:
                degree[v] -= 1
                if degree[v] == 0:
                    queue.append(v)
        
        return len(queue) == n
```



### DFS

```java
class Solution {    
    public boolean canFinish(int N, int[][] preqs) {        
        ArrayList[] G = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            G[i] = new ArrayList();       
        }
        
        for (int i = 0; i < preqs.length; i++) {
            G[preqs[i][1]].add(preqs[i][0]);    // G[1]=[0, 2, ...]把1的先修课加到集合里
        }
        
        boolean[] visited = new boolean[N];
        
        for (int course_idx = 0; course_idx < N; course_idx++) {
            if (!dfs(G, visited, course_idx)) {
                return false;
            }
        }
    
        return true;
    }
    
    private boolean dfs(ArrayList[] G, boolean[] visited, int course) {
        if (visited[course] == true)
            return false;
        
        visited[course] = true;
        
        for (int i = 0; i < G[course].size(); i++) {
            if (!dfs(G, visited, (int)G[course].get(i)))
                return false;
        }
        
        visited[course] = false;
        return true;
    }
}
```



# 210. Course Schedule II

https://leetcode.com/problems/course-schedule-ii/

>  若能上完所有课，返回修课的正确顺序，否则返回空。



### BFS

```python
class Solution:
    def findOrder(self, n: int, prerequisites: List[List[int]]) -> List[int]:    
        G = {i: set() for i in range(n)}    # put all node into Graph
        neigh = defaultdict(set)
        for u, v in prerequisites:
            G[u].add(v)
            neigh[v].add(u)
        
        queue = deque([i for i in G if not G[i]])
        count, order = 0, []
        while queue:
            pre = queue.popleft()
            order.append(pre)
            count += 1
            
            for child in neigh[pre]:
                G[child].remove(pre)
                if not G[child]:
                    queue.append(child)
        
        return order if count == n else []
```

