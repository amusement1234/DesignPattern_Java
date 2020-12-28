// 如何利用策略模式避免分支判断？

public class OrderService {
    public double discount(Order order) {
      double discount = 0.0;
      OrderType type = order.getType();
      if (type.equals(OrderType.NORMAL)) { // 普通订单
        //...省略折扣计算算法代码
      } else if (type.equals(OrderType.GROUPON)) { // 团购订单
        //...省略折扣计算算法代码
      } else if (type.equals(OrderType.PROMOTION)) { // 促销订单
        //...省略折扣计算算法代码
      }
      return discount;
    }
  }


//   如何来移除掉分支判断逻辑呢？那策略模式就派上用场了。我们使用策略模式对上面的代码重构，将不同类型订单的打折策略设计成策略类，并由工厂类来负责创建策略对象。具体的代码如下所示：

  
// 策略的定义
public interface DiscountStrategy {
    double calDiscount(Order order);
}

  // 省略NormalDiscountStrategy、GrouponDiscountStrategy、PromotionDiscountStrategy类代码...
  
  // 策略的创建
  public class DiscountStrategyFactory {
    private static final Map<OrderType, DiscountStrategy> strategies = new HashMap<>();
  
    static {
      strategies.put(OrderType.NORMAL, new NormalDiscountStrategy());
      strategies.put(OrderType.GROUPON, new GrouponDiscountStrategy());
      strategies.put(OrderType.PROMOTION, new PromotionDiscountStrategy());
    }
  
    public static DiscountStrategy getDiscountStrategy(OrderType type) {
      return strategies.get(type);
    }
  }
  
  // 策略的使用
  public class OrderService {
    public double discount(Order order) {
      OrderType type = order.getType();
      DiscountStrategy discountStrategy = DiscountStrategyFactory.getDiscountStrategy(type);
      return discountStrategy.calDiscount(order);
    }
  }

//   重构之后的代码就没有了 if-else 分支判断语句了。实际上，这得益于策略工厂类。在工厂类中，我们用 Map 来缓存策略，根据 type 直接从 Map 中获取对应的策略，从而避免 if-else 分支判断逻辑。
// 等后面讲到使用状态模式来避免分支判断逻辑的时候，你会发现，它们使用的是同样的套路。本质上都是借助“查表法”，根据 type 查表（代码中的 strategies 就是表）替代根据 type 分支判断。


// 策略模式用来解耦策略的定义、创建、使用。
// 实际上，一个完整的策略模式就是由这三个部分组成的。
// 1.策略类的定义比较简单，包含一个策略接口和一组实现这个接口的策略类。
// 2.策略的创建由工厂类来完成，封装策略创建的细节。
// 3.策略模式包含一组策略可选，客户端代码如何选择使用哪个策略，有两种确定方法：编译时静态确定和运行时动态确定。其中，“运行时动态确定”才是策略模式最典型的应用场景。