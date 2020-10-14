// 为了将框架代码和业务代码解耦，代理模式就派上用场了。代理类 UserControllerProxy 和原始类 UserController 实现相同的接口 IUserController。UserController 类只负责业务功能。代理类 UserControllerProxy 负责在业务代码执行前后附加其他逻辑代码，并通过委托的方式调用原始类来执行业务代码。具体的代码实现如下所示：
public interface IUserController {
    UserVo login(String telephone, String password);
    UserVo register(String telephone, String password);
  }
  
  public class UserController implements IUserController {
    //...省略其他属性和方法...
  
    @Override
    public UserVo login(String telephone, String password) {
      //...省略login逻辑...
      //...返回UserVo数据...
    }
  
    @Override
    public UserVo register(String telephone, String password) {
      //...省略register逻辑...
      //...返回UserVo数据...
    }
  }
  
  public class UserControllerProxy implements IUserController {
    private MetricsCollector metricsCollector;
    private UserController userController;
  
    public UserControllerProxy(UserController userController) {
      this.userController = userController;
      this.metricsCollector = new MetricsCollector();
    }
  
    @Override
    public UserVo login(String telephone, String password) {
      long startTimestamp = System.currentTimeMillis();
  
      // 委托
      UserVo userVo = userController.login(telephone, password);
  
      long endTimeStamp = System.currentTimeMillis();
      long responseTime = endTimeStamp - startTimestamp;
      RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimestamp);
      metricsCollector.recordRequest(requestInfo);
  
      return userVo;
    }
  
    @Override
    public UserVo register(String telephone, String password) {
      long startTimestamp = System.currentTimeMillis();
  
      UserVo userVo = userController.register(telephone, password);
  
      long endTimeStamp = System.currentTimeMillis();
      long responseTime = endTimeStamp - startTimestamp;
      RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
      metricsCollector.recordRequest(requestInfo);
  
      return userVo;
    }
  }
  
  //UserControllerProxy使用举例
  //因为原始类和代理类实现相同的接口，是基于接口而非实现编程
  //将UserController类对象替换为UserControllerProxy类对象，不需要改动太多代码
  IUserController userController = new UserControllerProxy(new UserController());



//   参照基于接口而非实现编程的设计思想，将原始类对象替换为代理类对象的时候，为了让代码改动尽量少，在刚刚的代理模式的代码实现中，代理类和原始类需要实现相同的接口。
// 但是，如果原始类并没有定义接口，并且原始类代码并不是我们开发维护的（比如它来自一个第三方的类库），我们也没办法直接修改原始类，给它重新定义一个接口。在这种情况下，我们该如何实现代理模式呢？