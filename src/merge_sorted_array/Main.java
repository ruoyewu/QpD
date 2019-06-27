package merge_sorted_array;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] num1 = new int[]{-1,-1,0,0,0,0}, num2 = new int[]{-1,0};
        int m = 4, n = 2;
        merge(num1, m, num2, n);
        System.out.println(Arrays.toString(num1));
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) return;
        int p1 = 0;
        while (p1 < m) {
            if (nums1[p1] <= nums2[0]) {
                p1++;
            } else {
                int tmp = nums1[p1], p2 = 1;
                nums1[p1] = nums2[0];
                while (p2 < n && nums2[p2] < tmp) {
                    nums2[p2-1] = nums2[p2];
                    p2++;
                }
                nums2[p2-1] = tmp;
            }
        }

        for (int num : nums2) {
            nums1[p1++] = num;
        }
    }

    public static void merge2(int[] nums1, int m, int[] nums2, int n) {
        if (m > n) {
            merge2(nums2, n, nums1, m);
            return;
        }
        int[] nums3 = new int[m];
        System.arraycopy(nums1, 0, nums3, 0, m);
        int p3 = 0, p2 = 0, p1 = 0;
        while (p3 < m && p2 < n) {
            if (nums3[p3] <= nums2[p2]) {
                nums1[p1++] = nums3[p3++];
            } else {
                nums1[p1++] = nums2[p2++];
            }
        }

        while (p3 < m) {
            nums1[p1++] = nums3[p3++];
        }

        while (p2 < n) {
            nums1[p1++] = nums2[p2++];
        }
    }
}
