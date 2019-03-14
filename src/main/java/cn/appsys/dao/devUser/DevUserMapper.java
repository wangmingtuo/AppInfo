package cn.appsys.dao.devUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DevUser;

/**
 * 开发者平台用户
 * @author Administrator
 *
 */
@Repository
public interface DevUserMapper {
	
	/**
	 * 开发者平台登录，通过用户名来查找开发者用户
	 * @param devCode
	 * @return
	 */
	public DevUser DevUserLogin(@Param("devCode")String devCode);
	
	
	
	/**
	 * 根据条件查询APP信息列表
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
			@Param(value="devId")Integer devId,
			@Param(value="from")Integer currentPageNo,
			@Param(value="pageSize")Integer pageSize);
	
	/**
	 * 查询APP信息总数
	 * @return
	 */
	public int total(@Param(value="softwareName")String querySoftwareName,
			@Param(value="status")Integer queryStatus,
			@Param(value="categoryLevel1")Integer queryCategoryLevel1,
			@Param(value="categoryLevel2")Integer queryCategoryLevel2,
			@Param(value="categoryLevel3")Integer queryCategoryLevel3,
			@Param(value="flatformId")Integer queryFlatformId,
			@Param(value="devId")Integer devId);

}
