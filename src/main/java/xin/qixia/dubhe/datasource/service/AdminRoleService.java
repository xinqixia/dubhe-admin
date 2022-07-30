package xin.qixia.dubhe.datasource.service;

import com.alibaba.fastjson.JSONArray;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.AdminRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Actrab
 * @since 2021-05-21
 */
public interface AdminRoleService extends IService<AdminRole> {
    /*更改代理权限*/
    Result editPower(String username, JSONArray appIdArray);
}
