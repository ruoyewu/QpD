package roman_to_integer;

/**
 * User: wuruoye
 * Date: 2019-03-03 15:47
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int romanToInt(String s) {
        int result = 0;
        int last = get(s.charAt(0)), cur;
        for (int i = 1; i < s.length(); i++) {
            cur = get(s.charAt(i));
            if (last >= cur) {
                result += last;
                last = cur;
            } else {
                result += cur - last;
                if (i < s.length()-1) {
                    last = get(s.charAt(++i));
                } else {
                    last = 0;
                }
            }
        }
        result += last;
        return result;
    }

    private static int get(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }

    public static int romanToInt2(String s) {
        int result = 0;
        for (int i = s.length()-1; i >= 0; i--) {
            switch (s.charAt(i)) {
                case 'I':
                    result += result < 5 ? 1 : -1;
                    break;
                case 'V':
                    result += 5;
                    break;
                case 'X':
                    result += result < 50 ? 10 : -10;
                    break;
                case 'L':
                    result += 50;
                    break;
                case 'C':
                    result += result < 500 ? 100 : -100;
                    break;
                case 'D':
                    result += 500;
                    break;
                case 'M':
                    result += 1000;
            }
        }
        return result;
    }
}
