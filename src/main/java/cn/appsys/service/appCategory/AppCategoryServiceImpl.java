package cn.appsys.service.appCategory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appCategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;

@Service
public class AppCategoryServiceImpl implements AppCategoryService {

	@Resource
	private AppCategoryMapper appCategory;
	
	@Override
	public List<AppCategory> getAppCategoryList(Integer parentId) {
		return appCategory.getAppCategoryList(parentId);
	}
	
	

}
