package cn.appsys.controller.user;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.appCategory.AppCategoryService;
import cn.appsys.service.appInfo.AppInfoService;
import cn.appsys.service.appVersion.AppVersionService;
import cn.appsys.service.dataDictionary.DataDictionaryService;
import cn.appsys.service.devUser.DevUserService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/backend")
public class BackendController {
	
	@Resource
	private DevUserService devUserservice;
	@Resource
	private DataDictionaryService datadictionaryservice;
	@Resource
	private AppCategoryService appCategoryservice;
	@Resource
	private AppInfoService appInfoservice;
	@Resource
	private AppVersionService appVersionService;
	
	@RequestMapping("/getapplist")
	public String getapplist(@RequestParam(value="querySoftwareName",required=false)String querySoftwareName,
		@RequestParam(value="queryStatus",required=false)Integer queryStatus,
		@RequestParam(value="queryCategoryLevel1",required=false)Integer queryCategoryLevel1,
		@RequestParam(value="queryCategoryLevel2",required=false)Integer queryCategoryLevel2,
		@RequestParam(value="queryCategoryLevel3",required=false)Integer queryCategoryLevel3,
		@RequestParam(value="queryFlatformId",required=false)Integer queryFlatformId,
		@RequestParam(value="currentPageNo",required=false)Integer currentPageNo,
		HttpServletRequest request){
		
		BackendUser backendUser = (BackendUser)request.getSession().getAttribute(Constants.USER_SESSION);
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo != null ? currentPageNo:1);
		pages.setPageSize(Constants.pageSize);
		pages.setTotalCount(devUserservice.total(querySoftwareName, 1,
				queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, 
				queryFlatformId,backendUser.getId()));
		
		List<AppInfo> appInfoList = devUserservice.AppInfoList(querySoftwareName, 1, 
				queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, 
				queryFlatformId, backendUser.getId(), pages.getCurrentPageNo(), pages.getPageSize());
		
		List<DataDictionary> flatFormList = datadictionaryservice.getDataDictionaryList("APP_FLATFORM");
		List<DataDictionary> statusList = datadictionaryservice.getDataDictionaryList("APP_STATUS");
		List<AppCategory> categoryLevel1List = appCategoryservice.getAppCategoryList(null);
		List<AppCategory> categoryLevel2List = null;
		List<AppCategory> categoryLevel3List = null;
		if(queryCategoryLevel2 != null){
			categoryLevel2List = appCategoryservice.getAppCategoryList(queryCategoryLevel1);
		}
		if(queryCategoryLevel3 != null){
			categoryLevel3List = appCategoryservice.getAppCategoryList(queryCategoryLevel2);
		}
		
		request.setAttribute("appInfoList", appInfoList);
		request.setAttribute("pages",pages);
		request.setAttribute("flatFormList",flatFormList);
		request.setAttribute("statusList",statusList);
		request.setAttribute("categoryLevel1List",categoryLevel1List);
		request.setAttribute("categoryLevel2List", categoryLevel2List);
		request.setAttribute("categoryLevel3List", categoryLevel3List);
		
		request.setAttribute("querySoftwareName",querySoftwareName);
		request.setAttribute("queryStatus",queryStatus);
		request.setAttribute("queryCategoryLevel1",queryCategoryLevel1);
		request.setAttribute("queryCategoryLevel2",queryCategoryLevel2);
		request.setAttribute("queryCategoryLevel3",queryCategoryLevel3);
		request.setAttribute("queryFlatformId",queryFlatformId);
		request.setAttribute("devId",backendUser.getId());
		
		return "backend/applist";
	}
	
	/**
	 * 根据id查询下一级分类
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<AppCategory> categoryLevel(Integer pid){
		List<AppCategory> categoryLevel =  appCategoryservice.getAppCategoryList(pid);
		return categoryLevel;
	}
	
	/**
	 * 查询所属平台
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/datadictionarylist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<DataDictionary> categoryLevel(String tcode){
		List<DataDictionary> statusList = datadictionaryservice.getDataDictionaryList(tcode);
		return statusList;
	}
	
	/**
	 * 跳转到审核页面
	 * @param aid
	 * @param vid
	 * @param request
	 * @return
	 */
	@RequestMapping("/check")
	public String getappcheck(@RequestParam(value="aid")String aid,
			@RequestParam(value="vid")String vid,HttpServletRequest request){
		AppInfo appInfo = appInfoservice.getAppInfoByid(aid);
		AppVersion appVersion = appVersionService.getAppVersionByid(vid);
		
		request.setAttribute("appInfo", appInfo);
		request.setAttribute("appVersion", appVersion);
		return "backend/appcheck";
	}
	
	/**
	 * APP审核
	 * @param vid
	 * @param aid
	 * @param request
	 * @return
	 */
	@RequestMapping("/checksave")
	public String checksaveSave(AppInfo appInfo,HttpServletRequest request){
		
		int num = appInfoservice.updatechecksave(appInfo.getId().toString(), appInfo.getStatus().toString());
		
		if(num > 0){
			return "redirect:/backend/getapplist";
		}
		
		return "backend/appcheck";
	}

}
