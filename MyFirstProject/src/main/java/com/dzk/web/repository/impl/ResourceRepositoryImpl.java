package com.dzk.web.repository.impl;

import com.dzk.web.entity.*;
import com.dzk.web.repository.ResourceRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/15 20:23
 * @description
 */
@Repository
public class ResourceRepositoryImpl implements ResourceRepository {
    @Autowired
    private SqlSessionTemplate sqlSession;
    @Override
    public void insertResource(UploadInfor uploadInfor) {
        sqlSession.getMapper(ResourceRepository.class).insertResource(uploadInfor);
    }

    @Override
    public List<DownloadInfor> selectInitResourcesTableData(Integer start,Integer end,String username,String sidx,String sord,String statue) {
        return sqlSession.getMapper(ResourceRepository.class).selectInitResourcesTableData(start,end,username,sidx,sord,statue);
    }

    @Override
    public int findResourcesSize(String statue) {
        return sqlSession.getMapper(ResourceRepository.class).findResourcesSize(statue);
    }

    @Override
    public void deleteFavorites(long id, String username) {
        sqlSession.getMapper(ResourceRepository.class).deleteFavorites(id,username);
    }

    @Override
    public void insertFavorites(long id, String username) {
        sqlSession.getMapper(ResourceRepository.class).insertFavorites(id,username);
    }

    @Override
    public List<DownloadInfor> selectSearchContextOfDownloadTable(String searchContext, Integer start, Integer end, String sidx, String sord, String statue,String username) {
        return sqlSession.getMapper(ResourceRepository.class).selectSearchContextOfDownloadTable(searchContext,start,end,sidx,sord,statue,username);
    }

    @Override
    public int findSearchContextSize(String searchContext,String statue) {
        return sqlSession.getMapper(ResourceRepository.class).findSearchContextSize(searchContext,statue);
    }

    @Override
    public Download findDownloadById(int id) {
        return sqlSession.getMapper(ResourceRepository.class).findDownloadById(id);
    }

    @Override
    public void updateResourceDownloadTimesById(int id) {
        sqlSession.getMapper(ResourceRepository.class).updateResourceDownloadTimesById(id);
    }

    @Override
    public List<String> selectAllResourcesClass() {
        return sqlSession.getMapper(ResourceRepository.class).selectAllResourcesClass();
    }

    @Override
    public List<UploadTable> selectInitUploadTableData(Integer start, Integer end, String sidx, String sord, String username, String statue) {
        return sqlSession.getMapper(ResourceRepository.class).selectInitUploadTableData(start,end,sidx,sord,username,statue);
    }

    @Override
    public int findUploadSizeByUsername(String username, String statue) {
        return sqlSession.getMapper(ResourceRepository.class).findUploadSizeByUsername(username,statue);
    }

    @Override
    public void updateUploadTable(ModifyUploadTable modifyUploadTable) {
        sqlSession.getMapper(ResourceRepository.class).updateUploadTable(modifyUploadTable);
    }

    @Override
    public void deleteUploadTable(int id) {
        sqlSession.getMapper(ResourceRepository.class).deleteUploadTable(id);
    }

    @Override
    public void deleteFavoriteByResourceId(int id) {
        sqlSession.getMapper(ResourceRepository.class).deleteFavoriteByResourceId(id);
    }

    @Override
    public String selectResourceUrlById(int id) {
        return sqlSession.getMapper(ResourceRepository.class).selectResourceUrlById(id);
    }

    @Override
    public List<String> selectAllAccountRoles() {
        return sqlSession.getMapper(ResourceRepository.class).selectAllAccountRoles();
    }

    @Override
    public void updateUploadResourcesStatueById(long id, String resourcesStatue) {
        sqlSession.getMapper(ResourceRepository.class).updateUploadResourcesStatueById(id,resourcesStatue);
    }
}
