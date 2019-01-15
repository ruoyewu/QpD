package trapping_rain_water;

import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019/1/15 12:39
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = {5,2,1,2,1,5};
    }

    public static int trap(int[] height) {
        int left = 0, right = 0, max = 0, block = 0;

        int maxR = 0;
        for (int i = 0; i < height.length; i++) {
            block += height[i];
            if (height[i] > maxR) {
                right += (height[i] - maxR) * (height.length - i);
                maxR = height[i];
            }
            if (height[i] > max) {
                max = height[i];
            }
        }

        int maxL = 0;
        for (int i = height.length-1; i >= 0; i--) {
            if (height[i] > maxL) {
                left += (height[i] - maxL) * (i + 1);
                maxL = height[i];
            }
        }

        return left + right - max * height.length - block;
    }

    public static int trap2(int[] height) {
        int trap = 0, cur = 0;
        Stack<Integer> stack = new Stack<>();
        while (cur < height.length) {
            while (!stack.empty() && height[cur] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.empty()) {
                    break;
                }
                if (height[stack.peek()] == height[top]) {
                    continue;
                }
                int dis = cur - stack.peek() - 1;
                int h = Math.min(height[cur], height[stack.peek()]) - height[top];
                trap += dis * h;
            }
            stack.push(cur++);
        }
        return trap;
    }

    public static int trap3(int[] height) {
        int trap = 0, maxL = 0, maxR = 0, left = 0, right = height.length-1;

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] > maxL) {
                    maxL = height[left];
                } else {
                    trap += maxL - height[left];
                }
                left++;
            } else {
                if (height[right] > maxR) {
                    maxR = height[right];
                } else {
                    trap += maxR - height[right];
                }
                right--;
            }
        }

        return trap;
    }
}
