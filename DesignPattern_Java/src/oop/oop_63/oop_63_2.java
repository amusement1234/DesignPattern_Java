
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      System.out.println("拦截客户端发送来的请求.");
      return true; // 继续后续的处理
    }
  
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
      System.out.println("拦截发送给客户端的响应.");
    }
  
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
      System.out.println("这里总是被执行.");
    }
  }
  
//   //在Spring MVC配置文件中配置interceptors
//   <mvc:interceptors>
//      <mvc:interceptor>
//          <mvc:mapping path="/*"/>
//          <bean class="com.xzg.cd.LogInterceptor" />
//      </mvc:interceptor>
//   </mvc:interceptors>





public class HandlerExecutionChain {
    private final Object handler;
    private HandlerInterceptor[] interceptors;
    
    public void addInterceptor(HandlerInterceptor interceptor) {
     initInterceptorList().add(interceptor);
    }
   
    boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
     HandlerInterceptor[] interceptors = getInterceptors();
     if (!ObjectUtils.isEmpty(interceptors)) {
      for (int i = 0; i < interceptors.length; i++) {
       HandlerInterceptor interceptor = interceptors[i];
       if (!interceptor.preHandle(request, response, this.handler)) {
        triggerAfterCompletion(request, response, null);
        return false;
       }
      }
     }
     return true;
    }
   
    void applyPostHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
     HandlerInterceptor[] interceptors = getInterceptors();
     if (!ObjectUtils.isEmpty(interceptors)) {
      for (int i = interceptors.length - 1; i >= 0; i--) {
       HandlerInterceptor interceptor = interceptors[i];
       interceptor.postHandle(request, response, this.handler, mv);
      }
     }
    }
   
    void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex)
      throws Exception {
     HandlerInterceptor[] interceptors = getInterceptors();
     if (!ObjectUtils.isEmpty(interceptors)) {
      for (int i = this.interceptorIndex; i >= 0; i--) {
       HandlerInterceptor interceptor = interceptors[i];
       try {
        interceptor.afterCompletion(request, response, this.handler, ex);
       } catch (Throwable ex2) {
        logger.error("HandlerInterceptor.afterCompletion threw exception", ex2);
       }
      }
     }
    }
   }


