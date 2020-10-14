// 3. 替换依赖的外部系统

// 当我们把项目中依赖的一个外部系统替换为另一个外部系统的时候，利用适配器模式，可以减少对代码的改动。具体的代码示例如下所示：


// 外部系统A
public interface IA {
    //...
    void fa();
  }
  public class A implements IA {
    //...
    public void fa() { //... }
  }
  // 在我们的项目中，外部系统A的使用示例
  public class Demo {
    private IA a;
    public Demo(IA a) {
      this.a = a;
    }
    //...
  }
  Demo d = new Demo(new A());
  
  // 将外部系统A替换成外部系统B
  public class BAdaptor implemnts IA {
    private B b;
    public BAdaptor(B b) {
      this.b= b;
    }
    public void fa() {
      //...
      b.fb();
    }
  }
  // 借助BAdaptor，Demo的代码中，调用IA接口的地方都无需改动，
  // 只需要将BAdaptor如下注入到Demo即可。
  Demo d = new Demo(new BAdaptor(new B()));





//   4. 兼容老版本接口

public class Collections {
    public static Emueration emumeration(final Collection c) {
      return new Enumeration() {
        Iterator i = c.iterator();
        
        public boolean hasMoreElments() {
          return i.hashNext();
        }
        
        public Object nextElement() {
          return i.next():
        }
      }
    }
  }


//   5. 适配不同格式的数据

//   Java 中的 Arrays.asList() 也可以看作一种数据适配器，将数组类型的数据转化为集合容器类型。
List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");