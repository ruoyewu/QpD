package swap_nodes_in_pairs;

import default_struct.ListNode;

/**
 * User: wuruoye
 * Date: 2019-03-15 20:18
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        swapPairs3(head);
    }

    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode pre = head, node = head.next, n, nn;
        head = node;
        pre.next = null;

        while (node != null) {
            n = node.next;
            node.next = pre;
            if (n == null || n.next == null) {
                pre.next = n;
                break;
            }
            nn = n.next;
            pre.next = nn;
            pre = n;
            node = nn;
        }

        return head;
    }

    public static ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode next = head.next, left = swapPairs2(head.next.next);
        next.next = head;
        head.next = left;
        return next;
    }

    public static ListNode swapPairs3(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode h, node, n = head.next, nn = head.next.next;
        h = n;
        h.next = head;
        node = head;
        head = nn;
        while (head != null) {
            n = head.next;
            if (n == null) {
                node.next = head;
                node = head;
                break;
            }
            nn = n.next;
            node.next = n;
            n.next = head;
            node = head;
            head = nn;
        }
        node.next = null;
        return h;
    }
}
