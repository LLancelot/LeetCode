# Bit-Manipulation 位运算

## 201. Bitwise AND of Number Range (Medium)

>Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND of all numbers in this range, inclusive.
>
>**Example 1:**
>
>```
>Input: [5,7]
>Output: 4
>```
>
>**Example 2:**
>
>```
>Input: [0,1]
>Output: 0
>```

#### 基本思路

- 因为range里面至少有一个奇数和一个偶数，所以逻辑与运算(&)的最后一位结果一定是0.

- 因此，我们只需要每一次对m和n右移一位，并且检查m和n是否已经相等，这么做的目的是，求出m和n最长的公共前缀即可，并且用一个```moveFactor```来记录移动的次数. ```moveFactor```每次自乘2， 用来记录最高位的值，比如5(1001) 和 7(1011) 的公共前缀是最高位的1，也就是说m=5和n=7都分别移动了3位，返回的结果应该是
  $$
  2^3 * LongestCommonPrefix(m=5, n=7) = 8 * ['1'] = 8
  $$

- 

$$
2^n * LongestCommonPrefix(m=113, n=117) = 2^4 * ['111'] = 16 * 7 = 112
$$

#### 代码

```python
class Solution(object):
    def rangeBitwiseAnd(self, m, n):
        """
        :type m: int
        :type n: int
        :rtype: int
        """
        if m == 0:  return 0
        moveFactor = 1
        while m != n:
            m >>= 1
            n >>= 1
            moveFactor <<= 1
        return m * moveFactor
```



## 137. Single Number II

https://leetcode.com/problems/single-number-ii/

Given an integer array `nums` where every element appears **three times** except for one, which appears **exactly once**. *Find the single element and return it*.

 

**Example 1:**

```
Input: nums = [2,2,3,2]
Output: 3
```

**Example 2:**

```
Input: nums = [0,1,0,1,0,1,99]
Output: 99
```

 

**Constraints:**

- `1 <= nums.length <= 3 * 104`
- `-231 <= nums[i] <= 231 - 1`
- Each element in `nums` appears exactly **three times** except for one element which appears **once**.

### 代码

- 用模3状态机的状态转移

```python
class Solution(object):
    def singleNumber(self, nums):
        ones, twos = 0, 0
        for x in nums:
            ones = (ones ^ x) & ~twos
            twos = (twos ^ x) & ~ones        
        return ones
```



## 2429. Minimize XOR

https://leetcode.com/problems/minimize-xor/



Given two positive integers `num1` and `num2`, find the integer `x` such that:

- `x` has the same number of set bits as `num2`, and
- The value `x XOR num1` is **minimal**.

Note that `XOR` is the bitwise XOR operation.

Return *the integer* `x`. The test cases are generated such that `x` is **uniquely determined**.

The number of **set bits** of an integer is the number of `1`'s in its binary representation.

 

**Example 1:**

```
Input: num1 = 3, num2 = 5
Output: 3
Explanation:
The binary representations of num1 and num2 are 0011 and 0101, respectively.
The integer 3 has the same number of set bits as num2, and the value 3 XOR 3 = 0 is minimal.
```

**Example 2:**

```
Input: num1 = 1, num2 = 12
Output: 3
Explanation:
The binary representations of num1 and num2 are 0001 and 1100, respectively.
The integer 3 has the same number of set bits as num2, and the value 3 XOR 1 = 2 is minimal.
```



### 代码

```java
class Solution {
    public int minimizeXor(int a, int b) {
        int cnt = 0;
        while (b!=0) {
            cnt += (b & 1);
            b >>= 1;
        }
        int res = 0;
        for (int i = 29; i >= 0 && cnt > 0 ; i--) {
            if (((a >> i) & 1) == 1) {
                res -= 1 << i;
                cnt --;
            }
        }
        for (int i = 0; i < 30 && cnt > 0 ; i++) {
            if (((a >> i) & 1) == 0) {
                res += 1 << i;
                cnt --;
            }
        }

        return (a + res) ^ a;
    }
}
```

