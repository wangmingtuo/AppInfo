package cn.appsys.service.backendUser;

import cn.appsys.pojo.BackendUser;

public interface BackendUserService {
	
	/**
	 * 系统管理员登录
	 * @param userCode
	 * @return
	 */
	public BackendUser BackendUserLogin(String userCode);

}
