package net.thinklog.database.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import net.thinklog.database.properties.MybatisPlusAutoFillProperties;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自定义填充公共字段
 *
 * @author azhao
 * @date 2018/12/11

 */
public class DateMetaObjectHandler implements MetaObjectHandler {
    private MybatisPlusAutoFillProperties autoFillProperties;

    public DateMetaObjectHandler(MybatisPlusAutoFillProperties autoFillProperties) {
        this.autoFillProperties = autoFillProperties;
    }

    /**
     * 是否开启了插入填充
     */
    @Override
    public boolean openInsertFill() {
        return autoFillProperties.getEnableInsertFill();
    }

    /**
     * 是否开启了更新填充
     */
    @Override
    public boolean openUpdateFill() {
        return autoFillProperties.getEnableUpdateFill();
    }

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(autoFillProperties.getCreateTimeProp(), metaObject);
        Object updateTime = getFieldValByName(autoFillProperties.getUpdateTimeProp(), metaObject);
        if (createTime == null || updateTime == null) {
            Date date = new Date();
            if (createTime == null) {
                setFieldValByName(autoFillProperties.getCreateTimeProp(), date, metaObject);
            }
            if (updateTime == null) {
                setFieldValByName(autoFillProperties.getUpdateTimeProp(), date, metaObject);
            }
        }
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName(autoFillProperties.getUpdateTimeProp(), new Date(), metaObject);
    }
}