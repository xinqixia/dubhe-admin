package xin.qixia.dubhe.datasource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.User;
import xin.qixia.dubhe.datasource.mapper.UserMapper;
import xin.qixia.dubhe.datasource.service.UserService;
import xin.qixia.dubhe.utils.DateUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Actrab
 * @since 2021-05-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    //如果帐号存在且未到期 返回用户信息
    @Override
    public Result isAccount(String appid, String account) {
        //MD5加密
        String md5account = new Md5Hash(account + "." + appid).toString();
        //帐号是否存在且是否已经过期
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", md5account); //帐号
        queryWrapper.ge("expiration", DateUtils.toString(null)); //到期时间
        //查询记录
        User one = this.getOne(queryWrapper, false);

        Result result = new Result();
        result.setData(one);
        if (one == null) result.setCode(400);
        else result.setCode(200);

        return result;
    }
}
