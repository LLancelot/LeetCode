## Trie 字符串统计

https://www.acwing.com/problem/content/description/837/

维护一个字符串集合，支持两种操作：

1. “I x”向集合中插入一个字符串x；
2. “Q x”询问一个字符串在集合中出现了多少次。

共有N个操作，输入的字符串总长度不超过 105105，字符串仅包含小写英文字母。

#### 输入格式

第一行包含整数N，表示操作数。

接下来N行，每行包含一个操作指令，指令为”I x”或”Q x”中的一种。

#### 输出格式

对于每个询问指令”Q x”，都要输出一个整数作为结果，表示x在集合中出现的次数。

每个结果占一行。

#### 数据范围

1≤N≤2∗104

#### 输入样例：

```
5
I abc
Q abc
Q ab
I ab
Q ab
```

#### 输出样例：

```
1
0
1
```

### 代码

```cpp
#include<iostream>
using namespace std;

const int N = 100010;
int son[N][26], cnt[N], idx; 
char str[N];

void insert(char str[])
{
    int p = 0;
    for (int i = 0; str[i]; i++) 
    {
        int u = str[i] - 'a';
        if (!son[p][u]) son[p][u] = ++ idx;
        p = son[p][u];
    }
    cnt[p] ++ ;
}
int query(char str[])
{
    int p = 0;
    for (int i = 0; str[i]; i++ )
    {
        int u = str[i] - 'a';
        if (!son[p][u]) return 0;
        p = son[p][u];
    }
    return cnt[p];
}
int main()
{
    int n;
    scanf("%d", &n);
    while (n --) 
    {
        char op[2];
        scanf("%s%s", op, str);
        if (op[0] == 'I') insert(str);
        else printf("%d\n", query(str));
    }
    return 0;
}
```

## 习题1：LC 208. Implement Trie (Prefix Tree)

https://leetcode.com/problems/implement-trie-prefix-tree/

mplement a trie with `insert`, `search`, and `startsWith` methods.

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

**Note:**

- You may assume that all inputs are consist of lowercase letters `a-z`.
- All inputs are guaranteed to be non-empty strings.

### 代码

```java
class Trie {
    int N = 100010;
    int[][] son;
    int[] cnt;
    int idx;
    String str;
    /** Initialize your data structure here. */
    public Trie() {
        son = new int[N][26];
        cnt = new int[N];
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        int p = 0;
        for (int i = 0; i < word.length(); i++) {
            int u = word.charAt(i) - 'a';
            if (son[p][u] == 0) son[p][u] = ++ idx;
            p = son[p][u];
        }
        cnt[p] ++;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        int p = 0;
        for (int i = 0; i < word.length(); i++) {
            int u = word.charAt(i) - 'a';
            if (son[p][u] == 0) return false;
            p = son[p][u];
        }
        return cnt[p] > 0;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        int p = 0;
        for (int i = 0; i < prefix.length(); i++) {
            int u = prefix.charAt(i) - 'a';
            if (son[p][u] == 0) return false;
            p = son[p][u];
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
```

## 习题2：LC 211. Design Add and Search Words Data Structure (带通配符'.'匹配)

https://leetcode.com/problems/design-add-and-search-words-data-structure/

Design a data structure that supports adding new words and finding if a string matches any previously added string.

Implement the `WordDictionary` class:

- `WordDictionary()` Initializes the object.
- `void addWord(word)` Adds `word` to the data structure, it can be matched later.
- `bool search(word)` Returns `true` if there is any string in the data structure that matches `word` or `false` otherwise. `word` may contain dots `'.'` where dots can be matched with any letter.

 

**Example:**

```
Input
["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
Output
[null,null,null,null,false,true,true,true]

Explanation
WordDictionary wordDictionary = new WordDictionary();
wordDictionary.addWord("bad");
wordDictionary.addWord("dad");
wordDictionary.addWord("mad");
wordDictionary.search("pad"); // return False
wordDictionary.search("bad"); // return True
wordDictionary.search(".ad"); // return True
wordDictionary.search("b.."); // return True
```

 

