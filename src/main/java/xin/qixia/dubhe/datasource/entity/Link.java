package xin.qixia.dubhe.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Actrab
 * @since 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("link")
@ApiModel(value="Link对象", description="")
public class Link implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("appid")
    private String appid;

    @TableField("name")
    private String name;

    @TableField("content")
    private String content;


}
