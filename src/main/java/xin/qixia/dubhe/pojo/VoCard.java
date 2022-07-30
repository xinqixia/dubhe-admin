package xin.qixia.dubhe.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* 用于导出excel
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "card")
public class VoCard implements Serializable {
    @TableField(value = "card")
    @Excel(name = "卡密",width = 50)
    private String card;

    @TableField(value = "time")
    @Excel(name = "卡密类型",type = 10)
    private int time;

    @TableField(value = "type")
    @Excel(name = "类型",replace ={"单Q_1","多Q_2"} )
    private int type;

    @Excel(name = "开卡时间",width = 30)
    @TableField(value = "used_time")
    private String userTime;

    //价格
    @TableField(exist = false)
    @Excel(name = "价格")
    private int price=0;
}
