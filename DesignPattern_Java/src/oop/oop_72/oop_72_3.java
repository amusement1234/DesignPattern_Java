
public class AlertRuleInterpreter {

    // key1 > 100 && key2 < 1000 || key3 == 200
    public AlertRuleInterpreter(String ruleExpression) {
      //TODO:由你来完善
    }
  
    //<String, Long> apiStat = new HashMap<>();
    //apiStat.put("key1", 103);
    //apiStat.put("key2", 987);
    public boolean interpret(Map<String, Long> stats) {
      //TODO:由你来完善
    }
  
  }
  
  public class DemoTest {
    public static void main(String[] args) {
      String rule = "key1 > 100 && key2 < 30 || key3 < 100 || key4 == 88";
      AlertRuleInterpreter interpreter = new AlertRuleInterpreter(rule);
      Map<String, Long> stats = new HashMap<>();
      stats.put("key1", 101l);
      stats.put("key3", 121l);
      stats.put("key4", 88l);
      boolean alert = interpreter.interpret(stats);
      System.out.println(alert);
    }
  }