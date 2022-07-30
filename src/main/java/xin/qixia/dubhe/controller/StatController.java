package xin.qixia.dubhe.controller;

import com.alibaba.fastjson.JSONObject;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
@RequestMapping("/stat")
public class StatController {
    @Autowired
    private StatService statService;
    //周销量统计
    @GetMapping("/weekStat")
    public Result weekStat() throws ParseException {
        return statService.weekStat();
    }
    //代理销量查询
    @GetMapping("/agentStat")
    public Result agentStat() throws ParseException {
        return statService.agentStat();
    }
    //按照时间查询
    @PostMapping("/timeStat")
    public Result timeStat(@RequestBody JSONObject jsonObject, HttpServletRequest request){
        return statService.timeStat(jsonObject,request);
    }
    //收入统计
    @PostMapping("/incomeStat")
    public Result incomeStat(@RequestBody JSONObject jsonObject,HttpServletRequest request) throws ParseException {
        return statService.incomeStat(jsonObject,request);
    }
}
