package com.dzk.web.repository.impl;

import com.dzk.web.entity.AccessRecordsTableData;
import com.dzk.web.entity.AccountListTableData;
import com.dzk.web.entity.SystemMonitoring;

import com.dzk.web.repository.SystemManagementRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/26 22:55
 * @description
 */
@Repository
public class SystemManagementRepositoryImpl implements SystemManagementRepository {
    @Autowired
    private SqlSessionTemplate sqlSession;
    @Override
    public long selectDataBaseSize(String dataBaseName) {
       return sqlSession.getMapper(SystemManagementRepository.class).selectDataBaseSize(dataBaseName);
    }

    @Override
    public void insertSystemMonitorData(SystemMonitoring systemMonitoring) {
        sqlSession.getMapper(SystemManagementRepository.class).insertSystemMonitorData(systemMonitoring);
    }

    @Override
    public List<SystemMonitoring> selectSystemMonitorData() {
        return sqlSession.getMapper(SystemManagementRepository.class).selectSystemMonitorData();
    }

    @Override
    public List<AccessRecordsTableData> selectAccessRecordsTableData(Integer start, Integer end, String sidx, String sord) {
        return sqlSession.getMapper(SystemManagementRepository.class).selectAccessRecordsTableData(start,end,sidx,sord);
    }

    @Override
    public Long selectCountAccessRecordsTableData() {
        return sqlSession.getMapper(SystemManagementRepository.class).selectCountAccessRecordsTableData();
    }

    @Override
    public List<AccessRecordsTableData> selectAccessRecordsBySearch(String searchContext, Integer start, Integer end, String sidx, String sord) {
        return sqlSession.getMapper(SystemManagementRepository.class).selectAccessRecordsBySearch(searchContext,start,end,sidx,sord);
    }

    @Override
    public Long selectCountAccessRecordsTableDataBySearch(String searchContext) {
        return sqlSession.getMapper(SystemManagementRepository.class).selectCountAccessRecordsTableDataBySearch(searchContext);
    }

    @Override
    public List<AccountListTableData> selectAccountListTableData(Integer start, Integer end, String sidx, String sord) {
        return sqlSession.getMapper(SystemManagementRepository.class).selectAccountListTableData(start,end,sidx,sord);
    }

    @Override
    public Long selectAccountListTableDataSize() {
        return sqlSession.getMapper(SystemManagementRepository.class).selectAccountListTableDataSize();
    }

    @Override
    public List<AccountListTableData> selectAccountListBySearch(String searchContext, Integer start, Integer end, String sidx, String sord) {
        return sqlSession.getMapper(SystemManagementRepository.class).selectAccountListBySearch(searchContext,start,end,sidx,sord);
    }

    @Override
    public Long selectAccountListTableDataSizeBySearch(String searchContext) {
        return sqlSession.getMapper(SystemManagementRepository.class).selectCountAccessRecordsTableDataBySearch(searchContext);
    }

    @Override
    public void updateAccountListTable(long id, String roleName, String status) {
        sqlSession.getMapper(SystemManagementRepository.class).updateAccountListTable(id,roleName,status);
    }
}
