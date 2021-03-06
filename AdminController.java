package com.example.demo;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
@Autowired
	Admindao dao;

Logger log=Logger.getAnonymousLogger();
@ResponseBody
@RequestMapping("/login")
public ModelAndView login(HttpServletRequest req,HttpServletResponse res,@RequestParam("suser") String suser,@RequestParam("spassword") String spassword) {
	
	ModelAndView mv=new ModelAndView();
	log.info("inside the request mapping of login");
	log.info("object from user: "+dao.findbyuser(suser));
	log.info("object from password: "+dao.findbypassword(spassword));
	if(dao.findbyuser(suser)==null)
	{
		log.info("-------NULL value CAUGHT....");
		mv.setViewName("falls.jsp");
	}
	else if(dao.findbyuser(suser).equals(dao.findbypassword(spassword))) {
		log.info("validation of the user is successfull");
			log.info("created mv object");
			mv.setViewName("default.jsp");
			
			//return "Login is successfull";
	}
	else {
		log.info("failed");
		mv.setViewName("falls.jsp");
	}
	return mv;
	
}


@ResponseBody
@RequestMapping("/register")
public ModelAndView register(HttpServletRequest req,HttpServletResponse res)
{
	
	log.info("into the register mapping");
	ModelAndView mv=new ModelAndView();
	String suser=req.getParameter("suser");
	String spassword=req.getParameter("spassword");
	String semail=req.getParameter("semail");
	log.info("data set");
	RestTemplate rest=new RestTemplate();
	String url="http://localhost:8082/register-user/"+suser+"/"+spassword+"/"+semail;
	log.info(url);
	if(rest.getForObject(url,String.class).equals("Successful"))
	{
		log.info("Successfully registered.");
		mv.setViewName("successfulRegistered.jsp");
	}
	else {
		log.info("Failed registeration");
		mv.setViewName("fallsRegister.jsp");
	}
	
		
	log.info("went to register ms");
	return mv;
	
}


@ResponseBody
@RequestMapping("/buyshoe")
public ModelAndView buyshoe(HttpServletRequest req,HttpServletResponse res,@RequestParam("sid") String sid) {
	
	ModelAndView mv=new ModelAndView();
	log.info("inside the request mapping of buyshoe");
	log.info("object from user"+ sid );
	int id= Integer.parseInt(sid);
	Buy s=new Buy();
	
	log.info("data set");
	log.info("pojo object created");
	s.setId(id);
	log.info("Type is set to the pojo"+ s.getId());
	int pid= dao.byCount();
	pid++;
	s.setPid(pid);
	log.info("model and view object created");
	
	Buy bd=dao.buy(s);
	log.info("insert method called successfully");
	if(bd!=null) {
		log.info("sucessful Shoes insertion");
		mv.setViewName("SuccessBuy.jsp");
	}
	return mv;
	
}




@ResponseBody
@RequestMapping("/getall")
public ModelAndView getShoess(HttpServletRequest req,HttpServletResponse res) {
	log.info("in get all request");
	ModelAndView mv=new ModelAndView();
	log.info("created mv object");
	List<Shoes> list=dao.getall();
	log.info("called getall method"+list);
	mv.setViewName("display.jsp");
	log.info("went to jsp");
	mv.addObject("list",list);
	log.info("sent a list to the jsp");
	return mv;
}
@ResponseBody
@RequestMapping("/insert")
public ModelAndView insert(HttpServletRequest req,HttpServletResponse res,@RequestParam("id") String sid,@RequestParam("size") String size,@RequestParam("sprice") String sprice,@RequestParam("stype") String stype) {
	ModelAndView mv=new ModelAndView();
	log.info("model and view object created (Insert)"+sid+" "+size+" "+sprice+" "+stype);
	int id=Integer.parseInt(sid);
	log.info("recieved Shoe info from front end");
	Shoes s=new Shoes();
	log.info("pojo object created");
	s.setId(id);
	s.setSize(size);
	log.info("Size is set to the pojo");
	s.setSprice(sprice);
	log.info("Price is set to the pojo");
	s.setStype(stype);
	log.info("Type is set to the pojo"+ s.getId()+s.getSize()+s.getSprice()+s.getStype());
	Shoes sd=dao.insert(s);
	log.info("insert method called successfully");
	if(sd!=null) {
		log.info("sucessful Shoes insertion");
		mv.setViewName("default.jsp");
	}
	return mv;
}



@ResponseBody
@RequestMapping("Plist")
public ModelAndView Plist(HttpServletRequest req,HttpServletResponse res) {
	log.info("in get all request");
	ModelAndView mv=new ModelAndView();
	log.info("created mv object");
	List<Buy> list=dao.Plist();
	log.info("called getall method"+list);
	mv.setViewName("purchaseList.jsp");
	log.info("went to jsp");
	mv.addObject("list",list);
	log.info("sent a list to the jsp");
	return mv;
}


@ResponseBody
@RequestMapping("UserList")
public ModelAndView UserList(HttpServletRequest req,HttpServletResponse res) {
	log.info("in get all request");
	ModelAndView mv=new ModelAndView();
	log.info("created mv object");
	List<Admin> list=dao.UserList();
	log.info("called getall method"+list);
	mv.setViewName("UserList.jsp");
	log.info("went to jsp");
	mv.addObject("list",list);
	log.info("sent a list to the jsp");
	return mv;
}







}




