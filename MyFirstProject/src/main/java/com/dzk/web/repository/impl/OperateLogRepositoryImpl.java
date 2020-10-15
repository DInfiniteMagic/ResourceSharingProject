package com.dzk.web.repository.impl;

import com.dzk.web.entity.OperateLogEntity;
import com.dzk.web.repository.OperateLogRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author dzk
 * @date 2020/7/27 21:14
 * @description
 */
@Repository
public class OperateLogRepositoryImpl implements OperateLogRepository {
    @Autowired
    private SqlSessionTemplate sqlSession;
    @Override
    public void insertOperateLog(OperateLogEntity operateLogEntity) {
        sqlSession.getMapper(OperateLogRepository.class).insertOperateLog(operateLogEntity);
    }
}
