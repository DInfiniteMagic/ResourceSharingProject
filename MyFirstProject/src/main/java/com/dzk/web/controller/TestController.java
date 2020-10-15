package com.dzk.web.controller;

import com.dzk.web.webSocket.WebsocketHandler;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;

import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;


import java.util.Collection;


/**
 * @author dzk
 * @date 2020/7/29 14:37
 * @description
 */
@RestController
@RequestMapping("/text")
public class TestController {
    @Autowired
    private SessionDAO sessionDAO;

    @Autowired(required = false)
    WebsocketHandler handler;

    @RequestMapping("/shiroTest")
    public void shiroTest(){
        Collection<Session> collection=sessionDAO.getActiveSessions();

        for (Session session:collection
             ) {
            System.err.println("--------------------------------------------------------------------------------");
            System.err.println(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)));
            System.err.println(String.valueOf(session.getAttribute(DefaultSubjectContext.SESSION_CREATION_ENABLED)));
            System.err.println(String.valueOf(session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY)));
            System.err.println(session.getHost());
            System.err.println(session.getLastAccessTime());
            System.err.println(session.getStartTimestamp());
            System.err.println("--------------------------------------------------------------------------------");
        }
    }




	/*@ResponseBody
	@RequestMapping("/get1")
	public String  send(String name){
		handler.sendMessageToUser("adminManager",new TextMessage("服务端发送的内容:"+name));
		return "success";
	}*/

}