**Constraints:**

- `1 <= word.length <= 500`
- `word` in `addWord` consists lower-case English letters.
- `word` in `search` consist of `'.'` or lower-case English letters.
- At most `50000` calls will be made to `addWord` and `search`.

### 代码

```java
class WordDictionary {
    public class Node {
        boolean is_end;
        Node[] next;
        Node() {
            is_end = false;
            next = new Node[26];
            for (int i = 0; i < 26; i++) next[i] = null;
        }
    }
    /** Initialize your data structure here. */
    public Node root;
    
    public WordDictionary() {
        root = new Node();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        Node p = root;
        for (char c : word.toCharArray()) {
            int u = c - 'a';
            if (p.next[u] == null) p.next[u] = new Node();
            p = p.next[u];
        }
        p.is_end = true;
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return dfs(word, 0, root);   
    }
    
    public boolean dfs(String word, int k, Node node) {
        if (k == word.length()) return node.is_end;
        char cur = word.charAt(k);
        if (cur != '.') {
            if (node.next[cur - 'a'] != null) 
                return dfs(word, k + 1, node.next[cur - 'a']);
        }
        else {
            for (int i = 0; i < 26; i++) {
                if (node.next[i] != null)
                    if (dfs(word, k + 1, node.next[i]))
                        return true;
            }
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

## 习题3： LC 212. Word Search II

https://leetcode.com/problems/word-search-ii/

Given an `m x n` `board` of characters and a list of strings `words`, return *all words on the board*.

Each word must be constructed from letters of sequentially adjacent cells, where **adjacent cells** are horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/11/07/search1.jpg)

```
Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
Output: ["eat","oath"]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/11/07/search2.jpg)

```
Input: board = [["a","b"],["c","d"]], words = ["abcb"]
Output: []
```

 

**Constraints:**

- `m == board.length`
- `n == board[i].length`
- `1 <= m, n <= 12`
- `board[i][j]` is a lowercase English letter.
- `1 <= words.length <= 3 * 10^4`
- `1 <= words[i].length <= 10`
- `words[i]` consists of lowercase English letters.
- All the strings of `words` are unique.

### 代码

```java
class Solution {
    public class TrieNode {
        int id;
        TrieNode[] son;
        TrieNode() {
            id = -1;
            son = new TrieNode[26];
            for (int i = 0; i < 26; i++) son[i] = null;
        }
    }
    public TrieNode root;
    public Set<Integer> ids;
    public char[][] g;
    public int[] dirs = {-1, 0, 1, 0, -1};
    
    void insert(String word, int id) {
        // insert ith word into trie.
        TrieNode p = root;
        for (char c : word.toCharArray()) {
            int u = c - 'a';
            if (p.son[u] == null) p.son[u] = new TrieNode();
            p = p.son[u];
        }
        p.id = id;
    }
    
    void dfs(int x, int y, TrieNode p) {
        if (p.id != -1) ids.add(p.id);
        // backtrack
        char ch = g[x][y];
        g[x][y] = '.';
        for (int d = 0; d < 4; d++) {
            int xx = x + dirs[d], yy = y + dirs[d + 1];
            if (xx >= 0 && 
                xx < g.length && 
                yy >= 0 && 
                yy < g[0].length 
                && g[xx][yy] != '.') {
                int u = g[xx][yy] - 'a';
                if (p.son[u] != null)
                    dfs(xx, yy, p.son[u]);
            }
        }
        // recover
        g[x][y] = ch;
    }
    
    public List<String> findWords(char[][] board, String[] words) {
        g = board;
        root = new TrieNode();
        ids = new HashSet<>();
        // insert all words into trie
        int k = 0;
        for (String w : words) insert(w, k++);
        // traverse the board.
        int n, m;
        n = g.length; m = g[0].length;
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++) {
                int u = g[i][j] - 'a';
                if (root.son[u] != null) 
                    dfs(i, j, root.son[u]);
            }
        List<String> res = new ArrayList<>();
        for (Integer id : ids) res.add(words[id]);
        return res;
    }
}
```

