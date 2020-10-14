// 如果你熟悉的是 Java 语言，实现动态代理就是件很简单的事情。因为 Java 语言本身就已经提供了动态代理的语法（实际上，动态代理底层依赖的就是 Java 的反射语法）。
// 我们来看一下，如何用 Java 的动态代理来实现刚刚的功能。具体的代码如下所示。其中，MetricsCollectorProxy 作为一个动态代理类，动态地给每个需要收集接口请求信息的类创建代理类。

public class MetricsCollectorProxy {
    private MetricsCollector metricsCollector;
  
    public MetricsCollectorProxy() {
      this.metricsCollector = new MetricsCollector();
    }
  
    public Object createProxy(Object proxiedObject) {
      Class<?>[] interfaces = proxiedObject.getClass().getInterfaces();
      DynamicProxyHandler handler = new DynamicProxyHandler(proxiedObject);
      return Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(), interfaces, handler);
    }
  
    private class DynamicProxyHandler implements InvocationHandler {
      private Object proxiedObject;
  
      public DynamicProxyHandler(Object proxiedObject) {
        this.proxiedObject = proxiedObject;
      }
  
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTimestamp = System.currentTimeMillis();
        Object result = method.invoke(proxiedObject, args);
        long endTimeStamp = System.currentTimeMillis();
        long responseTime = endTimeStamp - startTimestamp;
        String apiName = proxiedObject.getClass().getName() + ":" + method.getName();
        RequestInfo requestInfo = new RequestInfo(apiName, responseTime, startTimestamp);
        metricsCollector.recordRequest(requestInfo);
        return result;
      }
    }
  }
  
  //MetricsCollectorProxy使用举例
  MetricsCollectorProxy proxy = new MetricsCollectorProxy();
  IUserController userController = (IUserController) proxy.createProxy(new UserController());