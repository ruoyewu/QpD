package remove_nth_node_from_end_of_list;

/**
 * User: wuruoye
 * Date: 2019/1/11 19:15
 * Description:
 */
public class Main {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {

    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode last = null, cur, first = head;

        for (int i = 0; i < n-1; i++) {
            first = first.next;
        }

        cur = head;
        while (first.next != null) {
            last = cur;
            cur = cur.next;
            first = first.next;
        }

        if (last == null) {
            head = cur.next;
        }else {
            last.next = cur.next;
        }
        return head;
    }
}
