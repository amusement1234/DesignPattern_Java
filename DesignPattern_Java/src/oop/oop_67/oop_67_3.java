// 解决方案二


public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
  
    private int actualSize; //不包含标记删除元素
    private int totalSize; //包含标记删除元素
  
    private Object[] elements;
    private long[] addTimestamps;
    private long[] delTimestamps;
  
    public ArrayList() {
      this.elements = new Object[DEFAULT_CAPACITY];
      this.addTimestamps = new long[DEFAULT_CAPACITY];
      this.delTimestamps = new long[DEFAULT_CAPACITY];
      this.totalSize = 0;
      this.actualSize = 0;
    }
  
    @Override
    public void add(E obj) {
      elements[totalSize] = obj;
      addTimestamps[totalSize] = System.currentTimeMillis();
      delTimestamps[totalSize] = Long.MAX_VALUE;
      totalSize++;
      actualSize++;
    }
  
    @Override
    public void remove(E obj) {
      for (int i = 0; i < totalSize; ++i) {
        if (elements[i].equals(obj)) {
          delTimestamps[i] = System.currentTimeMillis();
          actualSize--;
        }
      }
    }
  
    public int actualSize() {
      return this.actualSize;
    }
  
    public int totalSize() {
      return this.totalSize;
    }
  
    public E get(int i) {
      if (i >= totalSize) {
        throw new IndexOutOfBoundsException();
      }
      return (E)elements[i];
    }
  
    public long getAddTimestamp(int i) {
      if (i >= totalSize) {
        throw new IndexOutOfBoundsException();
      }
      return addTimestamps[i];
    }
  
    public long getDelTimestamp(int i) {
      if (i >= totalSize) {
        throw new IndexOutOfBoundsException();
      }
      return delTimestamps[i];
    }
  }
  
  public class SnapshotArrayIterator<E> implements Iterator<E> {
    private long snapshotTimestamp;
    private int cursorInAll; // 在整个容器中的下标，而非快照中的下标
    private int leftCount; // 快照中还有几个元素未被遍历
    private ArrayList<E> arrayList;
  
    public SnapshotArrayIterator(ArrayList<E> arrayList) {
      this.snapshotTimestamp = System.currentTimeMillis();
      this.cursorInAll = 0;
      this.leftCount = arrayList.actualSize();;
      this.arrayList = arrayList;
  
      justNext(); // 先跳到这个迭代器快照的第一个元素
    }
  
    @Override
    public boolean hasNext() {
      return this.leftCount >= 0; // 注意是>=, 而非>
    }
  
    @Override
    public E next() {
      E currentItem = arrayList.get(cursorInAll);
      justNext();
      return currentItem;
    }
  
    private void justNext() {
      while (cursorInAll < arrayList.totalSize()) {
        long addTimestamp = arrayList.getAddTimestamp(cursorInAll);
        long delTimestamp = arrayList.getDelTimestamp(cursorInAll);
        if (snapshotTimestamp > addTimestamp && snapshotTimestamp < delTimestamp) {
          leftCount--;
          break;
        }
        cursorInAll++;
      }
    }
  }