
public interface List<E> {
    Iterator iterator();
    //...省略其他接口函数...
  }
  
  public class ArrayList<E> implements List<E> {
    //...
    public Iterator iterator() {
      return new ArrayIterator(this);
    }
    //...省略其他代码
  }
  
  public class Demo {
    public static void main(String[] args) {
      List<String> names = new ArrayList<>();
      names.add("xzg");
      names.add("wang");
      names.add("zheng");
      
      Iterator<String> iterator = names.iterator();
      while (iterator.hasNext()) {
        System.out.println(iterator.currentItem());
        iterator.next();
      }
    }
  }