package cn.appsys.dao.appInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.appsys.pojo.AppInfo;

@Repository
public interface AppInfoMapper {
	
	/**
	 * 查询AppInfo
	 * @param APKName
	 * @return
	 */
	public AppInfo getAppInfo(@Param("APKName")String APKName);
	
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
	public AppInfo getAppInfoByid(@Param("aid")String aid);
	
	/**
	 * 清除APP的LOGO图片
	 * @param aid
	 * @return
	 */
	public int updatedelfile(@Param("aid")String aid);
	
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
	public int updateAppInfoversionId(@Param("aid")String aid,@Param("versionId")String versionId);

	/**
	 * 删除APP
	 * @param id
	 * @return
	 */
	public int deleteApp(@Param("id")String id);
	
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
	public int updateDownloads(@Param("id")String id,@Param("downloads")String downloads);

	/**
	 * 修改审核状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updatechecksave(@Param("id")String id,@Param("status")String status);
	
	
	/**
	 * 导出Excle表格
	 * @param appinfo
	 * @param page
	 * @return
	 */
	public List<AppInfo> AppInfoList(@Param(value="softwareName")String querySoftwareName,
			@Param(value="status")Integer queryStatus,
			@Param(value="categoryLevel1")Integer queryCategoryLevel1,
			@Param(value="categoryLevel2")Integer queryCategoryLevel2,
			@Param(value="categoryLevel3")Integer queryCategoryLevel3,
			@Param(value="flatformId")Integer queryFlatformId,
			@Param(value="devId")Integer devId);
}
