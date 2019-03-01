package merge_two_binary_trees;

import default_struct.TreeNode;

import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019-03-01 13:54
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

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

    public static TreeNode mergeTrees2(TreeNode t1, TreeNode t2) {
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
}
