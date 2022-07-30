package xin.qixia.dubhe.datasource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.Admin;
import xin.qixia.dubhe.datasource.entity.AdminRole;
import xin.qixia.dubhe.datasource.mapper.AdminMapper;
import xin.qixia.dubhe.datasource.mapper.AdminRoleMapper;
import xin.qixia.dubhe.datasource.service.AdminRoleService;
import xin.qixia.dubhe.datasource.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xin.qixia.dubhe.pojo.Agent;
import xin.qixia.dubhe.utils.JwtUtils;
import xin.qixia.dubhe.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Actrab
 * @since 2021-05-21
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AdminRoleService adminRoleService;

    @Override
    public HashSet<String> getAgentList(String agent) {

        //获取所有下级代理
        List<Admin> adminList = this.list(null);
        HashMap<String, String> adminMap = new HashMap<>();
        //代理列表
        HashSet<String> agentList = new HashSet<>();
        agentList.add(agent);
        for (Admin item : adminList) {
            adminMap.put(item.getUsername(), item.getIdentity());
        }
        for (String key : adminMap.keySet()) {
            String keyOne = key;
            while (true) {
                //如果上级代理为当前代理 加入代理列表
                if (adminMap.get(key).equals(agent)) agentList.add(keyOne);
                //如果上级代理为作者 跳出循环
                if (key.equals("Actrab")) break;
                //继续寻找上级代理
                key = adminMap.get(key);
            }
        }

        return agentList;
    }

    @Override
    public Result getAgentList(int current) {
        Page<Admin> page=new Page<>(current,5);
        return Result.succ(this.page(page, new QueryWrapper<Admin>().ne("username", "admin")));
    }

    @Override
    public Result getAgentPower(String username) {
        return Result.succ(adminRoleMapper.getAgentPower(username));
    }

    @Override
    public Result AddAgent(Agent agent, HttpServletRequest request) {
        //获取用户信息
        String token = request.getHeader("Token");
        String viewAgent = JwtUtils.getString(token, "username");
        //权限认证
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.getPrincipals());
        if (!viewAgent.equals("admin")) return Result.fail("无权限！");
        //判断用户是否已存在
        int num=this.count(new QueryWrapper<Admin>().eq("username", agent.getUsername()));
        if(num>0) return Result.fail("用户名已存在");
        //注册代理
        Admin admin=new Admin();
        String salt = StringUtils.getRandomString(6);
        String md5Password = new Md5Hash(agent.getPassword(), salt, 1024).toString();
        admin.setEmail(agent.getEmail());
        admin.setUsername(agent.getUsername());
        admin.setPassword(md5Password);
        admin.setSalt(salt);
        admin.setIdentity(viewAgent);
        if(!this.save(admin)) return Result.fail("注册失败。请联系管理员");
        //代理授权
        for(String appId :agent.getAgentPower())
        {
            AdminRole adminRole = new AdminRole();
            adminRole.setUsername(agent.getUsername());
            adminRole.setAppid(appId);
            if(!adminRoleService.save(adminRole)) return Result.fail("注册失败。请联系管理员");
        }
        return Result.succ(null);
    }

    @Override
    public Result delAgent(String username) {
        //删除admin表
        Boolean delAdmin=this.remove(new QueryWrapper<Admin>().eq("username", username));
        //删除adminRole表
        Boolean delAdminRole=adminRoleService.remove(new QueryWrapper<AdminRole>().eq("username", username));
        if(delAdmin&&delAdminRole) return Result.succ(null);
        return Result.fail("删除失败,请联系管理员");
    }
}
