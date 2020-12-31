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

- 定义left,right数组，分别表示该数字左边&右边第一个比它小的数字的index.

```java
class Solution {
    public int largestRectangleArea(int[] h) {
        int n = h.length;
        int[] left = new int[n];
        int[] right = new int[n];
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        
        for (int i = 0; i < n; i ++) {
            while (!stack.isEmpty() && h[i] <= h[stack.peek()])
                stack.pop();
            if (stack.isEmpty())
                left[i] = -1;
            else
                left[i] = stack.peek();
            stack.push(i);
        }
        
        stack.clear();
        for (int i = n - 1; i >= 0; i --) {
            while (!stack.isEmpty() && h[i] <= h[stack.peek()])
                stack.pop();
            if (stack.isEmpty())
                right[i] = n;
            else
                right[i] = stack.peek();
            stack.push(i);
        }
        
        int res = 0;
        for (int i = 0; i < n; i ++) {
            res = Math.max(res, h[i] * (right[i] - left[i] - 1));
        }
        return res;
    }
}
```



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

## 232. Implement Queue using Stacks

> Implement the following operations of a queue using stacks.
>
> - push(x) -- Push element x to the back of queue.
> - pop() -- Removes the element from in front of queue.
> - peek() -- Get the front element.
> - empty() -- Return whether the queue is empty.
>
> **Example:**
>
> ```
> MyQueue queue = new MyQueue();
> 
> queue.push(1);
> queue.push(2);  
> queue.peek();  // returns 1
> queue.pop();   // returns 1
> queue.empty(); // returns false
> ```
>
> **Notes:**
>
> - You must use *only* standard operations of a stack -- which means only `push to top`, `peek/pop from top`, `size`, and `is empty` operations are valid.
> - Depending on your language, stack may not be supported natively. You may simulate a stack by using a list or deque (double-ended queue), as long as you use only standard operations of a stack.
> - You may assume that all operations are valid (for example, no pop or peek operations will be called on an empty queue).

题意：用栈实现队列的一系列操作。

**思路**

- 设立两个栈，s1 and s2.
- 对于 **push** 操作，直接在 s1 中加到末尾，即可
- 对于 **pop** 操作，我的思想是，既然要被pop掉的数字在栈底，那么我们就把栈 s1 的所有元素 pop 到另一个栈 s2 中去，这样能保证在 s1 中处于栈底的元素会出现在 s2 的栈顶，比如 s1 = [1, 2, 3]，我们需要元素"1"出队列，那么我们把 s1 按照栈的性质 pop 到 s2 中，那么 s2 这时就变成了 s2 = [3, 2, 1]，我们把 s2 的栈顶元素 pop，即1，就得到了结果。最后，再用同样的方式，把 s2 依次出栈到 s1，还原s1.
- 对于 **peek** 操作，思路和 pop 相同，需要在 s2.pop() 时，用 res 记录 s2.pop()，并且把 res 添加到 s1 中去，再用同样的方式，把s2依次出栈到s1，还原s1.
- 对于 empty()，判断 s1 == [] 与否即可

**代码**

```python
class MyQueue(object):

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.s1 = []
        self.s2 = []

    def push(self, x):
        """
        Push element x to the back of queue.
        :type x: int
        :rtype: None
        """
        self.s1.append(x)
        

    def pop(self):
        """
        Removes the element from in front of queue and returns that element.
        :rtype: int
        """
        while self.s1:
            self.s2.append(self.s1.pop())
        
        res = self.s2.pop()
        while self.s2:
            self.s1.append(self.s2.pop())
        
        return res

    def peek(self):
        """
        Get the front element.
        :rtype: int
        """
        while self.s1:
            self.s2.append(self.s1.pop())
        
        res = self.s2.pop()
        self.s1.append(res)
        while self.s2:
            self.s1.append(self.s2.pop())

        return res
        
    def empty(self):
        """
        Returns whether the queue is empty.
        :rtype: bool
        """
        return self.s1 == []
```