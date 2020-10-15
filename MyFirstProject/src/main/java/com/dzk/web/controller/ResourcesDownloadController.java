package com.dzk.web.controller;

import com.dzk.common.annotation.OperateLogAnnotation;
import com.dzk.common.utils.FileUtils;
import com.dzk.common.utils.JsonUtils;
import com.dzk.web.entity.Download;
import com.dzk.web.entity.DownloadInfor;
import com.dzk.web.entity.DownloadTable;
import com.dzk.web.service.ResourceService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author dzk
 * @date 2020/7/19 20:17
 * @description
 */
@Controller
@RequestMapping("/resources")
public class ResourcesDownloadController {
    @Autowired
    private ResourceService resourceService;
    @OperateLogAnnotation(moduleType = "/downloadPageRedirect",operateContent = "重定向进入下载页面",functionName = "uploadPageRedirect()")  //这边添加切入点接口的注解
    @RequestMapping("/downloadPageRedirect")
    public String uploadPageRedirect(){
        return "redirect:downloadPage";
    }

    @OperateLogAnnotation(moduleType = "/downloadPage",operateContent = "转发进入下载页面",functionName = "uploadPage()")  //这边添加切入点接口的注解
    @RequestMapping("/downloadPage")
    public String uploadPage(){
        return "menu/downloadPage";
    }

    @PostMapping("/initDownloadTable")
    @ResponseBody
    public String initDownloadTable(Integer page,Integer rows,String sidx,String sord) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        boolean flag=true;
        if(username==null){
            username="";
            flag=false;
        }
        DownloadTable downloadTable = resourceService.getInitDownloadTableData(page, rows, username,sidx,sord,"已审核");
        downloadTable.setLogin(flag);
        return JsonUtils.toJson(downloadTable);
    }

    @OperateLogAnnotation(moduleType = "/setFavorites",operateContent = "收藏资源",functionName = "setFavorites()")  //这边添加切入点接口的注解
    @PostMapping("/setFavorites")
    @ResponseBody
    public String  setFavorites(long id,long favorites,Integer page,Integer rows,String sidx,String sord){
        boolean flag=true;
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        resourceService.setFavoritesByResourcesId(id,favorites,username);
        if(username==null){
            username="";
            flag=false;
        }
        DownloadTable downloadTable = resourceService.getInitDownloadTableData(page, rows, username,sidx,sord,"已审核");//后期此处要修改成已审核
        downloadTable.setLogin(flag);
        return JsonUtils.toJson(downloadTable);
    }

    @OperateLogAnnotation(moduleType = "/searchDownloadTable",operateContent = "查找资源",functionName = "searchDownloadTable()")  //这边添加切入点接口的注解
    @PostMapping("/searchDownloadTable")
    @ResponseBody
    public String searchDownloadTable(String searchContext,Integer page,Integer rows,String sidx,String sord){
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        boolean flag=true;
        if(username==null){
            username="";
            flag=false;
        }
        DownloadTable downloadTable=new DownloadTable();
        if(searchContext.trim().length()<1){
            return JsonUtils.toJson(downloadTable);
        }
        downloadTable=resourceService.searchDownloadTable(searchContext,page,rows,sidx,sord,"已审核",username);//后期此处要修改成已审核
        downloadTable.setLogin(flag);
        return JsonUtils.toJson(downloadTable);
    }

    @OperateLogAnnotation(moduleType = "/downloadResources",operateContent = "下载资源",functionName = "downloadResources()")  //这边添加切入点接口的注解
    @GetMapping("/downloadResources")
    @ResponseBody
    public void downloadResources(@RequestParam int id, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        String returnMessage="";
        if(username==null){
            return ;//"请登录！"
        }
        Download download=resourceService.downloadResource(id);
        if(download!=null){
            String path=download.getResourceUrl();
            String name=download.getUploadResourceName();//所要下载的文件路径
            File file=new File(path,name);//通过文件路径获得File对象
            OutputStream outputStream=null;
            if(file.exists()){
                response.setContentType("application/forc-download");
                response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(name, "UTF-8"));//Content-Disposition就是当用户想把请求所得的内容存为一个文件的时候提供一个默认的文件名
                //Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。*/
                try {
                    outputStream=response.getOutputStream();//通过response获取ServletOutputStream对象
                    outputStream.write(FileUtils.readFileToByteArray(file));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    if (outputStream!=null){
                       IOUtils.closeQuietly(outputStream);
                    }
                }
                resourceService.addDownloadTime(id);//下载次数增加
                return;// "文件下载成功！"
            }
            return;// "文件不存在！"
        }
        return;// "请选择正确的资源！"
    }
}
