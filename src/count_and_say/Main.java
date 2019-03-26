package count_and_say;

/**
 * User: wuruoye
 * Date: 2019-03-23 23:26
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(countAndSay(5));
    }
    
    public static String countAndSay(int n) {
        StringBuilder result = new StringBuilder();
        String tmp = "1";
        for (int i = 1; i < n; i++) {
            int count = 1;
            char num = tmp.charAt(0);
            for (int j = 1; j < tmp.length(); j++) {
                if (tmp.charAt(j) != tmp.charAt(j-1)) {
                    result.append(count).append(num);

                    count = 1;
                    num = tmp.charAt(j);
                } else {
                    count++;
                }
            }
            result.append(count).append(num);
            tmp = result.toString();
            result.setLength(0);
        }
        return tmp;
    }
}
