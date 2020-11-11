
//   模板模式作用一：复用

// 1.Java InputStream

public abstract class InputStream implements Closeable {
    //...省略其他代码...
    
    public int read(byte b[], int off, int len) throws IOException {
      if (b == null) {
        throw new NullPointerException();
      } else if (off < 0 || len < 0 || len > b.length - off) {
        throw new IndexOutOfBoundsException();
      } else if (len == 0) {
        return 0;
      }
  
      int c = read();
      if (c == -1) {
        return -1;
      }
      b[off] = (byte)c;
  
      int i = 1;
      try {
        for (; i < len ; i++) {
          c = read();
          if (c == -1) {
            break;
          }
          b[off + i] = (byte)c;
        }
      } catch (IOException ee) {
      }
      return i;
    }
    
    public abstract int read() throws IOException;
  }
  
  public class ByteArrayInputStream extends InputStream {
    //...省略其他代码...
    
    @Override
    public synchronized int read() {
      return (pos < count) ? (buf[pos++] & 0xff) : -1;
    }
  }



  // 2.Java AbstractList
  
public boolean addAll(int index, Collection<? extends E> c) {
    rangeCheckForAdd(index);
    boolean modified = false;
    for (E e : c) {
        add(index++, e);
        modified = true;
    }
    return modified;
}

public void add(int index, E element) {
    throw new UnsupportedOperationException();
}