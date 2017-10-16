package servlet;

import java.util.List;

/**
 * Created by wangz on 17-7-2.
 */
public enum TransferCollection {
    workflowInstances, tasks;

    static boolean contain(String type) {
        for (TransferCollection t : TransferCollection.values()) {
            if (type.equals(t.name())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(TransferCollection.contain("a"));
        System.out.println(TransferCollection.contain("tasks"));
    }
}
