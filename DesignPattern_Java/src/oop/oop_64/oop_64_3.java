// 状态机实现方式二：查表法

// 相对于分支逻辑的实现方式，查表法的代码实现更加清晰，可读性和可维护性更好。当修改状态机时，我们只需要修改 transitionTable 和 actionTable 两个二维数组即可。实际上，如果我们把这两个二维数组存储在配置文件中，当需要修改状态机时，我们甚至可以不修改任何代码，只需要修改配置文件就可以了。具体的代码如下所示：

public enum Event {
    GOT_MUSHROOM(0),
    GOT_CAPE(1),
    GOT_FIRE(2),
    MET_MONSTER(3);
  
    private int value;
  
    private Event(int value) {
      this.value = value;
    }
  
    public int getValue() {
      return this.value;
    }
  }
  
  public class MarioStateMachine {
    private int score;
    private State currentState;
  
    private static final State[][] transitionTable = {
            {SUPER, CAPE, FIRE, SMALL},
            {SUPER, CAPE, FIRE, SMALL},
            {CAPE, CAPE, CAPE, SMALL},
            {FIRE, FIRE, FIRE, SMALL}
    };
  
    private static final int[][] actionTable = {
            {+100, +200, +300, +0},
            {+0, +200, +300, -100},
            {+0, +0, +0, -200},
            {+0, +0, +0, -300}
    };
  
    public MarioStateMachine() {
      this.score = 0;
      this.currentState = State.SMALL;
    }
  
    public void obtainMushRoom() {
      executeEvent(Event.GOT_MUSHROOM);
    }
  
    public void obtainCape() {
      executeEvent(Event.GOT_CAPE);
    }
  
    public void obtainFireFlower() {
      executeEvent(Event.GOT_FIRE);
    }
  
    public void meetMonster() {
      executeEvent(Event.MET_MONSTER);
    }
  
    private void executeEvent(Event event) {
      int stateValue = currentState.getValue();
      int eventValue = event.getValue();
      this.currentState = transitionTable[stateValue][eventValue];
      this.score += actionTable[stateValue][eventValue];
    }
  
    public int getScore() {
      return this.score;
    }
  
    public State getCurrentState() {
      return this.currentState;
    }
  
  }