package clone_graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
        public static void main(String[] args) {
        Node n1 = new Node(1, new ArrayList<>());
        Node n2 = new Node(2, new ArrayList<>());
        Node n3 = new Node(3, new ArrayList<>());
        Node n4 = new Node(4, new ArrayList<>());

        n1.neighbors.add(n2);
        n1.neighbors.add(n4);
        n2.neighbors.add(n1);
        n2.neighbors.add(n3);
        n3.neighbors.add(n2);
        n3.neighbors.add(n4);
        n4.neighbors.add(n1);
        n4.neighbors.add(n3);

        cloneGraph(n1);
    };

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

static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {}

        public Node(int _val,List<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
