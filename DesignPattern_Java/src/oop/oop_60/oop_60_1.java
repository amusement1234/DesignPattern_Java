// 60 | 策略模式（上）：如何避免冗长的if-else/switch分支判断代码？

// 定义一族算法类，将每个算法分别封装起来，让它们可以互相替换。策略模式可以使算法的变化独立于使用它们的客户端（这里的客户端代指使用算法的代码）。

// 1. 策略的定义
public interface Strategy {
    void algorithmInterface();
  }
  
  public class ConcreteStrategyA implements Strategy {
    @Override
    public void  algorithmInterface() {
      //具体的算法...
    }
  }
  
  public class ConcreteStrategyB implements Strategy {
    @Override
    public void  algorithmInterface() {
      //具体的算法...
    }
  }



// 2. 策略的创建
public class StrategyFactory {
    private static final Map<String, Strategy> strategies = new HashMap<>();
  
    static {
      strategies.put("A", new ConcreteStrategyA());
      strategies.put("B", new ConcreteStrategyB());
    }
  
    public static Strategy getStrategy(String type) {
      if (type == null || type.isEmpty()) {
        throw new IllegalArgumentException("type should not be empty.");
      }
      return strategies.get(type);
    }
  }


//   如果策略类是有状态的，根据业务场景的需要，我们希望每次从工厂方法中，获得的都是新创建的策略对象，而不是缓存好可共享的策略对象，那我们就需要按照如下方式来实现策略工厂类。
public class StrategyFactory {
    public static Strategy getStrategy(String type) {
      if (type == null || type.isEmpty()) {
        throw new IllegalArgumentException("type should not be empty.");
      }
  
      if (type.equals("A")) {
        return new ConcreteStrategyA();
      } else if (type.equals("B")) {
        return new ConcreteStrategyB();
      }
  
      return null;
    }
  }


// 3. 策略的使用


// 策略接口：EvictionStrategy
// 策略类：LruEvictionStrategy、FifoEvictionStrategy、LfuEvictionStrategy...
// 策略工厂：EvictionStrategyFactory
public class UserCache {
    private Map<String, User> cacheData = new HashMap<>();
    private EvictionStrategy eviction;
  
    public UserCache(EvictionStrategy eviction) {
      this.eviction = eviction;
    }
  
    //...
  }
  
  // 运行时动态确定，根据配置文件的配置决定使用哪种策略
  public class Application {
    public static void main(String[] args) throws Exception {
      EvictionStrategy evictionStrategy = null;
      Properties props = new Properties();
      props.load(new FileInputStream("./config.properties"));
      String type = props.getProperty("eviction_type");
      evictionStrategy = EvictionStrategyFactory.getEvictionStrategy(type);
      UserCache userCache = new UserCache(evictionStrategy);
      //...
    }
  }
  
  // 非运行时动态确定，在代码中指定使用哪种策略
  public class Application {
    public static void main(String[] args) {
      //...
      EvictionStrategy evictionStrategy = new LruEvictionStrategy();
      UserCache userCache = new UserCache(evictionStrategy);
      //...
    }
  }