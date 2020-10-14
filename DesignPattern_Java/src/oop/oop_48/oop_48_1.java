
// 代理模式

// 代理模式。它在不改变原始类（或者叫被代理类）代码的情况下，通过引入代理类来给原始类附加功能。代理模式在平时的开发经常被用到，常用在业务系统中开发一些非功能性需求，比如：监控、统计、鉴权、限流、事务、幂等、日志。

// 用来收集接口请求的原始数据，比如访问时间、处理时长等。在业务系统中，我们采用如下方式来使用这个 MetricsCollector 类：
public class UserController {
    //...省略其他属性和方法...
    private MetricsCollector metricsCollector; // 依赖注入
  
    public UserVo login(String telephone, String password) {
      long startTimestamp = System.currentTimeMillis();
  
      // ... 省略login逻辑...
  
      long endTimeStamp = System.currentTimeMillis();
      long responseTime = endTimeStamp - startTimestamp;
      RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimestamp);
      metricsCollector.recordRequest(requestInfo);
  
      //...返回UserVo数据...
    }
  
    public UserVo register(String telephone, String password) {
      long startTimestamp = System.currentTimeMillis();
  
      // ... 省略register逻辑...
  
      long endTimeStamp = System.currentTimeMillis();
      long responseTime = endTimeStamp - startTimestamp;
      RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
      metricsCollector.recordRequest(requestInfo);
  
      //...返回UserVo数据...
    }
  }


//   很明显，上面的写法有两个问题。
// 第一，性能计数器框架代码侵入到业务代码中，跟业务代码高度耦合。如果未来需要替换这个框架，那替换的成本会比较大。
// 第二，收集接口请求的代码跟业务代码无关，本就不应该放到一个类中。业务类最好职责更加单一，只聚焦业务处理。