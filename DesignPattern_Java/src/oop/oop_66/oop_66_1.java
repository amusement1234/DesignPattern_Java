// 66 | 迭代器模式（中）：遍历集合的同时，为什么不能增删集合元素？

public interface Iterator<E> {
    boolean hasNext();

    void next();

    E currentItem();
}

public class ArrayIterator<E> implements Iterator<E> {
    private int cursor;
    private ArrayList<E> arrayList;

    public ArrayIterator(ArrayList<E> arrayList) {
        this.cursor = 0;
        this.arrayList = arrayList;
    }

    @Override
    public boolean hasNext() {
        return cursor < arrayList.size();
    }

    @Override
    public void next() {
        cursor++;
    }

    @Override
    public E currentItem() {
        if (cursor >= arrayList.size()) {
            throw new NoSuchElementException();
        }
        return arrayList.get(cursor);
    }
}

public interface List<E> {
    Iterator iterator();
}

public class ArrayList<E> implements List<E> {
    //...
    public Iterator iterator() {
        return new ArrayIterator(this);
    }
    //...
}

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
    }
}