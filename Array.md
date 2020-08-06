# Array 数组

## 238. Product of Array Except Self （Medium）

>Given an array `nums` of *n* integers where *n* > 1,  return an array `output` such that `output[i]` is equal to the product of all the elements of `nums` except `nums[i]`.
>
>**Example:**
>
>```
>Input:  [1,2,3,4]
>Output: [24,12,8,6]
>```
>
>**Constraint:** It's guaranteed that the product of the elements of any prefix or suffix of the array (including the whole array) fits in a 32 bit integer.
>
>**Note:** Please solve it **without division** and in O(*n*).
>
>**Follow up:**
>Could you solve it with constant space complexity? (The output array **does not** count as extra space for the purpose of space complexity analysis.)

#### 分析

基本意思就是，给你一个长度为N的数组，要输出一个同样长度为N的Output，要求输出数组中的每个数字分别对应原数组中，所有不包含该数字的乘积，即```output[i]```等于```nums[0] * nums[1] * ... nums[i-1] * nums[i+1] ... ```这N-1个数（不含```nums[i]```的乘积）。例如输出结果中，第一个数是24 = 2 * 3 * 4，第二个数12 = 1 * 3 * 4，以此类推。

#### 基本思路

- 以test case为例，第一遍for循环（从 i =1开始），求出[1, **1** * 1 , **1 * 1** * 2, **1 * 1 * 2** * 3] = [1, 1, 2, 6]， 即当前f[N] = nums[0 ... N-1] 的乘积。
- 然后记录`nums[]`中最后一个数的值，记为```back_num```
- 第二遍循环，从倒数第二个数开始，分别对上面的f[N]乘以```back_num```，然后```back_num```再乘以```nums[i]```. 简单来说，这两次的循环，第一次循环做的是保证第N个数的result是前N-1个数的乘积，第二次循环做的是保证倒数第N个数的result是倒数N-1个数的乘积，相当于第N个数的结果同时包含了左半部分（第一次循环）和后半部分（第二次循环）的累加。时间复杂度O(n)，满足题目要求。

Python：

```python
class Solution:
    def productExceptSelf(self, nums: List[int]) -> List[int]:
        output = [1] * len(nums)
        
        for i in range(1, len(nums)):
            output[i] = output[i - 1] * nums[i - 1]
            
        r = nums[-1]
        for i in range(len(nums) - 2, -1, -1):
            output[i] *= r
            r *= nums[i]
            
        return output
```

Java:

```java
// Author: Lin
// Runtime: 1 ms (100%)
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        Arrays.fill(res, 1);
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        
        int back_num = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; i--) {
            res[i] *= back_num;
            back_num *= nums[i];
        }
        
        return res;
    }
}
```

## 334. Increasing Triplet Subsequence

https://leetcode.com/problems/increasing-triplet-subsequence/

> Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.
>
> Formally the function should:
>
> > Return true if there exists *i, j, k*
> > such that *arr[i]* < *arr[j]* < *arr[k]* given 0 ≤ *i* < *j* < *k* ≤ *n*-1 else return false.
>
> **Note:** Your algorithm should run in O(*n*) time complexity and O(*1*) space complexity.
>
> **Example 1:**
>
> ```
> Input: [1,2,3,4,5]
> Output: true
> ```
>
> **Example 2:**
>
> ```
> Input: [5,4,3,2,1]
> Output: false
> ```

### 思路

- 用 min1, min2 记录当前遇到的最小值和第二小值，若遇到 min1 < min2 < num，则说明能找到三个数是连续递增的序列。

### 代码

```python
class Solution:
    def increasingTriplet(self, nums: List[int]) -> bool:
        
        if len(nums) < 3:
            return False
        
        m1, m2 = float('inf'), float('inf')
        
        for num in nums:
            if num > m2:
                return True
            elif num < m1:
                m1 = num
            elif num > m1 and num < m2:
                m2 = num
        
        return False
```

## 442. Find All Duplicates in an Array

https://leetcode.com/problems/find-all-duplicates-in-an-array/

> Given an array of integers, 1 ≤ a[i] ≤ *n* (*n* = size of array), some elements appear **twice** and others appear **once**.
>
> Find all the elements that appear **twice** in this array.
>
> Could you do it without extra space and in O(*n*) runtime?
>
> **Example:**
>
> ```
> Input:
> [4,3,2,7,8,2,3,1]
> 
> Output:
> [2,3]
> ```

**找出数组中存在的重复数字**

### 思路

- 因为数字是从 1... n 排列的，所以我们可以把找到对应下标的数字乘以 -1，变成负数，那么如果下一次再遇到相同的数字，我们就会发现这个数字对应下标的那个位置已经是负数了，则说明找到重复数字。如果都不重复，最后的整个数组都应该是负数。

### 代码

```python
class Solution(object):
    def findDuplicates(self, nums):
        res = []
        for x in nums:
            if nums[abs(x) - 1] < 0:
                res.append(abs(x))
            else:
                nums[abs(x) - 1] *= -1
        return res
```

