package net.thinklog.notify.listener;

import com.alibaba.fastjson.JSONObject;
import net.thinklog.common.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import net.thinklog.feign.service.RestTemplateService;
import net.thinklog.notify.bean.EventNotifyParam;
import net.thinklog.notify.bean.EventNotifyTypeEnum;
import net.thinklog.feign.config.AsyncExecutorConfig;
import net.thinklog.notify.properties.EventNotifyProperties;
import net.thinklog.notify.service.FeignNotifyService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * 余额支付异步通知
 *
 * @author AZHAO
 */
@Slf4j
@ConditionalOnProperty(name = "tk.notify.type", havingValue = "db")
public class EventNotifyListener {
    private final RestTemplateService restTemplateService;
    private final EventNotifyProperties eventNotifyProperties;
    private final FeignNotifyService feignNotifyService;

    public EventNotifyListener(RestTemplateService restTemplateService, EventNotifyProperties eventNotifyProperties
            , FeignNotifyService feignNotifyService) {
        this.restTemplateService = restTemplateService;
        this.eventNotifyProperties = eventNotifyProperties;
        this.feignNotifyService = feignNotifyService;
    }

    /**
     * 通知其他子系统订阅的用户资料提交信息
     */
    @EventListener(value = EventNotifyParam.class)
    @Async(AsyncExecutorConfig.ASYNC_NAME)
    public void eventListener(EventNotifyParam event) {
        String jsonData = JSONObject.toJSONString(event);
        EventNotifyTypeEnum eventNotifyTypeEnum = EventNotifyTypeEnum.valueOf(event.getName());
        log.info("{}异步通知开始：{}", eventNotifyTypeEnum.getTitle(), jsonData);
        for (String url : eventNotifyProperties.getUrl()) {
            if (StrKit.notBlank(url)) {
                String msg = restTemplateService.postJson(url, jsonData);
                feignNotifyService.save(eventNotifyTypeEnum.getTitle(), null, url, jsonData, StrKit.substr(msg, 255));
                log.info("{}异步通知：{}", eventNotifyTypeEnum.getTitle(), msg);
            }
        }
    }
}
