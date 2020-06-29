# Two-Pointers 双指针问题

## 模板题1 (easy) - Pair with Target Sum

#### 题目


> Given an array of sorted numbers and a target sum, find a **pair in the array whose sum is equal to the given target**.
>
> Write a function to return the indices of the two numbers (i.e. the pair) such that they add up to the given target.
>
> ```
> Input: [1, 2, 3, 4, 6], target=6
> Output: [1, 3]
> Explanation: The numbers at index 1 and 3 add up to 6: 2+4=6
> ```

题意：给定一个**排序好**的数组，找出两个数，两数之和等于 target。返回两个数的下标。

#### 思路

- 用 left 和 right 分别指向 arr[0] 和 arr[-1] 的位置，判断两数之和是否 match
  - 若 match，直接返回下标。
  - 若和小于 target，这说明我们需要更大的加数，left++
  - 若和大于 target，说明需要更小的加数，right--
- 循环结束若还未成功匹配，说明数组中不存在这样的一对。

#### 代码

```python
def pair_with_targetsum(arr, target_sum):
    left, right = 0, len(arr) - 1
    while(left < right):
        current_sum = arr[left] + arr[right]
        if current_sum == target_sum:
            return [left, right]

        if target_sum > current_sum:
            left += 1  # we need a pair with a bigger sum
        else:
            right -= 1  # we need a pair with a smaller sum
    return [-1, -1]

```

## 模板题2 (easy) - Remove Duplicates

#### 题目

> Given an array of sorted numbers, **remove all duplicates** from it. You should **not use any extra space**; after removing the duplicates in-place return the new length of the array.
>
> **Example 1:**
>
> ```
> Input: [2, 3, 3, 3, 6, 9, 9]
> Output: 4
> Explanation: The first four elements after removing the duplicates will be [2, 3, 6, 9].
> ```
>
> **Example 2:**
>
> ```
> Input: [2, 2, 2, 11]
> Output: 2
> Explanation: The first two elements after removing the duplicates will be [2, 11].
> ```

#### 思路

