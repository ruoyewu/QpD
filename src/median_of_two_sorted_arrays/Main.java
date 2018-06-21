package median_of_two_sorted_arrays;

import java.util.Scanner;

/**
 * User: wuruoye
 * Date: 2018/6/19 16:21
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[] n1 = new int[m];
        int[] n2 = new int[n];
        for (int i = 0; i < m; i++) {
            n1[i] = scanner.nextInt();
        }
        for (int i = 0; i < n; i++) {
            n2[i] = scanner.nextInt();
        }

        System.out.println(find(n1, n2));
    }

    public static double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) {
            return findMedianSortedArrays(B, A);
        }

        int min = 0, max = m, half = (m + n + 1) / 2;
        while (true) {
            int i = (min + max) / 2;
            int j = half - i;
            if (i < max && B[j - 1] > A[i]) {
                min = min + 1;
            }else if (i > min && A[i - 1] > B[j]) {
                max = max - 1;
            }else {
                int maxLeft;
                if (i == 0) {
                    maxLeft = B[j - 1];
                }else if (j == 0) {
                    maxLeft = A[i - 1];
                }else {
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                int minRight;
                if (i == m) {
                    minRight = B[j];
                }else if (j == n) {
                    minRight = A[i];
                }else {
                    minRight = Math.min(B[j], A[i]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
    }

    public static double find(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) {
            return find(B, A);
        }

        int time = 0;
        int min = 0, max = m, half = (m + n + 1) / 2;
        while (min <= max) {
            System.out.println(++time);
            int i = (min + max) / 2;
            int j = half - i;
            if (i < max && B[j - 1] > A[i]) {
                min ++;
            }else if (i > min && A[i - 1] > B[j]) {
                max --;
            }else {
                int left;
                if (i == 0) {
                    left = B[j - 1];
                }else if (j == 0) {
                    left = A[i - 1];
                }else {
                    left = Math.max(A[i - 1], B[j - 1]);
                }

                if ((m + n) % 2 == 1) {
                    return left;
                }

                int right;
                if (i == m) {
                    right = B[j];
                }else if (j == n) {
                    right = A[i];
                }else {
                    right = Math.min(A[i], B[j]);
                }

                return (left + right) / 2.0;
            }
        }
        return -1;
    }

//    public static double find(int[] A, int[] B) {
//        int m = A.length;
//        int n = B.length;
//        if (m > n) {
//            return find(B, A);
//        }
//
//        int min = 0, max = m, half = (m + n + 1) / 2;
//        int i = (min + max) / 2, j;
//        while (i >= min && i <= max) {
//            j = half - i;
//            if (i < max && B[j - 1] > A[i]) {
//                i++;
//            }else if (i >= min && A[i - 1] > B[j]) {
//                i--;
//            }else {
//                int maxLeft;
//                if (i == 0) {
//                    maxLeft = B[j - 1];
//                }else if (j == 0) {
//                    maxLeft = A[i - 1];
//                }else {
//                    maxLeft = Math.max(B[j - 1], A[i - 1]);
//                }
//            }
//        }
//    }
}
