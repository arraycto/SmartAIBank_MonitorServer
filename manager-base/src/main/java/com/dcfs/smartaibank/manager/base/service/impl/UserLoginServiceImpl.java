package com.dcfs.smartaibank.manager.base.service.impl;

import com.dcfs.smartaibank.manager.base.dao.UserDao;
import com.dcfs.smartaibank.manager.base.domain.UserInfo;
import com.dcfs.smartaibank.manager.base.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanchena
 * @date 2019/8/20 9:54
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserDao dao;

	@Override
	public UserInfo userLogin(String id) {
		return dao.selectByUserId(id);
	}
}
