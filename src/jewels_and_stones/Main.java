package jewels_and_stones;

/**
 * User: wuruoye
 * Date: 2019-03-02 19:51
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int numJewelsInStones(String J, String S) {
        boolean[] contains = new boolean[128];
        for (int i = 0; i < J.length(); i++) {
            contains[J.charAt(i)] = true;
        }

        int num = 0;
        for (int i = 0; i < S.length(); i++) {
            if (contains[S.charAt(i)]) {
                num++;
            }
        }
        return num;
    }
}
