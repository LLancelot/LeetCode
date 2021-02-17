# Combination Sum

```python
class Solution:
    def combinationSum(self, arr: List[int], target: int) -> List[List[int]]:
        arr.sort()
        res = []
        self.dfs(arr, target, 0, [], res)
        return res

    def dfs(self, arr, target, index, temp, res):
        if target < 0:
            return
        if target == 0:
            # find one solution
            res.append(temp)
            return
        else:
            for i in range(index, len(arr)):
                self.dfs(arr, target-arr[i], i, temp + [arr[i]], res)
```

# Letter Combination of Phone Number

```python
phone = {'2': ['a', 'b', 'c'], '3' : ['d','e','f'], '4' : ['g','h','i'], '5' : ['j', 'k','l'], '6' : ['m','n', 'o'], '7' : ['p', 'q', 'r','s'], '8' : ['t','u','v'],        '9' : ['w','x','y','z']}
class Solution:
    def letterCombinations(self, digits: str) -> List[str]:
        if len(digits) == 0:
            return []
        res = []
        self.helper(digits, 0, [], res)
        return res
    
    def helper(self, digits, i, temp, res):
        if i == len(digits):
            res.append("".join(temp))
            return 
        btm = phone.get(digits[i])
        for j in range(len(btm)):
            temp.append(btm[j])
            self.helper(digits, i + 1, temp, res)
            temp.pop(-1)
```

# Permutation ②

```
Input: [1,1,2]
Output:
[
  [1,1,2],
  [1,2,1],
  [2,1,1]
]
```

### 代码

```python
class Solution:
    def permuteUnique(self, nums: List[int]) -> List[List[int]]:
        results = []
        nums.sort()
        self.helper(nums, [], results)
        return results
        
    def helper(self, nums, partial, results):
        if not nums:
            results.append(partial[:])
        else:
            for i, n in enumerate(nums):
                # 有重复的情况，加continue
                if i > 0 and nums[i-1]==nums[i]: continue
                partial.append(n)
                self.helper(nums[:i] + nums[i+1:], partial, results)
                partial.pop()
```

