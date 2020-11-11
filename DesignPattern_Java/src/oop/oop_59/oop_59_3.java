// 应用举例二：setClickListener(）


Button button = (Button)findViewById(R.id.button);
button.setOnClickListener(new OnClickListener() {
  @Override
  public void onClick(View v) {
    System.out.println("I am clicked.");
  }
});

// 应用举例三：addShutdownHook()


public class ShutdownHookDemo {

    private static class ShutdownHook extends Thread {
      public void run() {
        System.out.println("I am called during shutting down.");
      }
    }
  
    public static void main(String[] args) {
      Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }
  
  }


  
public class Runtime {
    public void addShutdownHook(Thread hook) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
        sm.checkPermission(new RuntimePermission("shutdownHooks"));
      }
      ApplicationShutdownHooks.add(hook);
    }
  }
  
  class ApplicationShutdownHooks {
      /* The set of registered hooks */
      private static IdentityHashMap<Thread, Thread> hooks;
      static {
              hooks = new IdentityHashMap<>();
          } catch (IllegalStateException e) {
              hooks = null;
          }
      }
  
      static synchronized void add(Thread hook) {
          if(hooks == null)
              throw new IllegalStateException("Shutdown in progress");
  
          if (hook.isAlive())
              throw new IllegalArgumentException("Hook already running");
  
          if (hooks.containsKey(hook))
              throw new IllegalArgumentException("Hook previously registered");
  
          hooks.put(hook, hook);
      }
  
      static void runHooks() {
          Collection<Thread> threads;
          synchronized(ApplicationShutdownHooks.class) {
              threads = hooks.keySet();
              hooks = null;
          }
  
          for (Thread hook : threads) {
              hook.start();
          }
          for (Thread hook : threads) {
              while (true) {
                  try {
                      hook.join();
                      break;
                  } catch (InterruptedException ignored) {
                  }
              }
          }
      }
  }

//   回调基于组合关系来实现，把一个对象传递给另一个对象，是一种对象之间的关系；模板模式基于继承关系来实现，子类重写父类的抽象方法，是一种类之间的关系。