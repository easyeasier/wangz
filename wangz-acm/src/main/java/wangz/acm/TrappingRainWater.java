package wangz.acm;

/**
 * Created by wangz on 17-6-21.
 */
public class TrappingRainWater {
    public static void main(String[] args) {
        int[] nums = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap(nums));
    }

    public static int trap(int[] nums){
        int len = nums.length;
        int left = 0,right = len -1;
        int secHigh = 0;
        int area = 0;
        while(left<right){
            if(nums[left]<nums[right]){
                secHigh = Math.max(secHigh,nums[left]);
                area += secHigh - nums[left];
                left++;
            }else{
                secHigh = Math.max(secHigh,nums[right]);
                area+= secHigh - nums[right];
                right--;
            }
        }

        return area;
    }
}
