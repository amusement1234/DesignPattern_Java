// 针对这个问题，我们对代码进行重构，利用模板模式，将调用 successor.handle() 的逻辑从具体的处理器类中剥离出来，放到抽象父类中。这样具体的处理器类只需要实现自己的业务逻辑就可以了。重构之后的代码如下所示：
public abstract class Handler {
    protected Handler successor = null;
  
    public void setSuccessor(Handler successor) {
      this.successor = successor;
    }
  
    public final void handle() {
      boolean handled = doHandle();
      if (successor != null && !handled) {
        successor.handle();
      }
    }
  
    protected abstract boolean doHandle();
  }
  
  public class HandlerA extends Handler {
    @Override
    protected boolean doHandle() {
      boolean handled = false;
      //...
      return handled;
    }
  }
  
  public class HandlerB extends Handler {
    @Override
    protected boolean doHandle() {
      boolean handled = false;
      //...
      return handled;
    }
  }
  
  // HandlerChain和Application代码不变
  
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