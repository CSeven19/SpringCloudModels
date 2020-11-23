package com.lsw.springcloud.configbean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.lisw.springcloud.entity.User2;
import com.lisw.springcloud.security.SecurityUser;
import com.lsw.springcloud.service.impl.UserServiceImpl;

/**
 * Created by EalenXie on 2018/1/11.
 * @see 自定义了一个springSecurity安全框架的配置类 继承WebSecurityConfigurerAdapter，重写其中的方法configure
 * 在我们实现该类后，在web容器启动的过程中该类实例对象会被WebSecurityConfiguration类处理。
 * 因为我们的路径被spring secuirty保护起来了，我们是没有权限访问的，所以我们会被引导至登录页面进行登录。
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);
	// 1 :认证配置策略1, configure()方法用来实现spring security的一些自定义的配置
	// 包括Filter的创建。其中http.authorizeRequests()、http.formLogin()、http.httpBasic()分别创建了ExpressionUrlAuthorizationConfigurer，FormLoginConfigurer，HttpBasicConfigurer。
	@Override
	protected void configure(HttpSecurity http) throws Exception { 
		http.csrf().disable();
		// authorizeRequests()对请求做一个授权
		// anyRequest()对任意请求 :antMatchers("/static/**").permitAll().anyRequest().authenticated() 对除了/static以外的所有请求进行权限认证.
		// authenticated()都需要认证
		// formLogin() 表单登陆,内部注册 UsernamePasswordAuthenticationFilter
		http.authorizeRequests().antMatchers("/static/**").permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().successHandler(loginSuccessHandler()).and().logout().permitAll()
				.invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler())
				.and().sessionManagement().maximumSessions(10).expiredUrl("/login");
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // 2:认证配置策略2，可注释掉.
//		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//		auth.eraseCredentials(false);
//	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() { // 密码加密
		return new BCryptPasswordEncoder(4);
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() { // 登出处理
		return new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
					Authentication authentication) throws IOException, ServletException {
				try {
					SecurityUser user = (SecurityUser) authentication.getPrincipal();
					logger.info("USER : " + user.getUsername() + " LOGOUT SUCCESS !  ");
				} catch (Exception e) {
					logger.info("LOGOUT EXCEPTION , e : " + e.getMessage());
				}
				httpServletResponse.sendRedirect("/login");
			}
		};
	}

	// SavedRequestAwareAuthenticationSuccessHandler：进行授权成功后处理。授权成功后，重定向回之前访问的页面即http://localhost:8005页面.
	// 若想重定向需要在onAuthenticationSuccess中
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { // 登入处理
		return new SavedRequestAwareAuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				User2 userDetails = (User2) authentication.getPrincipal();
				logger.info("USER : " + userDetails.getUsername() + " LOGIN SUCCESS !  ");
				super.onAuthenticationSuccess(request, response, authentication);
			}
		};
	}

	@Bean
	public UserDetailsService userDetailsService() { // 用户登录实现 ,UserDetailsService用于获取UserDetail，而UserDetail里包含权限。
		return new UserDetailsService() {
			@Autowired
			private UserServiceImpl userservice;

			@Override
			public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
				User2 user = userservice.findByUsername(s);
				if (user == null)
					throw new UsernameNotFoundException("Username " + s + " not found");
				return new SecurityUser(user);
			}
		};
	}
}