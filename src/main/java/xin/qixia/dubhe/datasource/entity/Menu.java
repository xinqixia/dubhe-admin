package xin.qixia.dubhe.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author liHeWei
 * @since 2022-01-19
 */
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级菜单
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权标识。user：list。user：create
     */
    private String permission;

    /**
     * 类型。目录：1：菜单。2：按钮。3：导航菜单
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 状态。0：正常。1：禁用
     */
    private Integer status;

    /**
     * 排序值，越小越靠前
     */
    private Integer sort;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
