
public class ArrayIterator implements Iterator {
    private int cursor;
    private ArrayList arrayList;
    private int expectedModCount;

    public ArrayIterator(ArrayList arrayList) {
        this.cursor = 0;
        this.arrayList = arrayList;
        this.expectedModCount = arrayList.modCount;
    }

    @Override
    public boolean hasNext() {
        checkForComodification();
        return cursor < arrayList.size();
    }

    @Override
    public void next() {
        checkForComodification();
        cursor++;
    }

    @Override
    public Object currentItem() {
        checkForComodification();
        return arrayList.get(cursor);
    }

    private void checkForComodification() {
        if (arrayList.modCount != expectedModCount)
            throw new ConcurrentModificationException();
    }
}

//代码示例
public class Demo {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("a");
        names.add("b");
        names.add("c");
        names.add("d");

        Iterator<String> iterator = names.iterator();
        iterator.next();
        names.remove("a");
        iterator.next();//抛出ConcurrentModificationException异常
    }
}