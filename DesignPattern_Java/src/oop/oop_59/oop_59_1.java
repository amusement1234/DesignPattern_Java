// 59 | 模板模式（下）：模板模式与Callback回调函数有何区别和联系？


public interface ICallback {
    void methodToCallback();
  }
  
  public class BClass {
    public void process(ICallback callback) {
      //...
      callback.methodToCallback();
      //...
    }
  }
  
  public class AClass {
    public static void main(String[] args) {
      BClass b = new BClass();
      b.process(new ICallback() { //回调对象
        @Override
        public void methodToCallback() {
          System.out.println("Call back me.");
        }
      });
    }
  }

//   回调可以分为同步回调和异步回调（或者延迟回调）。同步回调指在函数返回之前执行回调函数；异步回调指的是在函数返回之后执行回调函数。

