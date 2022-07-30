package xin.qixia.dubhe.datasource.mapper;

import xin.qixia.dubhe.datasource.entity.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xin.qixia.dubhe.datasource.entity.App;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Actrab
 * @since 2021-05-21
 */
@Mapper
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    //根据用户名查询代理权限
    List<App> getAgentPower(String username);
}
