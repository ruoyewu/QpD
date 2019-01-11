package container_with_most_water;

import java.util.Scanner;

/**
 * User: wuruoye
 * Date: 2018/6/26 10:54
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        while (n-- > 0) {
            int m = scanner.nextInt();
            int[] v = new int[m];
            for (int i = 0; i < m; i++) {
                v[i] = scanner.nextInt();
            }
            System.out.println(maxArea(v));
        }
    }

    public static int maxArea(int[] height) {
        int max = 0;
        int len = height.length;

        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                int w = j - i;
                int h = Math.min(height[i], height[j]);
                int area;
                if ((area = w * h) > max) {
                    max = area;
                }
            }
        }
        return max;
    }

    public static int maxArea2(int[] height) {
        int max = 0;
        int len = height.length;
        int l = 0, r = len - 1;

        while (l < r) {
            max = Math.max(max, Math.min(height[l], height[r]) * (r - l));
            if (height[l] < height[r]) {
                l++;
            }else {
                r--;
            }
        }

        return max;
    }
}
