package construct_binary_tree_from_inorder_and_postorder_traversal;

import default_struct.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * User: wuruoye
 * Date: 2019-05-12 09:07
 * Description:
 */
public class Main {
    private static int inIdx = 0;
    private static int postIdx = 0;

    public static void main(String[] args) {
        int[] inorder = new int[]{9,3,15,20,7};
        int[] postorder = new int[]{9,15,7,20,3};
        System.out.println(buildTree2(inorder, postorder));
    }

    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return build(map, postorder,0, inorder.length-1,  0, postorder.length-1);
    }

    private static TreeNode build(Map<Integer, Integer> map,
                                  int[] postorder, int is, int ie, int ps, int pe) {
        if (ps > pe || is-ie != ps-pe) return null;

        int root = postorder[pe];
        TreeNode node = new TreeNode(root);
        int pos = map.get(root);
        node.left = build(map, postorder, is, pos-1, ps, pos-is+ps-1);
        node.right = build(map, postorder, pos+1, ie, pos-is+ps, pe-1);
        return node;
    }

    public static TreeNode buildTree2(int[] inorder, int[] postorder) {
        inIdx = inorder.length-1;
        postIdx = postorder.length-1;
        return build(inorder, postorder, Integer.MAX_VALUE);
    }

    private static TreeNode build(int[] inorder, int[] postorder, int rootVal) {
        if (postIdx < 0 || inorder[inIdx] == rootVal) return null;

        TreeNode root = new TreeNode(postorder[postIdx]);
        postIdx--;
        root.right = build(inorder, postorder, root.val);
        inIdx--;
        root.left = build(inorder, postorder, rootVal);
        return root;
    }
}
