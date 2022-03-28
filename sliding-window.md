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

#### 思路：

- 用```left```和```right```来构建滑动窗口，用```counter```记录T中字符元素的个数，用```dic```记录循环中当前滑动窗口中字符元素的个数，用```matchNum```记录当前滑动窗口中，已经和T中匹配的字符个数（注意：如果T中有多个相同元素，在S中，当我们的滑动窗口里的匹配元素个数小于等于```counter[match_char]```时，才将```matchNum```+=1）

#### 代码及注释：

- Java

```java
class Solution {
    public String minWindow(String str, String pattern) {
        int winStart = 0, matched = 0, minLength = str.length() + 1, substrStart = 0;
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : pattern.toCharArray())
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);

        // extend windows [start, end]
        for (int end = 0; end < str.length(); end++) {
            char rightChar = str.charAt(end);
            if (freqMap.containsKey(rightChar)) {
                freqMap.put(rightChar, freqMap.get(rightChar) - 1);
                if (freqMap.get(rightChar) >= 0)
                    matched++;
            }

            while (matched == pattern.length()) {
                if (minLength > end - winStart + 1) {
                    minLength = end - winStart + 1;
                    substrStart = winStart;
                }

                char leftChar = str.charAt(winStart++);
                if (freqMap.containsKey(leftChar)) {
                    if (freqMap.get(leftChar) == 0)
                        matched--;
                    freqMap.put(leftChar, freqMap.get(leftChar) + 1);
                }

            }
        }
        return minLength > str.length() ? "" : str.substring(substrStart, substrStart + minLength);
    }
}
```



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

#### 代码2

```python
class Solution:
    def minWindow(self, s: str, pat: str) -> str:
        winStart, matched, minLength, substrStart = 0, 0, len(s) + 1, 0
        freq = defaultdict(int)
        for ch in pat:
            if ch not in pat:
                freq[ch] = 0
            freq[ch] += 1

        for end in range(len(s)):
            rch = s[end]
            if rch in freq:
                freq[rch] -= 1
                if freq[rch] >= 0:
                    matched += 1

            while matched == len(pat):
                if end - winStart + 1 < minLength:
                    minLength = end - winStart + 1
                    substrStart = winStart

                lch = s[winStart]
                winStart += 1
                if lch in freq:
                    if freq[lch] == 0:
                        matched -= 1
                    freq[lch] += 1

        return "" if minLength > len(s) else s[substrStart: substrStart + minLength]
```



## 3. Longest Substring Without Repeating Characters

> Given a string, find the length of the **longest substring** without repeating characters.
>
> **Example 1:**
>
> ```
> Input: "abcabcbb"
> Output: 3 
> Explanation: The answer is "abc", with the length of 3. 
> ```
>
> **Example 2:**
>
> ```
> Input: "bbbbb"
> Output: 1
> Explanation: The answer is "b", with the length of 1.
> ```
>
> **Example 3:**
>
> ```
> Input: "pwwkew"
> Output: 3
> Explanation: The answer is "wke", with the length of 3. 
>              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
> ```

### 代码

```java
class Solution {
    public int lengthOfLongestSubstring(String str) {
        int start = 0, maxLen = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0; end < str.length(); end++) {
            char rightChar = str.charAt(end);
            if (map.containsKey(rightChar)) {
                // move start next to duplicate char
                start = Math.max(start, map.get(rightChar) + 1);    
            }
            map.put(rightChar, end);
            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }
}
```

## 340. Longest Substring with At Most K Distinct Characters

https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/

Given a string `s` and an integer `k`, return *the length of the longest substring of* `s` *that contains at most* `k` ***distinct** characters*.

 

**Example 1:**

```
Input: s = "eceba", k = 2
Output: 3
Explanation: The substring is "ece" with length 3.
```

**Example 2:**

```
Input: s = "aa", k = 1
Output: 2
Explanation: The substring is "aa" with length 2.
```

### 代码

- 经典模板题，hash table + sliding window.

```python
class Solution(object):
    def lengthOfLongestSubstringKDistinct(self, s, k):
        if k == 0: return 0
        left, res = 0, 0
        dic = collections.defaultdict(int)
        for i, ch in enumerate(s):
            dic[ch] += 1
            if len(dic) <= k:
                res = max(res, i - left + 1)
                continue
            else:
                left_ch = s[left]
                dic[left_ch] -= 1
                if dic[left_ch] == 0:
                    dic.pop(left_ch)
                left += 1
        return res
```



## 30. Substring with Concatenation of All Words

> You are given a string, **s**, and a list of words, **words**, that are all of the same length. Find all starting indices of substring(s) in **s** that is a concatenation of each word in **words** exactly once and without any intervening characters.
>
>  
>
> **Example 1:**
>
> ```
> Input:
>   s = "barfoothefoobarman",
>   words = ["foo","bar"]
> Output: [0,9]
> Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively.
> The output order does not matter, returning [9,0] is fine too.
> ```
>
> **Example 2:**
>
> ```
> Input:
>   s = "wordgoodgoodgoodbestword",
>   words = ["word","good","best","word"]
> Output: []
> ```

模板题。

### 思路

This problem follows the **Sliding Window** pattern and has a lot of similarities with [Maximum Sum Subarray of Size K](https://www.educative.io/collection/page/5668639101419520/5671464854355968/5177043027230720/). We will keep track of all the words in a **HashMap** and try to match them in the given string. Here are the set of steps for our algorithm:

1. Keep the frequency of every word in a **HashMap**.
2. Starting from every index in the string, try to match all the words.
3. In each iteration, keep track of all the words that we have already seen in another **HashMap**.
4. If a word is not found or has a higher frequency than required, we can move on to the next character in the string.
5. Store the index if we have found all the words.

### 代码

```java
class Solution {
    public List<Integer> findSubstring(String str, String[] words) {
        Map<String, Integer> wordFreqMap = new HashMap<>();
        List<Integer> res = new ArrayList<Integer>();
        
        int wordsCount = words.length;
        if (wordsCount == 0)
            return res;
        
        int wordLength = words[0].length();
        if (str.length() < wordsCount * wordLength)
            return res;
        
        for (String word : words)
            wordFreqMap.put(word, wordFreqMap.getOrDefault(word, 0) + 1);
        
        for (int i = 0; i <= str.length() - wordsCount * wordLength; i++) { 
            //注意是：<=
            Map<String, Integer> wordsSeen = new HashMap<>();
            for (int j = 0; j < wordsCount; j++) {
                int nextWordIndex = i + j * wordLength;
                String word = str.substring(nextWordIndex, nextWordIndex + wordLength);
                
                if (!wordFreqMap.containsKey(word))
                    break;
                
                wordsSeen.put(word, wordsSeen.getOrDefault(word, 0) + 1);
                
                if (wordsSeen.get(word) > wordFreqMap.getOrDefault(word, 0))
                    break;
                
                if (j == wordsCount - 1) // end of iteration, add index to res
                    res.add(i);
            }
        }
        return res;
    }
}
```

