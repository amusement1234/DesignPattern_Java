// 我们再来看第二种实现方式，代码如下所示。这种实现方式更加简单。HandlerChain 类用数组而非链表来保存所有的处理器，并且需要在 HandlerChain 的 handle() 函数中，依次调用每个处理器的 handle() 函数。

public interface IHandler {
    boolean handle();
  }
  
  public class HandlerA implements IHandler {
    @Override
    public boolean handle() {
      boolean handled = false;
      //...
      return handled;
    }
  }
  
  public class HandlerB implements IHandler {
    @Override
    public boolean handle() {
      boolean handled = false;
      //...
      return handled;
    }
  }
  
  public class HandlerChain {
    private List<IHandler> handlers = new ArrayList<>();
  
    public void addHandler(IHandler handler) {
      this.handlers.add(handler);
    }
  
    public void handle() {
      for (IHandler handler : handlers) {
        boolean handled = handler.handle();
        if (handled) {
          break;
        }
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