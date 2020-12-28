// 65 | 迭代器模式（上）：相比直接遍历集合数据，使用迭代器有哪些优势？

// 迭代器模式将集合对象的遍历操作从集合类中拆分出来，放到迭代器类中，让两者的职责更加单一。

// 遍历集合一般有三种方式：for 循环、foreach 循环、迭代器遍历。后两种本质上属于一种，都可以看作迭代器遍历。相对于 for 循环遍历，利用迭代器来遍历有下面三个优势：
// 1、迭代器模式封装集合内部的复杂数据结构，开发者不需要了解如何遍历，直接使用容器提供的迭代器即可；
// 2、迭代器模式将集合对象的遍历操作从集合类中拆分出来，放到迭代器类中，让两者的职责更加单一；
// 3、迭代器模式让添加新的遍历算法更加容易，更符合开闭原则。除此之外，因为迭代器都实现自相同的接口，在开发中，基于接口而非实现编程，替换迭代器也变得更加容易。

// 接口定义方式一
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
        return cursor != arrayList.size(); //注意这里，cursor在指向最后一个元素的时候，hasNext()仍旧返回true。
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

public class Demo {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("xzg");
        names.add("wang");
        names.add("zheng");

        Iterator<String> iterator = new ArrayIterator(names);
        while (iterator.hasNext()) {
            System.out.println(iterator.currentItem());
            iterator.next();
        }
    }
}