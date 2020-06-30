# Merge Intervals 区间合并问题

## 模板题1 - Merge Intevals

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

