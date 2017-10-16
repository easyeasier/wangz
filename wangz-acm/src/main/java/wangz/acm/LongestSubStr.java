package wangz.acm;

/**
 * Created by wangz on 17-3-9.
 * 最长不重复字符子串
 * 例：
 * “abcabcbb" - > "abc"
 * "pwwkew" > "wke"
 * "tmmzuxt" -> mzuxt
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 思路 ： 1.取每一个字符，判断有没有重复
 * 2.如果有重复，重起始的下一个字符重新开始
 * 3.当剩余的字符创长度小于当前最大子串时，就不用继续了
 */
public class LongestSubStr {

    public static void main(String[] args) {
        String str = "tmmzuxt";
        System.out.println(lengthOfLongestSubstring(str));
    }

    public static int lengthOfLongestSubstring(String s) {
        int len = s.length();
        if (s == null || len == 0) {
            return 0;
        }

        if (len == 1) {
            return 1;
        }

        char[] chars = s.toCharArray();
        int maxLength = 1;
        Map<Character, Integer> charMap = new HashMap<>();
        charMap.put(chars[0], 0);
        int start = 0;
        int end = 1;
        while (end < len) {
            Integer repeatCharIndex = charMap.get(chars[end]);
            //从头到尾只用一个map。最大子串开始的前面字符可能会再后面有重复，但却不在最大子串内，需要相应的过滤掉。
            if (repeatCharIndex != null && repeatCharIndex >= start) {
                start = repeatCharIndex + 1;
                if ((len - start + 1) <= maxLength) {
                    break;
                }
            } else {
                maxLength = Math.max(maxLength, end -start + 1);
            }
            charMap.put(chars[end], end);
            end++;
        }
        return maxLength;
    }
}
