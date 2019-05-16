package convert_sorted_list_to_binary_search_tree;

import default_struct.ListNode;
import default_struct.TreeNode;

/**
 * User: wuruoye
 * Date: 2019-05-16 21:26
 * Description:
 */
public class Main {
    private static ListNode h;

    public static void main(String[] args) {

    }

    public static TreeNode sortedListToBST(ListNode head) {
        ListNode node = head;
        int len = 0;
        while (node != null) {
            len++;
            node = node.next;
        }

        int[] nums = new int[len];
        int pos = 0;
        node = head;
        while (node != null) {
            nums[pos++] = node.val;
            node = node.next;
        }

        return toBST(nums, 0, len-1);
    }

    private static TreeNode toBST(int[] nums, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = toBST(nums, start, mid-1);
        node.right = toBST(nums, mid+1, end);
        return node;
    }

    public static TreeNode sortedListToBST2(ListNode head) {
        ListNode node = head;
        int len = 0;
        while (node != null) {
            len++;
            node = node.next;
        }

        h = head;
        return toBST(0, len-1);
    }

    private static TreeNode toBST(int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;

        TreeNode left = toBST(start, mid-1);
        TreeNode root = new TreeNode(h.val);
        h = h.next;
        TreeNode right = toBST(mid+1, end);

        root.left = left;
        root.right = right;
        return root;
    }
}
