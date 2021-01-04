
public class Logger {
    private FileWriter writer;
    private static final Logger instance = new Logger();
  
    private Logger() {
      File file = new File("/Users/wangzheng/log.txt");
      writer = new FileWriter(file, true); //true表示追加写入
    }
    
    public static Logger getInstance() {
      return instance;
    }
    
    public void log(String message) {
      writer.write(mesasge);
    }
  }
  
  // Logger类的应用示例：
  public class UserController {
    public void login(String username, String password) {
      // ...省略业务逻辑代码...
      Logger.getInstance().log(username + " logined!");
    }
  }
  
  public class OrderController {  
    public void create(OrderVo order) {
      // ...省略业务逻辑代码...
      Logger.getInstance().log("Created a order: " + order.toString());
    }
  }