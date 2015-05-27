package com.colverframework.core.sample.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.colverframework.core.sample.dubbo.DemoService;
import com.colverframework.core.sample.model.Member;
import com.colverframework.core.sample.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class MemberController {

    @Resource(name="memberService")
    private MemberService memberService;
    
    @Resource(name="demoService")
    private DemoService demoService;

    
    @RequestMapping("/hi/{name}")
    public String sayHi(@PathVariable("name") String name){
    	System.out.println(demoService.sayHello(name));
    	return null;
    }
    
    
    @RequestMapping(value={"/add","/add.html"},method={RequestMethod.GET})
    public ModelAndView add(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> map = new HashMap<String, Object>();
        return new ModelAndView("member/add", map);
    }
    
    @RequestMapping(value={"/add","/add.html"},method={RequestMethod.POST})
    public String addMember(HttpServletRequest request,HttpServletResponse response,
            @ModelAttribute("member")Member member) throws IOException{
        Map<String,Object> map = new HashMap<String, Object>();
        System.out.println(member);
        map.put("message", "成功添加数据到库," + member);
        this.memberService.add(member);
        Member member2 = this.memberService.get(member.getId());
        if(null!=member2){
            map.put("message", "查询Id=" + member2.getId() + "的用户名为:" + member2.getName());
        }else{
            map.put("message", "没有查询到与Id=" +member.getId() + "相关的数据");
        }
	     request.setCharacterEncoding("UTF-8");
	     response.setHeader("Charset", "utf-8");
         JSONObject fromObject = JSONObject.fromObject(map);
         PrintWriter pw=new PrintWriter(response.getOutputStream());
         pw.write(fromObject.toString());
         pw.flush();
         return null;
    }
    
    @RequestMapping(value={"/{id:\\d+}/query","/{id:\\d+}/query.html"},method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView queryMember(HttpServletRequest request,HttpServletResponse response,
            @PathVariable("id")String id) throws IOException{
        Map<String,Object> map = new HashMap<String, Object>();
        
        System.out.println(id);
        Member member = this.memberService.get(id);
        if(null!=member){
            map.put("message", "查询Id=" + id + "的用户名为:" + member.getName());
        }else{
            map.put("message", "没有查询到与Id=" + id + "相关的数据");
        }
	     request.setCharacterEncoding("UTF-8");
	     response.setCharacterEncoding("UTF-8");
         JSONObject fromObject = JSONObject.fromObject(map);
         PrintWriter pw=new PrintWriter(response.getOutputStream());
         pw.write(fromObject.toString());
         pw.flush();
         return null;
    }
    
    @RequestMapping(value={"/{id:\\d+}/delete","/{id:\\d+}/delete.html"},method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView deleteMember(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("id")String id){
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            this.memberService.delete(id);
            map.put("message", "删除Id为" + id +"的用户成功.");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "删除Id为" + id +"的用户失败, "+e.getMessage());
        }
        return new ModelAndView("member/message", map);    
    }
    
}