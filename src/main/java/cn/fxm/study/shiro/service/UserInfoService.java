package cn.fxm.study.shiro.service;

import cn.fxm.study.shiro.bean.UserInfo;

public interface UserInfoService {
    /** 通过username查找用户信息；*/
    public UserInfo findByUsername(String username);
}