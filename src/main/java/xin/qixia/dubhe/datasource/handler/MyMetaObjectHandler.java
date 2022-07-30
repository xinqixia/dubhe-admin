package xin.qixia.dubhe.datasource.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import xin.qixia.dubhe.utils.DateUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component // 一定不要忘记把处理器加到IOC容器中！
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", DateUtils.toString(null), metaObject);
        this.setFieldValByName("updateTime", DateUtils.toString(null), metaObject);
    }

    // 更新时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", DateUtils.toString(null), metaObject);
    }
}
