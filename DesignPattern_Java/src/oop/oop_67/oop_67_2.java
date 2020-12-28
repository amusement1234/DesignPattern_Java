// 解决方案一

public class SnapshotArrayIterator<E> implements Iterator<E> {
    private int cursor;
    private ArrayList<E> snapshot;
  
    public SnapshotArrayIterator(ArrayList<E> arrayList) {
      this.cursor = 0;
      this.snapshot = new ArrayList<>();
      this.snapshot.addAll(arrayList);
    }
  
    @Override
    public boolean hasNext() {
      return cursor < snapshot.size();
    }
  
    @Override
    public E next() {
      E currentItem = snapshot.get(cursor);
      cursor++;
      return currentItem;
    }
  }

//   这个解决方案虽然简单，但代价也有点高。每次创建迭代器的时候，都要拷贝一份数据到快照中，会增加内存的消耗。