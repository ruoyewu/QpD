package sort_list;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019/1/28 17:13
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static ListNode sortList2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode pre = head, slow = head.next, fast = head.next.next;
        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        pre.next = null;
        ListNode l1 = sortList2(head);
        ListNode l2 = sortList2(slow);
        return merge(l1, l2);
    }

    private static ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode head;
        if (l1.val < l2.val) {
            head = l1;
            l1 = l1.next;
        } else {
            head = l2;
            l2 = l2.next;
        }

        ListNode node = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                node.next = l1;
                l1 = l1.next;
            } else {
                node.next = l2;
                l2 = l2.next;
            }
            node = node.next;
        }
        if (l1 == null) {
            node.next = l2;
        } else {
            node.next = l1;
        }
        return head;
    }

    public ListNode sortList(ListNode head) {
        ListNode node = head;
        int cnt = 0;
        while (node != null) {
            cnt++;
            node = node.next;
        }

        int[] nums = new int[cnt];
        node = head;
        cnt = 0;
        while (node != null) {
            nums[cnt++] = node.val;
            node = node.next;
        }

        Arrays.sort(nums);
        node = head;
        cnt = 0;
        while (node != null) {
            node.val = nums[cnt++];
            node = node.next;
        }
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
