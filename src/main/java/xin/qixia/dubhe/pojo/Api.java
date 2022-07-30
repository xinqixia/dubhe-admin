package xin.qixia.dubhe.pojo;

import lombok.Data;

@Data
public class Api {
    private String appid;
    private String account;
    private String card;
    private String master;
    private String time;
    private String type;
    private String used;
    private String [] checkTime;
    private int current; //分页查询时的当前页
    private String name;
    private String agent;
}
