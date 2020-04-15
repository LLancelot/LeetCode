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

