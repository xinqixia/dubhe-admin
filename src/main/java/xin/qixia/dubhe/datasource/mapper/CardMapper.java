package xin.qixia.dubhe.datasource.mapper;

import xin.qixia.dubhe.datasource.entity.Card;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Actrab
 * @since 2021-05-30
 */
@Mapper
public interface CardMapper extends BaseMapper<Card> {
    //查询用于导出excel的卡密
    //List<VoCard> getVoCard(Api card,String agent);
}
