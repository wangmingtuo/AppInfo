package cn.appsys.dao.backendUser;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.appsys.pojo.BackendUser;

/**
 * 系统管理员
 * @author Administrator
 *
 */
@Repository
public interface BackendUserMapper {
	
	/**
	 * 系统管理员登录
	 * @param userCode
	 * @return
	 */
	public BackendUser BackendUserLogin(@Param("userCode")String userCode);

}
