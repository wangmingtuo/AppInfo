package cn.appsys.controller.dev;

import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.appCategory.AppCategoryService;
import cn.appsys.service.appInfo.AppInfoService;
import cn.appsys.service.appVersion.AppVersionService;
import cn.appsys.service.dataDictionary.DataDictionaryService;
import cn.appsys.service.devUser.DevUserService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.ExcelUtil;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/dev")
public class DevController {
	
	private Logger logger = Logger.getLogger(DevController.class);
	
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
	
	@RequestMapping("/main")
	public String main(){
		return "pre/main";
	}
	
	/**
	 * 模糊查询所有APP
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param devId
	 * @param currentPageNo
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String appinfoview(@RequestParam(value="querySoftwareName",required=false)String querySoftwareName,
			@RequestParam(value="queryStatus",required=false)Integer queryStatus,
			@RequestParam(value="queryCategoryLevel1",required=false)Integer queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false)Integer queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false)Integer queryCategoryLevel3,
			@RequestParam(value="queryFlatformId",required=false)Integer queryFlatformId,
			@RequestParam(value="currentPageNo",required=false)Integer currentPageNo,
			HttpServletRequest request){
		DevUser devUser = (DevUser)request.getSession().getAttribute(Constants.DEV_USER_SESSION);
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo != null ? currentPageNo:1);
		pages.setPageSize(Constants.pageSize);
		pages.setTotalCount(devUserservice.total(querySoftwareName, queryStatus,
				queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, 
				queryFlatformId,devUser.getId()));
		
		List<AppInfo> appInfoList = devUserservice.AppInfoList(querySoftwareName, queryStatus, 
				queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, 
				queryFlatformId, devUser.getId(), pages.getCurrentPageNo(), pages.getPageSize());
		
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
		request.setAttribute("devId",devUser.getId());
		
		return "pre/appinfolist";
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
	 * 判断APKName的状态
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/apkexist.json",method=RequestMethod.GET)
	@ResponseBody
	public String apkexist(String APKName){
		HashMap<String,String> retule = new HashMap<String,String>();
		if(APKName.equals("")){
			retule.put("APKName", "empty");
		}else if(appInfoservice.getAppInfo(APKName) != null){
			retule.put("APKName", "exist");
		}
		return JSONArray.toJSONString(retule);
	}
	
	/**
	 * 跳转到新增App页面
	 * @return
	 */
	@RequestMapping("/getAppinfoadd")
	public String getAppinfoadd(HttpServletRequest request){
		List<DataDictionary> flatFormList = datadictionaryservice.getDataDictionaryList("APP_FLATFORM");
		List<AppCategory> categoryLevel1List = appCategoryservice.getAppCategoryList(null);
		request.setAttribute("flatFormList",flatFormList);
		request.setAttribute("categoryLevel1List",categoryLevel1List);
		return "pre/appinfoadd";
	}
	
	/**
	 * 新增App
	 * @return
	 */
	@RequestMapping(value="/addAppinfo",method=RequestMethod.POST)
	public String addAppinfo(AppInfo appInfo,HttpServletRequest request,
			@RequestParam(value="a_logoPicPath",required=false)MultipartFile attach){
		        
		String idPicPath = null;
		String idPicPath2 = null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			
			String oldFileName = attach.getOriginalFilename();//原文件名
			String perfix = FilenameUtils.getExtension(oldFileName);
			int filesize=500000;
			if(attach.getSize() > filesize){
				request.setAttribute("uploadFileError", "*上传大小不得超过500KB");
				return "pre/appinfoadd";
			}else if(perfix.equalsIgnoreCase("jpg")
					|| perfix.equalsIgnoreCase("png")
					|| perfix.equalsIgnoreCase("jpeg")
					|| perfix.equalsIgnoreCase("pneg")){
				String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";
				File targetFile = new File(path, fileName);
				if(!targetFile.exists()){
					targetFile.mkdirs();
				}
				
				
				try {
					attach.transferTo(targetFile);	
					
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadFileError", "*上传失败");
					return "pre/appinfoadd";
				}
				idPicPath = path+File.separator+fileName;
				idPicPath2 = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				appInfo.setLogoPicPath(idPicPath2);
				appInfo.setLogoLocPath(idPicPath);
			}else{
				request.setAttribute("uploadFileError", "*上传图片格式不正确");
				return "pre/appinfoadd";
			}
		}
		DevUser user = (DevUser)request.getSession().getAttribute(Constants.DEV_USER_SESSION);
		appInfo.setDevId(user.getId());
		appInfo.setCreationDate(new Date());
		
		
		if(appInfoservice.addAppInfo(appInfo) > 0){
			return "redirect:/dev/list";
		}
		
