package xin.qixia.dubhe.datasource.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Actrab
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("card")
@ApiModel(value = "Card对象", description = "")
public class Card implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "主人帐号")
    @TableField("master")
    private String master;

    @ApiModelProperty(value = "绑定帐号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "应用ID")
    @TableField("appid")
    private String appid;

    @Excel(name = "card")
    @ApiModelProperty(value = "卡密")
    @TableField("card")
    private String card;


    @ApiModelProperty(value = "代理")
    @TableField("agent")
    private String agent;

    @Excel(name = "time",type = 10)
    @ApiModelProperty(value = "时间")
    @TableField("time")
    private Integer time;

    @Excel(name = "type",type = 10)
    @ApiModelProperty(value = "类型")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "是否使用")
    @TableField("used")
    private Integer used;

    @ApiModelProperty(value = "到期时间")
    @TableField("expiration")
    private String expiration;

    @Excel(name = "used_time")
    @ApiModelProperty(value = "开始使用时间")
    @TableField("used_time")
    private String usedTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    @ApiModelProperty(value = "乐观锁")
    @TableField("version")
    @Version
    private Integer version;

    @ApiModelProperty(value = "逻辑删除")
    @TableField("deleted")
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;


}
