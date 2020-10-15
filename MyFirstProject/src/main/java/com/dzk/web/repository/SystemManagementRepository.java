package com.dzk.web.repository;

import com.dzk.web.entity.AccessRecordsTableData;
import com.dzk.web.entity.AccountListTableData;
import com.dzk.web.entity.SystemMonitoring;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/26 22:54
 * @description
 */
@Repository
public interface SystemManagementRepository {
   long selectDataBaseSize(String dataBaseName);
   void insertSystemMonitorData(SystemMonitoring systemMonitoring);
   List<SystemMonitoring> selectSystemMonitorData();//得到历史的监控数据
   List<AccessRecordsTableData> selectAccessRecordsTableData(Integer start,Integer end,String sidx,String sord);
   Long selectCountAccessRecordsTableData();
   List<AccessRecordsTableData> selectAccessRecordsBySearch(String searchContext,Integer start,Integer end,String sidx,String sord);
   Long selectCountAccessRecordsTableDataBySearch(String searchContext);
   List<AccountListTableData> selectAccountListTableData(Integer start,Integer end,String sidx,String sord);//查询用户列表
   Long selectAccountListTableDataSize();
   List<AccountListTableData> selectAccountListBySearch(String searchContext,Integer start,Integer end,String sidx,String sord);
   Long selectAccountListTableDataSizeBySearch(String searchContext);
   void updateAccountListTable(long id, String roleName, String status);
}
