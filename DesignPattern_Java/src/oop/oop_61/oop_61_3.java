// 实际上，上面的代码还可以继续优化。每种排序类都是无状态的，我们没必要在每次使用的时候，都重新创建一个新的对象。所以，我们可以使用工厂模式对对象的创建进行封装。按照这个思路，我们对代码进行重构。重构之后的代码如下所示：

public class SortAlgFactory {
    private static final Map<String, ISortAlg> algs = new HashMap<>();
  
    static {
      algs.put("QuickSort", new QuickSort());
      algs.put("ExternalSort", new ExternalSort());
      algs.put("ConcurrentExternalSort", new ConcurrentExternalSort());
      algs.put("MapReduceSort", new MapReduceSort());
    }
  
    public static ISortAlg getSortAlg(String type) {
      if (type == null || type.isEmpty()) {
        throw new IllegalArgumentException("type should not be empty.");
      }
      return algs.get(type);
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
        sortAlg = SortAlgFactory.getSortAlg("QuickSort");
      } else if (fileSize < 10 * GB) { // [6GB, 10GB)
        sortAlg = SortAlgFactory.getSortAlg("ExternalSort");
      } else if (fileSize < 100 * GB) { // [10GB, 100GB)
        sortAlg = SortAlgFactory.getSortAlg("ConcurrentExternalSort");
      } else { // [100GB, ~)
        sortAlg = SortAlgFactory.getSortAlg("MapReduceSort");
      }
      sortAlg.sort(filePath);
    }
  }