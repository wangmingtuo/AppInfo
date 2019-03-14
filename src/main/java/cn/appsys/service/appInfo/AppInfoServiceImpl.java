package cn.appsys.service.appInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appInfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Service
public class AppInfoServiceImpl implements AppInfoService {

	@Resource
	private AppInfoMapper appInfoMapper;
	
	@Override
	public AppInfo getAppInfo(String APKName) {
		return appInfoMapper.getAppInfo(APKName);
	}

	/**
	 * 添加App
	 * @param appInfo
	 * @return
	 */
	public int addAppInfo(AppInfo appInfo){
		return appInfoMapper.addAppInfo(appInfo);
	}

	@Override
	public AppInfo getAppInfoByid(String aid) {
		return appInfoMapper.getAppInfoByid(aid);
	}

	@Override
	public int updatedelfile(String aid) {
		return appInfoMapper.updatedelfile(aid);
	}

	@Override
	public int updateAppInfo(AppInfo appInfo) {
		return appInfoMapper.updateAppInfo(appInfo);
	}

	@Override
	public int updateAppInfoversionId(String aid, String versionId) {
		return appInfoMapper.updateAppInfoversionId(aid, versionId);
	}

	@Override
	public int deleteApp(String id) {
		return appInfoMapper.deleteApp(id);
	}

	@Override
	public int updateStatus(AppInfo appInfo) {
		return appInfoMapper.updateStatus(appInfo);
	}

	@Override
	public int updateDownloads(String id, String downloads) {
		return appInfoMapper.updateDownloads(id, downloads);
	}

	@Override
	public int updatechecksave(String id, String status) {
		return appInfoMapper.updatechecksave(id, status);
	}

	@Override
	public List<AppInfo> AppInfoList(String querySoftwareName,
			Integer queryStatus, Integer queryCategoryLevel1,
			Integer queryCategoryLevel2, Integer queryCategoryLevel3,
			Integer queryFlatformId, Integer devId) {
		return appInfoMapper.AppInfoList(querySoftwareName, queryStatus,
				queryCategoryLevel1, queryCategoryLevel2, 
				queryCategoryLevel3, queryFlatformId, devId);
	}

	
}
