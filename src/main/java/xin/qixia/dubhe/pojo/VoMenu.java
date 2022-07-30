package xin.qixia.dubhe.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VoMenu implements Serializable {
    private int id;
    private int parentId;
    private String name;
    private String icon;
    private String url;
    private int sort;
    private List<VoMenu> children;

}
