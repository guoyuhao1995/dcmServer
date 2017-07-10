package com.dryork.vision.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.dryork.vision.base.util.LogUtils;


/**
 * 监听方法执行时间，以debug模式记录
 * @author leoliang
 *
 */
public class ListenRequestTimeInterceptor extends  HandlerInterceptorAdapter{
	 
	 private NamedThreadLocal<Long>  requestTimeThreadLocal =  new NamedThreadLocal<Long>("listen request time");  
	 
	 @Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		   long beginTime = System.currentTimeMillis();//1、请求开始时间  
		   requestTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）  
		return true;
	
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		long endTime = System.currentTimeMillis();//2、结束时间  
        long beginTime = requestTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
        long consumeTime = endTime - beginTime;//3、消耗的时间  
        /**
         * 将方法的执行时间记录到日志
         */
        LogUtils.REQUEST_TIME.debug("{}{}\t{}{}",
        		"请求地址：",request.getServletPath(),
        		"执行时间：",consumeTime+"ms");
        if(ex!=null){
			LogUtils.ERROR.error(request.getRequestURI(),ex);
        	LogUtils.ERROR.error(JSON.toJSON(request.getParameterMap()));
        }
		
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
	
}
