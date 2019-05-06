package remove_duplicates_from_sorted_list_2;

import default_struct.ListNode;

/**
 * User: wuruoye
 * Date: 2019-05-05 10:04
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        ListNode head = ListNode.fromArray(new int[] {1,1,1,2,3});
        System.out.println(ListNode.toString(deleteDuplicates(head)));
    }

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode node = head, result = new ListNode(0), cur = result;
        int last;

        while (node != null) {
            if (node.next == null || node.val != node.next.val) {
                cur = (cur.next = node);
                node = node.next;
                cur.next = null;
            } else {
                last = node.val;
                node = node.next;
                while (node != null && node.val == last) node = node.next;
            }
        }

        return result.next;
    }

    public static ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) return head;

        if (head.val == head.next.val) {
            int val = head.val;
            head = head.next.next;
            while (head != null && head.val == val) head = head.next;
            return deleteDuplicates2(head);
        } else {
            head.next = deleteDuplicates2(head.next);
            return head;
        }
    }
}
