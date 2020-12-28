// 经过上面两次重构之后，现在的代码实际上已经符合策略模式的代码结构了。我们通过策略模式将策略的定义、创建、使用解耦，让每一部分都不至于太复杂。不过，Sorter 类中的 sortFile() 函数还是有一堆 if-else 逻辑。这里的 if-else 逻辑分支不多、也不复杂，这样写完全没问题。但如果你特别想将 if-else 分支判断移除掉，那也是有办法的。我直接给出代码，你一看就能明白。实际上，这也是基于查表法来解决的，其中的“algs”就是“表”。

public class Sorter {
    private static final long GB = 1000 * 1000 * 1000;
    private static final List<AlgRange> algs = new ArrayList<>();
    static {
      algs.add(new AlgRange(0, 6*GB, SortAlgFactory.getSortAlg("QuickSort")));
      algs.add(new AlgRange(6*GB, 10*GB, SortAlgFactory.getSortAlg("ExternalSort")));
      algs.add(new AlgRange(10*GB, 100*GB, SortAlgFactory.getSortAlg("ConcurrentExternalSort")));
      algs.add(new AlgRange(100*GB, Long.MAX_VALUE, SortAlgFactory.getSortAlg("MapReduceSort")));
    }
  
    public void sortFile(String filePath) {
      // 省略校验逻辑
      File file = new File(filePath);
      long fileSize = file.length();
      ISortAlg sortAlg = null;
      for (AlgRange algRange : algs) {
        if (algRange.inRange(fileSize)) {
          sortAlg = algRange.getAlg();
          break;
        }
      }
      sortAlg.sort(filePath);
    }
  
    private static class AlgRange {
      private long start;
      private long end;
      private ISortAlg alg;
  
      public AlgRange(long start, long end, ISortAlg alg) {
        this.start = start;
        this.end = end;
        this.alg = alg;
      }
  
      public ISortAlg getAlg() {
        return alg;
      }
  
      public boolean inRange(long size) {
        return size >= start && size < end;
      }
    }
  }

// 一提到策略模式，有人就觉得，它的作用是避免 if-else 分支判断逻辑。实际上，这种认识是很片面的。
// 策略模式主要的作用还是解耦策略的定义、创建和使用，控制代码的复杂度，让每个部分都不至于过于复杂、代码量过多。
// 除此之外，对于复杂代码来说，策略模式还能让其满足开闭原则，添加新策略的时候，最小化、集中化代码改动，减少引入 bug 的风险。