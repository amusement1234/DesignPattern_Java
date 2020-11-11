// 56 | 观察者模式（上）：详解各种应用场景下观察者模式的不同实现方式

// 创建型设计模式主要解决“对象的创建”问题，
// 结构型设计模式主要解决“类或对象的组合或组装”问题，
// 行为型设计模式主要解决的就是“类或对象之间的交互”问题。

// 设计模式要干的事情就是解耦。创建型模式是将创建和使用代码解耦，结构型模式是将不同功能代码解耦，行为型模式是将不同的行为代码解耦，具体到观察者模式，它是将观察者和被观察者代码解耦。

// 行为型设计模式比较多，有 11 个，几乎占了 23 种经典设计模式的一半。
// 它们分别是：观察者模式、模板模式、策略模式、职责链模式、状态模式、迭代器模式、访问者模式、备忘录模式、命令模式、解释器模式、中介模式。

// 观察者模式（Observer Design Pattern）也被称为发布订阅模式（Publish-Subscribe Design Pattern）。
// 翻译成中文就是：在对象之间定义一个一对多的依赖，当一个对象状态改变的时候，所有依赖的对象都会自动收到通知。


public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Message message);
  }
  
  public interface Observer {
    void update(Message message);
  }
  
  public class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<Observer>();
  
    @Override
    public void registerObserver(Observer observer) {
      observers.add(observer);
    }
  
    @Override
    public void removeObserver(Observer observer) {
      observers.remove(observer);
    }
  
    @Override
    public void notifyObservers(Message message) {
      for (Observer observer : observers) {
        observer.update(message);
      }
    }
  
  }
  
  public class ConcreteObserverOne implements Observer {
    @Override
    public void update(Message message) {
      //TODO: 获取消息通知，执行自己的逻辑...
      System.out.println("ConcreteObserverOne is notified.");
    }
  }
  
  public class ConcreteObserverTwo implements Observer {
    @Override
    public void update(Message message) {
      //TODO: 获取消息通知，执行自己的逻辑...
      System.out.println("ConcreteObserverTwo is notified.");
    }
  }
  
  public class Demo {
    public static void main(String[] args) {
      ConcreteSubject subject = new ConcreteSubject();
      subject.registerObserver(new ConcreteObserverOne());
      subject.registerObserver(new ConcreteObserverTwo());
      subject.notifyObservers(new Message());
    }
  }

  // 实际上，上面的代码算是观察者模式的“模板代码”，只能反映大体的设计思路。在真实的软件开发中，并不需要照搬上面的模板代码。观察者模式的实现方法各式各样，函数、类的命名等会根据业务场景的不同有很大的差别，比如 register 函数还可以叫作 attach，remove 函数还可以叫作 detach 等等。不过，万变不离其宗，设计思路都是差不多的。