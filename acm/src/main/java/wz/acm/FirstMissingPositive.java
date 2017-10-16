package wz.acm;

/**
 * Created by wangz on 17-6-19.
 */
public class FirstMissingPositive {
    public static void main(String[] args) {
//        int[] nums = {1, 2, -3, 99};
//        int[] nums = {6, 3, 1, 4};
//        int[] nums = {1,2,0};
        int[] nums = {3, 4, 2, 5};
        System.out.println(firstMissingPositive2(nums));
    }

    public static int firstMissingPositive(int[] nums) {
        int len = nums.length;
        int min = 1;
        for (int i : nums) {
            if (i <= 0) {
                continue;
            }
            min = Math.min(i, min);
        }

        int[] newNums = new int[len];
        for (int i = 0; i < len; i++) {
            if (nums[i] <= 0) continue;
            int index = nums[i] - min;
            if (index > len - 1) continue;
            newNums[index] = nums[i];
        }

        for (int i = 0; i < len; i++) {
            if (newNums[i] == 0) {
                return i + min;
            }
        }

        return min + len;
    }

    public static int firstMissingPositive2(int[] A)
    {
        if(A.length==0||A==null)
            return 1;
        //把元素放入正确的位置，例如1放在A[0]，2放在A[1]...
        for(int i = 0;i<A.length;i++)
        {
             while(A[i]!=i+1)
            {
                if(A[i]>=A.length||A[i]<=0||A[i]==A[A[i]-1])
                    break;
                int temp = A[i];
                A[i] = A[temp-1];
                A[temp-1] = temp;
            }
        }

        for(int i = 0;i<A.length;i++)
        {
            if(A[i]!=i+1)
                return i+1;
        }
        return A.length+1;
    }

}
