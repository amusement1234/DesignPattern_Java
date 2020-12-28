// 71 | 命令模式：如何利用命令模式实现一个手游后端架构？

// 命令模式将请求（命令）封装为一个对象，这样可以使用不同的请求参数化其他对象（将不同请求依赖注入到其他对象），并且能够支持请求（命令）的排队执行、记录日志、撤销等（附加控制）功能。

// 每个设计模式都应该由两部分组成：第一部分是应用场景，即这个模式可以解决哪类问题；第二部分是解决方案，即这个模式的设计思路和具体的代码实现。不过，代码实现并不是模式必须包含的。

public interface Command {
    void execute();
  }
  
  public class GotDiamondCommand implements Command {
    // 省略成员变量
  
    public GotDiamondCommand(/*数据*/) {
      //...
    }
  
    @Override
    public void execute() {
      // 执行相应的逻辑
    }
  }
  //GotStartCommand/HitObstacleCommand/ArchiveCommand类省略
  
  public class GameApplication {
    private static final int MAX_HANDLED_REQ_COUNT_PER_LOOP = 100;
    private Queue<Command> queue = new LinkedList<>();
  
    public void mainloop() {
      while (true) {
        List<Request> requests = new ArrayList<>();
        
        //省略从epoll或者select中获取数据，并封装成Request的逻辑，
        //注意设置超时时间，如果很长时间没有接收到请求，就继续下面的逻辑处理。
        
        for (Request request : requests) {
          Event event = request.getEvent();
          Command command = null;
          if (event.equals(Event.GOT_DIAMOND)) {
            command = new GotDiamondCommand(/*数据*/);
          } else if (event.equals(Event.GOT_STAR)) {
            command = new GotStartCommand(/*数据*/);
          } else if (event.equals(Event.HIT_OBSTACLE)) {
            command = new HitObstacleCommand(/*数据*/);
          } else if (event.equals(Event.ARCHIVE)) {
            command = new ArchiveCommand(/*数据*/);
          } // ...一堆else if...
  
          queue.add(command);
        }
  
        int handledCount = 0;
        while (handledCount < MAX_HANDLED_REQ_COUNT_PER_LOOP) {
          if (queue.isEmpty()) {
            break;
          }
          Command command = queue.poll();
          command.execute();
        }
      }
    }
  }