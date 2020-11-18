package com.dcfs.smartaibank.manager.base.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.base.domain.ChangePassword;
import com.dcfs.smartaibank.manager.base.domain.MediumInfo;
import com.dcfs.smartaibank.manager.base.domain.MediumType;
import com.dcfs.smartaibank.manager.base.domain.OrgLevel;
import com.dcfs.smartaibank.manager.base.domain.PwdType;
import com.dcfs.smartaibank.manager.base.service.PwdService;
import com.dcfs.smartaibank.manager.base.dao.PwdDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 密码管理服务
 *
 * @author
 */
@Service
public class PwdServiceImpl implements PwdService {

	@Autowired
	private PwdDao pwdDao;


	@Override
	public Integer updateById(MediumInfo entity) {
		try {
			entity.setMediumInfoUpdateTime(new Date());
			entity.setMediumType(MediumType.PASSWORD);
			return pwdDao.updateByPrimaryKey(entity);
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}

	@Override
	public Map<String, String> resetById(String id, String resetId, String orgId) {
		try {
			Map<String, String> map = new HashMap<String, String>(16);
			String s = pwdDao.checkOrgLevel(orgId);
			MediumInfo entity = new MediumInfo();
			entity.setId(resetId);
			entity.setStatus(PwdType.NORMAL);
			entity.setMediumInfoUpdateTime(new Date());
			entity.setMediumInfoLockTime(new Date());
			entity.setMediumType(MediumType.PASSWORD);
			entity.setMediumInfo(ChangePassword.ONCHANGE_PASSWORD);
			if (s.equals(OrgLevel.HEAD.getDesc())) {
				pwdDao.resetPwd(entity);
				map.put("success", "密码重置成功");
				return map;
			}
			String s1 = pwdDao.checkOrgUser(id, orgId);
			if (s1 != null) {
				pwdDao.resetPwd(entity);
				map.put("success", "密码重置成功");
				return map;
			} else {
				map.put("fail", "您无权重置该用户的密码");
				return map;
			}
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}

	}

	@Override
	public Integer lockById(String id) {
		try {
			MediumInfo entity = new MediumInfo();
			entity.setId(id);
			entity.setStatus(PwdType.LOCK);
			entity.setMediumType(MediumType.PASSWORD);
			entity.setMediumInfoLockTime(new Date());
			return pwdDao.lockPwd(entity);
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}

	@Override
	public String getUserName(String id) {
		return pwdDao.getUserName(id);
	}

	@Override
	public MediumInfo selectById(String id) {
		try {
			MediumInfo entity = new MediumInfo();
			entity.setId(id);
			entity.setStatus(PwdType.NORMAL);
			entity.setMediumType(MediumType.PASSWORD);
			return pwdDao.selectOne(entity);
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}
}
