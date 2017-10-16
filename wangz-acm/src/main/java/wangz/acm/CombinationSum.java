package wangz.acm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangz on 17-6-18.
 */
public class CombinationSum {
    public static void main(String[] args) {
        int[] nums = {2, 3, 6, 7};
        List<List<Integer>> ret = combinationSum(nums, 7);
        for (List l : ret) {
            l.forEach(System.out::print);
            System.out.println();
        }
    }

    public static List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return ret;
        }

        Arrays.sort(nums);
        combination(nums, target, 0, new ArrayList<>(), ret);
        return ret;
    }

    //N-QUEENS问题，递归尝试每一种情况，当不符合时注意回退的位置和要继续的步奏

    public static void combination(int nums[], int target, int start, List<Integer> temp, List<List<Integer>> ret) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            ret.add(new ArrayList<>(temp));
        }

        for (int i = start; i < nums.length; i++) {
            if (i != start && nums[i] == nums[i - 1]) {
                continue;
            }

            temp.add(nums[i]);
//            combination(nums, target- nums[i], i+1, temp, ret); //当不允许当前节点重复的时候
            combination(nums, target - nums[i], i, temp, ret);
            temp.remove(temp.size() - 1);

        }

    }

}