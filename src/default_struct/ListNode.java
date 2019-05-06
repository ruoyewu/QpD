package default_struct;

/**
 * User: wuruoye
 * Date: 2019-03-15 20:19
 * Description:
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(int val) {
        this.val = val;
    }

    public static ListNode fromArray(int[] nums) {
        ListNode head = null, node = null;
        for (int num : nums) {
            if (node == null) {
                node = head = new ListNode(num);
            } else {
                node.next = new ListNode(num);
                node = node.next;
            }
        }
        return head;
    }

    public static String toString(ListNode head) {
        StringBuilder builder = new StringBuilder("[");
        ListNode node = head;
        while (node != null) {
            builder.append(node.val).append(",");
            node = node.next;
        }
        if (builder.length() > 1) builder.setCharAt(builder.length()-1, ']');
        else builder.append("]");
        return builder.toString();
    }
}
