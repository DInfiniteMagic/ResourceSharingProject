package com.dzk.web.repository;

import com.dzk.web.entity.OperateLogEntity;

/**
 * @author dzk
 * @date 2020/7/27 21:11
 * @description
 */
public interface OperateLogRepository {
    void insertOperateLog(OperateLogEntity operateLogEntity);
}
