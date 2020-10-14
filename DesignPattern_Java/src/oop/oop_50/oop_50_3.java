// 代理模式中，代理类附加的是跟原始类无关的功能，而在装饰器模式中，装饰器类附加的是跟原始类相关的增强功能。

// 代理模式的代码结构(下面的接口也可以替换成抽象类)
public interface IA {
    void f();
  }
  public class A impelements IA {
    public void f() { //... }
  }
  public class AProxy impements IA {
    private IA a;
    public AProxy(IA a) {
      this.a = a;
    }
    
    public void f() {
      // 新添加的代理逻辑
      a.f();
      // 新添加的代理逻辑
    }
  }
  
  // 装饰器模式的代码结构(下面的接口也可以替换成抽象类)
  public interface IA {
    void f();
  }
  public class A impelements IA {
    public void f() { //... }
  }
  public class ADecorator impements IA {
    private IA a;
    public ADecorator(IA a) {
      this.a = a;
    }
    
    public void f() {
      // 功能增强代码
      a.f();
      // 功能增强代码
    }
  }