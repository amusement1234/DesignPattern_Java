// 55 | 享元模式（下）：剖析享元模式在Java Integer、String中的应用

// 享元模式在 Java Integer 中的应用

// 所谓的自动装箱，就是自动将基本数据类型转换为包装器类型。所谓的自动拆箱，也就是自动将包装器类型转化为基本数据类型。具体的代码示例如下所示：
Integer i = 56; //自动装箱
int j = i; //自动拆箱


Integer i = 59；// 底层执行了：Integer i = Integer.valueOf(59);

int j = i; // 底层执行了：int j = i.intValue();
 
Integer i = Integer.valueOf(59);

Integer i1 = 56;
Integer i2 = 56;
Integer i3 = 129;
Integer i4 = 129;
System.out.println(i1 == i2);//true
System.out.println(i3 == i4);//false

// 当我们通过自动装箱，也就是调用 valueOf() 来创建 Integer 对象的时候，如果要创建的 Integer 对象的值在 -128 到 127 之间，会从 IntegerCache 类中直接返回，否则才调用 new 方法创建。

// 为什么 IntegerCache 只缓存 -128 到 127 之间的整型值呢？
// 在 IntegerCache 的代码实现中，当这个类被加载的时候，缓存的享元对象会被集中一次性创建好。毕竟整型值太多了，我们不可能在 IntegerCache 类中预先创建好所有的整型值，这样既占用太多内存，也使得加载 IntegerCache 类的时间过长。所以，我们只能选择缓存对于大部分应用来说最常用的整型值，也就是一个字节的大小（-128 到 127 之间的数据）。


// 对于下面这样三种创建整型对象的方式，我们优先使用后两种。
Integer a = new Integer(123);
Integer a = 123;
Integer a = Integer.valueOf(123);


// 享元模式在 Java String 中的应用


String s1 = "小争哥";
String s2 = "小争哥";
String s3 = new String("小争哥");

System.out.println(s1 == s2);//ture
System.out.println(s1 == s3);//false

// String 类利用享元模式来复用相同的字符串常量（也就是代码中的“小争哥”）。JVM 会专门开辟一块存储区来存储字符串常量，这块存储区叫作“字符串常量池”。

// 不过，String 类的享元模式的设计，跟 Integer 类稍微有些不同。Integer 类中要共享的对象，是在类加载的时候，就集中一次性创建好的。但是，对于字符串来说，我们没法事先知道要共享哪些字符串常量，所以没办法事先创建好，只能在某个字符串常量第一次被用到的时候，存储到常量池中，当之后再用到的时候，直接引用常量池中已经存在的即可，就不需要再重新创建了。

// 如果对象的生命周期很短，也不会被密集使用，利用享元模式反倒可能会浪费更多的内存。