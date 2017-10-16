package zkclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by wangz on 17-3-31.
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//需要序列化
public class User implements Serializable{
    String id;
    String name;

    public String toString() {
        return "id = " + id + ", name = " + name;
    }
}
