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

## [Hard]  51. N-Queens 

>The *n*-queens puzzle is the problem of placing *n* queens on an *n*×*n* chessboard such that no two queens attack each other.
>
>![img](https://assets.leetcode.com/uploads/2018/10/12/8-queens.png)
>
>Given an integer *n*, return all distinct solutions to the *n*-queens puzzle.
>
>Each solution contains a distinct board configuration of the *n*-queens' placement, where `'Q'` and `'.'` both indicate a queen and an empty space respectively.

参考：[回溯算法详解](https://github.com/labuladong/fucking-algorithm/blob/master/%E7%AE%97%E6%B3%95%E6%80%9D%E7%BB%B4%E7%B3%BB%E5%88%97/%E5%9B%9E%E6%BA%AF%E7%AE%97%E6%B3%95%E8%AF%A6%E8%A7%A3%E4%BF%AE%E8%AE%A2%E7%89%88.md)

code:

```c++
class Solution {
public:
    vector<vector<string>> res;
    vector<vector<string>> solveNQueens(int n) {
        vector<string> board(n, string(n,'.'));
        backtrack(board, 0);
        return res;
    }
    
    void backtrack(vector<string>& board, int row){
        // 触发结束条件
        if (row == board.size()){
            res.push_back(board);
            return;
        }
        int n = board[row].size();
        for (int col = 0; col < n; col++){
            if (!isValid(board, row, col))
                continue;
            board[row][col] = 'Q';
            backtrack(board, row+1);
            board[row][col] = '.';
        }
    }
    
    // check if conflict
    bool isValid(vector<string>& board, int row, int col){
        int N = board.size();
        // check column first
        for (int i = 0; i < N; i++) {
            if (board[i][col] == 'Q')
                return false;
        }
        // check 右上方
        for (int i = row - 1, j = col + 1; i>=0 && j<N; i--, j++) {
            if (board[i][j] == 'Q')
                return false;
        }
        // check 左上方
        for (int i = row - 1, j = col - 1; i>=0 && j>=0; i--, j--){
            if (board[i][j] == 'Q')
                return false;
        }
        return true;
    }
};
```

