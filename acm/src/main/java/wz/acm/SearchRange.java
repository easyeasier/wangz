package wz.acm;

/**
 * Created by wangz on 17-6-15.
 */
public class SearchRange {

    public static void main(String[] args) {
//        int[] nums = {5,7,7,8,8,10};
//        int[] nums = {1};
        int[] nums = {2,2};
//        int target = 8;
//        int target = 0;
        int target = 3;
        int[] ret = searchRange(nums,target);
        for(int r : ret){
            System.out.print(r + " ");
        }
    }

    /**
     * 5 7 7 8 8 10
     * 8
     */
    public static int[] searchRange(int[] nums, int target){
        int[] ret = {-1,-1};

        if(nums == null || nums.length == 0){
            return ret;
        }

        int left = 0, right = nums.length-1, mid;
        while(left <= right && nums[left] != target){     //8 8 10
            mid = (left + right)/2;
            if(nums[mid] < target){
                left = mid+1;
            }else if(nums[mid] == target){
                right=mid;
            }else{
                right = mid - 1;
            }
        }

        if(left > right || nums[left] != target){
            return ret;
        }

        ret[0] = left;
        right = nums.length -1;

        while(left <=right && nums[right] != target){
            mid = (left+right)/2 + 1;
            if(nums[mid] > target){
                right =mid -1;
            }else {
                left = mid;
            }
        }

        ret[1] = right;

        return ret;
    }
}
