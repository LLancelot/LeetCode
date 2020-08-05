# Design 

# [208. Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/)

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

# [212. Word Search II](https://leetcode.com/problems/word-search-ii/)

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

# [211. Add and Search Word - Data structure design](https://leetcode.com/problems/add-and-search-word-data-structure-design/)

####  题目

Design a data structure that supports the following two operations:

```
void addWord(word)
bool search(word)
```

search(word) can search a literal word or a regular expression string containing only letters `a-z` or `.`. A `.` means it can represent any one letter.

**Example:**

```
addWord("bad")
addWord("dad")
addWord("mad")
search("pad") -> false
search("bad") -> true
search(".ad") -> true
search("b..") -> true
```

#### 代码

- TrieNode 结构

```java
class WordDictionary {

    private class TrieNode {
        public TrieNode[] children;
        public boolean isWord;
        public String word;
        public TrieNode() {
            children = new TrieNode[26];
            isWord = false;
            word = "";
        }
    }
    
    private TrieNode root;
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new TrieNode();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node.children[index] == null) {
                // init that children
                node.children[index] = new TrieNode();
            }
            // move node to next level
            node = node.children[index];
        }
        // at the end, set isWord to true
        node.isWord = true;
        node.word = word;
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return find(word, root, 0);
    }
    
    public boolean find(String word, TrieNode node, int index) {
        if (node == null)   return false;
        if (index == word.length()) return node.isWord;
        if (word.charAt(index) == '.') {
            for (TrieNode temp : node.children) {
                if (find(word, temp, index + 1))
                    return true;
            }
        }
        else {
            int tempIndex = word.charAt(index) - 'a';
            TrieNode temp = node.children[tempIndex];
            
            return find(word, temp, index + 1);
        }
        return false;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
```

- HashMap 结构

  key 为单词长度，value 为相同长度的单词列表

```java
class WordDictionary {
    private Map<Integer, List<String>> map;
    private boolean cmp(String s1, String s2) {
        for(int i = 0; i < s1.length(); i++) {
            char ch1 = s1.charAt(i);
            char ch2 = s2.charAt(i);
            if(ch1 == '.')  continue;
            else if(ch1 != ch2) return false;
        }
        return true;
    }
    /** Initialize your data structure here. */
    public WordDictionary() {
        map = new HashMap<Integer, List<String>>();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        int len = word.length();
        List<String> list = map.getOrDefault(len, new ArrayList<String>());
        list.add(word);
        map.put(len, list);
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        int len = word.length();
        if(!map.containsKey(len))   return false;
        List<String> list = map.get(len);
        for(String str : list) {
            if(cmp(word, str))  return true;
        }
        return false;
    }
}
```

