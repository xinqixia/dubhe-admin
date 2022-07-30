package xin.qixia.dubhe.datasource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import xin.qixia.dubhe.common.Result;
import xin.qixia.dubhe.datasource.entity.Card;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Actrab
 * @since 2021-05-30
 */
public interface CardService extends IService<Card> {
    Result isCard(String appid, String account, String card, String master);
    /*分页查询卡密*/
    Result getCardList(int current, QueryWrapper<Card> queryWrapper);
    /*批量删除卡密*/
    Result batchDel(int [] batchIds);
    /*删除卡密*/
    Result singleDel(Card card) throws ParseException;
}
