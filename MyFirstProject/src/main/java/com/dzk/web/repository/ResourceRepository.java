package com.dzk.web.repository;

import com.dzk.web.entity.*;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/15 20:22
 * @description
 */
public interface ResourceRepository {
    void insertResource(UploadInfor uploadInfor);
    List<DownloadInfor> selectInitResourcesTableData(Integer start,Integer end,String username,String sidx,String sord,String statue);
    int findResourcesSize(String statue);
    void deleteFavorites(long id,String username);
    void insertFavorites(long id,String username);
    List<DownloadInfor> selectSearchContextOfDownloadTable(String searchContext,Integer start,Integer end,String sidx,String sord,String statue,String username);
    int findSearchContextSize(String searchContext,String statue);
    Download findDownloadById(int id);
    void updateResourceDownloadTimesById(int id);
    List<String> selectAllResourcesClass();
    List<UploadTable> selectInitUploadTableData(Integer start,Integer end,String sidx,String sord,String username, String statue);
    int findUploadSizeByUsername(String username,String statue);
    void updateUploadTable(ModifyUploadTable modifyUploadTable);
    void deleteUploadTable(int id);
    void deleteFavoriteByResourceId(int id);
    String selectResourceUrlById(int id);
    List<String> selectAllAccountRoles();
    void updateUploadResourcesStatueById(long id,String resourcesStatue);
}
