package wangz.acm;

/**
 * Created by wangz on 17-6-12.
 */

/**
 * GOOD : ☆☆☆☆
 * Q ：
 *      1.交换相邻节点
 *      2.只能用一个变量空间
 * e.g :
 *      1->2->3->4  : 2->1->4->3
 * A ：
 *      1.不是简单的相邻两项调换位置，需要注意中间不要断掉，即 1->
 *      2.1->2调换后，1指向后续递归列表的返回的第一项
 *      3.依次递归
 *
 */
public class SwapPairs {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);

        l1.next = l2;
        l2.next = l3;
        l3.next = l4;

        l1 = swapPairs(l1);
        while(l1 != null){
            System.out.println(l1.val + " ");
            l1 = l1.next;
        }

    }

    //调换前两个的位置，并且，第二个的指向， 递归返回的第一个值
    public static ListNode swapPairs(ListNode head){
        if(head == null || head.next == null){
            return head;
        }

        ListNode temp = head.next;
        head.next = swapPairs(temp.next);
        temp.next = head;

        return temp;
    }
}
