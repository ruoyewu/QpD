package merge_two_sorted_lists;

import java.util.Scanner;

/**
 * User: wuruoye
 * Date: 2019/1/12 14:49
 * Description:
 */
public class Main {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sl1 = scanner.next();
        String sl2 = scanner.next();
        ListNode l1 = null, l2 = null, node = null;
        for (int i = 0; i < sl1.length(); i++) {
            int x = sl1.charAt(i) - '0';
            if (l1 == null) {
                l1 = new ListNode(x);
                node = l1;
            }else {
                node.next = new ListNode(x);
                node = node.next;
            }
        }
        for (int i = 0; i < sl2.length(); i++) {
            int x = sl2.charAt(i) - '0';
            if (l2 == null) {
                l2 = new ListNode(x);
                node = l2;
            }else {
                node.next = new ListNode(x);
                node = node.next;
            }
        }

        System.out.println(mergeTwoLists(l1, l2));
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = null, node = null, tmp = null;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                tmp = l1;
                l1 = l2;
                l2 = tmp;
            }
            if (head == null) {
                head = l1;
                l1 = l1.next;
                node = head;
            }else {
                node.next = l1;
                l1 = l1.next;
                node = node.next;
            }
        }

        if (l2 != null) {
            l1 = l2;
        }
        if (node != null) {
            node.next = l1;
        }else {
            head = l1;
        }
        return head;
    }
}
