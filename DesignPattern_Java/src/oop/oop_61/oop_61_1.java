// 61 | 策略模式（下）：如何实现一个支持给不同大小文件排序的小程序？

// 设计原则和思想其实比设计模式更加普适和重要，掌握了代码的设计原则和思想，我们甚至可以自己创造出来新的设计模式。

public class Sorter {
    private static final long GB = 1000 * 1000 * 1000;
  
    public void sortFile(String filePath) {
      // 省略校验逻辑
      File file = new File(filePath);
      long fileSize = file.length();
      if (fileSize < 6 * GB) { // [0, 6GB)
        quickSort(filePath);
      } else if (fileSize < 10 * GB) { // [6GB, 10GB)
        externalSort(filePath);
      } else if (fileSize < 100 * GB) { // [10GB, 100GB)
        concurrentExternalSort(filePath);
      } else { // [100GB, ~)
        mapreduceSort(filePath);
      }
    }
  
    private void quickSort(String filePath) {
      // 快速排序
    }
  
    private void externalSort(String filePath) {
      // 外部排序
    }
  
    private void concurrentExternalSort(String filePath) {
      // 多线程外部排序
    }
  
    private void mapreduceSort(String filePath) {
      // 利用MapReduce多机排序
    }
  }
  
  public class SortingTool {
    public static void main(String[] args) {
      Sorter sorter = new Sorter();
      sorter.sortFile(args[0]);
    }
  }

