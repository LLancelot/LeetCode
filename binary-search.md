# Binary Search 二分查找

## 162. Find Peak Element

>A peak element is an element that is greater than its neighbors.
>
>Given an input array `nums`, where `nums[i] ≠ nums[i+1]`, find a peak element and return its index.
>
>The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.
>
>You may imagine that `nums[-1] = nums[n] = -∞`.
>
>**Example 1:**
>
>```
>Input: nums = [1,2,3,1]
>Output: 2
>Explanation: 3 is a peak element and your function should return the index number 2.
>```
>
>**Example 2:**
>
>```
>Input: nums = [1,2,1,3,5,6,4]
>Output: 1 or 5 
>Explanation: Your function can return either index number 1 where the peak element is 2, 
>             or index number 5 where the peak element is 6.
>```
>
>**Follow up:** Your solution should be in **logarithmic** complexity.

代码：

```python
# Time complexity: O(logn)
# Space complexity: O(1)

class Solution(object):
    def findPeakElement(self, A):
        low, high = 0, len(A) - 1
        while (low < high):
            mid = low + (high - low) // 2
            # find first mid
            if (A[mid] > A[mid + 1]):
                high = mid
            else:
                low = mid + 1
        return low
```

## 4. Median of Two Sorted Arrays [Hard]

> There are two sorted arrays **nums1** and **nums2** of size m and n respectively.
>
> Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
>
> You may assume **nums1** and **nums2** cannot be both empty.
>
> **Example 1:**
>
> ```
> nums1 = [1, 3]
> nums2 = [2]
> 
> The median is 2.0
> ```
>
> **Example 2:**
>
> ```
> nums1 = [1, 2]
> nums2 = [3, 4]
> 
> The median is (2 + 3)/2 = 2.5
> ```

### 代码

```java
class Solution {
    public double findMedianSortedArrays(int[]nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        if (n1 < n2)
            return findMedianSortedArrays(nums2, nums1); // 保证nums1少于nums2个数
        int k = (n1 + n2 + 1) / 2; // 取前k个数，使 k = m1 + m2
        int l = 0, r = n1;
        
        while (l < r) {
            int m1 = (l + r) / 2;
            int m2 = k - m1;
            if (nums1[m1] < nums2[m2 - 1])
                l = m1 + 1;
            else
                r = m1;
        }
        
        int m1 = l, m2 = k - l;
        
        int c1 = Math.max(m1 <= 0 ? Integer.MIN_VALUE : nums1[m1 - 1],
                          m2 <= 0 ? Integer.MIN_VALUE : nums2[m2 - 1]);
        if ((n1 + n2) % 2 == 1)
            return c1;
        
        int c2 = Math.max(m1 >= n1 ? Integer.MAX_VALUE : nums1[m1],
                          m2 >= n2 ? Integer.MAX_VALUE : nums2[m2]);
        return 0.5 * (c1 + c2);
    }
}
```

## 1283. Find the Smallest Divisor Given a Threshold

https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/

> Given an array of integers `nums` and an integer `threshold`, we will choose a positive integer divisor and divide all the array by it and sum the result of the division. Find the **smallest** divisor such that the result mentioned above is less than or equal to `threshold`.
>
> Each result of division is rounded to the nearest integer greater than or equal to that element. (For example: 7/3 = 3 and 10/2 = 5).
>
> It is guaranteed that there will be an answer.
>
>  
>
> **Example 1:**
>
> ```
> Input: nums = [1,2,5,9], threshold = 6
> Output: 5
> Explanation: We can get a sum to 17 (1+2+5+9) if the divisor is 1. 
> If the divisor is 4 we can get a sum to 7 (1+1+2+3) and if the divisor is 5 the sum will be 5 (1+1+1+2). 
> ```
>
> **Example 2:**
>
> ```
> Input: nums = [2,3,5,7,11], threshold = 11
> Output: 3
> ```
>
> **Example 3:**
>
> ```
> Input: nums = [19], threshold = 5
> Output: 4
> ```
>
>  
>
> **Constraints:**
>
> - `1 <= nums.length <= 5 * 10^4`
> - `1 <= nums[i] <= 10^6`
> - `nums.length <= threshold <= 10^6`

### 代码

```python
class Solution:
    def smallestDivisor(self, A: List[int], threshold: int) -> int:
        l, r = 1, max(A)
        while l < r:
            m = (l + r) // 2
            if sum((i + m - 1) // m for i in A) > threshold:
                l = m + 1
            else:
                r = m
        return l
```

#### **Explanation**

Binary search the result.
If the `sum > threshold`, the divisor is too small.
If the `sum <= threshold`, the divisor is big enough.

#### **Complexity**

Time `O(NlogM)`, where `M = max(A)`
Space `O(1)`