# Backtracking 回溯

## [Medium] 17. Letter Combinations of a Phone Number

>Given a string containing digits from `2-9` inclusive, return all possible letter combinations that the number could represent.
>
>A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
>
>![img](http://upload.wikimedia.org/wikipedia/commons/thumb/7/73/Telephone-keypad2.svg/200px-Telephone-keypad2.svg.png)
>
>**Example:**
>
>```
>Input: "23"
>Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
>```

代码：

```python
class Solution(object):
    def letterCombinations(self, digits):
        """
        :type digits: str
        :rtype: List[str]
        """
        letterMap = [" ","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"]
        res = []
        def findCombination(digits, index, s):
            if index == len(digits):
                res.append(s)
                return
            ch = digits[index]
            ch_letters = letterMap[ord(ch)-ord('0')]
            for i in range(len(ch_letters)):
                findCombination(digits, index+1, s+ch_letters[i])
            return
        
        if digits == "":
            return []
        findCombination(digits, 0, "")
        return res
```

## [Median] 22. Generate Parentheses

>Given *n* pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
>
>For example, given *n* = 3, a solution set is:
>
>```
>[
>  "((()))",
>  "(()())",
>  "(())()",
>  "()(())",
>  "()()()"
>]
>```

**思路：**

- 要么加左括号，要么加右括号
- 递归终止：右括号个数已经等于n，证明n对括号已经匹配组合完成，把`cur_str`添加到`res`中，直接return.
- 加左括号的条件：当左括号个数小于n。
- 加右括号的条件：当右括号的个数小于左括号。

```python
class Solution(object):
    def generateParenthesis(self, n):
        """
        :type n: int
        :rtype: List[str]
        """
        res = []
        
        def dfs(cur_str, res, n, lp, rp):#lp: 左括号个数, rp: 右括号个数
            if rp == n:
                res.append(cur_str)
                return
            if lp < n:
                # add '('
                dfs(cur_str+"(", res, n, lp+1, rp)
            if rp < lp:
                # only the num of ')'< '(', we can add ')'
                dfs(cur_str+")", res, n, lp, rp+1)
        
        dfs("",res,n,0,0)
        return res
```

