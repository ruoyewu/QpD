package partition_list;

import default_struct.ListNode;

/**
 * User: wuruoye
 * Date: 2019-05-06 09:30
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        ListNode head = ListNode.fromArray(new int[] {1, 3, 5, 7, 9, 2, 4, 6, 8, 10});
        int x = 5;
        System.out.println(ListNode.toString(partition(head, x)));
    }

    public static ListNode partition(ListNode head, int x) {
        ListNode leftHead = null, rightHead = null, leftNode= null, rightNode = null;

        while (head != null) {
            if (head.val < x) {
                if (leftNode == null) leftHead = leftNode = head;
                else leftNode = (leftNode.next = head);
            } else {
                if (rightNode == null) rightHead = rightNode = head;
                else rightNode = (rightNode.next = head);
            }
            head = head.next;
        }

        if (rightNode != null) {
            rightNode.next = null;
        }
        if (leftNode != null) {
            leftNode.next = rightHead;
        }
        return leftHead == null ? rightHead : leftHead;
    }
}
