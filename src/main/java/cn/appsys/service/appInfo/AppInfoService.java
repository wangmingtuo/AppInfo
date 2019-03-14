package cn.appsys.service.appInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoService {
	
	/**
	 * 查询AppInfo
	 * @param APKName
	 * @return
	 */
	public AppInfo getAppInfo(String APKName);
	
	/**
	 * 添加App
	 * @param appInfo
	 * @return
	 */
	public int addAppInfo(AppInfo appInfo);
	
	/**
	 * 根据id查询AppInfo
	 * @param APKName
	 * @return
	 */
	public AppInfo getAppInfoByid(String aid);
	
	/**
	 * 清除APP的LOGO图片
	 * @param aid
	 * @return
	 */
	public int updatedelfile(String aid);
	
	/**
	 * 修改APP基本信息
	 * @param appInfo
	 * @return
	 */
	public int updateAppInfo(AppInfo appInfo);
	
	/**
	 * 修改App版本id
	 * @param versionId
	 * @return
	 */
	public int updateAppInfoversionId(String aid,String versionId);
	
	/**
	 * 删除APP
	 * @param id
	 * @return
	 */
	public int deleteApp(String id);
	
	/**
	 * 修改App的上架、下架
	 * @param appInfo
	 * @return
	 */
	public int updateStatus(AppInfo appInfo);
	
	/**
	 * 修改下载量
	 * @param id
	 * @param Downloads
	 * @return
	 */
	public int updateDownloads(String id,String downloads);
	
	/**
	 * 修改审核状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updatechecksave(String id,String status);
	
	/**
	 * 导出Excle表格
	 * @param appinfo
	 * @param page
	 * @return
	 */
	public List<AppInfo> AppInfoList(String querySoftwareName,
			Integer queryStatus,Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,Integer queryCategoryLevel3,
			Integer queryFlatformId,Integer devId);

}
