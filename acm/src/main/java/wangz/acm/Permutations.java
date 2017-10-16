package wangz.acm;

import java.util.ArrayList;
import java.util.List;

/**
 * 求数组的全排列组合
 * <p>
 * Created by wangz on 17-6-25.
 */
public class Permutations {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        permute(nums).forEach(list -> {
            list.forEach(System.out::print);
            System.out.println();
        });
    }

    /**
     * 求全排列组合
     * e.g  [1,2,3]  --->>> [1,2,3],[1,3,2],[2,1,3]等六种组合
     * 采用回溯的方法，每次从0开始添加元素，直到长度==length  ；（如1,2,3）
     * 然后回溯，去除当前一个，存入下一个（如1,2） -->> (1,3) ;(1)  --> (2)
     *
     * @param nums 数组
     * @return
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return ret;
        }
        List<Integer> tempList = new ArrayList<>();
        backtrack(nums, ret, tempList);         //每一次递归，必须从0开始，因为只是0的位置不同，仍然需要添加到templist中
        return ret;
    }

    /**
     * 回溯，当tempList.length == nums.length时，表明当前一次排列已经完成，添加到ret中
     * 回溯到上次排列的位置，继续for循环，取下一个应该放入的位置
     *
     * @param nums
     * @param ret
     * @param tempList
     */
    private static void backtrack(int[] nums, List<List<Integer>> ret, List<Integer> tempList) {
        if (tempList.size() == nums.length) {
            //不能直接add(tempList),在回溯后会更改tempList的值。这也是保护 不可变类 常用的方式，同样，get的时候，可以返回副本予以保护
            //addAll是可以的，因为是添加的副本
            ret.add(new ArrayList<>(tempList));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (tempList.contains(nums[i])) {
                continue;
            }
            tempList.add(nums[i]);

            backtrack(nums, ret, tempList);
            tempList.remove(tempList.size() - 1);//当回溯回来时，去掉已经进行完的元素，进行下一次循环
        }
    }
}
