package wz.test.zookeeper.takemaster.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by wangz on 17-4-1.
 */

/**
 * zk节点数据
 */
@Getter
@Setter
public class RunningData implements Serializable{
    String id;
    String name;

    public RunningData() {
    }

    public RunningData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "id = " + id + ",name = " + name;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof RunningData)) return false;
        RunningData runningData = (RunningData) obj;

        if(runningData.getName() == null && this.getName() != null){
            return false;
        }else if(runningData.getName() != null && !runningData.getName().equals(this.getName())){
            return false;
        }

        if(runningData.getId() == null && this.getId() != null){
            return false;
        }else if(runningData.getId() != null && !runningData.getId().equals(this.getId())){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode(){
        int ret = this.id == null ? 0 : this.id.hashCode();
        ret = ret * 31 + this.getName() == null ? 0 : this.getName().hashCode();
        return ret;
    }

    public static void main(String[] args) {
        System.out.println();
    }

}
