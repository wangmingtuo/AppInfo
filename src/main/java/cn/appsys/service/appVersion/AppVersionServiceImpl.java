package cn.appsys.service.appVersion;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appVersion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;

@Service
public class AppVersionServiceImpl implements AppVersionService {

	@Resource
	private AppVersionMapper appVersionMapper;
	
	@Override
	public List<AppVersion> getAppVersionList(String aid) {
		return appVersionMapper.getAppVersionList(aid);
	}

	@Override
	public AppVersion getAppVersionByid(String vid) {
		return appVersionMapper.getAppVersionByid(vid);
	}

	@Override
	public int AppVersionadd(AppVersion appVersion) {
		return appVersionMapper.AppVersionadd(appVersion);
	}

	@Override
	public int updateAppVersionApk(String vid) {
		return appVersionMapper.updateAppVersionApk(vid);
	}

	@Override
	public int updateAppVersion(AppVersion appVersion) {
		return appVersionMapper.updateAppVersion(appVersion);
	}

	@Override
	public int deleteAppVersion(String vid) {
		return appVersionMapper.deleteAppVersion(vid);
	}

}
