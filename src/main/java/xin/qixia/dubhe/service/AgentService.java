package xin.qixia.dubhe.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.Admin;
import xin.qixia.dubhe.datasource.entity.AdminRole;
import xin.qixia.dubhe.datasource.entity.App;
import xin.qixia.dubhe.datasource.entity.Card;
import xin.qixia.dubhe.datasource.mapper.AdminMapper;
import xin.qixia.dubhe.datasource.mapper.VoCardMapper;
import xin.qixia.dubhe.datasource.service.AdminRoleService;
import xin.qixia.dubhe.datasource.service.AdminService;
import xin.qixia.dubhe.datasource.service.AppService;
import xin.qixia.dubhe.datasource.service.CardService;
import xin.qixia.dubhe.pojo.Api;
import xin.qixia.dubhe.pojo.VoCard;
import xin.qixia.dubhe.utils.ExcelUtils;
import xin.qixia.dubhe.utils.JwtUtils;
import xin.qixia.dubhe.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
@Slf4j
public class AgentService {

    @Value("${imgFile}")
    private String dirPath;  //图片上传保存地址

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private VoCardMapper voCardMapper;

    private final AdminService adminService;
    private final AppService appService;
    private final CardService cardService;
    private final AdminRoleService adminRoleService;

    public AgentService(AdminService adminService, AppService appService, CardService cardService, AdminRoleService adminRoleService) {
        this.adminService = adminService;
        this.appService = appService;
        this.cardService = cardService;
        this.adminRoleService = adminRoleService;
    }

    public Result login(Admin user) {
        //查询用户
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        Admin admin = adminService.getOne(queryWrapper);
        if (admin == null) return Result.fail("用户不存在！");
        //验证密码
        String md5Password = new Md5Hash(user.getPassword(), admin.getSalt(), 1024).toString();
        if (!md5Password.equals(admin.getPassword())) {
            return Result.fail("密码错误！");
        }
        //生成token
        HashMap<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        String jwt = JwtUtils.getToken(map);

        //返回token数据
        HashMap<String, String> token = new HashMap<>();
        token.put("token", jwt);
        return Result.succ(token);
    }

