package serialize_and_deserialize_binary_tree;

import java.util.LinkedList;

/**
 * User: wuruoye
 * Date: 2019-02-02 18:26
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(serialize(deserialize("[]")));
    }

    public static String serialize(TreeNode root) {
        StringBuilder builder = new StringBuilder();
        if (root != null) {
            builder.append(root.val);
            TreeNode node;
            LinkedList<TreeNode> list = new LinkedList<>();
            list.add(root);
            while (!list.isEmpty()) {
                node = list.removeFirst();
                if (node.left == null) {
                    builder.append(",null");
                } else {
                    builder.append(",").append(node.left.val);
                    list.add(node.left);
                }
                if (node.right == null) {
                    builder.append(",null");
                } else {
                    builder.append(",").append(node.right.val);
                    list.add(node.right);
                }
            }
        }
        return "[" + builder.toString() + "]";
    }

    public static TreeNode deserialize(String data) {
        data = data.substring(1, data.length()-1);
        String[] nums = data.split(",");
        if (nums.length == 0 || nums[0].isEmpty()) return null;
        TreeNode root = new TreeNode(Integer.valueOf(nums[0]));
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(root);
        TreeNode node;
        for (int i = 1; i < nums.length; ) {
            node = list.removeFirst();
            // left
            if (nums[i].equals("null")) {
                node.left = null;
            } else {
                int num = Integer.valueOf(nums[i]);
                node.left = new TreeNode(num);
                list.add(node.left);
            }
            i++;
            if (i < nums.length && nums[i].equals("null")) {
                node.right = null;
            } else {
                int num = Integer.valueOf(nums[i]);
                node.right = new TreeNode(num);
                list.add(node.right);
            }
            i++;
        }
        return root;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
