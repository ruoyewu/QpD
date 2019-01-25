package largest_rectangle_in_histogram;

import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019/1/24 21:52
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] heights = {2,1,5,6,2,3};
        System.out.println(largestRectangleArea3(heights));
    }

    // 双重遍历法
    public static int largestRectangleArea(int[] heights) {
        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            int min = heights[i];
            for (int j = i; j >= 0; j--) {
                if (heights[j] < min) {
                    min = heights[j];
                }
                max = Math.max(max, min * (i-j+1));
            }
        }
        return max;
    }

    // 分治法
    public static int largestRectangleArea2(int[] heights) {
        return largest(heights, 0, heights.length-1);
    }

    public static int largest(int[] heights, int start, int end) {
        if (start > end) return 0;
        if (start == end) return heights[start];
        boolean sorted = true;
        int min = start;
        for (int i = start+1; i <= end; i++) {
            if (heights[i] < heights[i-1]) sorted = false;
            if (heights[i] < heights[min]) min = i;
        }

        if (sorted) {
            int max = heights[start];
            for (int i = start+1; i <= end; i++) {
                max = Math.max(max, heights[i] * (end - i + 1));
            }
            return max;
        }

        return Math.max(Math.max(largest(heights, start, min-1), largest(heights, min+1, end)),
                heights[min] * (end - start + 1));
    }

    // 栈
    public static int largestRectangleArea3(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int n = heights.length;
        int max = 0;
        for (int i = 0; i <= n; i++) {
            while (!stack.empty() && (i >= n || heights[stack.peek()] > heights[i])) {
                int top = stack.pop();
                int h = heights[top];
                int w = stack.empty() ? i : (i-stack.peek()-1);
                max = Math.max(max, h*w);
            }

            stack.push(i);
        }

        return max;
    }

    // 最长宽度法
    public static int largestRectangleArea4(int[] heights) {
        int n = heights.length;
        if (n == 0) return 0;
        int[] leftLess = new int[n], rightLess = new int[n];

        leftLess[0] = 0;
        for (int i = 1; i < n; i++) {
            int p = i-1;
            while (true) {
                if (p >= 0 && heights[i] <= heights[p]) {
                    p -= leftLess[p] + 1;
                } else {
                    leftLess[i] = i - p - 1;
                    break;
                }
            }
        }

        rightLess[n-1] = 0;
        for (int i = n-2; i >= 0; i--) {
            int p = i+1;
            while (true) {
                if (p < n && heights[i] <= heights[p]) {
                    p += rightLess[p] + 1;
                } else {
                    rightLess[i] = p - 1 - i;
                    break;
                }
            }
        }

        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, heights[i] * (leftLess[i] + rightLess[i] + 1));
        }
        return max;
    }
}
