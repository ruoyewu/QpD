package letter_combinations_of_a_phone_number;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * User: wuruoye
 * Date: 2019/1/11 17:32
 * Description:
 */
public class Main {
    public static final String[] INDEX = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String digits = scanner.next();

        List<String> result = letterCombinations(digits);

        for (String s : result) {
            System.out.println(s);
        }
    }

    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        List<String> temps = new ArrayList<>();
        List<List<Character>> indexes = new ArrayList<>();
        for (String i : INDEX) {
            List<Character> chs = new ArrayList<>();
            for (int j = 0; j < i.length(); j++) {
                chs.add(i.charAt(j));
            }
            indexes.add(chs);
        }

        //将字符串转化为数组
        int nums[] = new int[digits.length()];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = (int) digits.charAt(i) - 48 -2;
        }

        for (int num : nums) {
            if (result.size() == 0) {
                for (char c : indexes.get(num)) {
                    result.add("" + c);
                }
            } else {
                for (String s : result) {
                    for (char c : indexes.get(num)) {
                        temps.add(s + c);
                    }
                }
                result = temps;
                temps = new ArrayList<>();
            }
        }
        return result;
    }
}
