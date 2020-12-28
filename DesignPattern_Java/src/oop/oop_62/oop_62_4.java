// 职责链模式还有一种变体，那就是请求会被所有的处理器都处理一遍，不存在中途终止的情况。这种变体也有两种实现方式：用链表存储处理器和用数组存储处理器，跟上面的两种实现方式类似，只需要稍微修改即可。
// 我这里只给出其中一种实现方式，如下所示。另外一种实现方式你对照着上面的实现自行修改。
public abstract class Handler {
    protected Handler successor = null;
  
    public void setSuccessor(Handler successor) {
      this.successor = successor;
    }
  
    public final void handle() {
      doHandle();
      if (successor != null) {
        successor.handle();
      }
    }
  
    protected abstract void doHandle();
  }
  
  public class HandlerA extends Handler {
    @Override
    protected void doHandle() {
      //...
    }
  }
  
  public class HandlerB extends Handler {
    @Override
    protected void doHandle() {
      //...
    }
  }
  
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







  
public interface SensitiveWordFilter {
    boolean doFilter(Content content);
  }
  
  public class SexyWordFilter implements SensitiveWordFilter {
    @Override
    public boolean doFilter(Content content) {
      boolean legal = true;
      //...
      return legal;
    }
  }
  
  // PoliticalWordFilter、AdsWordFilter类代码结构与SexyWordFilter类似
  
  public class SensitiveWordFilterChain {
    private List<SensitiveWordFilter> filters = new ArrayList<>();
  
    public void addFilter(SensitiveWordFilter filter) {
      this.filters.add(filter);
    }
  
    // return true if content doesn't contain sensitive words.
    public boolean filter(Content content) {
      for (SensitiveWordFilter filter : filters) {
        if (!filter.doFilter(content)) {
          return false;
        }
      }
      return true;
    }
  }
  
  public class ApplicationDemo {
    public static void main(String[] args) {
      SensitiveWordFilterChain filterChain = new SensitiveWordFilterChain();
      filterChain.addFilter(new AdsWordFilter());
      filterChain.addFilter(new SexyWordFilter());
      filterChain.addFilter(new PoliticalWordFilter());
  
      boolean legal = filterChain.filter(new Content());
      if (!legal) {
        // 不发表
      } else {
        // 发表
      }
    }
  }



  
public class SensitiveWordFilter {
    // return true if content doesn't contain sensitive words.
    public boolean filter(Content content) {
      if (!filterSexyWord(content)) {
        return false;
      }
  
      if (!filterAdsWord(content)) {
        return false;
      }
  
      if (!filterPoliticalWord(content)) {
        return false;
      }
  
      return true;
    }
  
    private boolean filterSexyWord(Content content) {
      //....
    }
  
    private boolean filterAdsWord(Content content) {
      //...
    }
  
    private boolean filterPoliticalWord(Content content) {
      //...
    }
  }