// 62 | 职责链模式（上）：如何实现可灵活扩展算法的敏感信息过滤框架？

// 将请求的发送和接收解耦，让多个接收对象都有机会处理这个请求。将这些接收对象串成一条链，并沿着这条链传递这个请求，直到链上的某个接收对象能够处理它为止。

// Handler 是所有处理器类的抽象父类，handle() 是抽象方法
public abstract class Handler {
    protected Handler successor = null;
  
    public void setSuccessor(Handler successor) {
      this.successor = successor;
    }
  
    public abstract void handle();
  }

//   每个具体的处理器类（HandlerA、HandlerB）的 handle() 函数的代码结构类似，如果它能处理该请求，就不继续往下传递；如果不能处理，则交由后面的处理器来处理（也就是调用 successor.handle()）。
  public class HandlerA extends Handler {
    @Override
    public void handle() {
      boolean handled = false;
      //...
      if (!handled && successor != null) {
        successor.handle();
      }
    }
  }
  
  public class HandlerB extends Handler {
    @Override
    public void handle() {
      boolean handled = false;
      //...
      if (!handled && successor != null) {
        successor.handle();
      } 
    }
  }

//   HandlerChain 是处理器链，从数据结构的角度来看，它就是一个记录了链头、链尾的链表。其中，记录链尾是为了方便添加处理器。
  public class HandlerChain {
    private Handler head = null;
    private Handler tail = null;
  
    public void addHandler(Handler handler) {
      handler.setSuccessor(null);
  
      if (head == null) {
        head = handler;
        tail = handler;
        return;
      }
  
      tail.setSuccessor(handler);
      tail = handler;
    }
  
    public void handle() {
      if (head != null) {
        head.handle();
      }
    }
  }
  
  // 使用举例
  public class Application {
    public static void main(String[] args) {
      HandlerChain chain = new HandlerChain();
      chain.addHandler(new HandlerA());
      chain.addHandler(new HandlerB());
      chain.handle();
    }
  }

//   实际上，上面的代码实现不够优雅。处理器类的 handle() 函数，不仅包含自己的业务逻辑，还包含对下一个处理器的调用，也就是代码中的 successor.handle()。一个不熟悉这种代码结构的程序员，在添加新的处理器类的时候，很有可能忘记在 handle() 函数中调用 successor.handle()，这就会导致代码出现 bug。