package default_struct;

/**
 * User: wuruoye
 * Date: 2019-02-25 13:36
 * Description:
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int x) {
        this.val = x;
    }

    @Override
    public String toString() {
        return val + " " +
                left + " " +
                right + " ";
    }
}
