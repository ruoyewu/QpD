package reverse_linked_list;

import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019-01-29 21:52
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public ListNode reverseList(ListNode head) {
        ListNode last = null, node = head, next;
        while (node != null) {
            next = node.next;
            node.next = last;
            last = node;
            node = next;
        }
        return last;
    }

    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode node = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return node;
    }

    public ListNode reverseList3(ListNode head) {
        if (head == null || head.next == null) return head;
        Stack<ListNode> stack = new Stack<>();
        ListNode node = head;
        while (node != null) {
            stack.push(node);
            node = node.next;
        }

        head = stack.pop();
        node = head;
        while (!stack.empty()) {
            node.next = stack.pop();
        }
        node.next = null;
        return head;
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
