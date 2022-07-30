package xin.qixia.dubhe.datasource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.Card;
import xin.qixia.dubhe.datasource.entity.User;
import xin.qixia.dubhe.datasource.mapper.CardMapper;
import xin.qixia.dubhe.datasource.mapper.UserMapper;
import xin.qixia.dubhe.datasource.service.CardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xin.qixia.dubhe.datasource.service.UserService;
import xin.qixia.dubhe.utils.DateUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Actrab
 * @since 2021-05-30
 */
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements CardService {
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Override
    public Result isCard(String appid, String account, String card, String master) {
        Result result = new Result();
        //查询卡密
        QueryWrapper<Card> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card", card);
        Card one = this.getOne(queryWrapper, false);
        //验证卡密信息
        result.setCode(400);
        if (one == null) {
            result.setMsg("卡密不存在！");
        } else if (one.getType() == 1 && account.length() == 28) {
            result.setMsg("帐号匹配失败！");
        } else if (!one.getAppid().equals(appid)) {
            result.setMsg("应用ID不一致！");
        } else if (one.getUpdateTime() != null && DateUtils.toString(null).compareTo(one.getUpdateTime()) < 0) {
            result.setMsg("冷却时间未结束！");
        } else if (one.getMaster() != null && !one.getMaster().equals(master)) {
            result.setMsg("主人帐号错误！");
        } else {
            result.setCode(200);
            result.setData(one);
        }
        return result;
    }

    @Override
    public Result getCardList(int current,QueryWrapper<Card> queryWrapper) {
        Page<Card> page=new Page<>(current,10);
        return Result.succ(cardMapper.selectPage(page, queryWrapper));
    }

    @Override
    public Result batchDel(int[] delIds) {
        Collection<Integer> idList=new LinkedList<>();
        for(int id:delIds) idList.add(id);
        cardMapper.deleteBatchIds(idList);
        return Result.succ(null);
    }

    @Override
    public Result singleDel(Card card) throws ParseException {
        String accountMd5 = new Md5Hash(card.getAccount() + "." + card.getAppid()).toString();
        //到期时间
        String expiration=userMapper.selectOne(new QueryWrapper<User>().eq("account", accountMd5)).getExpiration();
        //新的到期时间
        String newTime=DateUtils.dateHour(DateUtils.toDate(expiration),card.getTime());
        User user=new User();
        user.setExpiration(newTime);
        boolean succ=userService.update(user,new QueryWrapper<User>().eq("account", card.getAccount()));
        if(succ) return Result.succ(null);
        else return Result.fail("卡密删除失败，请联系管理员");
    }
}
