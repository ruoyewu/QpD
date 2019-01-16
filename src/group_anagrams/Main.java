package group_anagrams;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2019/1/15 19:20
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        groupAnagrams(strs);
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        List<String> left = new ArrayList<>(Arrays.asList(strs));
        int[] index = new int[128];
        int length = 0;

        while (!left.isEmpty()) {
            String s = left.remove(left.size()-1);
            length = s.length();
            List<String> list = new ArrayList<>();
            list.add(s);

            Arrays.fill(index, 0);
            for (int i = 0; i < s.length(); i++) {
                index[s.charAt(i)] = index[s.charAt(i)] + 1;
            }

            for (int i = 0; i < left.size(); ) {
                s = left.get(i);
                int[] n = new int[128];
                System.arraycopy(index, 0, n, 0, 128);
                if (check(n, length, s)) {
                    left.remove(i);
                    list.add(s);
                } else {
                    i++;
                }
            }
            result.add(list);
        }

        return result;
    }

    public static boolean check(int[] index, int length, String s) {
        if (s.length() == length) {
            for (int i = 0; i < s.length(); i++) {
                int l = index[s.charAt(i)];
                if (l > 0) {
                    index[s.charAt(i)] = l - 1;
                } else {
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    public static List<List<String>> groupAnagrams2(String[] strs) {
        int len = strs.length;
        int[] sums = new int[len];
        boolean[] visited = new boolean[len];
        int[] index = new int[128];
        List<List<String>> result = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            int sum = 0;
            for (int j = 0; j < strs[i].length(); j++) {
                sum += strs[i].charAt(j);
            }
            sums[i] = sum;
        }

        for (int i = 0; i < len; i++) {
            if (!visited[i]) {
                visited[i] = true;
                List<String> list = new ArrayList<>();
                String s= strs[i];
                list.add(s);
                Arrays.fill(index, 0);
                for (int j = 0; j < s.length(); j++) {
                    index[s.charAt(j)] = index[s.charAt(j)] + 1;
                }

                for (int j = 1; j < len; j++) {
                    if (!visited[j] && sums[i] == sums[j]) {
                        int[] n = new int[128];
                        System.arraycopy(index, 0, n, 0, 128);
                        if (check(n, s.length(), strs[j])) {
                            list.add(strs[j]);
                            visited[j] = true;
                        }
                    }
                }

                result.add(list);
            }
        }

        return result;
    }

    public static List<List<String>> groupAnagrams3(String[] strs) {
        if (strs.length == 0)
            return new ArrayList<>();

        Map<String, List<String>> map = new HashMap<>();
        int[] count = new int[26];
        for (String s : strs) {
            Arrays.fill(count, 0);

            for (int i = 0; i < s.length(); i++) {
                count[s.charAt(i) - 'a']++;
            }
            StringBuilder builder = new StringBuilder();
            for (int c : count) {
                builder.append("_").append(c);
            }
            String key = builder.toString();
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }

        return new ArrayList<>(map.values());
    }

    public static List<List<String>> groupAnagrams4(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] cs = s.toCharArray();
            Arrays.sort(cs);
            String key = new String(cs);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
