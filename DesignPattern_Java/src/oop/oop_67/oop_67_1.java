// 67 | 迭代器模式（下）：如何设计实现一个支持“快照”功能的iterator？


List<Integer> list = new ArrayList<>();
list.add(3);
list.add(8);
list.add(2);

Iterator<Integer> iter1 = list.iterator();//snapshot: 3, 8, 2
list.remove(new Integer(2));//list：3, 8
Iterator<Integer> iter2 = list.iterator();//snapshot: 3, 8
list.remove(new Integer(3));//list：8
Iterator<Integer> iter3 = list.iterator();//snapshot: 3

// 输出结果：3 8 2
while (iter1.hasNext()) {
  System.out.print(iter1.next() + " ");
}
System.out.println();

// 输出结果：3 8
while (iter2.hasNext()) {
  System.out.print(iter1.next() + " ");
}
System.out.println();

// 输出结果：8
while (iter3.hasNext()) {
  System.out.print(iter1.next() + " ");
}
System.out.println();











public ArrayList<E> implements List<E> {
  // TODO: 成员变量、私有函数等随便你定义
  
  @Override
  public void add(E obj) {
    //TODO: 由你来完善
  }
  
  @Override
  public void remove(E obj) {
    // TODO: 由你来完善
  }
  
  @Override
  public Iterator<E> iterator() {
    return new SnapshotArrayIterator(this);
  }
}

public class SnapshotArrayIterator<E> implements Iterator<E> {
  // TODO: 成员变量、私有函数等随便你定义
  
  @Override
  public boolean hasNext() {
    // TODO: 由你来完善
  }
  
  @Override
  public E next() {//返回当前元素，并且游标后移一位
    // TODO: 由你来完善
  }
}