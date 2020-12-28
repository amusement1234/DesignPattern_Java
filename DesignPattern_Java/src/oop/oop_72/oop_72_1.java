// 72 | 解释器模式：如何设计实现一个自定义接口告警规则功能？


public class ExpressionInterpreter {
    private Deque<Long> numbers = new LinkedList<>();
  
    public long interpret(String expression) {
      String[] elements = expression.split(" ");
      int length = elements.length;
      for (int i = 0; i < (length+1)/2; ++i) {
        numbers.addLast(Long.parseLong(elements[i]));
      }
  
      for (int i = (length+1)/2; i < length; ++i) {
        String operator = elements[i];
        boolean isValid = "+".equals(operator) || "-".equals(operator)
                || "*".equals(operator) || "/".equals(operator);
        if (!isValid) {
          throw new RuntimeException("Expression is invalid: " + expression);
        }
  
        long number1 = numbers.pollFirst();
        long number2 = numbers.pollFirst();
        long result = 0;
        if (operator.equals("+")) {
          result = number1 + number2;
        } else if (operator.equals("-")) {
          result = number1 - number2;
        } else if (operator.equals("*")) {
          result = number1 * number2;
        } else if (operator.equals("/")) {
          result = number1 / number2;
        }
        numbers.addFirst(result);
      }
  
      if (numbers.size() != 1) {
        throw new RuntimeException("Expression is invalid: " + expression);
      }
  
      return numbers.pop();
    }
  }