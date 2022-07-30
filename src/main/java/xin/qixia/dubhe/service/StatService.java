package xin.qixia.dubhe.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.Admin;
import xin.qixia.dubhe.datasource.entity.Card;
import xin.qixia.dubhe.datasource.service.AdminService;
import xin.qixia.dubhe.datasource.service.CardService;
import xin.qixia.dubhe.pojo.VoAgentStat;
import xin.qixia.dubhe.utils.ArrayUtils;
import xin.qixia.dubhe.utils.DateUtils;
import xin.qixia.dubhe.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

@Service
public class StatService {
    @Autowired
    private CardService cardService;
    @Autowired
    private AdminService adminService;

    private static final int [] time=new int[]{0,24,720,2160,4320,8760,876000};  //对应卡类型的数组
    private static final int [] SinglePrice=new int[]{0,20,0,40,59,88};  //对应单Q卡价格的数组
    private static final int [] DoublePrice=new int[]{0,50,0,139,239,399};  //对应单Q卡价格的数组

    //周销量统计(统计每天不同类型的卡的销量)
    public Result weekStat() throws ParseException {
        ArrayList<ArrayList<Integer>> resultList=new ArrayList<>();
        Date date = new Date();
        Calendar monday = DateUtils.getWeek(date); //本周周一的日期
        Calendar tomorrow= DateUtils.getWeek(date);
        tomorrow.add(Calendar.DATE, 1);   //比tomorrow往后推一天的日期
        int num=0;
        while(num<7){
            ArrayList<Integer> arrayList=new ArrayList<>();
            for(int i=0;i<time.length-1;i++){
                arrayList.add(cardService.count(getQuery(DateUtils.toString(monday.getTime()),DateUtils.toString(tomorrow.getTime()),time[i],time[i+1],null,-1)));
            }
            monday.add(Calendar.DATE, 1);
            tomorrow.add(Calendar.DATE, 1);
            num++;
            resultList.add(arrayList);
        }
        return Result.succ(ArrayUtils.test(resultList));  //二维数组翻转

    }
    //代理周销量查询
    public Result agentStat() throws ParseException {
        List<Admin> adminList = adminService.list();
        List<VoAgentStat> agentStatList=new LinkedList<>();
        Date date = new Date();
        Calendar monday = DateUtils.getWeek(date); //本周周一的日期
        //monday.add(Calendar.DATE, 1);
        String nowDate=DateUtils.getNowAfterDate(date);
        for(Admin admin :adminList){
            VoAgentStat voAgentStat = new VoAgentStat();
            voAgentStat.setName(admin.getUsername());
            voAgentStat.setValue(cardService.count(getQuery(DateUtils.toString(monday.getTime()),nowDate,-1,-1, admin.getUsername(),-1)));
            agentStatList.add(voAgentStat);
        }
        return Result.succ(agentStatList);
    }
    //按照时间查询
    public Result timeStat(JSONObject jsonObject, HttpServletRequest request){
        String agent;
        List<Integer> singleList=new ArrayList<>(); //单Q结果
        List<Integer> doubleList=new ArrayList<>(); //多Q结果
        Map<String,Object> resultMap=new HashMap<>();
        if(jsonObject.getString("startTime").equals("")||jsonObject.getString("endTime").equals("")) return Result.fail("起至时间需填写完整");
        String startTime=jsonObject.getString("startTime");
        String endTime=jsonObject.getString("endTime");
        //传参如有代理就按代理查询，若没有就按登录的用户查询
        if(jsonObject.getString("agent").equals("")) {
            agent = JwtUtils.getString(request.getHeader("Token"), "username");
        }else agent= jsonObject.getString("agent");
        for(int i=0;i<time.length-1;i++){
            singleList.add(cardService.count(getQuery(startTime,endTime,time[i],time[i+1], agent,1)));
            doubleList.add(cardService.count(getQuery(startTime,endTime,time[i],time[i+1], agent,2)));
        }
        resultMap.put("singleSum", singleList);
        resultMap.put("doubleSum", doubleList);
        return Result.succ(resultMap);
    }
    //收入统计
    public Result incomeStat(JSONObject jsonObject,HttpServletRequest request) throws ParseException {
        List<Integer> SingleList=new LinkedList<>(); //单Q结果
        List<Integer> DoubleList=new LinkedList<>(); //多Q结果
        String beforeDate,afterDate;
        Date date=new Date();
        //判断是统计月收入还是天收入
        if(jsonObject.get("type").equals("day")){
            beforeDate=DateUtils.getNowBeforeDate(date);  //当天凌晨日期
            afterDate=DateUtils.getNowAfterDate(date);    //当天午夜日期
        }else {
            beforeDate=DateUtils.getMonth(date);  //当月第一天日期
            afterDate=DateUtils.getNowAfterDate(date);    //当天午夜日期
        }
        String agent;
        //传参如有代理就按代理查询，若没有就按登录的用户查询
        if(jsonObject.getString("agent").equals("")) {
            agent = JwtUtils.getString(request.getHeader("Token"), "username");
        }else agent= jsonObject.getString("agent");
        //查询单Q价格
        for(int i=0;i<time.length-1;i++){
            SingleList.add(cardService.count(getQuery(beforeDate,afterDate,time[i],time[i+1], agent,1))*SinglePrice[i]);
        }
        //查询多Q价格
        for(int i=0;i<time.length-1;i++){
            DoubleList.add(cardService.count(getQuery(beforeDate,afterDate,time[i],time[i+1], agent,2))*DoublePrice[i]);
        }
        //合并结果
        int singleSum=SingleList.stream().reduce(Integer::sum).orElse(0);
        int doublerSum=DoubleList.stream().reduce(Integer::sum).orElse(0);
        return Result.succ(singleSum+doublerSum);
    }

    /*
    * 构造查询条件
    * startDate 开始日期
    * endDate 结束日期
    * beforeTime 卡类型的前开区间
    * afterTime 卡类型的后闭区间
    * agent 代理名称
    * type 卡密类型 1:单Q 2:多Q
    * (24,720]即为月卡
    * */
    private QueryWrapper<Card> getQuery(String startDate,String endDate,int beforeTime,int afterTime,String agent,int type){
        QueryWrapper<Card> queryWrapper=new QueryWrapper<>();
        if(agent!=null) queryWrapper.eq("agent", agent);
        if(startDate!=null) queryWrapper.ge("used_time", startDate);
        if(endDate!=null) queryWrapper.le("used_time", endDate);
        if(beforeTime!=-1) queryWrapper.gt("time", beforeTime);
        if(afterTime!=-1) queryWrapper.le("time",afterTime);
        if(type!=-1) queryWrapper.eq("type", type);
        return queryWrapper;
    }
}
