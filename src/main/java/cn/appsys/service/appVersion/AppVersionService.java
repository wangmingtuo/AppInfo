package cn.appsys.service.appVersion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	
	/**
	 * 根据appid查询全部版本
	 * @param aid
	 * @return
	 */
	public List<AppVersion> getAppVersionList(String aid);

	/**
	 * 查询最新的版本
	 * @param vid
	 * @return
	 */
	public AppVersion getAppVersionByid(String vid);
	
	/**
	 * 新增版本信息
	 * @param appVersion
	 * @return
	 */
	public int AppVersionadd(AppVersion appVersion);
	
	/**
	 * 删除Apk的路径
	 * @param id
	 * @return
	 */
	public int updateAppVersionApk(String vid);
	
	/**
	 * 修改App版本
	 * @param appVersion
	 * @return
	 */
	public int updateAppVersion(AppVersion appVersion);
	
	/**
	 * 删除App版本
	 * @param vid
	 * @return
	 */
	public int deleteAppVersion(String vid);

}
