package reorder_list;

import com.sun.org.apache.bcel.internal.generic.LNEG;
import default_struct.ListNode;

import java.util.LinkedList;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        ListNode head = ListNode.fromArray(new int[] {1, 2, 3, 4, 5});
        new Main().reorderList3(head);
        System.out.println(ListNode.toString(head));
    }

    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        int count = 0;
        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head, next;
        while (cur != null) {
            count += 1;
            stack.push(cur);
            cur = cur.next;
        }

        cur = head;
        for (int i = 0; i < count / 2; i++) {
            next = cur.next;
            cur.next = stack.pop();
            cur.next.next = next;
            cur = next;
        }

        cur.next = null;
    }

    public void reorderList2(ListNode head) {
        if (head == null || head.next == null) return;

        LinkedList<ListNode> list = new LinkedList<>();
        ListNode cur = head, last = null;
        while (cur != null) {
            list.add(cur);
            cur = cur.next;
        }

        if (list.size() > 1) {
            cur = list.pollFirst();
            last = list.pollLast();
            cur.next = last;
        }
        while (list.size() > 1) {
            cur = list.pollFirst();
            last.next = cur;
            last = list.pollLast();
            cur.next = last;
        }
        if (list.size() > 0) {
            last.next = list.poll();
            last = last.next;
        }
        last.next = null;
    }

    public void reorderList3(ListNode head) {
        if (head == null || head.next == null) return;

        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode h2 = rev(slow), c1 = head, c2 = h2, n1, n2;
        while (c2.next != null) {
            n1 = c1.next;
            n2 = c2.next;
            c1.next = c2;
            c2.next = n1;
            c1 = n1;
            c2 = n2;
        }
    }

    public ListNode rev(ListNode head) {
        ListNode next = head.next, nn;
        if (next == null) return head;

        head.next = null;
        while (next != null) {
            nn = next.next;
            next.next = head;
            head = next;
            next = nn;
        }

        return head;
    }
}
