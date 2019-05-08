package restore_ip_addresses;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-05-08 10:42
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String s = "25525511135";
        System.out.println(restoreIpAddresses2(s));
    }

    public static List<String> restoreIpAddresses(String s) {
        int[] nums = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            nums[i] = s.charAt(i) - '0';
        }
        List<String> result = new ArrayList<>();
        restore(result, new StringBuilder(s.length()), nums, 0, 0);
        return result;
    }

    private static void restore(List<String> result,
                                StringBuilder builder, int[] nums, int pos, int len) {
        if (len == 4 && pos == nums.length)
            result.add(builder.toString().substring(0, builder.length()-1));
        if (len == 4 || pos == nums.length) return;

        int num = 0;
        for (int i = pos; i < pos + 3 && i < nums.length; i++) {
            num = num * 10 + nums[i];
            if (num < 256) {
                builder.append(num).append(".");
                restore(result, builder, nums, i+1, len+1);
                builder.setLength(builder.length() - (i - pos + 2));
            }
            if (num == 0) break;
        }
    }

    public static List<String> restoreIpAddresses2(String s) {
        List<String> result = new ArrayList<>();
        restore(result, new char[s.length()+4], s, 0, 0);
        return result;
    }

    private static void restore(List<String> result, char[] saved, String s, int pos, int len) {
        if (len == 4 && pos == s.length())
            result.add(new String(saved, 0, s.length()+3));
        if (len == 4 || pos == s.length()) return;

        int num = 0;
        for (int i = pos; i < pos+3 && i < s.length(); i++) {
            num = num * 10 + s.charAt(i) - '0';
            if (num < 256) {
                saved[len+i] = s.charAt(i);
                saved[len+i+1] = '.';
                restore(result, saved, s, i+1, len+1);
            }
            if (num == 0) break;
        }
    }
}
