package wangz.acm;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by wangz on 17-6-13.
 */

/**
 * R：   ☆☆☆☆☆
 * Q：
 *      给定单链表 ： reverse k 个元素
 * e.g:
 *     1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8
 *     k = 3 时 ， ret = 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 7 ->8
 *     k = 4 时 ， ret = 4 -> 3 -> 2 -> 1 -> 8 -> 7 -> 6 -> 5
 * A：
 *     重要的有两点 ：1.给定的k个元素怎么反转 2.反转后怎么跟后面的链表链接上
 *     1.使用stack进行反转
 *     2.记录stack栈顶的next，就是下段链表反转的入口，使用递归，链接上各段
 */
public class ReverseKGroup {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        ListNode l6 = new ListNode(6);
        ListNode l7 = new ListNode(7);
        ListNode l8 = new ListNode(8);

        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        l6.next = l7;
        l7.next = l8;

        l1 = reverseKGroup(l1, 3);
        while(l1 != null){
            System.out.println(l1.val + " ");
            l1 = l1.next;
        }
        Set<Integer> sets = new HashSet<>();
    }

    public static ListNode reverseKGroup(ListNode head, int k){
        if(head == null){
            return null;
        }

        //使用栈取反转前k个元素
        Stack<ListNode> stack = new Stack<>();
        ListNode temp = head;

        while(stack.size()<k){ //如果当前迭代，不满足k个大小，则不用反转，直接返回
            if(temp == null){
                return head;
            }
            stack.push(temp);
            temp = temp.next;
        }

        head = stack.pop();  //栈顶元素为原来的最后一个元素，记录其next值，用于递归
        ListNode end = head.next;
        temp = head;
        while(!stack.empty()){
            temp.next = stack.pop();
            temp = temp.next;
        }

        temp.next = reverseKGroup(end, k);
        return head;  //返回反转后的最前节点
    }
}
