package net.thinklog.notify.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import net.thinklog.common.kit.DateTimeKit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thinklog.feign.service.RestTemplateService;
import net.thinklog.notify.bean.NotifyParam;
import net.thinklog.notify.service.FeignNotifyService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author azhao
 * 2021/7/30 23:46
 */
@Slf4j
@EnableScheduling
@AllArgsConstructor
@Component
public class NotifyTaskAutoConfig {
    private final FeignNotifyService feignNotifyService;
    private final RestTemplateService restTemplateService;

    /**
     * 2分钟执行一次，定时通知
     */
    @Scheduled(fixedDelayString = "PT2M")
    public void configureTasks() {
        //防止重复执行
        log.info("===异步通知任务执行====[START]==={}", DateUtil.now());
        Date now = DateTimeKit.getNow();
        Long prevId = 0L;
        List<NotifyParam> paramList = feignNotifyService.listTask(prevId, now);
        while (CollectionUtil.isNotEmpty(paramList)) {
            for (NotifyParam rs : paramList) {
                prevId = rs.getId();
                String result = restTemplateService.postJson(rs.getUrl(), rs.getParams());
                feignNotifyService.update(rs.getId(), result, rs.getVersion() + 1);
            }
            paramList = feignNotifyService.listTask(prevId, now);
        }
        log.info("===异步通知任务执行====[END]==={}", DateUtil.now());
    }
}
