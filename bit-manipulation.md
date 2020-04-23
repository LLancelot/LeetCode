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

