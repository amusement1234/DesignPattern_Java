// 41 | 单例模式（上）：为什么说支持懒加载的双重检测不比饿汉式更优？

// 一个类只允许创建一个对象（或者实例），那这个类就是一个单例类，这种设计模式就叫作单例设计模式，简称单例模式。

// 单例有下面几种经典的实现方式。
// 饿汉式
// 饿汉式的实现方式，在类加载的期间，就已经将 instance 静态实例初始化好了，所以，instance 实例的创建是线程安全的。不过，这样的实现方式不支持延迟加载实例。

// 懒汉式
// 懒汉式相对于饿汉式的优势是支持延迟加载。这种实现方式会导致频繁加锁、释放锁，以及并发度低等问题，频繁的调用会产生性能瓶颈。

// 双重检测
// 双重检测实现方式既支持延迟加载、又支持高并发的单例实现方式。只要 instance 被创建之后，再调用 getInstance() 函数都不会进入到加锁逻辑中。所以，这种实现方式解决了懒汉式并发度低的问题。

// 静态内部类
// 利用 Java 的静态内部类来实现单例。这种实现方式，既支持延迟加载，也支持高并发，实现起来也比双重检测简单。枚举最简单的实现方式，基于枚举类型的单例实现。这种实现方式通过 Java 枚举类型本身的特性，保证了实例创建的线程安全性和实例的唯一性。





// 实战案例一：处理资源访问冲突


public class Logger {
    private FileWriter writer;
    
    public Logger() {
      File file = new File("/Users/wangzheng/log.txt");
      writer = new FileWriter(file, true); //true表示追加写入
    }
    
    public void log(String message) {
      writer.write(mesasge);
    }
  }
  
  // Logger类的应用示例：
  public class UserController {
    private Logger logger = new Logger();
    
    public void login(String username, String password) {
      // ...省略业务逻辑代码...
      logger.log(username + " logined!");
    }
  }
  
  public class OrderController {
    private Logger logger = new Logger();
    
    public void create(OrderVo order) {
      // ...省略业务逻辑代码...
      logger.log("Created an order: " + order.toString());
    }
  }


//   我们最先想到的就是通过加锁的方式：给 log() 函数加互斥锁（Java 中可以通过 synchronized 的关键字），同一时刻只允许一个线程调用执行 log() 函数。

public class Logger {
    private FileWriter writer;
  
    public Logger() {
      File file = new File("/Users/wangzheng/log.txt");
      writer = new FileWriter(file, true); //true表示追加写入
    }
    
    public void log(String message) {
      synchronized(this) {
        writer.write(mesasge);
      }
    }
  }

//   锁是一个对象级别的锁，一个对象在不同的线程下同时调用 log() 函数，会被强制要求顺序执行。但是，不同的对象之间并不共享同一把锁。在不同的线程下，通过不同的对象调用执行 log() 函数，锁并不会起作用，仍然有可能存在写入日志互相覆盖的问题。


// 我们只需要把对象级别的锁，换成类级别的锁就可以了。让所有的对象都共享同一把锁。
public class Logger {
  private FileWriter writer;

  public Logger() {
    File file = new File("/Users/wangzheng/log.txt");
    writer = new FileWriter(file, true); //true表示追加写入
  }
  
  public void log(String message) {
    synchronized(Logger.class) { // 类级别的锁
      writer.write(mesasge);
    }
  }
}

// 除了使用类级别锁之外，实际上，解决资源竞争问题的办法还有很多，分布式锁是最常听到的一种解决方案。

