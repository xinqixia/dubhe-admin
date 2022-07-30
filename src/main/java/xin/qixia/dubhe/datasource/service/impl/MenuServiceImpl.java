package xin.qixia.dubhe.datasource.service.impl;

import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.Menu;
import xin.qixia.dubhe.datasource.mapper.MenuMapper;
import xin.qixia.dubhe.datasource.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liHeWei
 * @since 2022-01-19
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    public Result getMenuList() {
        List<Menu> list=this.list();
        return Result.succ(list);
    }

}
