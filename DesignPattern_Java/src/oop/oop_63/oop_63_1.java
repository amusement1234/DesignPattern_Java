// 63 | 职责链模式（下）：框架中常用的过滤器、拦截器是如何实现的？


public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      // 在创建Filter时自动调用，
      // 其中filterConfig包含这个Filter的配置参数，比如name之类的（从配置文件中读取的）
    }
  
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      System.out.println("拦截客户端发送来的请求.");
      chain.doFilter(request, response);
      System.out.println("拦截发送给客户端的响应.");
    }
  
    @Override
    public void destroy() {
      // 在销毁Filter时自动调用
    }
  }
  
//   // 在web.xml配置文件中如下配置：
//   <filter>
//     <filter-name>logFilter</filter-name>
//     <filter-class>com.xzg.cd.LogFilter</filter-class>
//   </filter>
//   <filter-mapping>
//       <filter-name>logFilter</filter-name>
//       <url-pattern>/*</url-pattern>
//   </filter-mapping>





  
public final class ApplicationFilterChain implements FilterChain {
  private int pos = 0; //当前执行到了哪个filter
  private int n; //filter的个数
  private ApplicationFilterConfig[] filters;
  private Servlet servlet;
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response) {
    if (pos < n) {
      ApplicationFilterConfig filterConfig = filters[pos++];
      Filter filter = filterConfig.getFilter();
      filter.doFilter(request, response, this);
    } else {
      // filter都处理完毕后，执行servlet
      servlet.service(request, response);
    }
  }
  
  public void addFilter(ApplicationFilterConfig filterConfig) {
    for (ApplicationFilterConfig filter:filters)
      if (filter==filterConfig)
         return;

    if (n == filters.length) {//扩容
      ApplicationFilterConfig[] newFilters = new ApplicationFilterConfig[n + INCREMENT];
      System.arraycopy(filters, 0, newFilters, 0, n);
      filters = newFilters;
    }
    filters[n++] = filterConfig;
  }
}



@Override
public void doFilter(ServletRequest request, ServletResponse response) {
  if (pos < n) {
    ApplicationFilterConfig filterConfig = filters[pos++];
    Filter filter = filterConfig.getFilter();
    //filter.doFilter(request, response, this);
    //把filter.doFilter的代码实现展开替换到这里
    System.out.println("拦截客户端发送来的请求.");
    chain.doFilter(request, response); // chain就是this
    System.out.println("拦截发送给客户端的响应.")
  } else {
    // filter都处理完毕后，执行servlet
    servlet.service(request, response);
  }
}