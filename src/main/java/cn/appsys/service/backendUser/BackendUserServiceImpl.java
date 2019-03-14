package cn.appsys.service.backendUser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.backendUser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;

@Service
public class BackendUserServiceImpl implements BackendUserService {

	@Resource
	private BackendUserMapper backend;
	
	@Override
	public BackendUser BackendUserLogin(String userCode) {
		return backend.BackendUserLogin(userCode);
	}

}
