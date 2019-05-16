package convert_sorted_array_to_binary_search_tree;

import default_struct.TreeNode;

/**
 * User: wuruoye
 * Date: 2019-05-16 21:08
 * Description:
 */
public class Main {
    private static int pos = 0;

    public static void main(String[] args) {
        int[] nums = new int[]{-10,-3,0,5,9};
        TreeNode root = sortedArrayToBST(nums);
        System.out.println(TreeNode.toString(root));
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        return toBST(nums, 0, nums.length-1);
    }

    private static TreeNode toBST(int[] nums, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = toBST(nums, start, mid-1);
        node.right = toBST(nums, mid+1, end);
        return node;
    }

    private static TreeNode toBST2(int[] nums, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;

        TreeNode left = toBST2(nums, start, mid-1);
        TreeNode root = new TreeNode(nums[pos++]);
        TreeNode right = toBST(nums, mid+1, end);

        root.left = left;
        root.right = right;
        return root;
    }
}
