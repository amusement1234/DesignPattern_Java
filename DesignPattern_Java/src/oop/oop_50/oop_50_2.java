// 在第 10 节中，我们还讲到“组合优于继承”，可以“使用组合来替代继承”。针对刚刚的继承结构过于复杂的问题，我们可以通过将继承关系改为组合关系来解决。下面的代码展示了 Java IO 的这种设计思路。不过，我对代码做了简化，只抽象出了必要的代码结构，如果你感兴趣的话，可以直接去查看 JDK 源码。

public abstract class InputStream {
    //...
    public int read(byte b[]) throws IOException {
      return read(b, 0, b.length);
    }
    
    public int read(byte b[], int off, int len) throws IOException {
      //...
    }
    
    public long skip(long n) throws IOException {
      //...
    }
  
    public int available() throws IOException {
      return 0;
    }
    
    public void close() throws IOException {}
  
    public synchronized void mark(int readlimit) {}
      
    public synchronized void reset() throws IOException {
      throw new IOException("mark/reset not supported");
    }
  
    public boolean markSupported() {
      return false;
    }
  }
  
  public class BufferedInputStream extends InputStream {
    protected volatile InputStream in;
  
    protected BufferedInputStream(InputStream in) {
      this.in = in;
    }
    
    //...实现基于缓存的读数据接口...  
  }
  
  public class DataInputStream extends InputStream {
    protected volatile InputStream in;
  
    protected DataInputStream(InputStream in) {
      this.in = in;
    }
    
    //...实现读取基本类型数据的接口
  }