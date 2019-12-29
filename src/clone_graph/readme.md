### Question

Given a reference of a node in a **connected** undirected graph, return a [**deep copy**](https://en.wikipedia.org/wiki/Object_copying#Deep_copy) (clone) of the graph. Each node in the graph contains a val (`int`) and a list (`List[Node]`) of its neighbors.

 

**Example:**

![img](https://assets.leetcode.com/uploads/2019/02/19/113_sample.png)

```
Input:
{"$id":"1","neighbors":[{"$id":"2","neighbors":[{"$ref":"1"},{"$id":"3","neighbors":[{"$ref":"2"},{"$id":"4","neighbors":[{"$ref":"3"},{"$ref":"1"}],"val":4}],"val":3}],"val":2},{"$ref":"4"}],"val":1}

Explanation:
Node 1's value is 1, and it has two neighbors: Node 2 and 4.
Node 2's value is 2, and it has two neighbors: Node 1 and 3.
Node 3's value is 3, and it has two neighbors: Node 2 and 4.
Node 4's value is 4, and it has two neighbors: Node 1 and 3.
```

 

**Note:**

1. The number of nodes will be between 1 and 100.
2. The undirected graph is a [simple graph](https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)#Simple_graph), which means no repeated edges and no self-loops in the graph.
3. Since the graph is undirected, if node *p* has node *q* as neighbor, then node *q* must have node *p* as neighbor too.
4. You must return the **copy of the given node** as a reference to the cloned graph.

### Solution

深拷贝一个图，即重新创建图的每一个结点，使新的结点组成一个相同结构的图。所以，根据拷贝的定义，每一个结点必然都有一个拷贝，它们一一对应，每一个原始结点的所有邻居都是原始结点，每一个拷贝结点的邻居都是拷贝结点。

从复制第一个结点开始，分为两步，首先根据原始结点创建一个新的结点，然后找出它的邻居结点，它的所有邻居结点是原始结点的邻居结点的复制，而这些结点有两种可能性：已经复制过了或者还没复制。

如果是已经复制过了，那直接将这个复制过的结点加入当前结点的邻居列表中即可，如果还没复制，那就需要先完成这个结点的复制，然后再将复制结点加入邻居列表。

那么当前的问题是如何判断一个结点是否完成复制。上面已经说到原始结点与复制结点一一对应，且复制结点的邻居与原始结点的邻居也是一一对应，所以可以根据这个对应关系，建立一个哈希表，以原始结点为键，复制结点为值，则判断是否存在某个键值对便可判断某个结点是否已经复制。

于是，最终代码如下：

```java
public static Node cloneGraph(Node node) {
    if (node == null) return null;
    HashMap<Node, Node> map = new HashMap<>();
    Node n = new Node(node.val, new ArrayList<>());
    map.put(node, n);
    clo(map, node, n);
    return n;
}
private static void clo(HashMap<Node, Node> map, Node root, Node node) {
    for (Node neighbor : root.neighbors) {
        if (!map.containsKey(neighbor)) {
            Node n = new Node(neighbor.val, new ArrayList<>());
            node.neighbors.add(n);
            map.put(neighbor, n);
            clo(map, neighbor, n);
        } else {
            node.neighbors.add(map.get(neighbor));
        }
    }
}
```

