package com.dcfs.smartaibank.manager.base.service;

import com.dcfs.smartaibank.manager.base.domain.Org;
import com.dcfs.smartaibank.manager.base.domain.SpareDataDomain;
import com.dcfs.smartaibank.springboot.core.base.service.BaseService;
import com.dcfs.smartaibank.springboot.core.base.service.SelectPageByBeanService;

import java.util.List;
import java.util.Map;

/**
 * 机构信息维护接口
 *
 * @author wangtingo
 */
public interface OrgService extends BaseService<String, Org>, SelectPageByBeanService<Org> {
	/**
	 * 根据类型和级别查询机构的业务管理上级备选数据和账务机构
	 *
	 * @param queryMap
	 * @return
	 */
	Map<String, List<SpareDataDomain>> selectQuerySuperior(Map<String, Object> queryMap);

	/**
	 * 根据类型查询机构级别的备选数据
	 *
	 * @param queryMap
	 * @return
	 */
	List<SpareDataDomain> selectLevel(Map<String, Object> queryMap);
}
