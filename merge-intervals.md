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

