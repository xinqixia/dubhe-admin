package xin.qixia.dubhe.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("app")
@ApiModel(value = "App对象", description = "")
public class App implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "应用ID")
    @TableField("appid")
    private String appid;

    @ApiModelProperty(value = "版本号")
    @TableField("released")
    private String released;

    @ApiModelProperty(value = "强制更新")
    @TableField("updated")
    private Integer updated;

    @ApiModelProperty(value = "在线人数")
    @TableField("onlined")
    private Integer onlined;

    @ApiModelProperty(value = "卡密前缀")
    @TableField("prefixed")
    private String prefixed;

    @ApiModelProperty(value = "检查间隔")
    @TableField("time")
    private int time;

    @ApiModelProperty(value = "应用名称")
    @TableField("app_name")
    private String appName;

}
