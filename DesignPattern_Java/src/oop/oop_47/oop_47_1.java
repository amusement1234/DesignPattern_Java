// 1. 什么是原型模式？
// 如果对象的创建成本比较大，而同一个类的不同对象之间差别不大（大部分字段都相同），在这种情况下，我们可以利用对已有对象（原型）进行复制（或者叫拷贝）的方式，来创建新对象，以达到节省创建时间的目的。这种基于原型来创建对象的方式就叫作原型设计模式，简称原型模式。

public class Demo {
    private ConcurrentHashMap<String, SearchWord> currentKeywords = new ConcurrentHashMap<>();
    private long lastUpdateTime = -1;
  
    public void refresh() {
      // 从数据库中取出更新时间>lastUpdateTime的数据，放入到currentKeywords中
      List<SearchWord> toBeUpdatedSearchWords = getSearchWords(lastUpdateTime);
      long maxNewUpdatedTime = lastUpdateTime;
      for (SearchWord searchWord : toBeUpdatedSearchWords) {
        if (searchWord.getLastUpdateTime() > maxNewUpdatedTime) {
          maxNewUpdatedTime = searchWord.getLastUpdateTime();
        }
        if (currentKeywords.containsKey(searchWord.getKeyword())) {
          currentKeywords.replace(searchWord.getKeyword(), searchWord);
        } else {
          currentKeywords.put(searchWord.getKeyword(), searchWord);
        }
      }
  
      lastUpdateTime = maxNewUpdatedTime;
    }
  
    private List<SearchWord> getSearchWords(long lastUpdateTime) {
      // TODO: 从数据库中取出更新时间>lastUpdateTime的数据
      return null;
    }
  }


//   不过，现在，我们有一个特殊的要求：任何时刻，系统 A 中的所有数据都必须是同一个版本的，要么都是版本 a，要么都是版本 b，不能有的是版本 a，有的是版本 b。那刚刚的更新方式就不能满足这个要求了。除此之外，我们还要求：在更新内存数据的时候，系统 A 不能处于不可用状态，也就是不能停机更新数据。