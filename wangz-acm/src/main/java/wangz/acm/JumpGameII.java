package wangz.acm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangz on 17-6-23.
 */
public class JumpGameII {
    public static void main(String[] args) {
//        int[] nums = {2, 3, 1, 1, 4};
        int[] nums = {1,2,1,1,1};
        System.out.println(jump(nums));
    }

    /**
     * 每一的节点，在他的范围内，能确定下次跳能达到的最大边界reach，这次的reach也是下次的最小边界，也就是进入下一步的位置
     * 也可以看作树形层次结构，每一层的最小点代表上一层的最大点，这一层的最大点，是上一层遍历的结果max（index+nums[index]）
     * 最少几层能到达终点
     * @param nums
     * @return
     */
    public static int jump(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int reach = 0;
        int lastReach = 0;
        int step = 0;
        int index = 0;
        while (index < nums.length) {
            if (index > lastReach) {  //判断是否进入下一步
                lastReach = reach;
                step++;
            }

            reach = Math.max(reach, nums[index] + index);//查找当前跳跃的边界最大值
            index++;
        }

        return step;
    }


    //TLE : time limit
    public static int jump2(int[] nums) {
        if (nums == null || nums.length == 1) {
            return 0;
        }
        int len = nums.length;
        int i = 0;
        List<Integer> list = new ArrayList<>();
        list.add(nums[0]);
        while (i < len && nums[i] + i + 1 < len) {
            i = calculateNext(nums, i);
            list.add(nums[i]);
        }

        list.forEach(System.out::println);
        return list.size();
    }

    public static int calculateNext(int[] nums, int start) {
        int maxJumpIndex = start + 1;
        int ret = start + 1;
        for (int index = start + 1; index <= start + nums[start] && index < nums.length; index++) {
            if (index + nums[index] + 1 >= nums.length) {
                return index;
            }
            for (int nextIndex = index + 1; nextIndex <= index + nums[index] && nextIndex < nums.length; nextIndex++) {

                ret = (nextIndex + nums[nextIndex]) > maxJumpIndex ? index : ret;
                maxJumpIndex = Math.max(maxJumpIndex, nextIndex + nums[nextIndex]);
            }
        }

        return ret;
    }
}
