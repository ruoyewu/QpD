package gas_station;

public class Main {
    public static void main(String[] args) {
        System.out.println(Long.toString(527231, 16));
        System.out.println(Long.toString(2621567, 16));
        System.out.println(Long.toString(2621441, 16));

//        int[] gas = new int[]{1, 2, 3, 4, 5};
//        int[] cost = new int[]{3, 4, 5, 1, 2};
//        System.out.println(canCompleteCircuit(gas, cost));
    }

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int len = gas.length;
        int left;
        for (int i = 0; i < len; i++) {
            left = 0;
            for (int j = 0; j < len; j++) {
                int pos = (i + j) % len;
                left += gas[pos] - cost[pos];
                if (left < 0) {
                    break;
                }
                if (j == len-1) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int canCompleteCircuit2(int[] gas, int[] cost) {
        int len = gas.length, left = 0, result = -1;
        boolean isOk = false;
        for (int i = 0; i < len; i++) {
            gas[i] -= cost[i];
            if (gas[i] >= 0 && left == 0) {
                isOk = true;
                result = i;
            }
            left += gas[i];
            if (left < 0) {
                left = 0;
                isOk = false;
            }
        }

        if (!isOk) {
            return -1;
        } else {
            for (int i = 0; i < result; i++) {
                left += gas[i];
                if (left < 0) {
                    return -1;
                }
            }
        }

        return result;
    }
}
