// 58 | 模板模式（上）：剖析模板模式在JDK、Servlet、JUnit等中的应用


// 模板方法模式在一个方法中定义一个算法骨架，并将某些步骤推迟到子类中实现。模板方法模式可以让子类在不改变算法整体结构的情况下，重新定义算法中的某些步骤。

// 模板模式主要是用来解决复用和扩展两个问题。

// 模板模式有两大作用：复用和扩展。其中，
// 复用指的是，所有的子类可以复用父类中提供的模板方法的代码。
// 扩展指的是，框架通过模板模式提供功能扩展点，让框架用户可以在不修改框架源码的情况下，基于扩展点定制化框架的功能。

public abstract class AbstractClass {
    public final void templateMethod() {
      //...
      method1();
      //...
      method2();
      //...
    }
    
    protected abstract void method1();
    protected abstract void method2();
  }
  
  public class ConcreteClass1 extends AbstractClass {
    @Override
    protected void method1() {
      //...
    }
    
    @Override
    protected void method2() {
      //...
    }
  }
  
  public class ConcreteClass2 extends AbstractClass {
    @Override
    protected void method1() {
      //...
    }
    
    @Override
    protected void method2() {
      //...
    }
  }
  
  AbstractClass demo = ConcreteClass1();
  demo.templateMethod();

