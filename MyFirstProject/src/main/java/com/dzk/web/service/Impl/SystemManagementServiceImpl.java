package com.dzk.web.service.Impl;

import com.dzk.common.utils.ForceQuitMap;
import com.dzk.common.utils.PropertiesLoader;
import com.dzk.common.utils.StringUtils;
import com.dzk.common.utils.WebUtils;
import com.dzk.web.entity.*;
import com.dzk.web.repository.SystemManagementRepository;
import com.dzk.web.service.SystemManagementService;

import com.dzk.web.webSocket.WebsocketHandler;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;


import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * @author dzk
 * @date 2020/7/26 20:51
 * @description
 */
@Service
public class SystemManagementServiceImpl implements SystemManagementService {
    @Autowired
    private  SystemManagementRepository systemManagementRepository;
    private static PropertiesLoader loader = new PropertiesLoader("applicationProperties.properties");
    @Autowired
    private  SessionDAO sessionDAO;
    @Autowired
    @Qualifier("rememberMeCookie")
    SimpleCookie simpleCookie;
    @Autowired
    WebsocketHandler handler;
    @Override
    public SystemMonitoring getSystemMonitoring(){
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long[] ticks = processor.getSystemCpuLoadTicks();
        SystemMonitoring systemMonitoring=new SystemMonitoring();
        systemMonitoring.setCPUCount(processor.getLogicalProcessorCount());//CPU核数
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];//用户使用量
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];//系统使用量
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;//总量
        systemMonitoring.setCPUPercentage(new DecimalFormat("#.##").format(1.0-(idle * 1.0 / totalCpu)));//cpu使用率

        GlobalMemory memory = systemInfo.getHardware().getMemory();
        //总内存
        long totalByte = memory.getTotal();
        //剩余
        long acaliableByte = memory.getAvailable();
        systemMonitoring.setRAMPercentage(new DecimalFormat("#.##").format((totalByte-acaliableByte)*1.0/totalByte));//内存使用率

        Properties props = System.getProperties();
        Runtime runtime = Runtime.getRuntime();
        //jvm总内存
        long jvmTotalMemoryByte = runtime.totalMemory();
        //空闲空间
        long freeMemoryByte = runtime.freeMemory();
        String jdkVersion = props.getProperty("java.version");
        systemMonitoring.setJVMPercentage(new DecimalFormat("#.##").format((jvmTotalMemoryByte-freeMemoryByte)*1.0/jvmTotalMemoryByte));//jvm使用率
        systemMonitoring.setTime(new Date());//当前时间
        systemMonitoring.setJdkVersion(jdkVersion);//jdk版本
         //系统名称
        String osName = props.getProperty("os.name");
        systemMonitoring.setOSName(osName);//系统名称
        File[] files = File.listRoots();
        long totalSpace=0;
        long freeSpace=0;
        for (File file : files){
            totalSpace+=file.getTotalSpace();
            freeSpace+=file.getFreeSpace();
        }
        long useSpace=(totalSpace-freeSpace)/(1024*1024*1024);
        totalSpace=totalSpace/(1024*1024*1024);
        systemMonitoring.setHardDiskPercentage(new DecimalFormat("#.##").format(useSpace*1.0/totalSpace));//磁盘使用率
        String dataBaseName=loader.getProperty("dataBaseName");
        long dataBaseSize=systemManagementRepository.selectDataBaseSize(dataBaseName);
        double size=Double.valueOf(dataBaseSize)/(1024*1024);
        systemMonitoring.setDataBase(String.valueOf(size)+"MB");
        //获取 ip mac 地址
        byte[] mac = new byte[0];
        try {
            InetAddress ia = InetAddress.getLocalHost();
            mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            String ipAddress=ia.getHostAddress();//获取ip地址
            systemMonitoring.setIpAddress(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		systemMonitoring.setMacAddress(sb.toString());//mac地址
        systemMonitoring.setStartTime(WebUtils.getStartTime().toString());//获取服务器启动时间

        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        /*小时差*/

        //Date startTime=simpleFormat.parse(StringUtils.dataChange(WebUtils.getStartTime(),"yyyy-MM-dd hh:mm"));//服务器启动时间
        //Date nowTime=simpleFormat.parse(StringUtils.dataChange(new Date(),"yyyy-MM-dd hh:mm"));//当前时间
        Date nowTime=new Date();
        long time=nowTime.getTime()-WebUtils.getDateS();
        long day=time/(24*60*60*1000);
        long hour=(time/(60*60*1000)-day*24);
        long min=((time/(60*1000))-day*24*60-hour*60);
        systemMonitoring.setRunTime(day+"天"+hour+"时"+min+"分");

        return systemMonitoring;
    }

    public void autoSavaSystemMonitory(){
        SystemMonitoring systemMonitoring=getSystemMonitoring();//每隔一段时间将系统的值存到数据库中
        systemManagementRepository.insertSystemMonitorData(systemMonitoring);
    }

    @Override
    public HistorySystemMonitor getHistorySystemMonitor() {
        List<SystemMonitoring> list=systemManagementRepository.selectSystemMonitorData();
        HistorySystemMonitor historySystemMonitor=new HistorySystemMonitor();
        for (SystemMonitoring s:list) {
            historySystemMonitor.getCPUPercentageList().add(s.getCPUPercentage());
            historySystemMonitor.getHardDiskPercentageList().add(s.getHardDiskPercentage());
            historySystemMonitor.getJVMPercentageList().add(s.getJVMPercentage());
            historySystemMonitor.getRAMPercentageList().add(s.getRAMPercentage());
        }
        return historySystemMonitor;
    }

    @Override
    public AccessRecordsTable getAccesssRecordsTableData(Integer page, Integer rows, String sidx, String sord) {
        Integer start=(page-1)*rows;
        Integer end=start+rows;
        List<AccessRecordsTableData> accessRecordsTableData=systemManagementRepository.selectAccessRecordsTableData(start,end,sidx,sord);
        AccessRecordsTable accessRecordsTable =new AccessRecordsTable();
        accessRecordsTable.setRows(accessRecordsTableData);
        long size=systemManagementRepository.selectCountAccessRecordsTableData();
        accessRecordsTable.setRecords(size);//设置总条数
        accessRecordsTable.setPage(page);//设置当前页
        long total=(size+rows-1)/rows;//总页数
        accessRecordsTable.setTotal(total);//设置总页数
        return accessRecordsTable;
    }

    @Override
    public AccessRecordsTable getSearchAccessRecordsTableData(String searchContext, Integer page, Integer rows, String sidx, String sord) {
        Integer start=(page-1)*rows;
        Integer end=start+rows;
        searchContext="%"+searchContext+"%";//模糊查询
        List<AccessRecordsTableData> searchList=systemManagementRepository.selectAccessRecordsBySearch(searchContext,start,end,sidx,sord);
        AccessRecordsTable accessRecordsTable=new AccessRecordsTable();
        accessRecordsTable.setRows(searchList);
        long size=systemManagementRepository.selectCountAccessRecordsTableDataBySearch(searchContext);
        accessRecordsTable.setRecords(size);//设置总条数
        accessRecordsTable.setPage(page);//设置当前页
        long total=(size+rows-1)/rows;//总页数
        accessRecordsTable.setTotal(total);//设置总页数
        return accessRecordsTable;
    }

    @Override
    public AccountListTable getAccountListTable(Integer page, Integer rows, String sidx, String sord) {
        Integer start=(page-1)*rows;
        Integer end=start+rows;
        List<AccountListTableData> accessRecordsTableData=systemManagementRepository.selectAccountListTableData(start,end,sidx,sord);
        AccountListTable accountListTable=new AccountListTable();
        accountListTable.setRows(accessRecordsTableData);
        long size=systemManagementRepository.selectAccountListTableDataSize();
        accountListTable.setRecords(size);//设置总条数
        accountListTable.setPage(page);//设置当前页
        long total=(size+rows-1)/rows;//总页数
        accountListTable.setTotal(total);//设置总页数
        return accountListTable;
    }

    @Override
    public AccountListTable getSearchAccountListTableData(String searchContext, Integer page, Integer rows, String sidx, String sord) {
        Integer start=(page-1)*rows;
        Integer end=start+rows;
        searchContext="%"+searchContext+"%";//模糊查询
        List<AccountListTableData> searchList=systemManagementRepository.selectAccountListBySearch(searchContext,start,end,sidx,sord);
        AccountListTable accountListTable=new AccountListTable();
        accountListTable.setRows(searchList);
        long size=systemManagementRepository.selectAccountListTableDataSizeBySearch(searchContext);
        accountListTable.setRecords(size);//设置总条数
        accountListTable.setPage(page);//设置当前页
        long total=(size+rows-1)/rows;//总页数
        accountListTable.setTotal(total);//设置总页数
        return accountListTable;
    }

    @Override
    public void modifyAccountListTable(long id, String roleName, String status) {
        systemManagementRepository.updateAccountListTable(id,roleName,status);
    }

    @Override
    public OnlineUserListTable getOnlineUserListTableData(Integer page, Integer rows, String sidx, String sord) {
        Integer start=(page-1)*rows;
        Integer end=start+rows;
        Collection<Session> collection=sessionDAO.getActiveSessions();//得到session集合
        List<OnlineUserListTableData> list=new ArrayList<>();
        List<OnlineUserListTableData> resultList=new ArrayList<>();
        Iterator<Session>  iterator = collection.iterator();
        int j=1;
        while (iterator.hasNext()){
            Session s=iterator.next();
            System.err.println(s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)+String.valueOf(s.getId())+"--"+String.valueOf(s.getTimeout()));

            if(s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)!=null){
                OnlineUserListTableData onlineUserListTableData=new OnlineUserListTableData();
                onlineUserListTableData.setUsername(String.valueOf(s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)));//得到在线用户
                onlineUserListTableData.setId(j++);
                onlineUserListTableData.setIpHost(s.getHost());//得到ip地址
                list.add(onlineUserListTableData);
           }
        }

        int size=list.size();//获取总条数
        for (int i=start;i<(end>size?size:end);i++){
           resultList.add(list.get(i));
        }
        OnlineUserListTable onlineUserListTable=new OnlineUserListTable();
        onlineUserListTable.setRows(resultList);
        onlineUserListTable.setRecords(size);//设置总条数
        onlineUserListTable.setPage(page);//设置当前页
        long total=(size+rows-1)/rows;//总页数
        onlineUserListTable.setTotal(total);//设置总页数
        return onlineUserListTable;
    }
    @Override
    public void forceQuitOnlineUser(String username) {
        Collection<Session> collection=sessionDAO.getActiveSessions();//得到session集合
        /* Iterator<Session>  iterator = collection.iterator();
         while (iterator.hasNext()){
            Session s=iterator.next();
            if(username!=null && username.equals(String.valueOf(s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))){
               collection.remove(s.getId());
            }
        }*/

        for (Session session:collection) {
            if(username!=null && username.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))){
                handler.sendMessageToUser(username,new TextMessage("您的账号已被强制下线！请重新登录！"));
              /*  System.err.println("sys:"+session.getId());*/
                session.setAttribute("forceQuit","true");
                Map<String,String> map=ForceQuitMap.getForceUserMap();
                map.put(username,"forceQuit");
                ForceQuitMap.setForceUserMap(map);
                session.setTimeout(0);//设置session立即失效，即将其踢出系统
              /*  System.err.println("s:"+session.getTimeout());
                session.setTimeout(30000);//此处处理异常退出浏览器的用户
                System.err.println("e:"+session.getTimeout());*/
            }
        }

    }
}
