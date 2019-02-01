### Question

Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the [definition of LCA on Wikipedia](https://en.wikipedia.org/wiki/Lowest_common_ancestor): “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow **a node to be a descendant of itself**).”

Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]

 

**Example 1:**

```
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.
```

**Example 2:**

```
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
```

 

**Note:**

-   All of the nodes' values will be unique.
-   p and q are different and both values will exist in the binary tree.

### Solution

#### S1:递归

求两个结点的最近的共同祖先，可以依次从下往上判断，从最低的一层开始，如果这个子树同时包含 p q 两个结点，那它就是两者最近的祖先。可以使用递归判断：

```java
private static TreeNode result;
public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    result = null;
    containNode(root, p, q);
    return result;
}
private static boolean containNode(TreeNode node, TreeNode p, TreeNode q) {
    if (node == null) return false;
    int left = containNode(node.left, p, q) ? 1 : 0;
    int right = containNode(node.right, p, q) ? 1 : 0;
    int mid = (node.val == q.val || node.val == p.val) ? 1 : 0;
    if (left + right + mid == 2 && result == null) {
        result = node;
    }
    return (left + right + mid) > 0;
}
```

#### S2:路径

对于二叉树的每一个结点，都有一条从根结点到这一结点的路径，两个有共同祖先的结点，必然有一段路径是重合的，求出这段重合的路径中的最后一个结点，就是它们的最近结点。要求根结点到某一结点的路径，可以使用递归，或者是后序遍历（非递归，使用非递归后序遍历的时候栈中存放的就是路径）。使用递归的话，就是先求出两个结点各自的路径，然后从头开始比较：

```java
private static boolean go;
public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    List<TreeNode> pathP = new ArrayList<>(), pathQ = new ArrayList<>();
    go = true;
    postOrder(pathP, root, p);
    go = true;
    postOrder(pathQ, root, q);
    TreeNode parent = null;
    int i = 0;
    while (i < pathP.size() && i < pathQ.size()) {
        if (pathP.get(i) == pathQ.get(i)) {
            parent = pathP.get(i);
            i++;
        } else {
            break;
        }
    }
    return parent;
}
private static void postOrder(List<TreeNode> path, TreeNode node, TreeNode search) {
    if (node != null && go) {
        if (node.val == search.val) {
            path.add(node);
            go = false;
            return;
        }
        path.add(node);
        postOrder(path, node.left, search);
        postOrder(path, node.right, search);
        if (go) {
            path.remove(path.size()-1);
        }
    }
}
```

如果使用非递归后序遍历的话，当我们找到一个结点之后，栈中存放的是根结点到这一点的路径，如果两个结点有共同祖先的话，共同祖先必然就在这条路径上，然后再从这条路径上依次倒退，看是否能到达另一个结点，以此来寻找二者的共同祖先。

```java
public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode pre = null, node, result = null;
    boolean findOne = false, first = true;
    stack.push(root);
    while (!stack.empty()) {
        node = stack.peek();
        if (!first && (node.right == null || pre == node.right)) {
            if (result == stack.pop() && findOne) {
                result = stack.peek();
            }
            pre = node;
        } else {
            if (first) {
                first = false;
            } else {
                node = node.right;
            }
            while (node != null) {
                stack.push(node);
                if (node == q || node == p) {
                    if (findOne) {
                        return result;
                    } else {
                        result = node;
                        findOne = true;
                    }
                }
                node = node.left;
            }
        }
    }
    return null;
}
```

result 在某一结点的路径上移动，从这条路径的最底层（也就是这个结点本身）一直可以移动到根结点，同时为了保证 result 是沿着这个结点的路径移动的，加了个`result == stack.pop()`的判断。