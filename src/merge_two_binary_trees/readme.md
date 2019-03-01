### Question

Given two binary trees and imagine that when you put one of them to cover the other, some nodes of the two trees are overlapped while the others are not.

You need to merge them into a new binary tree. The merge rule is that if two nodes overlap, then sum node values up as the new value of the merged node. Otherwise, the NOT null node will be used as the node of new tree.

**Example 1:**

```
Input: 
	Tree 1                     Tree 2                  
          1                         2                             
         / \                       / \                            
        3   2                     1   3                        
       /                           \   \                      
      5                             4   7                  
Output: 
Merged tree:
	     3
	    / \
	   4   5
	  / \   \ 
	 5   4   7
```

 

**Note:** The merging process must start from the root nodes of both trees.

### Solution

合并两个二叉树，如果对应位置两棵树都有结点，那么将其结点值相加。这也是需要一个遍历：两棵树同步遍历，都有某个结点则值相加，再向下遍历；某个结点为 null 的时候则直接取另一结点。

递归实现如下：

```java
public static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
    if (t1 == null && t2 == null) {
        return null;
    } else if (t1 == null) {
        return t2;
    } else if (t2 == null) {
        return t1;
    } else {
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }
}
```

或者也可以采用非递归：

```java
public static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
    if (t1 == null) return t2;
    Stack<TreeNode> s1 = new Stack<>();
    Stack<TreeNode> s2 = new Stack<>();
    s1.push(t1);
    s2.push(t2);
    TreeNode n1, n2;
    while (!s1.empty()) {
        n1 = s1.pop();
        n2 = s2.pop();
        if (n1 == null || n2 == null) continue;
        n1.val += n2.val;
        if (n1.left == null) {
            n1.left = n2.left;
        } else {
            s1.push(n1.left);
            s2.push(n2.left);
        }
        if (n1.right == null) {
            n1.right = n2.right;
        } else {
            s1.push(n1.right);
            s2.push(n2.right);
        }
    }
    return t1;
}
```

与一般的非递归遍历类似，采用两个栈分别存放两棵树的结点，并始终保持同步。