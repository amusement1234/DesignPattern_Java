// 有何替代解决方案？



// 静态方法实现方式
public class IdGenerator {
    private static AtomicLong id = new AtomicLong(0);
    
    public static long getId() { 
      return id.incrementAndGet();
    }
  }
  // 使用举例
  long id = IdGenerator.getId();

//   实际上，它比单例更加不灵活，比如，它无法支持延迟加载。


  
// 1. 老的使用方式
public demofunction() {
    //...
    long id = IdGenerator.getInstance().getId();
    //...
  }
  
  // 2. 新的使用方式：依赖注入
  public demofunction(IdGenerator idGenerator) {
    long id = idGenerator.getId();
  }
  // 外部调用demofunction()的时候，传入idGenerator
  IdGenerator idGenerator = IdGenerator.getInsance();
  demofunction(idGenerator);

//   我们将单例生成的对象，作为参数传递给函数（也可以通过构造函数传递给类的成员变量），可以解决单例隐藏类之间依赖关系的问题。

