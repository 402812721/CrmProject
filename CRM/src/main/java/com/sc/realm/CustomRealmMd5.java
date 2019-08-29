package com.sc.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.sc.bean.SysUsers;
import com.sc.service.SysUsersService;

public class CustomRealmMd5 extends AuthorizingRealm {

	//ע���û���service
	@Autowired
	SysUsersService sysuserService;
	
	/*@Autowired
	SysPermissionService syspermissionService;*/
	
	//�û���Ȩ
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		
		System.out.println("�����û���Ȩ");
		SysUsers sysuser = (SysUsers)arg0.getPrimaryPrincipal();
		System.out.println("��Ҫ��Ȩ���û��ǣ�"+sysuser.getUserName());
		
		//ͨ���û��������ݿ��ѯ���û���ӵ�е�Ȩ��
		/*ArrayList<String> perms = new ArrayList<String>();
		List<SysPermission> list = syspermissionService.getPermsByUid(sysuser.getId());
		
		if(list != null && list.size() > 0){
			for (SysPermission per : list) {
				String percode = per.getPercode();
				if(percode != null && !percode.equals("")){
					perms.add(per.getPercode());
					System.out.println("------���û�ӵ�е�Ȩ����Դ�ǣ�"+per.getPercode());
				}
				
			}
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if(perms != null && perms.size() > 0){
			info.addStringPermissions(perms);
		}
		return info;*/
		
		return null;
	}

	
	//�û���֤
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("�����Զ���realm");
		//��ȡ��¼���û���
		String uname = (String)arg0.getPrincipal();
		System.out.println("��¼���û����ǣ�" + uname);
		
		SysUsers sysuser = sysuserService.login(uname);
		System.out.println("���ݿ��ѯ���û������ǣ�"+sysuser);
		//ͨ�����û�����ѯ���ݿ��Ƿ���ڸ��û�
		if(sysuser == null){
			return null;//�����ڴ��û���ֱ�ӷ���
		}
		
		String pass = sysuser.getUserPassword();
		String salt = "qwerty";
		//���û��������봫�룬��֤�����Ƿ���ȷ
		SimpleAuthenticationInfo info = 
				new SimpleAuthenticationInfo(sysuser, pass, ByteSource.Util.bytes(salt), super.getName());
		
		return info;
	}

}
