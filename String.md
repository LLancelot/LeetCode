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

# 10. Regular Expression Matching

## 题目

https://leetcode.com/problems/regular-expression-matching/

Given an input string (`s`) and a pattern (`p`), implement regular expression matching with support for `'.'` and `'*'`.

```
'.' Matches any single character.
'*' Matches zero or more of the preceding element.
```

The matching should cover the **entire** input string (not partial).

**Note:**

- `s` could be empty and contains only lowercase letters `a-z`.
- `p` could be empty and contains only lowercase letters `a-z`, and characters like `.` or `*`.

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
p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
```

**Example 3:**

```
Input:
s = "ab"
p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
```

**Example 4:**

```
Input:
s = "aab"
p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".
```

**Example 5:**

```
Input:
s = "mississippi"
p = "mis*is*p*."
Output: false
```

## 思路

这道题和上一题 [44. Wildcard Matching](https://github.com/LLancelot/LeetCode/blob/master/String.md#44-wildcard-matching) 都属于字符串匹配的问题，不同点在于：

- 星号 '*' 在这里表示前一个字符可以有0个或多个
- 点号 '.' 表示可以匹配任意一个字符

所以主要思路是**动态规划**，我们定义一个```boolean[][] matched``` 数组，即```boolean[S.length + 1][P.length + 1]```

- 假设 s = "abcde"，p = "abc" 。以 ```matched[3][3]```为例，它表示 **s 的前三个字符** 与 **p 的前三个字符** 的匹配情况，在这里就是 "abc" 和  "abc"，即能匹配，所以 ```matched[3][3] = true``` 。同样地 ```matched[3][2] = false```，因为 “abc” ≠ “ab”。 

- **首先**，对于 p 中的字符，我们需要判断它和 s 中当前字符是否匹配，匹配相同的条件有两个：

  1. ```p[p_index] == s[s_index]``` 
  2. ```p[p_index] == '.'```

  就是要么当前 p 和 s 的对应字符相等，要么 p 自己是 ' . '

- **其次**，如果当前 p 和 s 对应字符不相等，且 p 当前不是 ' . ' ，那么我们要检查 p 当前是否是星号 ' * '：

  1. 如果是星号，我们需要检查星号前面的字符是否与 s 当前字符相等，例如 "abcd**d**" 和 "abc**d***"，即找到 ‘d‘，如果出现这种情况则说明当前```matched[si][pi]```只和 p 中 "*" 之前的 “abc” 的匹配情况有关，因为这里 * 可以表示**多个 d**，也就是说，* 之前的 p 整体如果是匹配（true）状态的，那么当前的状态也是 true。反之，如果之前的 p 整体如果是不匹配（false）状态的，那么当前的状态也是 false。这一步可以写成 ```matched[si][pi] = matched[si][pi - 2]```，即只和 p 中除掉 "d*" 后的 “abc” 有关。

  2. 前面说了 * 匹配多个相同字符的情况，同样，也有可能匹配0个s中的字符，如果 * 可以匹配0个，那么我们可以写成```matched[si][pi] = matched[si - 1][pi]```，即 p[0 ~ pi] 只与 s[0 ~ si-1] 匹配，即 s = “**abcd**d” 与 p = "**abcd***" 的匹配情况。

- **最后**，返回 matched 矩阵最后一个元素，即 ```matched[s.length()][p.length()]```，表示 s 的全部字符和 p 的全部字符都匹配过的情况。

## 代码

     ```java
// Author: LLancelot
// Date: 2020-07-24

class Solution {
    public boolean isMatch(String s, String p) {
        if (s == null || p == null)
            return false;
        boolean[][] matched = new boolean[s.length()+1][p.length()+1];
        // init matched，默认两个空串之间，也就是matched[0][0]是可以匹配成功的
        matched[0][0] = true;
        // i := index in matched matrix, to get char, we need to use [i - 1]
        for (int i = 1; i <= p.length(); i++) {
            if (p.charAt(i-1) == '*'){
                matched[0][i] = matched[0][i-2];
            }
        }
        
        // loop string s and p, s[si - 1] & p[pi - 1] := current char
        for (int si = 1; si <= s.length(); si++) {
            for (int pi = 1; pi <= p.length(); pi++) {
                if (p.charAt(pi-1) == s.charAt(si-1) ||
                   p.charAt(pi-1) == '.') {
                    matched[si][pi] = matched[si-1][pi-1];
                }
                else if (p.charAt(pi-1) == '*') {
                    if (p.charAt(pi-2) == s.charAt(si-1) || p.charAt(pi-2) == '.') {
                        matched[si][pi] = matched[si][pi - 2] || matched[si - 1][pi];
                    }
                    else {
                        matched[si][pi] = matched[si][pi - 2];
                    }
                }
            }
        }
        
        return matched[s.length()][p.length()];
    }
}
     ```

