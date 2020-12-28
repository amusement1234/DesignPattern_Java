// 状态机实现方式三：状态模式

// 在查表法的代码实现中，事件触发的动作只是简单的积分加减，所以，我们用一个 int 类型的二维数组 actionTable 就能表示，二维数组中的值表示积分的加减值。但是，如果要执行的动作并非这么简单，而是一系列复杂的逻辑操作（比如加减积分、写数据库，还有可能发送消息通知等等），我们就没法用如此简单的二维数组来表示了。这也就是说，查表法的实现方式有一定局限性。


public interface IMario { //所有状态类的接口
    State getName();
    //以下是定义的事件
    void obtainMushRoom();
    void obtainCape();
    void obtainFireFlower();
    void meetMonster();
  }
  
  public class SmallMario implements IMario {
    private MarioStateMachine stateMachine;
  
    public SmallMario(MarioStateMachine stateMachine) {
      this.stateMachine = stateMachine;
    }
  
    @Override
    public State getName() {
      return State.SMALL;
    }
  
    @Override
    public void obtainMushRoom() {
      stateMachine.setCurrentState(new SuperMario(stateMachine));
      stateMachine.setScore(stateMachine.getScore() + 100);
    }
  
    @Override
    public void obtainCape() {
      stateMachine.setCurrentState(new CapeMario(stateMachine));
      stateMachine.setScore(stateMachine.getScore() + 200);
    }
  
    @Override
    public void obtainFireFlower() {
      stateMachine.setCurrentState(new FireMario(stateMachine));
      stateMachine.setScore(stateMachine.getScore() + 300);
    }
  
    @Override
    public void meetMonster() {
      // do nothing...
    }
  }
  
  public class SuperMario implements IMario {
    private MarioStateMachine stateMachine;
  
    public SuperMario(MarioStateMachine stateMachine) {
      this.stateMachine = stateMachine;
    }
  
    @Override
    public State getName() {
      return State.SUPER;
    }
  
    @Override
    public void obtainMushRoom() {
      // do nothing...
    }
  
    @Override
    public void obtainCape() {
      stateMachine.setCurrentState(new CapeMario(stateMachine));
      stateMachine.setScore(stateMachine.getScore() + 200);
    }
  
    @Override
    public void obtainFireFlower() {
      stateMachine.setCurrentState(new FireMario(stateMachine));
      stateMachine.setScore(stateMachine.getScore() + 300);
    }
  
    @Override
    public void meetMonster() {
      stateMachine.setCurrentState(new SmallMario(stateMachine));
      stateMachine.setScore(stateMachine.getScore() - 100);
    }
  }
  
  // 省略CapeMario、FireMario类...
  
  public class MarioStateMachine {
    private int score;
    private IMario currentState; // 不再使用枚举来表示状态
  
    public MarioStateMachine() {
      this.score = 0;
      this.currentState = new SmallMario(this);
    }
  
    public void obtainMushRoom() {
      this.currentState.obtainMushRoom();
    }
  
    public void obtainCape() {
      this.currentState.obtainCape();
    }
  
    public void obtainFireFlower() {
      this.currentState.obtainFireFlower();
    }
  
    public void meetMonster() {
      this.currentState.meetMonster();
    }
  
    public int getScore() {
      return this.score;
    }
  
    public State getCurrentState() {
      return this.currentState.getName();
    }
  
    public void setScore(int score) {
      this.score = score;
    }
  
    public void setCurrentState(IMario currentState) {
      this.currentState = currentState;
    }
  }




//   实际上，上面的代码还可以继续优化，我们可以将状态类设计成单例，毕竟状态类中不包含任何成员变量。但是，当将状态类设计成单例之后，我们就无法通过构造函数来传递 MarioStateMachine 了，而状态类又要依赖 MarioStateMachine，那该如何解决这个问题呢？

//   在这里，我们可以通过函数参数将 MarioStateMachine 传递进状态类。根据这个设计思路，我们对上面的代码进行重构。重构之后的代码如下所示：  
public interface IMario {
    State getName();
    void obtainMushRoom(MarioStateMachine stateMachine);
    void obtainCape(MarioStateMachine stateMachine);
    void obtainFireFlower(MarioStateMachine stateMachine);
    void meetMonster(MarioStateMachine stateMachine);
  }
  
  public class SmallMario implements IMario {
    private static final SmallMario instance = new SmallMario();
    private SmallMario() {}
    public static SmallMario getInstance() {
      return instance;
    }
  
    @Override
    public State getName() {
      return State.SMALL;
    }
  
    @Override
    public void obtainMushRoom(MarioStateMachine stateMachine) {
      stateMachine.setCurrentState(SuperMario.getInstance());
      stateMachine.setScore(stateMachine.getScore() + 100);
    }
  
    @Override
    public void obtainCape(MarioStateMachine stateMachine) {
      stateMachine.setCurrentState(CapeMario.getInstance());
      stateMachine.setScore(stateMachine.getScore() + 200);
    }
  
    @Override
    public void obtainFireFlower(MarioStateMachine stateMachine) {
      stateMachine.setCurrentState(FireMario.getInstance());
      stateMachine.setScore(stateMachine.getScore() + 300);
    }
  
    @Override
    public void meetMonster(MarioStateMachine stateMachine) {
      // do nothing...
    }
  }
  
  // 省略SuperMario、CapeMario、FireMario类...
  
  public class MarioStateMachine {
    private int score;
    private IMario currentState;
  
    public MarioStateMachine() {
      this.score = 0;
      this.currentState = SmallMario.getInstance();
    }
  
    public void obtainMushRoom() {
      this.currentState.obtainMushRoom(this);
    }
  
    public void obtainCape() {
      this.currentState.obtainCape(this);
    }
  
    public void obtainFireFlower() {
      this.currentState.obtainFireFlower(this);
    }
  
    public void meetMonster() {
      this.currentState.meetMonster(this);
    }
  
    public int getScore() {
      return this.score;
    }
  
    public State getCurrentState() {
      return this.currentState.getName();
    }
  
    public void setScore(int score) {
      this.score = score;
    }
  
    public void setCurrentState(IMario currentState) {
      this.currentState = currentState;
    }
  }