![two-pointers-1.png](https://github.com/LLancelot/LeetCode/blob/master/images/two-pointers-1.png?raw=true)

- ```nextNonDuplicate``` 永远指向**不重复数**的**下一个**下标。也就是说，在它之前的数全都是已经处理好、唯一的。
- ```next``` 一直向右移动，并且判断当前数字与```arr[next_non_duplicate - 1]```数字是否重复。
  - 若不重复，则让 ```nextNonDuplicate``` 所在位置填上next对应的数字，也就是不重复的数字，然后右移```nextNonDuplicate++```
  - 若重复，那么我们不能把 ```next``` **对应的重复的数字**填在```nextNonDuplicate``` 的位置上，所以只需要继续移动 ```next``` 即可，```nextNonDuplicate``` 则原地不动。

#### 代码

```python
def remove_duplicates(arr):
    # index of the next non-duplicate element
    next_non_duplicate = 1

    i = 1		# i对应图中的next
    while(i < len(arr)):
        if arr[next_non_duplicate - 1] != arr[i]:
            arr[next_non_duplicate] = arr[i]
            next_non_duplicate += 1
        i += 1

    return next_non_duplicate

```

## 模板题4 (medium) - Triplet Sum to Zero

### 相似题：

- Triplet Sum Close to Target (medium) - 近似 target 的三元组

- Triplets with Smaller Sum (medium) - 小于 target 的 三元组

#### 题目

> Given an array of unsorted numbers, find all **unique triplets in it that add up to zero**.
>
> **Example 1:**
>
> ```
> Input: [-3, 0, 1, 2, -1, 1, -2]
> Output: [-3, 1, 2], [-2, 0, 2], [-2, 1, 1], [-1, 0, 1]
> Explanation: There are four unique triplets whose sum is equal to zero.
> ```
>
> **Example 2:**
>
> ```
> Input: [-5, 2, -1, -2, 3]
> Output: [[-5, 2, 3], [-2, -1, 3]]
> Explanation: There are two unique triplets whose sum is equal to zero.
> ```

题意：找到所有和为0的三元组。

#### 思路

该问题遵循双指针模式，并与目标和有相似之处。不同之处在于输入数组没有排序，我们需要找到目标和为零的三个一组，而不是一对。

为了遵循类似的方法，首先，我们将对数组排序，然后遍历它，每次取一个数字。假设在我们的迭代中，我们在number ' X '处，因此我们需要找到' Y '和' Z '，使 **X + Y + Z == 0**。在这个阶段，我们的问题转化为找到一对的和等于“**-X**” (从上面的方程**Y + Z == -X**)。

Pair与目标Sum的另一个区别是，我们需要找到所有唯一的三胞胎。为了处理这个问题，我们必须跳过任何重复的数字。因为我们将对数组进行排序，所以所有重复的数字将相邻，更容易跳过。

#### 代码

```python
def search_triplets(arr):
    arr.sort()
    triplets = []
    for i in range(len(arr)):
        if i > 0 and arr[i] == arr[i-1]:  # skip same element to avoid duplicate triplets
            continue
        search_pair(arr, -arr[i], i+1, triplets)

    return triplets


def search_pair(arr, target_sum, left, triplets):
    right = len(arr) - 1
    while(left < right):
        current_sum = arr[left] + arr[right]
        if current_sum == target_sum:  # found the triplet
            triplets.append([-target_sum, arr[left], arr[right]])
            left += 1
            right -= 1
            while left < right and arr[left] == arr[left - 1]:
                left += 1  # skip same element to avoid duplicate triplets
            while left < right and arr[right] == arr[right + 1]:
                right -= 1  # skip same element to avoid duplicate triplets
        elif target_sum > current_sum:
            left += 1  # we need a pair with a bigger sum
        else:
            right -= 1  # we need a pair with a smaller sum

```

### Follow up: Triplet Sum Close to Target (medium)

#### 题目

> Given an array of unsorted numbers and a target number, find a **triplet in the array whose sum is as close to the target number as possible**, return the sum of the triplet. If there are more than one such triplet, return the sum of the triplet with the smallest sum.
>
> **Example 1:**
>
> ```
> Input: [-2, 0, 1, 2], target=2
> Output: 1
> Explanation: The triplet [-2, 1, 2] has the closest sum to the target.
> ```
>
> **Example 2:**
>
> ```
> Input: [-3, -1, 1, 2], target=1
> Output: 0
> Explanation: The triplet [-3, 1, 2] has the closest sum to the target.
> ```
>
> **Example 3:**
>
> ```
> Input: [1, 0, 1, 1], target=100
> Output: 3
> Explanation: The triplet [1, 1, 1] has the closest sum to the target.
> ```

#### 思路

我们可以采用类似的方法遍历数组，每次取一个数字。在每一步中，我们将保存三重值 (```arr[i] + arr[left] + arr[right]```) 与目标数之间的差异，以便在最后，我们可以返回具有最接近和的三重值。

#### 代码

```java
class Solution {
    public int threeSumClosest(int[] arr, int targetSum) {
        Arrays.sort(arr);
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length - 2; i++) {
            int left = i + 1, right = arr.length - 1;
            while (left < right) {
                int targetDiff = targetSum - (arr[i] + arr[left] + arr[right]);
                if (targetDiff == 0) // we've found a triplet with an exact sum
                    return targetSum - targetDiff; // return sum of all the numbers
                // the second part of the above 'if' is to handle the smallest sum when we have
                // more than one solution
                if (Math.abs(targetDiff) < Math.abs(minDiff)
                        || Math.abs(targetDiff) == Math.abs(minDiff) && targetDiff > minDiff)
                {    
                    minDiff = targetDiff;
                }
                if (targetDiff > 0)
                    // arr[left] is quite small, we need to have bigger arr[left], just move left++;
                    left++;
                else
                    right--;
            }
        }
        return targetSum - minDiff;
    }
}

```

## 713. Subarray Product Less Than K

#### 题目

> Your are given an array of positive integers `nums`.
>
> Count and print the number of (contiguous) subarrays where the product of all the elements in the subarray is less than `k`.
>
> **Example 1:**
>
> ```
> Input: nums = [10, 5, 2, 6], k = 100
> Output: 8
> Explanation: The 8 subarrays that have product less than 100 are: [10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6].
> Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
> ```

1、找到的 subarray 必须是**连续**的

2、subarray 的乘积小于 K

#### 思路

- 双指针，left 和 right，right 用来遍历整个数组（常规定义），left 指向第一个元素。均表示**下标**。
- for 循环每次遍历一个元素，且将乘积 product 累乘，乘完之后，为了保证 product 要小于 K，我们还要加一个 while 循环，循环的判断条件是：left <= right 且 product >= K，意思就说，如果我们发现当前的乘积 product 已经超出了 K，我们就要减少一些元素，做法是： ```product /= arr[left++]```，即除去```arr[left]```的值，并移动左指针 ```left++```。
- while 循环判断结束后，我们就得到了一个总乘积不超过 K 的，[left, right]区间的 subarray，如何得到这个区间的所有连续子序列的个数呢？
  - 以 [1, 3, 6] 为例，left 对应arr[0]=1，right 对应arr[2]=6，所有连续子序列为：[1]，[1, 3]，[1, 3, 6]，不难发现子序列个数即为 2 - 0 + 1 = 3，即：**right - left + 1**

- 最后将所有的子序列个数累加，```count += right - left + 1```，结束、返回。

#### 代码

```java
class Solution {
    public int numSubarrayProductLessThanK(int[] arr, int k) {
        if (k == 0)
            return 0;
        int cnt = 0, product = 1;
        // i means left, j means right
        for (int i = 0, j = 0; j < arr.length; j++) {
            product *= arr[j];
            while (i <= j && product >= k) {
                product /= arr[i++];
            }
            cnt += j - i + 1;
        }
        return cnt;
    }
}
```

#### 复杂度分析

- 外层for循环，因为 j 要遍历 n 次，即O(n)，内层while循环，因为 i 每次增加1，且 i <= j，也就是说无论外循环执行 n 次，内层的 while 循环总共也最多执行 n 次，所以也是 O(n)。（这种内外循环的题，主要看内层究竟是每次执行n次还是总共执行n次，因为内层的 i 是累加的，最多执行 j 次，而外层循环一次，内层的 i 并不会初始化为0，所以内层并不是每次都执行n遍，所以总时间复杂度并不是O(n^2)！）
- 综上，time complexity 为 O(n)
- 此外，并无额外数组开销，space complexity 为 O(1)

## 18. 4Sum (Quadruple Sum to Target (medium))

#### 题目

> Given an array of unsorted numbers and a target number, find all **unique quadruplets** in it, whose **sum is equal to the target number**.
>
> **Example 1:**
>
> ```
> Input: [4, 1, 2, -1, 1, -3], target=1
> Output: [-3, -1, 1, 4], [-3, 1, 1, 2]
> Explanation: Both the quadruplets add up to the target.
> ```
>
> **Example 2:**
>
> ```
> Input: [2, 0, -1, 1, -2, 2], target=2
> Output: [-2, 0, 2, 2], [-1, 0, 1, 2]
> Explanation: Both the quadruplets add up to the target.
> ```

1、不重复的四元组，跟 [3Sum](https://github.com/LLancelot/LeetCode/blob/master/two-pointers.md#%E6%A8%A1%E6%9D%BF%E9%A2%984-medium---triplet-sum-to-zero) 类似

#### 思路

假设存在四元组，<i, j, ?, ?>

- 先固定前两个数，即用两层 for 循环(i, j)，调用search_pairs函数，去查找满足四个数之和等于 targetSum 的另外两个数
- search_pairs() 中，运用双指针，left 表示要找的第3个数，right 表示要找的第4个数。**四个步骤**跟3Sum的做法一样：
  - 求当前 curSum
  - 判断 curSum == targetSum ？匹配，则加到result，left 右移，right 左移
    - 匹配后，两个while，去掉重复的数
  - 小于targetSum，left 右移
  - 大于targetSum，right 左移

#### 代码

```python
class Solution(object):
    def fourSum(self, arr, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[List[int]]
        """
        if arr == [] or len(arr) < 4:
            return []
        arr.sort()
        result = []
        # first:i, second:j
        for i in range(0, len(arr) - 3):
            if i > 0 and arr[i] == arr[i-1]:
                continue
            for j in range(i+1, len(arr) - 2):
                if j > i+1 and arr[j] == arr[j-1]:
                    continue
                self.search_pairs(arr, target, i, j, result)
    
        return result
    
    def search_pairs(self, arr, targetSum, first, second, result):
        left = second + 1   # the third number after second
        right = len(arr) - 1
        while left < right:
            curSum = arr[first] +arr[second] +arr[left] +arr[right]
            if curSum == targetSum:
                # add to result
                result.append([arr[first], arr[second], arr[left], arr[right]])
                left += 1
                right -= 1
                # pass duplicate for left and right
                while left < right and arr[left] == arr[left - 1]:
                    left += 1
                while left < right and arr[right] == arr[right + 1]:
                    right -= 1
            elif curSum < targetSum:
                # less than target, move left++
                left += 1
            else:
                right -= 1
        
```

## 模板题5 - Minimum Window Sort

同 [leetcode 581. Shortest Unsorted Continuous Subarray](https://leetcode.com/problems/shortest-unsorted-continuous-subarray/)

#### 题目

> Given an array, find the length of the **smallest** subarray in it which when sorted will sort the whole array.
>
> **Example 1:**
>
> ```
> Input: [1, 2, 5, 3, 7, 10, 9, 12]
> Output: 5
> Explanation: We need to sort only the subarray [5, 3, 7, 10, 9] to make the whole array sorted
> ```
>
> **Example 2:**
>
> ```
> Input: [1, 3, 2, 0, -1, 7, 10]
> Output: 5
> Explanation: We need to sort only the subarray [1, 3, 2, 0, -1] to make the whole array sorted
> ```
>
> **Example 3:**
>
> ```
> Input: [1, 2, 3]
> Output: 0
> Explanation: The array is already sorted
> ```
>
> **Example 4:**
>
> ```
> Input: [3, 2, 1]
> Output: 3
> Explanation: The whole array needs to be sorted.
> ```

1、找到最小的区间长度，使得 sort 这个区间之后，整个数组便成为升序列。

#### 思路

假设例子 [1, 3, 2, 0, -1, 7, 10]，左异常值：3，和右异常值：-1，因为3是第一个从前往后出现比后面大的数字，-1是从后往前第一个出现比左边小的数字（即不满足升序条件的左右边界）

- 双指针，low 和 high。首先找到从左往右方向的第一个异常值，以及右异常值。low 和 high 分别为异常值下标
- 对于**正区间** [low, high]，分别求区间内的**最小值**和**最大值**，记 sub_min 和 sub_max
- 对于**左区间** [0, low)，若找到比 sub_min 还要大的数，则 low -= 1，表示目前区间窗口需要扩展
- 对于**右区间** (high, ~)，若找到比 sub_max 还要小的数，high += 1，表示扩展

**解释：** 为什么要排查区间的左、右部分，扩展？很简单，我们已经知道了区间内的最小值和最大值，如果左区间的数都比正区间的最小值要小，右区间的数都比正区间的大，那么这整个数组当然是升序的了，那么正区间就是我们要找的最小待处理区间。**但是，**如果左区间的数并非小于正区间的最小值，那说明左区间的这个数不应该出现在这个位置，以思路中的例子，3到-1之间为正区间，-1是最小值，此时左区间只有一个数，1，而1比-1大，说明1不应该出现在这，因为就算正区间排好序，比-1大的数竟然还在左区间，显然不符合要求，换句话说1也该被考虑到加入正区间才对。同理，右区间需要排查。

#### 代码

```python
import math
class Solution(object):
    def findUnsortedSubarray(self, arr):
        """
        :type nums: List[int]
        :rtype: int
        """
        low, high = 0, len(arr) - 1
        
        while (low < len(arr) - 1 and arr[low] <= arr[low + 1]):
            low += 1
            
        if low == len(arr) - 1:
            # already sorted
            return 0
        
        while (high > 0 and arr[high] >= arr[high - 1]):
            high -= 1
        
        sub_min = float('inf')
        sub_max = -float('inf')
        
        for k in range(low, high + 1):
            sub_min = min(sub_min, arr[k])
            sub_max = max(sub_max, arr[k])
        
        # extend numbers
        while (low > 0 and arr[low - 1] > sub_min):		# 左区间排查
            low -= 1
        
        while (high < len(arr) - 1 and arr[high + 1] < sub_max):	# 右区间排查
            high += 1
        
        return high - low + 1
```

