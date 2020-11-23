//package com.lsw.springcloud.test;
//
//import java.util.Arrays;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.config.IniSecurityManagerFactory;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.util.Factory;
//import org.junit.Assert;
//import org.junit.Test;
//import org.apache.shiro.mgt.SecurityManager;
//
//public class ShiroTest {
//	@Test
//	public void testHelloworld() {
//		// 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
//		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini"); //采用自带的iniRealm验证.
////		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini"); //采用自定义的Realm验证.
////		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-multi-realm.ini"); //采用自定义的Realm验证.
//		// 2、得到SecurityManager实例 并绑定给SecurityUtils
//		SecurityManager securityManager = factory.getInstance();
//		SecurityUtils.setSecurityManager(securityManager);
//		Subject subject = SecurityUtils.getSubject();
////		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "1234");
//		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
////		UsernamePasswordToken token = new UsernamePasswordToken("wang", "123");
////		
//		// 二 角色认证
//        //判断拥有角色：role1
//        Assert.assertTrue(subject.hasRole("role1"));
//        //判断拥有角色：role1 and role2
//        Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));
//        //判断拥有角色：role1 and role2 and !role3
//        boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));
//        Assert.assertEquals(true, result[0]);
//        Assert.assertEquals(true, result[1]);
//        Assert.assertEquals(false, result[2]);
//		try {
//			// 4、登录，即身份验证
//			subject.login(token);
//		} catch (AuthenticationException e) {
//			// 5、身份验证失败
//		}
//		Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录
//		// 6、退出
//		subject.logout();
//	}
//}
