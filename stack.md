# Stack 栈

## 84. Largest Rectangle in Histogram (单调栈系列)

- 参考单调栈系列博客：[here](https://blog.csdn.net/qy724728631/article/details/82350682?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task)

>Given *n* non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.
>
> 
>
>![img](https://assets.leetcode.com/uploads/2018/10/12/histogram.png)
>Above is a histogram where width of each bar is 1, given height = `[2,1,5,6,2,3]`.
>
> 
>
>![img](https://assets.leetcode.com/uploads/2018/10/12/histogram_area.png)
>The largest rectangle is shown in the shaded area, which has area = `10` unit.
>
> 
>
>**Example:**
>
>```
>Input: [2,1,5,6,2,3]
>Output: 10
>```

**主要思路**：

- ```stack```存放矩形的坐标，用于计算面积时的宽度。```i```记录遍历矩形时的下标。

- 入栈的条件：当前矩形要比前一个矩形要高(高于栈顶index的矩形)，或者栈为空。

- 出栈的情形：若当前矩形比前一个要低，由于要保持栈的单调递增，此时用```previous_h```存放栈顶元素，即比当前矩形高的前一个矩形的下标，例如图中的1,5,6均入栈，在遇到2的时候，```previous_h```记录栈顶“6”的下标，等于3。```heights[previous_h]```的值是6。接着，将栈顶出栈，计算面积的最大值：

  ```maxarea = Math.max(res, heights[previous_h] * (st.empty() ? i : i - st.peek() -1));```

- 这里，```i - st.peek() -1```是为了在计算面积时，从宽度为1开始计算，例如图中最后一个入栈的是“6”对应的下标3，因为每次入栈都会让```i++```，因此我们在处理矩形“2”的时候，此时 i = 4，栈顶元素```st.peek()```是矩形“5”的下标，为2，因此要将4-2-1=1，然后从宽度为1开始计算面积。因为这一步是在大循环```i < heights.length```里面的，因此，当我们对矩形“2”处理的时候，每一次的循环都会先对当前高度和栈顶高度作比较，直到矩形“2”的前面比“2”大的元素都出栈了，判断完```if```里面的语句之后，```i```才会继续移动，后面的元素才会接着进栈。

**Python**：

```python
class Solution(object):
    def largestRectangleArea(self, heights):
        """
        :type heights: List[int]
        :rtype: int
        """
        heights.append(0)
        stack = []
        i, res = 0, 0
        while i < len(heights):
            if stack == [] or heights[stack[-1]] < heights[i]:
                # check if current height greater than stack.top, make sure
                # the stack is monotone stack.
                stack.append(i)
                i += 1
            else:
                previous_index = stack[-1]
                stack.pop(-1)
                if stack == []:
                    res = max(res, heights[previous_index]*i)
                else:
                    res = max(res, heights[previous_index]*(i-stack[-1]-1))
        return res
```

**Java**：

```java
class Solution {
    public int largestRectangleArea(int[] h) {
        ArrayList<Integer> heights = new ArrayList<>();
        for (int num: h)
            heights.add(num);
        if (heights.size() == 0)
            return 0;
        Stack<Integer> st = new Stack<>();
        int i = 0, res = 0;
        heights.add(0);
        while (i < heights.size()){
            if (st.empty() || heights.get(i) > heights.get(st.peek()))
                st.push(i++);
            else{
                int t = st.peek();
                st.pop();
                // t := index of the larger previous num               
                res = Math.max(res, heights.get(t) * (st.empty() ? i : i - st.peek() -1));
            }
        }
        return res;
    }
}
```





