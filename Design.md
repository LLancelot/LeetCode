# Design 

## [208. Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/)

#### 题目

Implement a trie with `insert`, `search`, and `startsWith` methods.

**Example:**

```
Trie trie = new Trie();

trie.insert("apple");
trie.search("apple");   // returns true
trie.search("app");     // returns false
trie.startsWith("app"); // returns true
trie.insert("app");   
trie.search("app");     // returns true
```

#### 代码

- ```init()``` create a empty dictionary, which can holds multiple dictionaries within the root.
- ```insert()``` for each char in word, add {ch: {}} into the root. "p" is like a pointer that switch to next dictionary.
- ```findPrefix()``` find if prefix exists in the Trie, for example, suppose you already insert "apple", if you find "app" that will return true.
- ```search()``` search for a whole word.
- ```startsWith()``` given a prefix, check the ```findPrefix()``` if is true.

```python
class Trie(object):

    def __init__(self):
        # use dictionary
        self.root = {}

    def insert(self, word):
        p = self.root
        for ch in word:
            if ch not in p:
                p[ch] = {}
            p = p[ch]
        # mark end char of a word with "#"    
        p["#"] = True
        
        
    def findPrefix(self, prefix):
        p = self.root
        for ch in prefix:
            if ch not in p:
                return None
            p = p[ch]
        return p

    def search(self, word):
        node = self.findPrefix(word)
        return node is not None and "#" in node
        

    def startsWith(self, prefix):
        return self.findPrefix(prefix) is not None
```

## [212. Word Search II](https://leetcode.com/problems/word-search-ii/)

#### 题目

Given a 2D board and a list of words from the dictionary, find all words in the board.

Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

 

**Example:**

```
Input: 
board = [
  ['o','a','a','n'],
  ['e','t','a','e'],
  ['i','h','k','r'],
  ['i','f','l','v']
]
words = ["oath","pea","eat","rain"]

Output: ["eat","oath"]
```

#### 代码

```python
class Trie(object):

    def __init__(self):
        # use dictionary
        self.root = {}

    def addWord(self, word):
        p = self.root
        for ch in word:
            if ch not in p:
                p[ch] = {}
            p = p[ch]
        # mark end char of a word with "#"    
        p["#"] = True
        
        
    def findPrefix(self, prefix):
        p = self.root
        for ch in prefix:
            if ch not in p:
                return None
            p = p[ch]
        return p

    def search(self, word):
        node = self.findPrefix(word)
        return node is not None and "#" in node


class Solution(object):
    def findWords(self, board, words):
        """
        :type board: List[List[str]]
        :type words: List[str]
        :rtype: List[str]
        """
        res = set()
        trie = Trie()
        for word in words:
            trie.addWord(word)
        
        # start dfs search
        for i in range(len(board)):
            for j in range(len(board[0])):
                self.dfs(board, trie.root, "", i, j, res)
        return list(res)
    
    def dfs(self, board, trie, word, i, j, res):
        if "#" in trie:
            res.add(word)
            trie["#"] = False
            
        if i < 0 or i >= len(board) or j < 0 or j >= len(board[0]) or not trie:
            return 
        
        char = board[i][j]
        if char in trie:
            board[i][j] = "."
            trie = trie[char]
            for dx, dy in [[-1, 0], [0, -1], [0, 1], [1, 0]]:
                self.dfs(board, trie, word + char, i+dx, j+dy, res)
            board[i][j] = char
  
```

