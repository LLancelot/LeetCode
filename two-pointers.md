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