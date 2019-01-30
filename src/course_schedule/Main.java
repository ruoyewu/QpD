package course_schedule;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2019-01-30 09:53
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(canFinish2(5, new int[][]{{0, 2}, {0, 1},
                {1, 2}, {2, 3}, {2, 4}, {3, 4}}));
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        HashSet<Integer>[] sets = new HashSet[numCourses];
        for (int i = 0; i < numCourses; i++) {
            sets[i] = new HashSet<>();
        }

        for (int[] prerequisite : prerequisites) {
            int cur = prerequisite[0], pre = prerequisite[1];
            sets[cur].add(pre);
        }

        int[] removed = new int[numCourses];
        int pos = 0;
        for (int i = 0; i < numCourses; i++) {
            if (sets[i].size() == 0) {
                removed[pos++] = i;
                sets[i] = null;
            }
        }

        int i = 0;
        for (; i < pos; i++) {
            for (int j = 0; j < numCourses; j++) {
                if (sets[j] != null && sets[j].remove(removed[i])) {
                    if (sets[j].size() == 0) {
                        removed[pos++] = j;
                        sets[j] = null;
                    }
                }
            }
        }

        return pos == numCourses;
    }

    public static boolean canFinish2(int numCourses, int[][] prerequisites) {
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

    // 环判断
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

    // 逐步判断
    static class GraphNode {
        int parent;
        List<GraphNode> children;

        public GraphNode() {
            parent = 0;
            children = new ArrayList<>();
        }
    }
}
