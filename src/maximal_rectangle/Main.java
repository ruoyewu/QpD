package maximal_rectangle;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019/1/25 15:01
 * Description:
 */
public class Main {
    // 递归法
    private static int max = 0;

    public static void main(String[] args) {
        char[][] matrix = {{'0', '1'}, {'1', '0'}};

    }

    public static int maximalRectangle(char[][] matrix) {
        max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    max = Math.max(max, 1);
                    max(matrix, i, j, 1, 1);
                }
            }
        }
        return max;
    }

    public static void max(char[][] matrix, int i, int j, int w, int h) {
        //right
        if (j+w < matrix[0].length) {
            for (int k = 0; k < h; k++) {
                if (matrix[i+k][j+w] == '0') {
                    break;
                }
                if (k == h-1) {
                    max = Math.max(max, h * (w + 1));
                    max(matrix, i, j, w+1, h);
                }
            }
        }
        //down
        if (i+h < matrix.length) {
            for (int k = 0; k < w; k++) {
                if (matrix[i+h][j+k] == '0') {
                    break;
                }
                if (k == w-1) {
                    max = Math.max(max, (h + 1) * w);
                    max(matrix, i, j, w, h+1);
                }
            }
        }
    }

    // 利用 largest rectangle in histogram
    public static int maximalRectangle2(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return 0;
        int m = matrix.length, n = matrix[0].length;
        int[] heights = new int[n];
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    heights[j]++;
                } else {
                    heights[j] = 0;
                }
            }

            max = Math.max(max, largest(heights, 0, n-1));
        }

        return max;
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
            int max = heights[start] * (end - start + 1);
            for (int i = start+1; i <= end; i++) {
                max = Math.max(max, heights[i] * (end - i + 1));
            }
            return max;
        }

        return Math.max(Math.max(largest(heights, start, min-1), largest(heights, min+1, end)),
                heights[min] * (end - start + 1));
    }

    // 利用 largest rectangle in histogram 2
    public static int maximalRectangle4(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return 0;
        int m = matrix.length, n = matrix[0].length;
        int[] left = new int[n], right = new int[n], height = new int[n];
        Arrays.fill(right, n);

        int max = 0;
        for (int i = 0; i < m; i++) {
            int curLeft = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    height[j]++;
                    left[j] = Math.max(left[j], curLeft);
                } else {
                    height[j] = 0;
                    left[j] = 0;
                    curLeft = j + 1;
                }
            }

            int curRight = n;
            for (int j = n-1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    right[j] = Math.min(right[j], curRight);
                } else {
                    right[j] = n;
                    curRight = j;
                }
            }

            for (int j = 0; j < n; j++) {
                max = Math.max(max, (right[j] - left[j]) * height[j]);
            }
        }

        return max;
    }

    // dp
    public static int maximalRectangle3(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    matrix[i][j] = (char) (matrix[i][j-1] + 1);
                } else {
                    matrix[i][j] = '0';
                }
            }
        }

        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int min = matrix[i][j] - '0';
                if (min > 0) {
                    max = Math.max(max, min);
                    for (int k = i-1; k >= 0 && matrix[k][j] != '0'; k--) {
                        min = Math.min(min, matrix[k][j] - '0');
                        max = Math.max(max, min * (i-k+1));
                    }
                }
            }
        }

        return max;
    }
}
