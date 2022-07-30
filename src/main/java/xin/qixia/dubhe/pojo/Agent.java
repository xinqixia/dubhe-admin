package xin.qixia.dubhe.pojo;

import lombok.Data;

@Data
public class Agent {
    private String token;
    private String appid;
    private String card;
    private String name;
    private String username;
    private String password;
    private String [] agentPower; //代理授权的应用id
    private String email;
    private int num;
    private int type;
    private int time;
}
