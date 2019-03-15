package reverse_nodes_in_k_group;

import default_struct.ListNode;

/**
 * User: wuruoye
 * Date: 2019-03-15 22:20
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        reverseKGroup(head, 3);
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k == 1) return head;
        boolean first = true;
        ListNode pre = head, node, tmp;
        while (true) {
            node = head.next;
            pre.next = null;
            int pos = 1;
            while (node != null && pos < k) {
                tmp = node.next;
                node.next = pre;
                pre = node;
                node = tmp;
                pos++;
            }

            if (first && pos < k) {
                head = pre;
                first = false;
            } else {
                break;
            }
        }

        head.next = reverseKGroup(node, k);
        return pre;
    }

    public static ListNode reverseKGroup2(ListNode head, int k) {
        if (head == null || k == 1) return head;
        int pos = 1;
        ListNode node = head, left = null;
        while (node.next != null && pos < k) {
            node = node.next;
            pos++;
        }

        if (pos < k) return head;

        if (node.next != null) {
            left = reverseKGroup2(node.next, k);
            node.next = null;
        }

        node = reverser(head);
        head.next = left;
        return node;
    }

    private static ListNode reverser(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode pre = head, node = head.next, tmp;
        pre.next = null;
        while (node != null) {
            tmp = node.next;
            node.next = pre;
            pre = node;
            node = tmp;
        }
        return pre;
    }
}
