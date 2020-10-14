// 建造者模式：建造者主要解决参数过多、参数检验、控制对象创建后不可变的问题

// 与工厂模式有何区别？
// 1.工厂模式是用来创建不同但是相关类型的对象（继承同一父类或者接口的一组子类），由给定的参数来决定创建哪种类型的对象。
// 2.建造者模式是用来创建一种类型的复杂对象，通过设置不同的可选参数，“定制化”地创建不同的对象。

// 如果一个类中有很多属性，为了避免构造函数的参数列表过长，影响代码的可读性和易用性，我们可以通过构造函数配合 set() 方法来解决。但是，如果存在下面情况中的任意一种，我们就要考虑使用建造者模式了。

public class ResourcePoolConfig {
    private static final int DEFAULT_MAX_TOTAL = 8;
    private static final int DEFAULT_MAX_IDLE = 8;
    private static final int DEFAULT_MIN_IDLE = 0;
  
    private String name;
    private int maxTotal = DEFAULT_MAX_TOTAL;
    private int maxIdle = DEFAULT_MAX_IDLE;
    private int minIdle = DEFAULT_MIN_IDLE;
  
    public ResourcePoolConfig(String name, Integer maxTotal, Integer maxIdle, Integer minIdle) {
      if (StringUtils.isBlank(name)) {
        throw new IllegalArgumentException("name should not be empty.");
      }
      this.name = name;
  
      if (maxTotal != null) {
        if (maxTotal <= 0) {
          throw new IllegalArgumentException("maxTotal should be positive.");
        }
        this.maxTotal = maxTotal;
      }
  
      if (maxIdle != null) {
        if (maxIdle < 0) {
          throw new IllegalArgumentException("maxIdle should not be negative.");
        }
        this.maxIdle = maxIdle;
      }
  
      if (minIdle != null) {
        if (minIdle < 0) {
          throw new IllegalArgumentException("minIdle should not be negative.");
        }
        this.minIdle = minIdle;
      }
    }
    //...省略getter方法...
  }


  
// 参数太多，导致可读性差、参数可能传递错误
ResourcePoolConfig config = new ResourcePoolConfig("dbconnectionpool", 16, null, 8, null, false , true, 10, 20，false， true);