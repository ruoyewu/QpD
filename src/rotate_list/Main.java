package rotate_list;

import default_struct.ListNode;

/**
 * User: wuruoye
 * Date: 2019-05-03 13:47
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        n1.next = n2;
        rotateRight(n1, 2);
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        int len = 1;
        ListNode node = head, tail;
        while (node.next != null) {
            node = node.next;
            len++;
        }
        tail = node;

        k = len - k % len;
        if (k == len) return head;

        ListNode newTail = head, newHead;
        int pos = 1;
        while (pos++ < k) {
            newTail = newTail.next;
        }

        newHead = newTail.next;
        newTail.next = null;
        tail.next = head;

        return newHead;
    }
}
