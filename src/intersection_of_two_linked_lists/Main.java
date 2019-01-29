package intersection_of_two_linked_lists;

/**
 * User: wuruoye
 * Date: 2019-01-29 10:33
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode node, firstInterNode = null;
        ListNode[] A, B;
        int cnt = 0;

        node = headA;
        while (node != null) {
            cnt++;
            node = node.next;
        }
        A = new ListNode[cnt];
        cnt = 0;
        node = headA;
        while (node != null) {
            A[cnt++] = node;
            node = node.next;
        }

        cnt = 0;
        node = headB;
        while (node != null) {
            cnt++;
            node = node.next;
        }
        B = new ListNode[cnt];
        cnt = 0;
        node = headB;
        while (node != null) {
            B[cnt++] = node;
            node = node.next;
        }

        int pA = A.length - 1, pB = B.length - 1;
        while (pA >= 0 && pB >= 0 && A[pA] == B[pB]) {
            firstInterNode = A[pA];
            pA--;
            pB--;
        }

        return firstInterNode;
    }

    public static ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode node = headA;
        while (node.next != null) {
            node = node.next;
        }
        node.next = headB;

        ListNode result = detectCycle(headA);
        node.next = null;
        return result;
    }

    private static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null)
            return null;
        ListNode slow = head.next, fast = head.next.next;
        while (slow != fast) {
            if (fast == null || fast.next == null)
                return null;
            slow = slow.next;
            fast = fast.next.next;
        }

        while (head != slow) {
            head = head.next;
            slow = slow.next;
        }
        return slow;
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
