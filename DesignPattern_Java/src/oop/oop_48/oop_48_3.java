// 对于这种外部类的扩展，我们一般都是采用继承的方式。这里也不例外。我们让代理类继承原始类，然后扩展附加功能。原理很简单，不需要过多解释，你直接看代码就能明白。具体代码如下所示：

public class UserControllerProxy extends UserController {
    private MetricsCollector metricsCollector;
  
    public UserControllerProxy() {
      this.metricsCollector = new MetricsCollector();
    }
  
    public UserVo login(String telephone, String password) {
      long startTimestamp = System.currentTimeMillis();
  
      UserVo userVo = super.login(telephone, password);
  
      long endTimeStamp = System.currentTimeMillis();
      long responseTime = endTimeStamp - startTimestamp;
      RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimestamp);
      metricsCollector.recordRequest(requestInfo);
  
      return userVo;
    }
  
    public UserVo register(String telephone, String password) {
      long startTimestamp = System.currentTimeMillis();
  
      UserVo userVo = super.register(telephone, password);
  
      long endTimeStamp = System.currentTimeMillis();
      long responseTime = endTimeStamp - startTimestamp;
      RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
      metricsCollector.recordRequest(requestInfo);
  
      return userVo;
    }
  }
  //UserControllerProxy使用举例
  UserController userController = new UserControllerProxy();


//   不过，刚刚的代码实现还是有点问题。一方面，我们需要在代理类中，将原始类中的所有的方法，都重新实现一遍，并且为每个方法都附加相似的代码逻辑。另一方面，如果要添加的附加功能的类有不止一个，我们需要针对每个类都创建一个代理类。

// 如果有 50 个要添加附加功能的原始类，那我们就要创建 50 个对应的代理类。这会导致项目中类的个数成倍增加，增加了代码维护成本。并且，每个代理类中的代码都有点像模板式的“重复”代码，也增加了不必要的开发成本。那这个问题怎么解决呢？

// 我们可以使用动态代理来解决这个问题。所谓动态代理（Dynamic Proxy），就是我们不事先为每个原始类编写代理类，而是在运行的时候，动态地创建原始类对应的代理类，然后在系统中用代理类替换掉原始类。那如何实现动态代理呢？