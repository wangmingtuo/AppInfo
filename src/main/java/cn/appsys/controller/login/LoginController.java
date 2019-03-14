package cn.appsys.controller.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.backendUser.BackendUserService;
import cn.appsys.service.devUser.DevUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Resource
	private DevUserService devUser;
	@Resource
	BackendUserService backendUser;
	
	/**
	 * 跳转到开发者登录页面
	 * @return
	 */
	@RequestMapping("/devUserLogins")
	public String DevUserLogins(){
		return "pre/devlogin";
	}
	
	/**
	 * 跳转到后台管理登录页面
	 * @return
	 */
	@RequestMapping("/BackendUserLogins")
	public String BackendUserLogins(){
		return "backend/backendlogin";
	}
	
	/**
	 * 开发者登录页面
	 * @return
	 */
	@RequestMapping(value="/devUserLogin",method=RequestMethod.POST)
	public String DevUserLogin(@RequestParam(value="devCode",required=false)String devCode,
			@RequestParam(value="devPassword",required=false)String devPassword,
			HttpServletRequest request){
		DevUser devuser = devUser.DevUserLogin(devCode);
		if(devuser != null){
			if(devuser.getDevPassword().equals(devPassword)){
				request.getSession().setAttribute(Constants.DEV_USER_SESSION,devuser);
				return "redirect:/login/sys/mains";
			}else{
				request.setAttribute("error","密码不正确");
				return "pre/devlogin";
			}
		}
		request.setAttribute("error","用户名不存在");
		return "pre/devlogin";
	}
	
	/**
	 * 跳转到前台
	 * @return
	 */
	@RequestMapping(value="/sys/mains")
	public String mains(){
		return "pre/main";
	}
	
	/**
	 * 后台管理登录页面
	 * @return
	 */
	@RequestMapping(value="/BackendUserLogin",method=RequestMethod.POST)
	public String BackendUserLogin(@RequestParam(value="userCode",required=false)String userCode,
			@RequestParam(value="userPassword",required=false)String userPassword,
			HttpServletRequest request){
		BackendUser user = backendUser.BackendUserLogin(userCode);
		if(user != null){
			if(user.getUserPassword().equals(userPassword)){
				request.getSession().setAttribute(Constants.USER_SESSION,user);
				return "redirect:/login/sys/main";
			}else{
				request.setAttribute("error","密码不正确");
				return "backend/backendlogin";
			}
		}
		request.setAttribute("error","用户名不存在");
		return "backend/backendlogin";
	}
	
	/**
	 * 跳转到后台
	 * @return
	 */
	@RequestMapping(value="/sys/main")
	public String main(){
		return "backend/main";
	}
	
	
	/**
	 * 注销用户
	 * @return
	 */
	@RequestMapping(value="/LoginoOut")
	public String LoginoOut(HttpServletRequest request){
		request.getSession().invalidate();
		return "index";
	}

}
