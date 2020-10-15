package com.dzk.web.service.Impl;

import com.dzk.common.utils.FileUtils;
import com.dzk.common.utils.PropertiesLoader;
import com.dzk.common.utils.StringUtils;
import com.dzk.web.controller.RegisterController;
import com.dzk.web.entity.*;
import com.dzk.web.repository.ResourceRepository;
import com.dzk.web.service.AccountService;
import com.dzk.web.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * @author dzk
 * @date 2020/7/15 17:30
 * @description
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    private Logger logger = LoggerFactory.getLogger(RegisterController.class);
    //加载属性文件
    private PropertiesLoader loader=new PropertiesLoader("applicationProperties.properties");
    @Autowired
    private AccountService accountService;
    @Autowired
    private ResourceRepository resourceRepository;
    @Override
    public void saveUploadResource(UploadInfor uploadInfor,String username) {
        String uploadURL=loader.getProperty("upload.url");//获取文件存储地址
        Calendar cale=Calendar.getInstance();
        String fileTime=StringUtils.dataToString(cale);//用于作为文件的时间目录
        uploadURL=uploadURL+"/"+username+"/"+fileTime;//资源存放目录
        long id=accountService.getAccountByUsername(username).getId();
        uploadInfor.setUploadResourceName(uploadInfor.getFileUpload().getOriginalFilename());//获取上传文件的全名
        uploadInfor.setResourceUrl(uploadURL);
        uploadInfor.setResourceStatue("未审核");
        uploadInfor.setUploadTime(StringUtils.dataChange(cale.getTime(),"yyyy/MM/dd-HH:mm:ss"));//存储文件上传的时间
        uploadInfor.setUploadAccountId(id);//资源上传人的id
        uploadInfor.setResourceSize(uploadInfor.getFileUpload().getSize());//文件大小 字节
        File file= FileUtils.creatFile(uploadInfor.getResourceUrl(),uploadInfor.getUploadResourceName());//在file文件中创建一个于上传文件名字相同的文件
        try {
            uploadInfor.getFileUpload().transferTo(file);//将上传的文件中的内容注入到本地创建的文件中
        } catch (IOException e) {
            if(logger.isErrorEnabled()){
                logger.debug(e.getMessage());
            }
            e.printStackTrace();
        }
        saveUploadResourceToDataBase(uploadInfor);//将上传信息存入数据库
    }


    @Override
    public void saveUploadResourceToDataBase(UploadInfor uploadInfor) {//将上传信息存入数据库
        resourceRepository.insertResource(uploadInfor);
    }

    @Override
    public DownloadTable getInitDownloadTableData(Integer page,Integer rows,String username,String sidx,String sord,String statue) {
        Integer start=(page-1)*rows;
        Integer end=start+rows;
        List<DownloadInfor> downloadInfors=resourceRepository.selectInitResourcesTableData(start,end,username,sidx,sord,statue);
        DownloadTable downloadTable=new DownloadTable();
        downloadTable.setRows(downloadInfors);
        int size=resourceRepository.findResourcesSize(statue);
        downloadTable.setRecords(size);//设置总条数
        downloadTable.setPage(page);//设置当前页
        Integer total=(size+rows-1)/rows;//总页数
        downloadTable.setTotal(total);//设置总页数
        return downloadTable;
    }

    @Override
    public void setFavoritesByResourcesId(long id,long favorites,String username) {
        //如果favorites =0 代表设置收藏  其他任何数字代表取消收藏
        if(favorites==0){
            resourceRepository.deleteFavorites(id,username);
            resourceRepository.insertFavorites(id,username);
        }else{
            resourceRepository.deleteFavorites(id,username);
        }
    }

    @Override
    public DownloadTable searchDownloadTable(String searchContext, Integer page, Integer rows, String sidx, String sord,String statue,String username) {
        Integer start=(page-1)*rows;
        Integer end=start+rows;
        searchContext="%"+searchContext+"%";//模糊查询
        List<DownloadInfor> searchList=resourceRepository.selectSearchContextOfDownloadTable(searchContext,start,end,sidx,sord,statue,username);
        DownloadTable downloadTable=new DownloadTable();
        downloadTable.setRows(searchList);
        int size=resourceRepository.findSearchContextSize(searchContext,statue);
        downloadTable.setRecords(size);//设置总条数
        downloadTable.setPage(page);//设置当前页
        Integer total=(size+rows-1)/rows;//总页数
        downloadTable.setTotal(total);//设置总页数
        return downloadTable;
    }

    @Override
    public Download downloadResource(int id) {
        Download download=resourceRepository.findDownloadById(id);
        return download;
    }

    @Override
    public void addDownloadTime(int id) {
        resourceRepository.updateResourceDownloadTimesById(id);
    }

    @Override
    public String getAllResourcesClass() {
        List<String> list=resourceRepository.selectAllResourcesClass();
        StringBuffer stringBuffer=new StringBuffer("<option style='display: none'></option>");
        for (int i=0;i<list.size();i++){
            StringBuffer sb=new StringBuffer();
            String value=list.get(i);
            sb.append("<option value='");
            sb.append(value);
            sb.append("'>");
            sb.append(value);
            sb.append("</option>");
            stringBuffer.append(sb);
        }
        return stringBuffer.toString();
    }

    @Override
    public UploadTableInfor getInitUploadTableData(Integer page, Integer rows, String sidx, String sord, String username, String statue) {
        Integer start=(page-1)*rows;
        Integer end=start+rows;
        List<UploadTable> uploadTables=resourceRepository.selectInitUploadTableData(start,end,sidx,sord,username,statue);
        UploadTableInfor downloadInfors=new UploadTableInfor();
        downloadInfors.setRows(uploadTables);
        int size=resourceRepository.findUploadSizeByUsername(username,statue);
        downloadInfors.setRecords(size);//设置总条数
        downloadInfors.setPage(page);//设置当前页
        Integer total=(size+rows-1)/rows;//总页数
        downloadInfors.setTotal(total);//设置总页数
        return downloadInfors;
    }

    @Override
    public void modifyUploadTable(ModifyUploadTable modifyUploadTable) {
        resourceRepository.updateUploadTable(modifyUploadTable);
    }

    @Override
    public void deleteUploadTable(int resourceId) {
        String path=findUploadFileUrlById(resourceId);//得到文件所在的地址
        resourceRepository.deleteFavoriteByResourceId(resourceId);//删除该资源上的收藏
        resourceRepository.deleteUploadTable(resourceId);//删除该资源
        File file=new File(path);//删除上传的文件
        if(!file.exists()){
            logger.info("文件不存在！path:"+path);
            return;
        }
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String findUploadFileUrlById(int id) {
        return resourceRepository.selectResourceUrlById(id);
    }

    @Override
    public String getAllAcoountRolesForJqgrid() {
        List<String> list=resourceRepository.selectAllAccountRoles();
        StringBuffer stringBuffer=new StringBuffer();
        for (int i=0;i<list.size();i++){
            StringBuffer sb=new StringBuffer();
            String value=list.get(i);
            sb.append("<option value='");
            sb.append(value);
            sb.append("'>");
            sb.append(value);
            sb.append("</option>");
            stringBuffer.append(sb);
        }
        return stringBuffer.toString();
    }

    @Override
    public void modifyUploadResoucesStatueById(long id, String resourcesStatue) {
        resourceRepository.updateUploadResourcesStatueById(id,resourcesStatue);
    }
}