    public Result getInfo(String token) {
        String username = JwtUtils.getString(token, "username");
        Admin admin=adminService.getOne(new QueryWrapper<Admin>().eq("username", username));
        if (username == null) return Result.fail("token已失效!");
        //查询权限信息
        QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<AdminRole> adminRoles = adminRoleService.list(queryWrapper);

        ArrayList<String> list = new ArrayList<>();
        for (AdminRole adminRole : adminRoles) list.add(adminRole.getAppid());
        //返回结果
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", username);
        map.put("avatar", admin.getAvatar());
        map.put("avator",  list);
        map.put("email", admin.getEmail());
        return Result.succ(map);
    }

    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    public Result generate(String appid, int num, int type, int time, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        String token = request.getHeader("Token");
        String username = JwtUtils.getString(token, "username");
        QueryWrapper<Card> wrapper = new QueryWrapper<>();
        wrapper.eq("appid", appid);
        wrapper.eq("used", 0);
        wrapper.eq("type", type);
        wrapper.eq("time", time);
        wrapper.eq("agent", username);
        int count = cardService.count(wrapper);
        if (count + num > 500) return Result.fail("未使用的同类型卡密不能超过500个！");
        //获取卡密前缀
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("appid", appid);
        App one = appService.getOne(queryWrapper);
        if (one == null) return Result.fail("应用不存在！");
        String pre = one.getPrefixed();
        //生成卡密
        Card card = new Card();
        List<Card> cards = new LinkedList<>();
        card.setAppid(appid);
        card.setAgent(username);
        card.setTime(time);
        card.setType(type);
        for (int i = 0; i < num; i++) {
            card.setCard(StringUtils.getCard(pre, time, type));
            cardService.save(card);
            Card item = new Card();
            BeanUtils.copyProperties(item, card);
            cards.add(item);
        }
        return Result.succ(cards);
    }
    //导出为excel
    public void toExcel(Api card, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws IOException {
        ExcelUtils.exportExcel(getVoCard(card,request), "卡密列表", "卡密列表", VoCard.class, "卡密列表", response);
    }
    //分页查询卡密
    public Result query(Api card, HttpServletRequest request) {
        return cardService.getCardList(card.getCurrent(), getQuery(card,request,0));
    }
    //添加应用
    public Result addApp(String name, HttpServletRequest request) {
        App app = new App();
        String appid = Long.toString(new Date().getTime());
        app.setAppid(appid);
        app.setTime(4);
        app.setOnlined(0);
        app.setReleased("1.0.0");
        app.setUpdated(0);
        app.setPrefixed(name);
        UpdateWrapper<App> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("appid", appid);
        updateWrapper.eq("prefixed", name);
        appService.saveOrUpdate(app, updateWrapper);
        //register(true, appid, "Actrab", "34707050", request);
        return Result.succ(app);
    }

    public Result deleteApp(String name) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("prefixed", name);
        appService.remove(queryWrapper);
        return Result.succ(null);
    }
    //上传头像
    public Result upAvatar(MultipartFile file, HttpServletRequest request){
        String username = JwtUtils.getString(request.getHeader("Token"), "username");
        String fileName=file.getOriginalFilename();
        Admin admin = new Admin();
        String newFileName= UUID.randomUUID()+"-"+fileName;
        admin.setAvatar(newFileName);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        String oldName=adminMapper.selectOne(queryWrapper).getAvatar(); //旧文件名
        File filePath=new File(dirPath);
        if(oldName!=null&&!oldName.equals("userImg.png")){
            File oldFile=new File(dirPath+oldName);
            if(oldFile.exists()){
                if(!oldFile.delete()) return Result.fail("旧文件无法移除");
            }
        }
        if(!filePath.exists()){
            if(!filePath.mkdirs()) return Result.fail("无法生成文件路径");
        }
        try{
            file.transferTo(new File(dirPath,newFileName));
            adminMapper.update(admin, queryWrapper);
            return Result.succ(newFileName);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("未知错误。请联系管理员");
        }
    }
    //构造excel卡密数据
    private List<VoCard> getVoCard(Api card, HttpServletRequest request){
        List<VoCard> list=voCardMapper.selectList(getQuery(card,request,1));
        for(VoCard voCard :list){
            if(voCard.getUserTime()!=null){
                if(voCard.getTime()>24&&voCard.getTime()<=720) {
                    if(voCard.getType()==1) voCard.setPrice(20);
                    else voCard.setPrice(50);
                }
                else if(voCard.getTime()>2160&&voCard.getPrice()<=4320){
                    if(voCard.getType()==1) voCard.setPrice(40);
                    else voCard.setPrice(139);
                }
                else if(voCard.getTime()>4320&&voCard.getPrice()<=8760){
                    if(voCard.getType()==1) voCard.setPrice(59);
                    else voCard.setPrice(239);
                }
                else if(voCard.getTime()>8760&&voCard.getPrice()<=876000){
                    if(voCard.getType()==1) voCard.setPrice(88);
                    else voCard.setPrice(399);
                }
            }
        }
        return list;
    }
    /*
    * 构造查询条件
    * num=0: 查询卡密条件
    * num=1: 导出excel条件
    */
    private <T> T getQuery(Api card, HttpServletRequest request,int num){
        String token = request.getHeader("Token");
        String agent = JwtUtils.getString(token, "username");
        QueryWrapper<T> queryWrapper;
        if(num==0){
           queryWrapper = (QueryWrapper<T>) new QueryWrapper<Card>();
        }else queryWrapper = (QueryWrapper<T>) new QueryWrapper<VoCard>();
        //QueryWrapper<Card> queryWrapper = new QueryWrapper<>();
        if(!card.getAgent().equals("")) agent=card.getAgent();
        if(card.getCheckTime().length!=0) {
            queryWrapper.ge("used_time", card.getCheckTime()[0]);
            queryWrapper.le("used_time", card.getCheckTime()[1]);
        }
        if (!card.getAppid().equals("")) queryWrapper.eq("appid", card.getAppid());
        if (!card.getMaster().equals("")) queryWrapper.eq("master", card.getMaster());
        if (!card.getCard().equals("")) queryWrapper.eq("card", card.getCard());
        if (!card.getAccount().equals("")) queryWrapper.eq("account", card.getAccount());
        if (card.getTime() != null) queryWrapper.eq("time", card.getTime());
        if (card.getType() != null) queryWrapper.eq("type", card.getType());
        if (card.getUsed() != null) queryWrapper.eq("used", card.getUsed());
        queryWrapper.eq("agent", agent);
        queryWrapper.orderByDesc("used_time");
        return (T) queryWrapper;
    }
//    //构造voCard查询条件
//    private QueryWrapper<VoCard> getVoQuery(Api card, HttpServletRequest request){
//        String token = request.getHeader("Token");
//        String agent = JwtUtils.getString(token, "username");
//        QueryWrapper<VoCard> queryWrapper = new QueryWrapper<>();
//        if(!card.getAgent().equals("")) agent=card.getAgent();
//        if(card.getCheckTime().length!=0) {
//            queryWrapper.ge("used_time", card.getCheckTime()[0]);
//            queryWrapper.le("used_time", card.getCheckTime()[1]);
//        }
//        if (!card.getAppid().equals("")) queryWrapper.eq("appid", card.getAppid());
//        if (!card.getMaster().equals("")) queryWrapper.eq("master", card.getMaster());
//        if (!card.getCard().equals("")) queryWrapper.eq("card", card.getCard());
//        if (!card.getAccount().equals("")) queryWrapper.eq("account", card.getAccount());
//        if (card.getTime() != null) queryWrapper.eq("time", card.getTime());
//        if (card.getType() != null) queryWrapper.eq("type", card.getType());
//        if (card.getUsed() != null) queryWrapper.eq("used", card.getUsed());
//        queryWrapper.eq("agent", agent);
//        queryWrapper.orderByDesc("used_time");
//        return queryWrapper;
//    }
}
