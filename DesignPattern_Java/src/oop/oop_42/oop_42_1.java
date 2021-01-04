// 42 | 单例模式（中）：我为什么不推荐使用单例模式？又有何替代方案？

// 1. 单例对 OOP 特性的支持不友好
public class Order {
    public void create(...) {
      //...
      long id = IdGenerator.getInstance().getId();
      //...
    }
  }
  
  public class User {
    public void create(...) {
      // ...
      long id = IdGenerator.getInstance().getId();
      //...
    }
  }

//   IdGenerator 的使用方式违背了基于接口而非实现的设计原则，也就违背了广义上理解的 OOP 的抽象特性。


public class Order {
    public void create(...) {
      //...
      long id = IdGenerator.getInstance().getId();
      // 需要将上面一行代码，替换为下面一行代码
      long id = OrderIdGenerator.getIntance().getId();
      //...
    }
  }
  
  public class User {
    public void create(...) {
      // ...
      long id = IdGenerator.getInstance().getId();
      // 需要将上面一行代码，替换为下面一行代码
      long id = UserIdGenerator.getIntance().getId();
    }
  }

//   一旦你选择将某个类设计成到单例类，也就意味着放弃了继承和多态这两个强有力的面向对象特性，也就相当于损失了可以应对未来需求变化的扩展性。





// 2. 单例会隐藏类之间的依赖关系
// 单例类不需要显示创建、不需要依赖参数传递，在函数中直接调用就可以了。如果代码比较复杂，这种调用关系就会非常隐蔽。




// 3. 单例对代码的扩展性不友好
// 如果未来某一天，我们需要在代码中创建两个实例或多个实例，那就要对代码有比较大的改动。

// 如果我们将数据库连接池设计成单例类，显然就无法适应这样的需求变更，也就是说，单例类在某些情况下会影响代码的扩展性、灵活性。
// 所以，数据库连接池、线程池这类的资源池，最好还是不要设计成单例类。实际上，一些开源的数据库连接池、线程池也确实没有设计成单例类。




// 4. 单例对代码的可测试性不友好
// 如果这个全局变量是一个可变全局变量，也就是说，它的成员变量是可以被修改的，那我们在编写单元测试的时候，还需要注意不同测试用例之间，修改了单例类中的同一个成员变量的值，从而导致测试结果互相影响的问题。



// 5. 单例不支持有参数的构造函数

// 第一种解决思路是：创建完实例之后，再调用 init() 函数传递参数。
public class Singleton {
    private static Singleton instance = null;
    private final int paramA;
    private final int paramB;
  
    private Singleton(int paramA, int paramB) {
      this.paramA = paramA;
      this.paramB = paramB;
    }
  
    public static Singleton getInstance() {
      if (instance == null) {
         throw new RuntimeException("Run init() first.");
      }
      return instance;
    }
  
    public synchronized static Singleton init(int paramA, int paramB) {
      if (instance != null){
         throw new RuntimeException("Singleton has been created!");
      }
      instance = new Singleton(paramA, paramB);
      return instance;
    }
  }
  
//   Singleton.init(10, 50); // 先init，再使用
//   Singleton singleton = Singleton.getInstance();



// 第二种解决思路是：将参数放到 getIntance() 方法中。

public class Singleton {
    private static Singleton instance = null;
    private final int paramA;
    private final int paramB;
  
    private Singleton(int paramA, int paramB) {
      this.paramA = paramA;
      this.paramB = paramB;
    }
  
    public synchronized static Singleton getInstance(int paramA, int paramB) {
      if (instance == null) {
        instance = new Singleton(paramA, paramB);
      }
      return instance;
    }
  }
  
//   Singleton singleton = Singleton.getInstance(10, 50);



// 第三种解决思路是：将参数放到另外一个全局变量中。

public class Config {
    public static final int PARAM_A = 123;
    public static final int PARAM_B = 245;
  }
  
  public class Singleton {
    private static Singleton instance = null;
    private final int paramA;
    private final int paramB;
  
    private Singleton() {
      this.paramA = Config.PARAM_A;
      this.paramB = Config.PARAM_B;
    }
  
    public synchronized static Singleton getInstance() {
      if (instance == null) {
        instance = new Singleton();
      }
      return instance;
    }
  }