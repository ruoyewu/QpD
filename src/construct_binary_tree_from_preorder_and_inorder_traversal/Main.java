package construct_binary_tree_from_preorder_and_inorder_traversal;

/**
 * User: wuruoye
 * Date: 2019/1/26 16:05
 * Description:
 */
public class Main {
    private static int preIdx = 0;
    private static int inIdx = 0;

    public static void main(String[] args) {
        buildTree2(new int[]{1,2,3}, new int[]{3,2,1});
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;
        return build(preorder, inorder, 0, 0, inorder.length-1);
    }

    public static TreeNode build(int[] preorder, int[] inorder, int sp, int si, int ei) {
        if (si == ei) {
            return new TreeNode(preorder[sp]);
        } else {
            TreeNode node = new TreeNode(preorder[sp]);
            int val = preorder[sp];
            int left;
            for (left = 0; left + si <= ei && inorder[si+left] != val; left++) ;
            if (left > 0) {
                node.left = build(preorder, inorder, sp+1, si, si+left-1);
            }
            if (left+si < ei) {
                node.right = build(preorder, inorder, sp+left+1, si+left+1, ei);
            }
            return node;
        }
    }

    public static TreeNode buildTree2(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0
                || preorder.length != inorder.length) {
            return null;
        }

        return build(preorder, inorder, Integer.MAX_VALUE);
    }

    private static TreeNode build(int[] preorder, int[] inorder, int rootVal) {
        if (preIdx >= preorder.length || inorder[inIdx] == rootVal) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[preIdx]);
        preIdx += 1;

        root.left = build(preorder, inorder, root.val);
        inIdx += 1;
        root.right = build(preorder, inorder, rootVal);

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
