package cn.appsys.service.devUser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.devUser.DevUserMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DevUser;

@Service
public class DevUserServiceImpl implements DevUserService {

	@Resource
	private DevUserMapper devUser;
	
	@Override
	public DevUser DevUserLogin(String devCode) {
		return devUser.DevUserLogin(devCode);
	}

	@Override
	public List<AppInfo> AppInfoList(String querySoftwareName, Integer queryStatus,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2,
			Integer queryCategoryLevel3, Integer queryFlatformId,
			Integer devId, Integer currentPageNo, Integer pageSize) {
		int index = (currentPageNo-1)*pageSize;
		return devUser.AppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, 
				queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, index,
				pageSize);
	}

	@Override
	public int total(String querySoftwareName,Integer queryStatus,Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,Integer queryCategoryLevel3,Integer queryFlatformId,
			Integer devId) {
		return devUser.total(querySoftwareName, queryStatus, queryCategoryLevel1, 
				queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
	}

}
