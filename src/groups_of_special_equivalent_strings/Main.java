package groups_of_special_equivalent_strings;

/**
 * User: wuruoye
 * Date: 2019-03-09 21:28
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int numSpecialEquivGroups(String[] A) {
        boolean[] visited = new boolean[A.length];
        int num = 0;
        for (int i = 0; i < A.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                ++num;
                for (int j = i+1; j < A.length; j++) {
                    if (!visited[j]) {
                        if (equal(A[i], A[j])) {
                            visited[j] = true;
                        }
                    }
                }
            }
        }
        return num;
    }

    private static boolean equal(String a, String b) {
        if (a.length() != b.length()) return false;
        int[] counts1 = new int[128];
        int[] counts2 = new int[128];
        for (int i = 0; i < a.length(); i++) {
            counts1[a.charAt(i)]++;
            if (++i < a.length()) {
                counts2[a.charAt(i)]++;
            }
        }
        for (int i = 0; i < b.length(); i++) {
            if (counts1[b.charAt(i)]-- == 0) {
                return false;
            }
            if (++i < a.length() && counts2[b.charAt(i)]-- == 0) {
                return false;
            }
        }
        return true;
    }
}
