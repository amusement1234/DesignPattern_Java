
public interface MsgSender {
    void send(String message);
  }
  
  public class TelephoneMsgSender implements MsgSender {
    private List<String> telephones;
  
    public TelephoneMsgSender(List<String> telephones) {
      this.telephones = telephones;
    }
  
    @Override
    public void send(String message) {
      //...
    }
  
  }
  
  public class EmailMsgSender implements MsgSender {
    // 与TelephoneMsgSender代码结构类似，所以省略...
  }
  
  public class WechatMsgSender implements MsgSender {
    // 与TelephoneMsgSender代码结构类似，所以省略...
  }
  
  public abstract class Notification {
    protected MsgSender msgSender;
  
    public Notification(MsgSender msgSender) {
      this.msgSender = msgSender;
    }
  
    public abstract void notify(String message);
  }
  
  public class SevereNotification extends Notification {
    public SevereNotification(MsgSender msgSender) {
      super(msgSender);
    }
  
    @Override
    public void notify(String message) {
      msgSender.send(message);
    }
  }
  
  public class UrgencyNotification extends Notification {
    // 与SevereNotification代码结构类似，所以省略...
  }
  public class NormalNotification extends Notification {
    // 与SevereNotification代码结构类似，所以省略...
  }
  public class TrivialNotification extends Notification {
    // 与SevereNotification代码结构类似，所以省略...
  }