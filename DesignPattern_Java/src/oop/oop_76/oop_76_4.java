// 适配器模式在 Collections 类中的应用

// 在新版本的 JDK 中，Enumeration 类是适配器类。它适配的是客户端代码（使用 Enumeration 类）和新版本 JDK 中新的迭代器 Iterator 类。不过，从代码实现的角度来说，这个适配器模式的代码实现，跟经典的适配器模式的代码实现，差别稍微有点大。enumeration() 静态函数的逻辑和 Enumeration 适配器类的代码耦合在一起，enumeration() 静态函数直接通过 new 的方式创建了匿名类对象。具体的代码如下所示：

/**
 * Returns an enumeration over the specified collection.  This provides
 * interoperability with legacy APIs that require an enumeration
 * as input.
 *
 * @param  <T> the class of the objects in the collection
 * @param c the collection for which an enumeration is to be returned.
 * @return an enumeration over the specified collection.
 * @see Enumeration
 */
public static <T> Enumeration<T> enumeration(final Collection<T> c) {
    return new Enumeration<T>() {
      private final Iterator<T> i = c.iterator();
  
      public boolean hasMoreElements() {
        return i.hasNext();
      }
   StringBuilder
      public T nextElement() {
        return i.next();
      }
    };
  }