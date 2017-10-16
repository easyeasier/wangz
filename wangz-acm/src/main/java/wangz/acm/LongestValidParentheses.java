package wangz.acm;

import java.util.Stack;

/**
 * Created by wangz on 17-6-14.
 */

/**
 * R : ☆☆☆☆☆
 * Q ：给定一段由 （ ） 组成的字符串。求合法配对的最长子串
 * E.G :
 *     1. )()(()  //out:2
 *     2. )()(()))  //out:6
 *     3. )()(((())(  //out:4
 * A:
 *      想清楚几个问题：
 *      1.）的情况需要重新计数
 *      2.（）的情况是连续的 left
 *      3.（（（）需要计算最后一个暂时不配对的（的位置index，后面可能有他的配对，可能没有；有就是2的情况，如果没有，就记录index起始的位置
 *   使用栈，将每一个（ 的位置压入栈。。
 *
 *
 * 注：
 *    1.对于（）配对的情况，注意进行是非的转换。  如 1 0 ， 或者 只记录（ 的位置
 */
public class LongestValidParentheses {
    public static void main(String[] args) {
//        String str = ")()(()"; // 2
//        String str = ")()(()))"; // 6
        String str = ")()(((())("; // 4

        System.out.println(longestValidParentheses(str));
    }

    /**
     * ")()(()"
     * 1.当“(” 左边没有“）”就说明连续合法的子串断裂了，需要重新计数
     * 2.在连续的最长子串中，配对的都应该被计算的，比如（）。但（（）就要比较两段的长度了。比如（）(()()
     * 3.使用stack
     */
    public static int longestValidParentheses(String parentheses) {
        Stack<Integer> stack = new Stack<>();
        int left = -1;
        int max = 0;
        for (int i = 0; i < parentheses.length(); i++) {
            char c = parentheses.charAt(i);
            if (c == '(') {             // (
                stack.push(i);
            } else if (stack.empty()) {  // )
                left = i;
            } else {
                stack.pop();
                if (stack.empty()) {     //  ()
                    max = Math.max(max, i - left);
                } else {                   //(()
                    max = Math.max(max, i - stack.peek());
                }
            }
        }

        return max;
    }

}
