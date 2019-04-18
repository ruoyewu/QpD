package length_of_last_word;

/**
 * User: wuruoye
 * Date: 2019-04-18 16:12
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int lengthOfLastWord(String s) {
        if (s.length() == 0) return 0;
        int count = 0;
        int pos = s.length();
        while (pos > 0 && s.charAt(pos-1) == ' ') --pos;
        while (--pos >= 0) {
            if (s.charAt(pos) != ' ') {
                count++;
            } else {
                return count;
            }
        }
        return count;
    }
}
