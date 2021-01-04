

// 如何实现一个单例？

// 要实现一个单例，我们需要关注的点无外乎下面几个：
// 1.构造函数需要是 private 访问权限的，这样才能避免外部通过 new 创建实例；
// 2.考虑对象创建时的线程安全问题；
// 3.考虑是否支持延迟加载；
// 4.考虑 getInstance() 性能是否高（是否加锁）。

// 1. 饿汉式
// 饿汉式的实现方式比较简单。在类加载的时候，instance 静态实例就已经创建并初始化好了，所以，instance 实例的创建过程是线程安全的。不过，这样的实现方式不支持延迟加载

public class IdGenerator { 
    private AtomicLong id = new AtomicLong(0);
    private static final IdGenerator instance = new IdGenerator();
    private IdGenerator() {}
    public static IdGenerator getInstance() {
      return instance;
    }
    public long getId() { 
      return id.incrementAndGet();
    }
  }

//   采用饿汉式实现方式，将耗时的初始化操作，提前到程序启动的时候完成，这样就能避免在程序运行的时候，再去初始化导致的性能问题。
// 如果实例占用资源多，按照 fail-fast 的设计原则（有问题及早暴露），那我们也希望在程序启动时就将这个实例初始化好。



// 2. 懒汉式
// 懒汉式相对于饿汉式的优势是支持延迟加载。

public class IdGenerator { 
    private AtomicLong id = new AtomicLong(0);
    private static IdGenerator instance;
    private IdGenerator() {}
    public static synchronized IdGenerator getInstance() {
      if (instance == null) {
        instance = new IdGenerator();
      }
      return instance;
    }
    public long getId() { 
      return id.incrementAndGet();
    }
  }
//   懒汉式的缺点也很明显，我们给 getInstance() 这个方法加了一把大锁（synchronzed），导致这个函数的并发度很低。
// 如果频繁地用到，那频繁加锁、释放锁及并发度低等问题，会导致性能瓶颈，这种实现方式就不可取了。



// 3. 双重检测
// 饿汉式不支持延迟加载，懒汉式有性能问题，不支持高并发。那我们再来看一种既支持延迟加载、又支持高并发的单例实现方式，也就是双重检测实现方式。
// 只要 instance 被创建之后，即便再调用 getInstance() 函数也不会再进入到加锁逻辑中了。

public class IdGenerator { 
    private AtomicLong id = new AtomicLong(0);
    private static IdGenerator instance;
    private IdGenerator() {}
    public static IdGenerator getInstance() {
      if (instance == null) {
        synchronized(IdGenerator.class) { // 此处为类级别的锁
          if (instance == null) {
            instance = new IdGenerator();
          }
        }
      }
      return instance;
    }
    public long getId() { 
      return id.incrementAndGet();
    }
  }



//   4. 静态内部类
// 我们再来看一种比双重检测更加简单的实现方法，那就是利用 Java 的静态内部类。它有点类似饿汉式，但又能做到了延迟加载。具体是怎么做到的呢？我们先来看它的代码实现。
public class IdGenerator { 
    private AtomicLong id = new AtomicLong(0);
    private IdGenerator() {}
  
    private static class SingletonHolder{
      private static final IdGenerator instance = new IdGenerator();
    }
    
    public static IdGenerator getInstance() {
      return SingletonHolder.instance;
    }
   
    public long getId() { 
      return id.incrementAndGet();
    }
  }

//   instance 的唯一性、创建过程的线程安全性，都由 JVM 来保证。所以，这种实现方法既保证了线程安全，又能做到延迟加载。

