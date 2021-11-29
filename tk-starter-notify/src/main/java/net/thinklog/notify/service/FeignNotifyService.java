package net.thinklog.notify.service;

import cn.hutool.core.date.DateUtil;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import net.thinklog.common.config.BaseConstant;
import net.thinklog.common.kit.DateTimeKit;
import net.thinklog.common.kit.StrKit;
import net.thinklog.feign.config.AsyncExecutorConfig;
import net.thinklog.notify.bean.NotifyParam;
import net.thinklog.notify.properties.NotifyDbProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 消息通知实现类-数据库
 * 使用方法：application.yml配置如下参数
 * st.feign.notify.type=db
 *
 * @author azhao
 * @date 2020/2/8
 */
@Slf4j
@Component
@ConditionalOnClass(JdbcTemplate.class)
public class FeignNotifyService {
    /**
     * 添加记录
     */
    private static final String INSERT_SQL = " insert into tk_starter_notify_log " +
            " (title, module_name, url, params, result, status,version,create_time,update_time,next_time) " +
            " values (?,?,?,?,?,?,1,now(),now(),'" + DateUtil.offsetMinute(DateTimeKit.getNow(), 2) + "')";
    /**
     * 更新记录
     */
    private static final String UPDATE_SQL = "update tk_starter_notify_log set result=?,status=?,version=?" +
            ",next_time=?,update_time=now() " +
            " where id=?";
    /**
     * 查询记录
     */
    private static final String SELECT_SQL = "select id,title,module_name as moduleName,url,params,result," +
            "status,version,create_time as createTime," +
            "update_time as updateTime,next_time as nextTime from tk_starter_notify_log " +
            "where status=0 order by id desc limit ?";
    /**
     * 获取单个记录
     */
    private static final String GET_SQL = "select id,title,module_name as moduleName,url,params,result," +
            "status,version,create_time as createTime," +
            "update_time as updateTime,next_time as nextTime from tk_starter_notify_log " +
            "where id=?";

    /**
     * 定时任务
     */
    private static final String TASK_SQL = "select id,title,module_name as moduleName,url,params,result," +
            "status,version,create_time as createTime," +
            "update_time as updateTime,next_time as nextTime from tk_starter_notify_log " +
            "where status=0 and id > ? and next_time < ? limit 10";

    private final JdbcTemplate jdbcTemplate;

    public FeignNotifyService(@Autowired(required = false) NotifyDbProperties dbProperties, DataSource dataSource) {
        //优先使用配置的日志数据源，否则使用默认的数据源
        if (dbProperties != null && StrKit.notBlank(dbProperties.getJdbcUrl())) {
            dataSource = new HikariDataSource(dbProperties);
        }
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS `tk_starter_notify_log` (\n" +
                "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
                "  `title` varchar(64) DEFAULT NULL COMMENT '标题描述',\n" +
                "  `method` varchar(10) DEFAULT 'POST' COMMENT '请求方式',\n" +
                "  `module_name` varchar(48) DEFAULT NULL COMMENT '模块名称',\n" +
                "  `url` varchar(255) DEFAULT NULL COMMENT '通知id',\n" +
                "  `params` json DEFAULT NULL COMMENT '请求参数',\n" +
                "  `result` varchar(255) DEFAULT NULL COMMENT '返回结果',\n" +
                "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                "  `next_time` datetime DEFAULT NULL COMMENT '下次执行时间',\n" +
                "  `status` tinyint(3) unsigned DEFAULT '0' COMMENT '状态',\n" +
                "  `version` int(10) unsigned DEFAULT '0' COMMENT '版本',\n" +
                "  KEY `idx_time` (`status`,`next_time`) USING BTREE COMMENT '执行时间',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        this.jdbcTemplate.execute(sql);
    }

    @Async(AsyncExecutorConfig.ASYNC_NAME)
    public void update(Long id, String result, int version) {
        int status = Objects.equals(BaseConstant.NOTIFY_SUCCESS, result) ? 1 : 0;
        //最多执行8次
        if (version >= 8) {
            status = 1;
        }
        this.jdbcTemplate.update(UPDATE_SQL, result, status, version, DateUtil.offsetMinute(DateTimeKit.getNow(), version), id);
    }

    public List<NotifyParam> list(Integer size) {
        return this.jdbcTemplate.query(SELECT_SQL, new BeanPropertyRowMapper<>(NotifyParam.class), size);
    }

    public List<NotifyParam> listTask(Long prevId, Date nextTime) {
        return this.jdbcTemplate.query(TASK_SQL, new BeanPropertyRowMapper<>(NotifyParam.class), prevId, nextTime);
    }

    public NotifyParam get(Long id) {
        List<NotifyParam> paramList = this.jdbcTemplate.query(GET_SQL, new BeanPropertyRowMapper<>(NotifyParam.class), id);
        if (paramList.isEmpty()) {
            return null;
        }
        return paramList.get(0);
    }

    @Async(AsyncExecutorConfig.ASYNC_NAME)
    public void save(String title, String moduleName, String url, String params, String result) {
        this.jdbcTemplate.update(INSERT_SQL
                , title, moduleName, url, params
                , result, Objects.equals(BaseConstant.NOTIFY_SUCCESS, result) ? 1 : 0);
    }
}
