package search_a_2d_matrix_2;

/**
 * User: wuruoye
 * Date: 2019-02-02 13:12
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) return false;
        int c = search(matrix[0], target);

        for (int j = c; j >= 0; j++) {
            // search i
            int start = 0, end = matrix.length-1;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (matrix[mid][j] < target) {
                    start = mid+1;
                } else if (matrix[mid][j] > target) {
                    end = mid-1;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private static int search(int[] nums, int target) {
        int start = 0, end = nums.length-1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] < target) {
                start = mid+1;
            } else if (nums[mid] > target) {
                end = mid-1;
            } else {
                return mid;
            }
        }
        return end;
    }

    public static boolean searchMatrix2(int[][] matrix, int target) {
        int[][] saved = new int[matrix.length][matrix[0].length];
        return search(matrix, 0, 0, target, saved);
    }

    private static boolean search(int[][] matrix, int i, int j, int target, int[][] saved) {
        if (i >= matrix.length || j >= matrix[0].length)
            return false;
        if (saved[i][j] != 0) {
            return saved[i][j] == 1;
        }
        boolean find = matrix[i][j] == target ||
                search(matrix, i+1, j, target, saved) || search(matrix, i, j+1, target, saved);
        saved[i][j] = find ? 1 : -1;
        return find;
    }

    public static boolean searchMatrix3(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) return false;
        int i = matrix.length-1, j = 0;
        while (i >= 0 && j < matrix[0].length) {
            if (matrix[i][j] > target) {
                i--;
            } else if (matrix[i][j] < target) {
                j++;
            } else {
                return true;
            }
        }
        return false;
    }
}
