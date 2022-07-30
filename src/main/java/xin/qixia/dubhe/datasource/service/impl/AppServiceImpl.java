package xin.qixia.dubhe.datasource.service.impl;

import xin.qixia.dubhe.datasource.entity.App;
import xin.qixia.dubhe.datasource.mapper.AppMapper;
import xin.qixia.dubhe.datasource.service.AppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Actrab
 * @since 2021-05-21
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

}
