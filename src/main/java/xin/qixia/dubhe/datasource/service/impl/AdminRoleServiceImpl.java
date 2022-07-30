package xin.qixia.dubhe.datasource.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.AdminRole;
import xin.qixia.dubhe.datasource.mapper.AdminRoleMapper;
import xin.qixia.dubhe.datasource.service.AdminRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Actrab
 * @since 2021-05-21
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Override
    public Result editPower(String username, JSONArray appIdArray) {
        //删除原本授权
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("username", username));
        //更新授权
        for(Object appId :appIdArray)
        {
            AdminRole adminRole = new AdminRole();
            adminRole.setUsername(username);
            adminRole.setAppid((String) appId);
            this.save(adminRole);
        }
        return Result.succ(null);
    }
}
