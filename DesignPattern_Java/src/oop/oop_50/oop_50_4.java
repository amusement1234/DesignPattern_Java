
public class BufferedInputStream extends InputStream {
    protected volatile InputStream in;
  
    protected BufferedInputStream(InputStream in) {
      this.in = in;
    }
    
    // f()函数不需要增强，只是重新调用一下InputStream in对象的f()
    public void f() {
      in.f();
    }  
  }


//   实际上，DataInputStream 也存在跟 BufferedInputStream 同样的问题。为了避免代码重复，Java IO 抽象出了一个装饰器父类 FilterInputStream，代码实现如下所示。InputStream 的所有的装饰器类（BufferedInputStream、DataInputStream）都继承自这个装饰器父类。这样，装饰器类只需要实现它需要增强的方法就可以了，其他方法继承装饰器父类的默认实现。
  
public class FilterInputStream extends InputStream {
    protected volatile InputStream in;
  
    protected FilterInputStream(InputStream in) {
      this.in = in;
    }
  
    public int read() throws IOException {
      return in.read();
    }
  
    public int read(byte b[]) throws IOException {
      return read(b, 0, b.length);
    }
     
    public int read(byte b[], int off, int len) throws IOException {
      return in.read(b, off, len);
    }
  
    public long skip(long n) throws IOException {
      return in.skip(n);
    }
  
    public int available() throws IOException {
      return in.available();
    }
  
    public void close() throws IOException {
      in.close();
    }
  
    public synchronized void mark(int readlimit) {
      in.mark(readlimit);
    }
  
    public synchronized void reset() throws IOException {
      in.reset();
    }
  
    public boolean markSupported() {
      return in.markSupported();
    }
  }