package all_possible_full_binary_trees;

import default_struct.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-03-09 21:46
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        allPossibleFBT(7);
    }

    public static List<TreeNode> allPossibleFBT(int N) {
        List<TreeNode>[] dp = new List[N+1];
        dp[1] = Collections.singletonList(new TreeNode(0));
        for (int i = 3; i <= N; i += 2) {
            List<TreeNode> list = new ArrayList<>();
            for (int left = 1; left < i; left += 2) {
                int right = i - 1 - left;
                for (TreeNode nl : dp[left]) {
                    for (TreeNode nr : dp[right]) {
                        TreeNode node = new TreeNode(0);
                        node.left = nl;
                        node.right = nr;
                        list.add(node);
//                        node = new TreeNode(0);
//                        node.left = nr;
//                        node.right = nl;
//                        list.add(node);
                    }
                }
            }
            dp[i] = list;
        }
        return N % 2 == 1 ? dp[N] : new ArrayList<>();
    }
}
