// 57 | 观察者模式（下）：如何实现一个异步非阻塞的EventBus框架？


// 异步非阻塞观察者模式的简易实现

// 我们有两种实现方式。其中一种是：在每个 handleRegSuccess() 函数中创建一个新的线程执行代码逻辑；另一种是：在 UserController 的 register() 函数中使用线程池来执行每个观察者的 handleRegSuccess() 函数。两种实现方式的具体代码如下所示：


// 第一种实现方式，其他类代码不变，就没有再重复罗列
public class RegPromotionObserver implements RegObserver {
    private PromotionService promotionService; // 依赖注入
  
    @Override
    public void handleRegSuccess(Long userId) {
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          promotionService.issueNewUserExperienceCash(userId);
        }
      });
      thread.start();
    }
  }
  
  // 第二种实现方式，其他类代码不变，就没有再重复罗列
  public class UserController {
    private UserService userService; // 依赖注入
    private List<RegObserver> regObservers = new ArrayList<>();
    private Executor executor;
  
    public UserController(Executor executor) {
      this.executor = executor;
    }
  
    public void setRegObservers(List<RegObserver> observers) {
      regObservers.addAll(observers);
    }
  
    public Long register(String telephone, String password) {
      //省略输入参数的校验代码
      //省略userService.register()异常的try-catch代码
      long userId = userService.register(telephone, password);
  
      for (RegObserver observer : regObservers) {
        executor.execute(new Runnable() {
          @Override
          public void run() {
            observer.handleRegSuccess(userId);
          }
        });
      }
  
      return userId;
    }
  }

//   对于第一种实现方式，频繁地创建和销毁线程比较耗时，并且并发线程数无法控制，创建过多的线程会导致堆栈溢出。第二种实现方式，尽管利用了线程池解决了第一种实现方式的问题，但线程池、异步执行逻辑都耦合在了 register() 函数中，增加了这部分业务代码的维护成本。

// 我们知道，框架的作用有：隐藏实现细节，降低开发难度，做到代码复用，解耦业务与非业务代码，让程序员聚焦业务开发。