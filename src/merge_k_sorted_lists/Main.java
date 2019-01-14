package merge_k_sorted_lists;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2019/1/13 15:08
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
        ListNode[] lists = new ListNode[3];
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(5);
        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);
        ListNode l3 = new ListNode(2);
        l3.next = new ListNode(6);
//        l3.next.next = new ListNode(6);
        lists[0] = l1;
        lists[1] = l2;
        lists[2] = l3;
        ListNode res = mergeKLists2(lists);
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        ListNode head = null, node = null;

        while (true) {
            // find min node
            int min = -1;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null && (min == -1 || lists[min].val > lists[i].val)) {
                    min = i;
                }
            }
            if (min == -1) {
                break;
            }

            ListNode minN = lists[min];
            if (head == null) {
                head = minN;
                node = head;
            } else {
                node.next = minN;
                node = node.next;
            }
            lists[min] = minN.next;
        }

        return head;
    }

    public static void heapAdjust(ListNode[] list, int start, int end, Comparator<ListNode> com) {
        ListNode n = list[start];
        for (int i = 2 * start + 1; i <= end; i = i * 2 + 1) {
            if (i < end && com.compare(list[i], list[i+1]) > 0) {
                i ++;
            }
            if (com.compare(n, list[i]) <= 0) {
                break;
            }
            list[start] = list[i];
            start = i;
        }
        list[start] = n;
    }

    public static ListNode mergeKLists2(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        ListNode head = null, node = null;
        Comparator<ListNode> com = new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                if (o1 == null && o2 == null)
                    return 0;
                else if (o1 == null) {
                    return 1;
                } else if (o2 == null) {
                    return -1;
                } else {
                    return o1.val - o2.val;
                }
            }
        };
        for (int i = lists.length / 2; i >= 0; i--) {
            heapAdjust(lists, i, lists.length-1, com);
        }

        while (lists[0] != null) {
            ListNode n = lists[0];
            ListNode nn = n.next;
            if (head == null) {
                head = n;
                lists[0] = nn;
                node = head;
            }else {
                node.next = n;
                lists[0] = nn;
                node = node.next;
            }

            heapAdjust(lists, 0, lists.length-1, com);
        }
        return head;
    }

    public static ListNode mergeKLists3(ListNode[] lists) {
        List<ListNode> list = new ArrayList<>();
        for (int i = 0; i < lists.length; i++) {
            ListNode n = lists[i];
            while (n != null) {
                list.add(n);
                n = n.next;
            }
        }

        list.sort(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });

        ListNode head = null, node = null;
        for (int i = 0; i < list.size(); i++) {
            if (head == null) {
                head = list.get(i);
                node = head;
            }else {
                node.next = list.get(i);
                node = node.next;
            }
        }

        return head;
    }
}
