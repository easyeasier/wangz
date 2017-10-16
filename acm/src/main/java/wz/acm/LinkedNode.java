package wz.acm;


/**
 * Created by wangz on 17-3-7.
 */


public class LinkedNode {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        ListNode l11 = new ListNode(2);
        ListNode l12 = new ListNode(4);
        ListNode l13 = new ListNode(3);

        l11.next = l12;
        l12.next = l13;

        ListNode l21 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l23 = new ListNode(4);

        l21.next = l22;
        l22.next = l23;

        ListNode list = addTwoNumbers(l11, l21);
        while (list != null) {
            System.out.println(list.val);
            list = list.next;
        }
    }

    /**
     * 给定两个链表数组，逐项相加，形成一个新的链表。要求每项为单位数。当》10的时候，相后增位
     * @param l1
     * @param l2
     * @example [5] [5] -> [0,1];[1] [9,9] -> [0,0,1]
     * @thoughts 当一项为空时，不是简单的链接。需要判断是否进位，进位就要考略是否大于10
     * @todo 有优化空间
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        int[] tempSum = sum(l1.val, l2.val);
        ListNode headNode = new ListNode(tempSum[1]);
        ListNode currentNode = headNode;
        ListNode nextNode;
        ListNode l1Next = l1.next;
        ListNode l2Next = l2.next;
        while (true) {
            if (l1Next == null && l2Next != null) {
                tempSum = sum(tempSum[0], l2Next.val);
            } else if (l2Next == null && l1Next != null) {
                tempSum = sum(l1Next.val, tempSum[0]);
            } else if (l1Next == null && l2Next == null) {
                if (tempSum[0] > 0) {
                    currentNode.next = new ListNode(tempSum[0]);
                }
                break;
            }else {
                tempSum = sum(l1Next.val + tempSum[0], l2Next.val);
            }

            nextNode = new ListNode(tempSum[1]);
            currentNode.next = nextNode;
            currentNode = nextNode;
            l1Next = l1Next == null ? null : l1Next.next;
            l2Next = l2Next == null ? null :l2Next.next;
        }
        return headNode;
    }

    public static int[] sum(int i1, int i2) {
        int[] arr = {0, 0};
        int temp = i1 + i2;
        if (temp >= 10) {
            arr[0] = 1;
            arr[1] = temp % 10;
        } else {
            arr[1] = temp;
        }
        return arr;
    }
}
