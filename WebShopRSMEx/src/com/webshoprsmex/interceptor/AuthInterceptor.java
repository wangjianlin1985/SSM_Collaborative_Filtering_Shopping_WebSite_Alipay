package com.webshoprsmex.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.webshoprsmex.util.Constant;

/**
 * 权限拦截器，登录拦截（有部分操作是需要用户或者管理员登录后才能有权限操作）
 */
public class AuthInterceptor implements HandlerInterceptor{
	
	//前台用户验证，map集合中的url可不需要登录验证
	private static Map<String,String> frontUriMap = new HashMap<String, String>();
	//后台管理员验证，map集合中的url可不需要登录验证	
	private static Map<String,String> adminUriMap = new HashMap<String, String>();
	
	static{
		//前台用户
		frontUriMap.put("/,",null);
		frontUriMap.put("/front,",null);
		frontUriMap.put("/login,",null);
		frontUriMap.put("/doLogin,",null);
		frontUriMap.put("/register,",null);
		frontUriMap.put("/doRegister,",null);
		frontUriMap.put("/quit,",null);
		frontUriMap.put("/front/item/view,",null);
		frontUriMap.put("/front/item/list,",null);
		
		//支付宝支付成功后返回接口
		frontUriMap.put("/front/order/doPayReturn,",null);
		//忘记密码
		frontUriMap.put("/front/user/forgetPassword,",null);
		frontUriMap.put("/front/user/doForgetPassword,",null);
		frontUriMap.put("/front/doValcode,",null);
		
		//后台管理员
		adminUriMap.put("/admin/login,",null);
		adminUriMap.put("/admin/doLogin,",null);
		adminUriMap.put("/admin/quit,",null);
	}
	
	/**
	 * 用户请求控制器中的具体方法执行之前进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());
        System.out.println("权限拦截："+url);
        HttpSession session = request.getSession();
        //管理员权限设置开始
  		if(url.startsWith("/admin")){
  			if(!adminUriMap.containsKey(url+",")){
				if(session.getAttribute(Constant.session_admin)==null){
					if (request.getHeader("x-requested-with") != null && 
							request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
						//成立的话说明这是ajax请求过来的
						return false;//如果没有权限，需要登录
					}else{
						response.sendRedirect(contextPath+"/admin/login");
  						return false;//如果没有权限，需要登录
					}
				}
  			}
  		}
  		//管理员权限设置结束
  		//前台权限设置开始
  		if(url.startsWith("/front")){
  			if(!frontUriMap.containsKey(url+",")){
  				if(session.getAttribute(Constant.session_user)==null){
  					if (request.getHeader("x-requested-with") != null && 
							request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
						//成立的话说明这是ajax请求过来的
						return false;//如果没有权限，需要登录
					}else{
						response.sendRedirect(contextPath+"/login");
  						return false;//如果没有权限，需要登录
					}
  				}
  			}
  		}
  		//前台权限设置结束
		return true;
	}

	/**
	 * 用户请求控制器中的具体方法执行之后进行拦截，这里不做处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	/**
	 * 用户请求控制器中的具体方法执行中进行拦截，这里不做处理
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
	}

}
