package net.thinklog.log.service;


import net.thinklog.log.model.Audit;

/**
 * 审计日志接口
 *
 * @author azhao
 * @date 2020/2/3

 */
public interface IAuditService {
    void save(Audit audit);
}
