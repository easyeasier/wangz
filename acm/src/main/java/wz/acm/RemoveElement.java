package wz.acm;

/**
 * Created by wangz on 17-6-14.
 */

/**
 * R：☆☆☆
 * Q：
 *      给定target，去掉数组中相同的值
 * A：
 *      index为后指针，i为前指针，遍历数组，当nums[i]！=target时，存放到index的位置，如果==targte，i++跳过
 */
public class RemoveElement {
    public static void main(String[] args) {
        int[] nums = {1,2,3,3};
        int len = removeElement(nums,4);
        System.out.println(len);
    }
    public static int removeElement(int[] nums, int val){
        int len = nums.length;
        int index = 0;
        for(int i = 0; i< len;i++){
            if(nums[i] == val){
                continue;
            }

            nums[index++] = nums[i];
        }
        return index;
    }
}
