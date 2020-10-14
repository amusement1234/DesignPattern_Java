
// 现在，我们再来看一下，DriverManager 类是干什么用的。
// 具体的代码如下所示。当我们把具体的 Driver 实现类（比如，com.mysql.jdbc.Driver）注册到 DriverManager 之后，后续所有对 JDBC 接口的调用，都会委派到对具体的 Driver 实现类来执行。而 Driver 实现类都实现了相同的接口（java.sql.Driver ），这也是可以灵活切换 Driver 的原因。
public class DriverManager {
    private final static CopyOnWriteArrayList<DriverInfo> registeredDrivers = new CopyOnWriteArrayList<DriverInfo>();
  
    //...
    static {
      loadInitialDrivers();
      println("JDBC DriverManager initialized");
    }
    //...
  
    public static synchronized void registerDriver(java.sql.Driver driver) throws SQLException {
      if (driver != null) {
        registeredDrivers.addIfAbsent(new DriverInfo(driver));
      } else {
        throw new NullPointerException();
      }
    }
  
    public static Connection getConnection(String url, String user, String password) throws SQLException {
      java.util.Properties info = new java.util.Properties();
      if (user != null) {
        info.put("user", user);
      }
      if (password != null) {
        info.put("password", password);
      }
      return (getConnection(url, info, Reflection.getCallerClass()));
    }
    //...
  }