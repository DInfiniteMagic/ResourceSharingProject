package com.dzk.web.service;

import com.dzk.web.entity.*;

import java.util.List;


/**
 * @author dzk
 * @date 2020/7/26 20:45
 * @description
 */
public interface  SystemManagementService {
    SystemMonitoring getSystemMonitoring();
    void autoSavaSystemMonitory();
    HistorySystemMonitor getHistorySystemMonitor();
    AccessRecordsTable getAccesssRecordsTableData(Integer page, Integer rows, String sidx, String sord);
    AccessRecordsTable getSearchAccessRecordsTableData(String searchContext,Integer page,Integer rows,String sidx,String sord);
    AccountListTable getAccountListTable(Integer page, Integer rows, String sidx, String sord);
    AccountListTable getSearchAccountListTableData(String searchContext,Integer page,Integer rows,String sidx,String sord);
    void modifyAccountListTable(long id,String roleName,String status);//修改用户列表
    OnlineUserListTable getOnlineUserListTableData(Integer page, Integer rows, String sidx, String sord);
    void forceQuitOnlineUser(String username);
}
