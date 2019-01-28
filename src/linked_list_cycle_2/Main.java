package linked_list_cycle_2;

import java.util.HashSet;

/**
 * User: wuruoye
 * Date: 2019/1/27 22:05
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static ListNode detectCycle(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return head;
            } else {
                set.add(head);
                head = head.next;
            }
        }

        return null;
    }

    public static ListNode detectCycle2(ListNode head) {
        if (head == null || head.next == null)
            return null;
        ListNode slow = head.next, fast = head.next.next;
        while (slow != fast) {
            if (fast == null || fast.next == null)
                return null;
            slow = slow.next;
            fast = fast.next.next;
        }

        while (head != slow) {
            head = head.next;
            slow = slow.next;
        }
        return slow;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
