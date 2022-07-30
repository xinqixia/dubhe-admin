package xin.qixia.dubhe.datasource.service;

import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import xin.qixia.dubhe.pojo.Agent;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Actrab
 * @since 2021-05-21
 */
public interface AdminService extends IService<Admin> {
    HashSet<String> getAgentList(String agent);
    /*获取所有代理列表*/
    Result getAgentList(int current);
    /*获取指定代理权限*/
    Result getAgentPower(String username);
    /*注册代理*/
    Result AddAgent(Agent agent, HttpServletRequest request);
    /*删除代理*/
    Result delAgent(String username);
}
