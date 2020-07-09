# Heap / Heap Sort 堆排序

## 973. K Closest Points to Origin (Medium)

>We have a list of `points` on the plane. Find the `K` closest points to the origin `(0, 0)`.
>
>(Here, the distance between two points on a plane is the Euclidean distance.)
>
>You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in.)
>
> 
>
>**Example 1:**
>
>```
>Input: points = [[1,3],[-2,2]], K = 1
>Output: [[-2,2]]
>Explanation: 
>The distance between (1, 3) and the origin is sqrt(10).
>The distance between (-2, 2) and the origin is sqrt(8).
>Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
>We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].
>```
>
>**Example 2:**
>
>```
>Input: points = [[3,3],[5,-1],[-2,4]], K = 2
>Output: [[3,3],[-2,4]]
>(The answer [[-2,4],[3,3]] would also be accepted.)
>```
>
> 
>
>**Note:**
>
>1. `1 <= K <= points.length <= 10000`
>2. `-10000 < points[i][0] < 10000`
>3. `-10000 < points[i][1] < 10000`

#### 分析

这道题官方给出了两种方法，分别是排序（时间复杂度为*O*(*N*log*N*)）、以及分治，时间复杂度为*O*(*N*)。注意到题目给出的数据规模已经上万，所以除了这两种方法外，还可以用max-heap来实现。

#### 思路

- 对于N个点，先把前K个点，计算其到原点距离，把<distance, points[i]>存入heap. 这样的目的是保证了heap的size始终为K.
- 对于剩下的N-K个点，我们只需要判断当前点到原点距离（以下简称dist）是否小于max-heap中的最大值，也就是heap根节点的值，若当前dist更小，则进行heappop() 操作，将较大的值移除出heap，并将新的较小值添加进heap。以此类推，最后我们发现heap中存在的K个值是我们需要找的K个最小值。返回heap即可。

#### 代码

1. 排序实现

   ```python
   def kClosest(self, points, K):
       points.sort(key = lambda x: x[0]**2 + x[1]**2)
       return points[0:K]
   ```

2. Heap 实现

   ```python
   from math import sqrt
   from heapq import heappush, heappop
   
   class HeapItem:
       # purpose: create the max-heap with K size.
       def __init__(self, distance, point):
           self.distance = distance
           self.point = point
           
       def __lt__(self, other):
           return self.distance > other.distance
       
       
   class Solution(object):
       def kClosest(self, points, K):
           heap = []
           # put the first K element to maintain its size
           for i in range(0, K):
               heappush(heap, HeapItem(get_dist(points[i]), points[i]))
           
           # for the rest of N-K points:
           for i in range(K, len(points)):
               cur_dist = get_dist(points[i])
               # if dist is less than the max we got in heap, just replace it with this smaller one
               if cur_dist < heap[0].distance:
                   heappop(heap)
                   heappush(heap, HeapItem(cur_dist, points[i]))
                   
           return [each.point for each in heap]
       
               
   def get_dist(p):
       x = p[0]
       y = p[1]
       return sqrt(x**2 + y**2)
```
   
   