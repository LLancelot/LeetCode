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

