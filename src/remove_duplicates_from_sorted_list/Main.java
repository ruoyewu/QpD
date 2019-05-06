package remove_duplicates_from_sorted_list;

import default_struct.ListNode;

/**
 * User: wuruoye
 * Date: 2019-05-06 09:47
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode node = head.next, cur = head;
        int last = head.val;
        while (node != null) {
            if (node.val != last) {
                cur = (cur.next = node);
            }
            last = node.val;
            node = node.next;
        }
        cur.next = null;
        return head;
    }

    public static ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode node = head;

        while (node.next != null) {
            if (node.val == node.next.val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }

        return head;
    }
}
