# Sliding Window

## [Hard] 76. Minimum Window Substring

>Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
>
>**Example:**
>
>```
>Input: S = "ADOBECODEBANC", T = "ABC"
>Output: "BANC"
>```
>
>**Note:**
>
>- If there is no such window in S that covers all characters in T, return the empty string `""`.
>- If there is such window, you are guaranteed that there will always be only one unique minimum window in S.

主要思路：

- 用```left```和```right```来构建滑动窗口，用```counter```记录T中字符元素的个数，用```dic```记录循环中当前滑动窗口中字符元素的个数，用```matchNum```记录当前滑动窗口中，已经和T中匹配的字符个数（注意：如果T中有多个相同元素，在S中，当我们的滑动窗口里的匹配元素个数小于等于```counter[match_char]```时，才将```matchNum```+=1）

代码及注释：

```python
class Solution:
    def minWindow(self, s: str, t: str) -> str:
        left = right = matchNum = 0
        MinLen = 2**31
        dic = {}
        rst = ''
        count = collections.Counter(t)
        while left <= right and right < len(s):
            char = s[right]
            # dic 记录当前滑动窗口中包含T中字符的元素个数，找到+=1，没找到初始化为1
            dic[char] = 1 if char not in dic else dic[char] + 1
            if dic[char] <= count[char] : matchNum += 1
            while matchNum == len(t):
                # matchNum 已经找齐
                if MinLen > right - left + 1:
                    # 检查是否存在更短的窗口长度
                    MinLen = right - left + 1
                    # 获取新的较短字符串
                    rst = s[left:right+1]
                # 比较完，每次matchNum == len(t)之后都要移动left，并且dic[s[left]] -=1
                dic[s[left]] -= 1
                # 如果s[left]刚好属于T中的目标元素，并且小于目标元素的个数
                # 则移动之后要减去一个matchNum，用来后续的寻找，比如有两个b在T中，当前S[left]是b
                # 那么往右移动时，必然要减去matchNum
                if dic[s[left]] < count[s[left]]: matchNum -= 1
                # 只有matchNum匹配时，才移动左指针
                left +=1
            # 其他情况移动右指针
            right += 1
        return rst        
```

