package com.dzk.web.service;

import com.dzk.web.entity.*;

/**
 * @author dzk
 * @date 2020/7/15 17:28
 * @description 资源上传下载
 */
public interface ResourceService {
    void saveUploadResource(UploadInfor uploadInfor,String username);//将上传文件存入本地
    void saveUploadResourceToDataBase(UploadInfor uploadInfor);//将上传文件信息 存入数据库
    DownloadTable getInitDownloadTableData(Integer page,Integer rows,String username,String sidx,String sord,String statue);//获取初始化表格的数据
    void setFavoritesByResourcesId(long id,long favorites,String username);
    DownloadTable searchDownloadTable(String searchContext,Integer page,Integer rows,String sidx,String sord,String statue, String username);//查找
    Download downloadResource(int id);
    void addDownloadTime(int id);
    String getAllResourcesClass();
    UploadTableInfor getInitUploadTableData(Integer page, Integer rows, String sidx, String sord, String username, String statue);
    void modifyUploadTable(ModifyUploadTable modifyUploadTable);
    void deleteUploadTable(int resourceId);
    String findUploadFileUrlById(int id);
    String getAllAcoountRolesForJqgrid();
    void modifyUploadResoucesStatueById(long id,String resourcesStatue);
}
