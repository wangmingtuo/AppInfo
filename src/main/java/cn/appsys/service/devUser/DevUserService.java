package cn.appsys.service.devUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DevUser;

public interface DevUserService {
	
	/**
	 * 开发者平台登录，通过用户名来查找开发者用户
	 * @param devCode
	 * @return
	 */
	public DevUser DevUserLogin(String devCode);
	
	/**
	 * 根据条件查询APP信息列表
	 * @param appinfo
	 * @param page
	 * @return
	 */
	public List<AppInfo> AppInfoList(String querySoftwareName,Integer queryStatus,Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,Integer queryCategoryLevel3,Integer queryFlatformId,
			Integer devId,Integer currentPageNo,Integer pageSize);

	/**
	 * 查询APP信息总数
	 * @return
	 */
	public int total(String querySoftwareName,Integer queryStatus,Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,Integer queryCategoryLevel3,Integer queryFlatformId,
			Integer devId);
	
}
