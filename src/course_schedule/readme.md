### Question

There are a total of *n* courses you have to take, labeled from `0` to `n-1`.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: `[0,1]`

Given the total number of courses and a list of prerequisite **pairs**, is it possible for you to finish all courses?

**Example 1:**

```
Input: 2, [[1,0]] 
Output: true
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0. So it is possible.
```

**Example 2:**

```
Input: 2, [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.
```

**Note:**

1.  The input prerequisites is a graph represented by **a list of edges**, not adjacency matrices. Read more about [how a graph is represented](https://www.khanacademy.org/computing/computer-science/algorithms/graph-representation/a/representing-graphs).
2.  You may assume that there are no duplicate edges in the input prerequisites.

### Solution

#### S1:拓扑排序

本题其实就是一个关于拓扑排序的题目，如果本题不是求在这种情况下是否能够修完所有课程，而是求这些课程的学习顺序，那就会完全变成一个拓扑排序，即本题是求这样一个图是否可以被拓扑排序。

那么我们只需要按照拓扑排序的方法对其操作一遍，如果能够得到这个序列，那肯定就能拓扑排序，否则就不能。所以下面的关键是拓扑排序：每次选择一个无前驱结点的结点，并将以这个结点为前驱结点的边都删去。

```java
static class GraphNode {
    int parent;
    List<GraphNode> children;
    public GraphNode() {
        parent = 0;
        children = new ArrayList<>();
    }
}
public static boolean canFinish2(int numCourses, int[][] prerequis
    GraphNode[] nodes = new GraphNode[numCourses];
    for (int i = 0; i < numCourses; i++) {
        nodes[i] = new GraphNode();
    }
    for (int[] pre : prerequisites) {
        GraphNode parent = nodes[pre[1]];
        GraphNode child = nodes[pre[0]];
        parent.children.add(child);
        child.parent++;
    }
    LinkedList<GraphNode> queue = new LinkedList<>();
    for (GraphNode node : nodes) {
        if (node.parent == 0) {
            queue.add(node);
        }
    }
    int cnt = 0;
    GraphNode node;
    while (!queue.isEmpty()) {
        cnt++;
        node = queue.removeFirst();
        for (GraphNode child : node.children) {
            if (child.parent-- == 1) {
                queue.add(child);
            }
        }
    }
    return cnt == numCourses;
}
```

如上，先根据输入数据创建一个图，然后对它进行拓扑排序，这里只计入可排序的结点数，那么当所有结点都可排序，那就是说有拓扑排序。

#### S2:找环路

关于拓扑排序的判断还有，如果一个图中没有环路，那么它就可以拓扑排序，所以，对一个图进行判断，判断它是否有环路，来看它是否可以拓扑排序。而判断环路的话可以使用深度优先遍历，使用递归函数：

```java
public static boolean canFinish3(int numCourses, int[][] prerequisites) {
    ArrayList<Integer>[] graph = new ArrayList[numCourses];
    for (int i = 0; i < numCourses; i++) {
        graph[i] = new ArrayList<>();
    }
    for (int[] pre : prerequisites) {
        graph[pre[1]].add(pre[0]);
    }
    boolean[] visited = new boolean[numCourses];
    boolean[] saved = new boolean[numCourses];
    for (int i = 0; i < numCourses; i++) {
        if (detectCycle(graph, i, visited, saved)) {
            return false;
        }
    }
    return true;
}
private static boolean detectCycle(List<Integer>[] graph, int i, boolean[] visited, boolean[] saved) {
    if (saved[i]) return true;
    if (visited[i]) return false;
    visited[i] = true;
    saved[i] = true;
    for (int child : graph[i]) {
        if (detectCycle(graph, child, visited, saved)) {
            return true;
        }
    }
    saved[i] = false;
    return false;
}
```

使用`visited`数组保存已访问过的结点，使用`saved`数组保存当前的路径，然后每次从一个未曾访问过的结点开始，通过保存当前走过的路径判断是否构成回路。