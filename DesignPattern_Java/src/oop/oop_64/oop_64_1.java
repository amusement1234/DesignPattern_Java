// 64 | 状态模式：游戏、工作流引擎中常用的状态机是如何实现的？

// 状态机有 3 个组成部分：状态（State）、事件（Event）、动作（Action）。其中，事件也称为转移条件（Transition Condition）。事件触发状态的转移及动作的执行。



public enum State {
    SMALL(0),
    SUPER(1),
    FIRE(2),
    CAPE(3);
  
    private int value;
  
    private State(int value) {
      this.value = value;
    }
  
    public int getValue() {
      return this.value;
    }
  }
  
  public class MarioStateMachine {
    private int score;
    private State currentState;
  
    public MarioStateMachine() {
      this.score = 0;
      this.currentState = State.SMALL;
    }
  
    public void obtainMushRoom() {
      //TODO
    }
  
    public void obtainCape() {
      //TODO
    }
  
    public void obtainFireFlower() {
      //TODO
    }
  
    public void meetMonster() {
      //TODO
    }
  
    public int getScore() {
      return this.score;
    }
  
    public State getCurrentState() {
      return this.currentState;
    }
  }
  
  public class ApplicationDemo {
    public static void main(String[] args) {
      MarioStateMachine mario = new MarioStateMachine();
      mario.obtainMushRoom();
      int score = mario.getScore();
      State state = mario.getCurrentState();
      System.out.println("mario score: " + score + "; state: " + state);
    }
  }








