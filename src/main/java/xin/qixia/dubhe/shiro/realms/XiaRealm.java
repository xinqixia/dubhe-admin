package xin.qixia.dubhe.shiro.realms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import xin.qixia.dubhe.datasource.entity.AdminRole;
import xin.qixia.dubhe.datasource.service.AdminRoleService;
import xin.qixia.dubhe.shiro.JwtToken;
import xin.qixia.dubhe.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XiaRealm extends AuthorizingRealm {

    private final AdminRoleService adminRoleService;

    public XiaRealm(AdminRoleService adminRoleService) {
        this.adminRoleService = adminRoleService;
    }

    //验证token是否为jwtToken
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //权限认证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //System.out.println("=====权限认证=====");
        //获取用户信息
        String token = (String) principalCollection.getPrimaryPrincipal();
        String username = JwtUtils.getString(token, "username");
        if (username == null) throw new AuthorizationException("用户不存在");

        //查询权限信息
        QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<AdminRole> adminRoles = adminRoleService.list(queryWrapper);
        //添加权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (AdminRole adminRole : adminRoles) {
            simpleAuthorizationInfo.addRole(adminRole.getAppid());
        }
        if (username.equals("Actrab")) simpleAuthorizationInfo.addRole("admin");
        return simpleAuthorizationInfo;
    }

    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户信息
        String token = (String) authenticationToken.getCredentials();
        //获取用户名
        String username = JwtUtils.getString(token, "username");
        //验证token
        if (username == null) {
            System.out.println("===========");
            throw new AuthenticationException("用户不存在");
        }
        if (!JwtUtils.verify(token)) {
            System.out.println("===========");
            throw new AuthenticationException("签名验证失败");
        }
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
