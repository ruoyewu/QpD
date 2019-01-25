package demo;

/**
 * User: wuruoye
 * Date: 2019/1/24 21:31
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        double sum = 1;
        for (int i = 2; i <= 10; i++) {
            sum *= (1f - 1f / Math.pow(i, 2));
        }
        System.out.println(sum);
    }
}
