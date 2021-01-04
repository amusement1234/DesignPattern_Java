// 45 | 工厂模式（下）：如何设计实现一个Dependency Injection框架？

// 工厂模式和 DI 容器有何区别？

// 实际上，DI 容器底层最基本的设计思路就是基于工厂模式的。DI 容器相当于一个大的工厂类，负责在程序启动的时候，根据配置（要创建哪些类对象，每个类对象的创建需要依赖哪些其他类对象）事先创建好对象。当应用程序需要使用某个类对象的时候，直接从容器中获取即可。正是因为它持有一堆对象，所以这个框架才被称为“容器”。

// 一个工厂类只负责某个类对象或者某一组相关类对象（继承自同一抽象类或者接口的子类）的创建，而 DI 容器负责的是整个应用中所有类对象的创建。

// DI 容器的核心功能有哪些？

// 一个简单的 DI 容器的核心功能一般有三个：配置解析、对象创建和对象生命周期管理。

// 首先，我们来看配置解析。
public class RateLimiter {
    private RedisCounter redisCounter;
    public RateLimiter(RedisCounter redisCounter) {
      this.redisCounter = redisCounter;
    }
    public void test() {
      System.out.println("Hello World!");
    }
    //...
  }
  
  public class RedisCounter {
    private String ipAddress;
    private int port;
    public RedisCounter(String ipAddress, int port) {
      this.ipAddress = ipAddress;
      this.port = port;
    }
    //...
  }
  
//   配置文件beans.xml：
//   <beans>
//      <bean id="rateLimiter" class="com.xzg.RateLimiter">
//         <constructor-arg ref="redisCounter"/>
//      </bean>
   
//      <bean id="redisCounter" class="com.xzg.redisCounter">
//        <constructor-arg type="String" value="127.0.0.1">
//        <constructor-arg type="int" value=1234>
//      </bean>
//   </beans>

 