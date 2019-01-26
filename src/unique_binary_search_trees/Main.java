package unique_binary_search_trees;

/**
 * User: wuruoye
 * Date: 2019/1/25 22:14
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(numTrees(3));
    }

    public static int numTrees(int n) {
        int[] trees = new int[n+1];
        trees[0] = 1;
        trees[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                trees[i] += trees[j] * trees[i-1-j];
            }
        }
        return trees[n];
    }

    public static int numTrees2(int n) {
        long tree = 1;
        for (int i = 0; i < n; i++) {
            tree = tree * 2 * (2 * i + 1) / (i + 2);
        }
        return (int) tree;
    }
}
