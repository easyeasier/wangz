package wz.acm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangz on 17-3-7.
 */
public class TwoSum {
    /**
     * 给定数组list和值target
     * 求两值相加等于target的坐标
     * 例：list=[3,4,5,6] target=7， result=[3,4]
     *
     */

    public static void main(String[] args) {
        int[] nums = {3, 2, 4};
        int target = 6;
        int[] ret = twoSum(nums, target);
        System.out.println(ret[0] + "  " + ret[1]);
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] ret = {-1, -1};
        Map<Integer, Integer> arrToMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int value = nums[i];
            if (arrToMap.get(target - value) != null && arrToMap.get(target - value) != i) {
                ret[1] = i;
                ret[0] = arrToMap.get(target - value);
                return ret;
            }else{
                arrToMap.put(nums[i], i);
            }
        }

        throw new RuntimeException("no match fields");
    }
}
