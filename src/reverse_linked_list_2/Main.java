package reverse_linked_list_2;

import default_struct.ListNode;

/**
 * User: wuruoye
 * Date: 2019-05-07 22:14
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        ListNode head = ListNode.fromArray(new int[] {1,2,3,4,5});
        int m = 1, n = 5;
        System.out.println(ListNode.toString(reverseBetween(head, m, n)));
    }

    public static ListNode reverseBetween(ListNode head, int m, int n) {
        int pos = 0;
        ListNode node = head, midHead = null, midTail = null, leftTail = null, last = null, next;

        while (node != null) {
            next = node.next;
            pos++;
            if (pos >= m && pos <= n) {
                if (midHead == null) {
                    midHead = midTail = node;
                    node.next = null;
                    if (last == null) head = node;
                    else last.next = node;
                    leftTail = last;
                } else {
                    node.next = midTail;
                    midTail = node;
                }
            } else if (midTail != null || next == null) {
                break;
            }
            last = node;
            node = next;
        }

        if (midHead == null) return head;

        midHead.next = node;
        if (leftTail != null) leftTail.next = midTail;
        else head = midTail;

        return head;
    }
}
