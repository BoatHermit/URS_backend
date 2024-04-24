package com.nju.urs.dao.mysql.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@TableName("school_major")
public class SchoolMajor implements Serializable {
    private int id;
    private Integer schoolId;
    private Integer majorId;
    private Integer provinceId;
    private Integer subject;
    private String majorCode;
    private String majorName;
    public String requirement;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolMajor target = (SchoolMajor) o;
        return this.id == target.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolId, majorId, provinceId);
    }

    // public static void main(String[] args) {
    //     HashMap<SchoolMajor, String> map = new HashMap<>();
    //
    //     SchoolMajor s1 = new SchoolMajor(1,1,"A", "100000");
    //     map.put(s1, "s1");
    //     System.out.println("s1" + s1.hashCode());
    //
    //     SchoolMajor s2 = new SchoolMajor(1,1,"A", "100000");
    //     if (map.containsKey(s2)) {
    //         System.out.println("!!!!!!!!!!!!!!");
    //     }
    //     System.out.println("s2" + s2.hashCode());
    //
    //     for (SchoolMajor s : map.keySet()) {
    //         System.out.println(map.get(s));
    //     }
    // }

}
