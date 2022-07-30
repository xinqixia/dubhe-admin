package xin.qixia.dubhe.datasource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.Menu;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liHeWei
 * @since 2022-01-19
 */
public interface MenuService extends IService<Menu> {
    /*菜单查询*/
    Result getMenuList();
}
