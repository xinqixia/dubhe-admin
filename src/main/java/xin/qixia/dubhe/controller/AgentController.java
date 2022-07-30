package xin.qixia.dubhe.controller;

import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.service.MenuService;
import xin.qixia.dubhe.pojo.Agent;
import xin.qixia.dubhe.datasource.entity.Admin;
import xin.qixia.dubhe.pojo.Api;
import xin.qixia.dubhe.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Controller
public class AgentController {

    @Autowired
    private AgentService service;
    @Autowired
    private MenuService menuService;

    //管理员登录
    @ResponseBody
    @PostMapping("login")
    public Result login(@RequestBody Admin user) {
        if (user.getUsername() == null) return Result.fail("用户名不能为空！");
        if (user.getPassword() == null) return Result.fail("密码不能为空！");
        return service.login(user);
    }

    //获取用户信息
    @ResponseBody
    @PostMapping("getInfo")
    public Result getInfo(@RequestBody Agent str) {
        if (str.getToken() == null) return Result.fail("Token不能为空！");
        return service.getInfo(str.getToken());
    }

    //退出登录
    @PostMapping("logout")
    @ResponseBody
    public Result logout() {
        return service.logout();
    }

    //查询卡密
    @PostMapping("/que")
    @ResponseBody
    public Result query(@RequestBody Api api, HttpServletRequest request) {
        return service.query(api, request);
    }

    //导出excel
    @PostMapping("/toExcel")
    public void toExcel(@RequestBody Api api, HttpServletRequest request, ModelMap modelMap, HttpServletResponse response) throws IOException {
        service.toExcel(api, request,response,modelMap);
    }

    //生成卡密
    @ResponseBody
    @PostMapping("/gen")
    public Result generate(@RequestBody Agent str, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        if (str.getAppid().equals("")) return Result.fail("请先选择应用");
        if (str.getNum() == 0) return Result.fail("数量不能为0！");
        if (str.getType() == 0) str.setType(1);
        if (str.getTime() == 0) str.setTime(24);
        return service.generate(str.getAppid(), str.getNum(), str.getType(), str.getTime(), request);
    }

    //添加应用
    @ResponseBody
    @PostMapping("/addApp")
    public Result addApp(@RequestBody Agent str, HttpServletRequest request) {
        return service.addApp(str.getName(), request);
    }

    //删除应用
    @ResponseBody
    @PostMapping("/delApp")
    public Result deleteApp(@RequestBody Agent str) {
        return service.deleteApp(str.getName());
    }

    //查询菜单列表
    @ResponseBody
    @GetMapping("/getMenuList")
    public Result getMenuList(){
        return menuService.getMenuList();
    }

    //上传头像
    @ResponseBody
    @RequestMapping("/upAvatar")
    public Result upAvatar(MultipartFile file, HttpServletRequest request){
        return service.upAvatar(file,request);
    }
}
