package wz.acm;

/**
 * Created by wangz on 17-3-10.
 * 问题：
 * 求两个有序数组的中值
 * 例：
 * nums1 = [1, 3] nums2 = [2] ，ans = 2.0
 * nums1 = [1, 2] nums2 = [3, 4] ans = 2+3/2 = 2.5
 */

public class MedianOfTwoSortedArrays {
    public static void main(String[] args) {
        int[] nums1 = {1,3,5,7};
        int[] nums2 = {2,4,6,8};
        System.out.println(findMedianSortedArrays(nums1,nums2));
    }
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        boolean isEven = (len1 + len2) % 2 == 0 ? true : false;
        int midIndex = (len1 + len2)/2;
        int[] newArr = new int[midIndex+1];
        int i = 0;
        int index1 = 0;
        int index2 = 0;
        while (i<(midIndex+1)) {
            if(index1 >= len1){
                newArr[i++] = nums2[index2++];
            }else if(index2 >= len2){
                newArr[i++] = nums1[index1++];
            }else {
                if(nums1[index1] > nums2[index2]){
                    newArr[i++] = nums2[index2++];
                }else{
                    newArr[i++] = nums1[index1++];
                }
            }
        }

        if(isEven){
           return (newArr[midIndex]+newArr[midIndex-1])/2.0;
        }else{
            return newArr[midIndex];
        }
    }
}
