
// 代码优化与重构


public interface ISortAlg {
    void sort(String filePath);
  }
  
  public class QuickSort implements ISortAlg {
    @Override
    public void sort(String filePath) {
      //...
    }
  }
  
  public class ExternalSort implements ISortAlg {
    @Override
    public void sort(String filePath) {
      //...
    }
  }
  
  public class ConcurrentExternalSort implements ISortAlg {
    @Override
    public void sort(String filePath) {
      //...
    }
  }
  
  public class MapReduceSort implements ISortAlg {
    @Override
    public void sort(String filePath) {
      //...
    }
  }
  
  public class Sorter {
    private static final long GB = 1000 * 1000 * 1000;
  
    public void sortFile(String filePath) {
      // 省略校验逻辑
      File file = new File(filePath);
      long fileSize = file.length();
      ISortAlg sortAlg;
      if (fileSize < 6 * GB) { // [0, 6GB)
        sortAlg = new QuickSort();
      } else if (fileSize < 10 * GB) { // [6GB, 10GB)
        sortAlg = new ExternalSort();
      } else if (fileSize < 100 * GB) { // [10GB, 100GB)
        sortAlg = new ConcurrentExternalSort();
      } else { // [100GB, ~)
        sortAlg = new MapReduceSort();
      }
      sortAlg.sort(filePath);
    }
  }