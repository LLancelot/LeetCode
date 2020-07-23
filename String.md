# String 

## 38. Count and Say

#### [题目](https://leetcode.com/problems/count-and-say/)

The count-and-say sequence is the sequence of integers with the first five terms as following:

```
1.     1
2.     11
3.     21
4.     1211
5.     111221
```

`1` is read off as `"one 1"` or `11`.
`11` is read off as `"two 1s"` or `21`.
`21` is read off as `"one 2`, then `one 1"` or `1211`.

Given an integer *n* where 1 ≤ *n* ≤ 30, generate the *n*th term of the count-and-say sequence. You can do so recursively, in other words from the previous member read off the digits, counting the number of digits in groups of the same digit.

Note: Each term of the sequence of integers will be represented as a string.

#### 代码

```java
class Solution {
    public String countAndSay(int n) {
        if (n <= 0) return "";
        String str = "1";
        for (int i = 1; i < n; i++) {
            int count = 0;
            char prev = '.';
            StringBuilder sb = new StringBuilder();
            for (int idx = 0; idx < str.length(); idx++)
            {
                if (str.charAt(idx) == prev || prev == '.') {
                    count += 1;
                }
                else {
                    sb.append(count + Character.toString(prev));
                    count = 1;
                }
                prev = str.charAt(idx);
            }
            
            // final step when for-loop ends: add "count + prev"
            sb.append(count + Character.toString(prev));
            str = sb.toString(); // update str by sb.
        }
        return str;
    }
}
```

## 44. Wildcard Matching

题目：https://leetcode.com/problems/wildcard-matching/

**Example 1:**

```
Input:
s = "aa"
p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
```

**Example 2:**

```
Input:
s = "aa"
p = "*"
Output: true
Explanation: '*' matches any sequence.
```

**Example 3:**

```
Input:
s = "cb"
p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
```

**Example 4:**

```
Input:
s = "adceb"
p = "*a*b"
Output: true
Explanation: The first '*' matches the empty sequence, while the second '*' matches the substring "dce".
```

**Example 5:**

```
Input:
s = "acdcb"
p = "a*c?b"
Output: false
```

#### 思路

参考：https://www.cnblogs.com/grandyang/p/4401196.html

<p>开始进行匹配，若i小于s串的长度，进行 while 循环。若当前两个字符相等，或着p中的字符是问号，则i和j分别加1。若 p[j] 是星号，要记录星号的位置，jStar 赋为j，此时j再自增1，iStar 赋为i。若当前 p[j] 不是星号，并且不能跟 p[i] 匹配上，此时就要靠星号了，若之前星号没出现过，那么就直接跪，比如 s = "aa" 和 p = "c*"，此时 s[0] 和 p[0] 无法匹配，虽然 p[1] 是星号，但还是跪。如果星号之前出现过，可以强行续一波命，比如 s = "aa" 和 p = "*c"，当发现 s[1] 和 p[1] 无法匹配时，但是好在之前 p[0] 出现了星号，把 s[1] 交给 p[0] 的星号去匹配。至于如何知道之前有没有星号，这时就能看出 iStar 的作用了，因为其初始化为 -1，而遇到星号时，其就会被更新为i，只要检测 iStar 的值，就能知道是否可以使用星号续命。虽然成功续了命，匹配完了s中的所有字符，但是之后还要检查p串，此时没匹配完的p串里只能剩星号，不能有其他的字符，将连续的星号过滤掉，如果j不等于p的长度，则返回 false



#### 代码

```c++
class Solution {
public:
    bool isMatch(string s, string p) {
        int i = 0, j = 0, iStar = -1, jStar = -1, m = s.size(), n = p.size();
        while (i < m) {
            if (j < n && s[i] == p[j] || p[j] == '?') {
                ++i;
                ++j;
            }
            else if (j < n && p[j] == '*') {
                iStar = i;
                jStar = j++;
            }
            else if (iStar >= 0) {
                i = ++iStar;
                j = jStar + 1;
            }
            else
                return false;
        }
        
        // pass the continuous '*' in rest of pattern string
        while (j < n && p[j] == '*')
            ++j;
        // return true if j reached the end of pattern
        return j == n;
    }
private:
    int m;
    int n;
};
```