		return "pre/appinfoadd";
	}
	
	/**
	 * 跳转到APP版本修改页面
	 * @param vid
	 * @param aid
	 * @param request
	 * @return
	 */
	@RequestMapping("/appversionmodify")
	public String appversionmodify(@RequestParam(value="vid")String vid,
			@RequestParam(value="aid")String aid,HttpServletRequest request){
		List<AppVersion> appVersionList = appVersionService.getAppVersionList(aid);
		AppVersion appVersion = appVersionService.getAppVersionByid(vid);
		request.setAttribute("appVersionList", appVersionList);
		request.setAttribute("appVersion", appVersion);
		return "pre/appversionmodify";
	}
	
	/**
	 * APP版本修改
	 * @param vid
	 * @param aid
	 * @param request
	 * @return
	 */
	@RequestMapping("/appversionUpdateSave")
	public String appversionUpdateSave(AppVersion appVersion,HttpServletRequest request,
			@RequestParam(value="attach",required=false)MultipartFile attach){
		AppInfo appInfo = appInfoservice.getAppInfoByid(appVersion.getAppId().toString());
		String idPicPath = null;
		String idPicPath2 = null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"apkfiles");
			
			String oldFileName = attach.getOriginalFilename();//原文件名
			String perfix = FilenameUtils.getExtension(oldFileName);
			if(perfix.equalsIgnoreCase("apk")){
				String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+appInfo.getAPKName()+".apk";
				File targetFile = new File(path, fileName);
				if(!targetFile.exists()){
					targetFile.mkdirs();
				}
				
				try {
					attach.transferTo(targetFile);	
					
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadFileError", "*上传失败");
					return "pre/appversionadd";
				}
				idPicPath = path+File.separator+fileName;
				idPicPath2 = request.getContextPath()+"/statics/apkfiles/"+fileName;
				
				appVersion.setApkLocPath(idPicPath);
				appVersion.setDownloadLink(idPicPath2);
				appVersion.setApkFileName(fileName);
			}else{
				request.setAttribute("uploadFileError", "*上传文件格式不正确");
				return "pre/appversionadd";
			}
			
		}
		DevUser user = (DevUser)request.getSession().getAttribute(Constants.DEV_USER_SESSION);
		
		appVersion.setModifyBy(user.getId());
		appVersion.setModifyDate(new Date());
		
		int updatenum = appVersionService.updateAppVersion(appVersion);
		
		if(updatenum>0){
			return "redirect:/dev/list";
		}
		
		return "pre/appversionadd";
	}
	
	/**
	 * 跳转到APP版本新增页面
	 * @param vid
	 * @param aid
	 * @param request
	 * @return
	 */
	@RequestMapping("/getappversionadd")
	public String getappversionadd(String id,HttpServletRequest request){
		List<AppVersion> appVersionList = appVersionService.getAppVersionList(id);
		
		AppInfo appInfo = appInfoservice.getAppInfoByid(id);
		AppVersion appVersion = null;
		if(appInfo.getVersionId() == null){
			appVersion = new AppVersion();
			appVersion.setAppId(appInfo.getId());
		}else{
			appVersion = appVersionService.getAppVersionByid(appInfo.getVersionId().toString());
		}
		
		request.setAttribute("appVersionList", appVersionList);
		request.setAttribute("appVersion", appVersion);
		return "pre/appversionadd";
	}
	
	/**
	 * APP版本新增
	 * @param vid
	 * @param aid
	 * @param request
	 * @return
	 */
	@RequestMapping("/appversionaddSave")
	public String appversionaddSave(AppVersion appVersion,HttpServletRequest request,
			@RequestParam(value="a_downloadLink",required=false)MultipartFile attach){
		AppInfo appInfo = appInfoservice.getAppInfoByid(appVersion.getAppId().toString());
		String idPicPath = null;
		String idPicPath2 = null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"apkfiles");
			
			String oldFileName = attach.getOriginalFilename();//原文件名
			String perfix = FilenameUtils.getExtension(oldFileName);
			if(perfix.equalsIgnoreCase("apk")){
				String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+appInfo.getAPKName()+".apk";
				File targetFile = new File(path, fileName);
				if(!targetFile.exists()){
					targetFile.mkdirs();
				}
				
				try {
					attach.transferTo(targetFile);	
					
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadFileError", "*上传失败");
					return "pre/appversionadd";
				}
				idPicPath = path+File.separator+fileName;
				idPicPath2 = request.getContextPath()+"/statics/apkfiles/"+fileName;
				appVersion.setApkFileName(fileName);
			}else{
				request.setAttribute("uploadFileError", "*上传文件格式不正确");
				return "pre/appversionadd";
			}
			
		}
		DevUser user = (DevUser)request.getSession().getAttribute(Constants.DEV_USER_SESSION);
		
		appVersion.setCreatedBy(user.getId());
		appVersion.setCreationDate(new Date());
		appVersion.setApkLocPath(idPicPath);
		appVersion.setDownloadLink(idPicPath2);
		
		int num = appVersionService.AppVersionadd(appVersion);
		
		System.out.println(appVersion.getId());
		
		int updatenum = appInfoservice.updateAppInfoversionId(appInfo.getId().toString(),appVersion.getId().toString());
		
		if(updatenum>0){
			return "redirect:/dev/list";
		}
		
		return "pre/appversionadd";
	}
	
	/**
	 * 跳转到APP修改页面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getappinfomodify")
	public String getappinfomodify(@RequestParam(value="id")String id,
			HttpServletRequest request){
		AppInfo appInfo = appInfoservice.getAppInfoByid(id);
		request.setAttribute("appInfo", appInfo);
		return "pre/appinfomodify";
	}
	
	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delfile.json",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> delfile(@RequestParam(value="id")String id,@RequestParam(value="flag")String flag){
		HashMap<String,String> resultMap = new HashMap<String,String>();
		
		if(flag.equals("apk")){
			
			int num = appVersionService.updateAppVersionApk(id);
			if(num > 0){
				resultMap.put("result", "success");
				AppVersion appVersion = appVersionService.getAppVersionByid(id);
				File file = new File(appVersion.getApkLocPath());
				file.delete();
			}else{
				resultMap.put("result", "failed");
			}
			
		}else if(flag.equals("logo")){
			
			int num = appInfoservice.updatedelfile(id);
			if(num > 0){
				resultMap.put("result","success");
				AppInfo appInfo = appInfoservice.getAppInfoByid(id);
				File file = new File(appInfo.getLogoLocPath());
				file.delete();
			}else{
				resultMap.put("result","failed");
			}
			
		}
		
		return resultMap;
	}
	
	/**
	 * 修改APP
	 * @param appInfo
	 * @param request
	 * @param attach
	 * @return
	 */
	@RequestMapping(value="/appinfomodify",method=RequestMethod.POST)
	public String appinfomodify(AppInfo appInfo,HttpServletRequest request,
			@RequestParam(value="attach",required=false)MultipartFile attach){
		
			String idPicPath = null;
			String idPicPath2 = null;
			if(!attach.isEmpty()){
				String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
				
				String oldFileName = attach.getOriginalFilename();//原文件名
				String perfix = FilenameUtils.getExtension(oldFileName);
				int filesize=500000;
				if(attach.getSize() > filesize){
					request.setAttribute("uploadFileError", "*上传大小不得超过500KB");
					return "pre/appinfomodify";
				}else if(perfix.equalsIgnoreCase("jpg")
						|| perfix.equalsIgnoreCase("png")
						|| perfix.equalsIgnoreCase("jpeg")
						|| perfix.equalsIgnoreCase("pneg")){
					String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";
					File targetFile = new File(path, fileName);
					if(!targetFile.exists()){
						targetFile.mkdirs();
					}
					
					try {
						attach.transferTo(targetFile);	
						
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("uploadFileError", "*上传失败");
						return "pre/appinfomodify";
					}
					idPicPath = path+File.separator+fileName;
					idPicPath2 = request.getContextPath()+"/statics/uploadfiles/"+fileName;
					
				}else{
					request.setAttribute("uploadFileError", "*上传图片格式不正确");
					return "pre/appinfomodify";
				}
				appInfo.setLogoPicPath(idPicPath2);
				appInfo.setLogoLocPath(idPicPath);
			}
						
		DevUser user = (DevUser)request.getSession().getAttribute(Constants.DEV_USER_SESSION);
		appInfo.setModifyBy(user.getId());
		appInfo.setModifyDate(new Date());
		appInfo.setUpdateDate(new Date());
		
		
		if(appInfoservice.updateAppInfo(appInfo) > 0){
			return "redirect:/dev/list";
		}
		return "pre/appinfomodify";
	}
	
	/**
	 * 查看APP
	 * @param aid
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/appview/{aid}",method=RequestMethod.GET)
	public String AppinfoView(@PathVariable String aid,HttpServletRequest request){
		AppInfo appInfo = appInfoservice.getAppInfoByid(aid);
		List<AppVersion> appVersionList = appVersionService.getAppVersionList(aid);
		request.setAttribute("appInfo", appInfo);
		request.setAttribute("appVersionList", appVersionList);
		return "pre/appinfoview";
	}
	
	/**
	 * 删除APP
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delapp.json",method=RequestMethod.GET)
	@ResponseBody
	public HashMap<String,String> deleteApp(String id){
		HashMap<String,String> resultMap = new HashMap<String,String>();
		
		AppInfo appInfo = appInfoservice.getAppInfoByid(id);
		
		if(appInfo != null){
			List<AppVersion> appVersionList = appVersionService.getAppVersionList(appInfo.getId().toString());
			
			File file = new File(appInfo.getLogoLocPath());
			file.delete();
			
			for(AppVersion appVersion:appVersionList){
				File file2 = new File(appVersion.getApkLocPath());
				file2.delete();
			}
			
			int appVersionNum = appVersionService.deleteAppVersion(id);
			
			if(appVersionNum > 0){
				int appInfonum = appInfoservice.deleteApp(id);
				if(appInfonum > 0){
					resultMap.put("delResult", "true");
				}else{
					resultMap.put("delResult", "false");
				}
			}else{
				resultMap.put("delResult", "false");
			}		
		}else{
			resultMap.put("delResult", "notexist");
		}

		return resultMap;
	}
	
	/**
	 * APP上架、下架
	 * @param appId
	 * @return
	 */
	@RequestMapping(value="{appId}/sale.json",method=RequestMethod.PUT)
	@ResponseBody
	public HashMap<String,String> saleSwitchAjax(@PathVariable String appId){
		HashMap<String,String> resultMap = new HashMap<String,String>();
		
		AppInfo appInfo = appInfoservice.getAppInfoByid(appId);
		
		if(appInfo != null){
			resultMap.put("errorCode", "0");
			
			if(appInfo.getStatus() == 4){
				appInfo.setStatus(5);
				appInfo.setOffSaleDate(new Date());
				int num = appInfoservice.updateStatus(appInfo);
				if(num > 0){
					resultMap.put("resultMsg", "success");
				}else{
					resultMap.put("resultMsg", "failed");
				}
			}else if(appInfo.getStatus() == 5){
				appInfo.setStatus(4);
				appInfo.setOnSaleDate(new Date());
				int num = appInfoservice.updateStatus(appInfo);
				if(num > 0){
					resultMap.put("resultMsg", "success");
				}else{
					resultMap.put("resultMsg", "failed");
				}
			}else if(appInfo.getStatus() == 2){
				appInfo.setStatus(4);
				appInfo.setOnSaleDate(new Date());
				int num = appInfoservice.updateStatus(appInfo);
				if(num > 0){
					resultMap.put("resultMsg", "success");
				}else{
					resultMap.put("resultMsg", "failed");
				}
			}
			
		}
		
		return resultMap;
	}
	
	/**
	 * 修改下载量
	 * @param appId
	 * @return
	 */
	@RequestMapping(value="/xia.json",method=RequestMethod.GET)
	@ResponseBody
	public HashMap<String,String> UpdateSize(@RequestParam(value="appId") String appId){
		HashMap<String,String> resultMap = new HashMap<String,String>();
		AppInfo appInfo = appInfoservice.getAppInfoByid(appId);
		
		appInfo.setDownloads(appInfo.getDownloads()+1);
		
		int num = appInfoservice.updateDownloads(appId, appInfo.getDownloads().toString());
		if(num > 0){
			resultMap.put("resultMsg", "success");
		}else{
			resultMap.put("resultMsg", "failed");
		}
		
		return resultMap;
	}
	
	/**
	 * 导出excel表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/export")
	@ResponseBody
	public String export(@RequestParam(value="currentPageNo",required=false) String currentPageNo,
			HttpServletRequest request, HttpServletResponse response){
		DevUser user = (DevUser)request.getSession().getAttribute(Constants.DEV_USER_SESSION);
		
		// 获取数据
		List<AppInfo> list = null;
		
		if(currentPageNo != null){
			list = devUserservice.AppInfoList(null, null, null, null, null, null, user.getId(),Integer.parseInt(currentPageNo),Constants.pageSize);
		}else{
			list = appInfoservice.AppInfoList(null, null, null, null, null, null, null);
		}
		
		// excel标题
		String[] title = { "软件ID", "软件名称", "APk名称", "软件大小", "所属平台", "所属分类",
				"状态", "下载次数", "最新版本" };

		// excel文件名
		String fileName = "App信息表" + System.currentTimeMillis() + ".xls";

		// sheet名
		String sheetName = "App信息表";
		String[][] content = new String[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			content[i] = new String[title.length];
			AppInfo obj = list.get(i);
			content[i][0] = obj.getId().toString();
			content[i][1] = obj.getSoftwareName();
			content[i][2] = obj.getAPKName();
			content[i][3] = obj.getSoftwareSize().toString();
			content[i][4] = obj.getFlatformName();
			content[i][5] = obj.getCategoryLevel1Name() + "-"
					+ obj.getCategoryLevel2Name() + "-"
					+ obj.getCategoryLevel3Name();
			content[i][6] = obj.getStatusName();
			content[i][7] = obj.getDownloads().toString();
			content[i][8] = obj.getVersionNo();
		}
		// 创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content,null);
		// 响应到客户端
		try {

			this.setResponseHeader(response, fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);

			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/dev/list";
	}

	// 发送响应流方法
	public void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
