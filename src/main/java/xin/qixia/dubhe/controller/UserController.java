package xin.qixia.dubhe.controller;

import com.alibaba.fastjson.JSONObject;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.Card;
import xin.qixia.dubhe.datasource.service.AdminRoleService;
import xin.qixia.dubhe.datasource.service.AdminService;
import xin.qixia.dubhe.datasource.service.AppService;
import xin.qixia.dubhe.datasource.service.CardService;
import xin.qixia.dubhe.pojo.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private CardService cardService;
    @Autowired
    private AppService appService;

    //获取代理的分页数据
    @GetMapping("/getAgentList")
    public Result getAgentList(int current){
        return adminService.getAgentList(current);
    }

    //获取指定代理应用权限
    @GetMapping("getAgentPower")
    public Result getAgentPower(String username){
        return adminService.getAgentPower(username);
    }

    //获取全部应用
    @GetMapping("/getAppId")
    public Result getAppId(){
        return Result.succ(appService.list(null));
    }

    //更改指定代理权限
    @PostMapping("/editPower")
    public Result editPower(@RequestBody JSONObject jsonObject){
        return  adminRoleService.editPower(jsonObject.getString("username"),jsonObject.getJSONArray("appIdArray"));
    }

    //注册代理
    @PostMapping("addAgent")
    public Result addAgent(@RequestBody Agent agent, HttpServletRequest request){
        return adminService.AddAgent(agent, request);
    }

    //删除代理
    @GetMapping("/delAgent")
    public Result delAgent(String username){
        return adminService.delAgent(username);
    }

    //批量删除卡密（逻辑）
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody  int[] batchIds){
        return cardService.batchDel(batchIds);
    }

    //删除卡密
    @PostMapping("/singleDel")
    public Result delCard(@RequestBody Card card) throws ParseException {
        return cardService.singleDel(card);
    }

}
