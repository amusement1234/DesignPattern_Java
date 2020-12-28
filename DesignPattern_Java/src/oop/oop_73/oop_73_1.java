// 73 | 中介模式：什么时候用中介模式？什么时候用观察者模式？

// 中介模式定义了一个单独的（中介）对象，来封装一组对象之间的交互。将这组对象之间的交互委派给与中介对象交互，来避免对象之间的直接交互。

// 中介模式的设计思想跟中间层很像，通过引入中介这个中间层，将一组对象之间的交互关系（或者说依赖关系）从多对多（网状关系）转换为一对多（星状关系）。


public class UIControl {
    private static final String LOGIN_BTN_ID = "login_btn";
    private static final String REG_BTN_ID = "reg_btn";
    private static final String USERNAME_INPUT_ID = "username_input";
    private static final String PASSWORD_INPUT_ID = "pswd_input";
    private static final String REPEATED_PASSWORD_INPUT_ID = "repeated_pswd_input";
    private static final String HINT_TEXT_ID = "hint_text";
    private static final String SELECTION_ID = "selection";
  
    public static void main(String[] args) {
      Button loginButton = (Button)findViewById(LOGIN_BTN_ID);
      Button regButton = (Button)findViewById(REG_BTN_ID);
      Input usernameInput = (Input)findViewById(USERNAME_INPUT_ID);
      Input passwordInput = (Input)findViewById(PASSWORD_INPUT_ID);
      Input repeatedPswdInput = (Input)findViewById(REPEATED_PASSWORD_INPUT_ID);
      Text hintText = (Text)findViewById(HINT_TEXT_ID);
      Selection selection = (Selection)findViewById(SELECTION_ID);
  
      loginButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          String username = usernameInput.text();
          String password = passwordInput.text();
          //校验数据...
          //做业务处理...
        }
      });
  
      regButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
        //获取usernameInput、passwordInput、repeatedPswdInput数据...
        //校验数据...
        //做业务处理...
        }
      });
  
      //...省略selection下拉选择框相关代码....
    }
  }