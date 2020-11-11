// 假设我们在开发一个 P2P 投资理财系统，用户注册成功之后，我们会给用户发放投资体验金。代码实现大致是下面这个样子的：


public class UserController {
    private UserService userService; // 依赖注入
    private PromotionService promotionService; // 依赖注入
  
    public Long register(String telephone, String password) {
      //省略输入参数的校验代码
      //省略userService.register()异常的try-catch代码
      long userId = userService.register(telephone, password);
      promotionService.issueNewUserExperienceCash(userId);
      return userId;
    }
  }

//   虽然注册接口做了两件事情，注册和发放体验金，违反单一职责原则，但是，如果没有扩展和修改的需求，现在的代码实现是可以接受的。如果非得用观察者模式，就需要引入更多的类和更加复杂的代码结构，反倒是一种过度设计。

// 相反，如果需求频繁变动，比如，用户注册成功之后，不再发放体验金，而是改为发放优惠券，并且还要给用户发送一封“欢迎注册成功”的站内信。这种情况下，我们就需要频繁地修改 register() 函数中的代码，违反开闭原则。而且，如果注册成功之后需要执行的后续操作越来越多，那 register() 函数的逻辑会变得越来越复杂，也就影响到代码的可读性和可维护性。

// 这个时候，观察者模式就能派上用场了。利用观察者模式，我对上面的代码进行了重构。重构之后的代码如下所示：


public interface RegObserver {
    void handleRegSuccess(long userId);
  }
  
  public class RegPromotionObserver implements RegObserver {
    private PromotionService promotionService; // 依赖注入
  
    @Override
    public void handleRegSuccess(long userId) {
      promotionService.issueNewUserExperienceCash(userId);
    }
  }
  
  public class RegNotificationObserver implements RegObserver {
    private NotificationService notificationService;
  
    @Override
    public void handleRegSuccess(long userId) {
      notificationService.sendInboxMessage(userId, "Welcome...");
    }
  }
  
  public class UserController {
    private UserService userService; // 依赖注入
    private List<RegObserver> regObservers = new ArrayList<>();
  
    // 一次性设置好，之后也不可能动态的修改
    public void setRegObservers(List<RegObserver> observers) {
      regObservers.addAll(observers);
    }
  
    public Long register(String telephone, String password) {
      //省略输入参数的校验代码
      //省略userService.register()异常的try-catch代码
      long userId = userService.register(telephone, password);
  
      for (RegObserver observer : regObservers) {
        observer.handleRegSuccess(userId);
      }
  
      return userId;
    }
  }

//   当我们需要添加新的观察者的时候，比如，用户注册成功之后，推送用户注册信息给大数据征信系统，基于观察者模式的代码实现，UserController 类的 register() 函数完全不需要修改，只需要再添加一个实现了 RegObserver 接口的类，并且通过 setRegObservers() 函数将它注册到 UserController 类中即可。


// 发布-订阅模型（消息队列），是一对多的关系，可以以同步的方式实现，也可以以异步的方式实现。
// 生产-消费模型，是多对多的关系，一般以异步的方式实现
// 两者都可以达到解耦的作用 