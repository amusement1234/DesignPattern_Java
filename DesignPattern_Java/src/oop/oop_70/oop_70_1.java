// 70 | 备忘录模式：对于大对象的备份和恢复，如何优化内存和时间的消耗？

// 在不违背封装原则的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，以便之后恢复对象为先前的状态。

// 一部分是，存储副本以便后期恢复。这一部分很好理解。另一部分是，要在不违背封装原则的前提下，进行对象的备份和恢复。

// 当我们需要恢复到某一时间点的备份的时候，如果这一时间点有做全量备份，我们直接拿来恢复就可以了。如果这一时间点没有对应的全量备份，我们就先找到最近的一次全量备份，然后用它来恢复，之后执行此次全量备份跟这一时间点之间的所有增量备份，也就是对应的操作或者数据变动。这样就能减少全量备份的数量和频率，减少对时间、内存的消耗。
public class InputText {
    private StringBuilder text = new StringBuilder();
  
    public String getText() {
      return text.toString();
    }
  
    public void append(String input) {
      text.append(input);
    }
  
    public void setText(String text) {
      this.text.replace(0, this.text.length(), text);
    }
  }
  
  public class SnapshotHolder {
    private Stack<InputText> snapshots = new Stack<>();
  
    public InputText popSnapshot() {
      return snapshots.pop();
    }
  
    public void pushSnapshot(InputText inputText) {
      InputText deepClonedInputText = new InputText();
      deepClonedInputText.setText(inputText.getText());
      snapshots.push(deepClonedInputText);
    }
  }
  
  public class ApplicationMain {
    public static void main(String[] args) {
      InputText inputText = new InputText();
      SnapshotHolder snapshotsHolder = new SnapshotHolder();
      Scanner scanner = new Scanner(System.in);
      while (scanner.hasNext()) {
        String input = scanner.next();
        if (input.equals(":list")) {
          System.out.println(inputText.getText());
        } else if (input.equals(":undo")) {
          InputText snapshot = snapshotsHolder.popSnapshot();
          inputText.setText(snapshot.getText());
        } else {
          snapshotsHolder.pushSnapshot(inputText);
          inputText.append(input);
        }
      }
    }
  }