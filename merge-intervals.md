# Merge Intervals 区间合并问题

## 模板题1 - Merge Intervals

#### 题目

Given a list of intervals, **merge all the overlapping intervals** to produce a list that has only mutually exclusive intervals.

**Example 1:**

```
Intervals: [[1,4], [2,5], [7,9]]
Output: [[1,5], [7,9]]
Explanation: Since the first two intervals [1,4] and [2,5] overlap, we merged them into 
one [1,5].
```

#### 思路

- 明确四种情况：
  - b 整体在 a 后面，无交集（不用合并）
  - b 与 a 部分重叠，需要合并 （a的起点，b的终点）
  - b 完全被 a 覆盖 （a的起点，a的终点）
  - b 与 a 起点均相同，看谁的终点长。

![merge-intervals-1.png](https://github.com/LLancelot/LeetCode/blob/master/images/merge-intervals-1.png?raw=true)

- 步骤：
  - 将所有 intervals 按**起点**进行排序，保证 a.start <= b.start
  - 若发现重叠，即 a.start <= b.start < a.end，也就是b的起点在a的区间内：
    - 更改当前 end = max(a.end, b.end)，即**上图一、图二**情况
  - 若不重叠，这说明当前 interval 和之前访问过的区间无交集，我们则可以将之前的 [start, end] 加入 result，并将新的 start 和 end 更新为当前 interval 的始末位置。

#### 代码

```python
class Solution(object):
    def merge(self, intervals):
        """
        :type intervals: List[List[int]]
        :rtype: List[List[int]]
        """
        if len(intervals) < 2:
            return intervals
        # sort the intervals by interval.start
        intervals.sort(key = lambda x:x[0])
        
        merged_intervals = []
        start = intervals[0][0]
        end = intervals[0][1]
        
        for i in range(1, len(intervals)):
            interval_ = intervals[i]
            if interval_[0] <= end:
                end = max(end, interval_[1]) # compare a.end or b.end
            else:
                # b.start > a.end, no need to merge, just append to result
                merged_intervals.append([start, end])
                # update new start & end with current interval value
                start, end = interval_[0], interval_[1]
        
        # finally, add last [start, end] to result
        merged_intervals.append([start, end])
        return merged_intervals
```

## 模板题2 - Insert Interval

#### 题目

Given a list of non-overlapping intervals **sorted** by their start time, **insert a given interval at the correct position** and merge all necessary intervals to produce a list that has only mutually exclusive intervals.

**Example 1:**

```
Input: Intervals=[[1,3], [5,7], [8,12]], New Interval=[4,6]
Output: [[1,3], [4,7], [8,12]]
Explanation: After insertion, since [4,6] overlaps with [5,7], we merged them into one [4,7].
```

**Example 2:**

```
Input: Intervals=[[1,3], [5,7], [8,12]], New Interval=[4,10]
Output: [[1,3], [4,12]]
Explanation: After insertion, since [4,10] overlaps with [5,7] & [8,12], we merged them into [4,12].
```

**Example 3:**

```
Input: Intervals=[[2,3],[5,7]], New Interval=[1,4]
Output: [[1,4], [5,7]]
Explanation: After insertion, since [1,4] overlaps with [2,3], we merged them into one [1,4].
 
```

总结：对于一个**排好序**的区间序列，插入一个新区间，返回插入且合并后的区间结果。

#### 思路

- 如果用上一道题 Merge Intervals 的方法，先排序，再合并，时间复杂度为 O(N*logN)。但考虑到本题所给区间序列已排好序，我们尝试用 O(N) 的办法一趟完成合并。

#### 图解&算法

![merge-intervals-2.png](https://github.com/LLancelot/LeetCode/blob/master/images/merge-intervals-2.png?raw=true)

- 对第一种情况：也就是 a.end < b.start 的区间，将这些区间直接加入到 result 并跳过即可，直到发现有 overlap 的区间为止。
- 对剩余四种情况，共同特点 a.start < b.end，即必有交集，需要合并：
  - 合并后的起点 start，谁在前，谁是 start，即 ***min***(a.start, b.start)
  - 合并后的终点 end，谁在后，谁是 end，即 ***max***(a.end, b.end)

#### 代码

```python
class Solution(object):
    def insert(self, intervals: List[List[int]], newInterval: List[int]) -> List[List[int]]:
        
        merged, i, start, end = [], 0, 0, 1
        while i < len(intervals) and intervals[i][end] < newInterval[start]:
            # skip these intervals and append them into merged[]
            merged.append(intervals[i])
            i += 1
        
        # already find interval do not follow the condition
        while i < len(intervals) and intervals[i][start] <= newInterval[end]:
            newInterval[start] = min(newInterval[start], intervals[i][start])	# 谁小谁起点
            newInterval[end] = max(newInterval[end], intervals[i][end])			# 谁大谁终点
            i += 1
            
        merged.append(newInterval)	# 合并完，加到merged
        
        # for the rest of intervals
        while i < len(intervals):
            merged.append(intervals[i])
            i += 1
        
        return merged
```

## 模板题3 - Interval Intersection

[leetcode 986. Interval List Intersections](https://leetcode.com/problems/interval-list-intersections/)

#### 题目

Given two lists of **closed** intervals, each list of intervals is pairwise disjoint and in sorted order.

Return the intersection of these two interval lists.

*(Formally, a closed interval `[a, b]` (with `a <= b`) denotes the set of real numbers `x` with `a <= x <= b`. The intersection of two closed intervals is a set of real numbers that is either empty, or can be represented as a closed interval. For example, the intersection of [1, 3] and [2, 4] is [2, 3].)*

 

**Example 1:**

**![img](https://assets.leetcode.com/uploads/2019/01/30/interval1.png)**

```
Input: A = [[0,2],[5,10],[13,23],[24,25]], B = [[1,5],[8,12],[15,24],[25,26]]
Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
```

#### 思路

与上一题一样，有五种情况：

![merge-intervals-3.png](https://github.com/LLancelot/LeetCode/blob/master/images/merge-intervals-3.png?raw=true)

- while 循环，查找overlap的情况（图2,3,4,5），并对交集作如下操作：
  1. 更改交集起点 start = ***max***(a.start, b.start)
  2. 更改交集终点 end = ***min***(a.end, b.end)

#### 代码

```python
class Solution(object):
    def intervalIntersection(self, A, B):
        """
        :type A: List[List[int]]
        :type B: List[List[int]]
        :rtype: List[List[int]]
        """
        merged, i, j, start, end = [], 0, 0, 0, 1
        
        while i < len(A) and j < len(B):
            A_after_B = B[j][start] <= A[i][start] <= B[j][end]
            B_after_A = A[i][start] <= B[j][start] <= A[i][end]
            
            if (A_after_B or B_after_A):
                intersect_start = max(A[i][start], B[j][start])
                intersect_end = min(A[i][end], B[j][end])
                merged.append([intersect_start, intersect_end])
                
            if A[i][end] < B[j][end]:
                i += 1
            else:
                j += 1
            
        return merged
            
```

#### 复杂度

- **Time complexity**: As we are iterating through both the lists once, the time complexity of the above algorithm is **O(N + M)**, where ‘N’ and ‘M’ are the total number of intervals in the input arrays respectively.

- **Space complexity**: Ignoring the space needed for the result list, the algorithm runs in constant space **O(1)**.



## 模板题4 - Conflicting Appointments

#### 题目

Given an array of intervals representing ‘N’ appointments, find out if a person can **attend all the appointments**.

**Example 1:**

```
Appointments: [[1,4], [2,5], [7,9]]
Output: false
Explanation: Since [1,4] and [2,5] overlap, a person cannot attend both of these appointments.
```

**Example 2:**

```
Appointments: [[6,7], [2,4], [8,12]]
Output: true
Explanation: None of the appointments overlap, therefore a person can attend all of them.
```

**Example 3:**

```
Appointments: [[4,5], [2,3], [3,6]]
Output: false
Explanation: Since [4,5] and [3,6] overlap, a person cannot attend both of these appointments.
```

#### 代码

```python
def can_attend_all_appointments(intervals):
    intervals.sort(key=lambda x: x[0])
    start, end = 0, 1
    for i in range(1, len(intervals)):
        if intervals[i][start] < intervals[i-1][end]:
            # please note the comparison above, it is "<" and not "<="
            # while merging we needed "<=" comparison, as we will be merging the two
            # intervals having condition "intervals[i][start] == intervals[i - 1][end]" but
            # such intervals don't represent conflicting appointments as one starts right
            # after the other
            return False
    return True
```



## leetcode 729. My Calendar I

#### 题目

> Implement a `MyCalendar` class to store your events. A new event can be added if adding the event will not cause a double booking.
>
> Your class will have the method, `book(int start, int end)`. Formally, this represents a booking on the half open interval `[start, end)`, the range of real numbers `x` such that `start <= x < end`.
>
> A *double booking* happens when two events have some non-empty intersection (ie., there is some time that is common to both events.)
>
> For each call to the method `MyCalendar.book`, return `true` if the event can be added to the calendar successfully without causing a double booking. Otherwise, return `false` and do not add the event to the calendar.
>
> Your class will be called like this: `MyCalendar cal = new MyCalendar();` `MyCalendar.book(start, end)`

> **Example 1:**
>
> ```
> MyCalendar();
> MyCalendar.book(10, 20); // returns true
> MyCalendar.book(15, 25); // returns false
> MyCalendar.book(20, 30); // returns true
> Explanation: 
> The first event can be booked.  The second can't because time 15 is already booked by another event.
> The third event can be booked, as the first event takes every time less than 20, but not including 20.
> ```

#### 思路

- Brute force 暴力搜索，时间复杂度O(n^2)。因为总搜索次数为1+2+3+...+n，每次book都要重头查找一遍
- Binary Search 二分查找，时间复杂度O(n*logn)。

#### 定义：

数据结构定义：定义一个orderMap或者TreeMap的数据类型，保存当前已经book的所有pairs，即TreeMap<int, int>，这个Map的映射关系是 start -> end，即 start 为 key，end 为 value。

假定 booked 中已有数据：[(**10**, 20), (**15**, 20)]，我们这时如果要查询<12, 18>，那么：

floor 定义：Largest entry whose key <= query_key。 即当前小于query_key=**12** 的最大时间段，就是[10, 20]，因为**10最接近12**

ceiling 定义：smallest entry whose key >query_key。即当前大于query_key=**12** 的最小时间段，就是[15, 20]，因为**15刚好大于10**

所以我们只需要找两次，即分别找出```booked(int start, int end)``` 时，以start为query_key所对应的floor和celling，在 java/c++ 中这一步操作时间复杂度为 O(logn)。找到 floor 和 ceiling 有何用？

- 以查询<**12**, 18> 为例，如果我们的query_start=12 比 floor.end (对应 value值) 要小，也就是12<20，则必定overlap。
- 同样，如果我们的ceiling.start 比 end=18 小，也就是**15** < 18，也属于overlap。

#### 代码

```java
class MyCalendar {
    TreeMap<Integer, Integer> m_booked;
    
    public MyCalendar() {
        m_booked = new TreeMap<>();
    }
    
    public boolean book(int start, int end) {
        Integer largest_key_smaller = m_booked.floorKey(start);		// := floor.key
        if (largest_key_smaller != null && m_booked.get(largest_key_smaller) > start) {
            // lower_bound.end > start, then overlap
            return false;
        }
        Integer smallest_key_larger = m_booked.ceilingKey(start);	// := ceiling.key
        if (smallest_key_larger != null && smallest_key_larger < end) {
            // upper_bound.start < end, then overlap
            return false;
        }
        m_booked.put(start, end);	// not overlap, add them to TreeMap
        return true;
    }
}

```

## leetcode 253. Meeting Rooms II

#### 题目

Given an array of meeting time intervals consisting of start and end times `[[s1,e1],[s2,e2],...]` (si < ei), find the minimum number of conference rooms required.

**Example 1:**

```
Input: [[0, 30],[5, 10],[15, 20]]
Output: 2
```

**Example 2:**

```
Input: [[7,10],[2,4]]
Output: 1
```

#### 思路

- 用 Heapq ，每次遇到一个 meeting，先比较 Heap 里面是否存在已经结束的会议，即检查 [s1, e1] 中 e1 <= s2
- 若存在这样已经结束的 meetings，那么就用 Min-Heap 去 pop 这些会议区间

#### 代码

```python
from heapq import *

class Solution(object):
    def minMeetingRooms(self, meetings):
        meetings.sort(key = lambda x: x[0])
        
        minRooms = 0
        minHeap = []
        
        for meet in meetings:
            # remove all the meetings that have ended
            while (len(minHeap) > 0 and meet[0] >= minHeap[0]):              
                heappop(minHeap)       
            # add current meeting into min_heap
            heappush(minHeap, meet[1])
            # all active meetings are in heap, we need to know the max rooms if happened.
            minRooms = max(minRooms, len(minHeap))
        return minRooms
```

