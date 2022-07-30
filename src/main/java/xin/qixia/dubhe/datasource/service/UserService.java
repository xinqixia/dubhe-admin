package xin.qixia.dubhe.datasource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Actrab
 * @since 2021-05-15
 */
public interface UserService extends IService<User> {
    Result isAccount(String appid, String account);
}
