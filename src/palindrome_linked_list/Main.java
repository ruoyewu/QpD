package palindrome_linked_list;

import java.util.LinkedList;

/**
 * User: wuruoye
 * Date: 2019-01-30 21:24
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(1);
//        head.next.next.next.next = new ListNode(1);
        System.out.println(isPalindrome2(head));
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        ListNode slow = head, fast = head.next;
        LinkedList<ListNode> list = new LinkedList<>();
        list.add(head);
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            list.add(slow);
        }

        slow = slow.next;
        if (fast.next != null) {
            slow = slow.next;
        }
        while (!list.isEmpty() && slow != null) {
            if (slow.val == list.getLast().val) {
                list.removeLast();
                slow = slow.next;
            } else {
                return false;
            }
        }

        return list.isEmpty();
    }

    public static boolean isPalindrome2(ListNode head) {
        if (head == null || head.next == null) return true;
        ListNode slow = head, fast = head.next, pre = null;
        while (true) {
            ListNode tmp = slow.next;
            slow.next = pre;
            pre = slow;
            slow = tmp;
            if (fast.next == null) {
                // 偶数个
                break;
            }
            if (fast.next.next == null) {
                // 奇数个
                slow = slow.next;
                break;
            }
            fast = fast.next.next;
        }

        while (pre != null && slow != null) {
            if (pre.val == slow.val) {
                pre = pre.next;
                slow = slow.next;
            } else {
                return false;
            }
        }

        return pre == slow;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
