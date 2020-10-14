// 装饰器模式

// 装饰器模式主要解决继承关系过于复杂的问题，通过组合来替代继承。它主要的作用是给原始类添加增强功能。这也是判断是否该用装饰器模式的一个重要的依据。除此之外，装饰器模式还有一个特点，那就是可以对原始类嵌套使用多个装饰器。

// 代理模式和装饰者模式的区别：
// 1.代理模式和装饰者模式都是 代码增强这一件事的落地方案。前者个人认为偏重业务无关，高度抽象，和稳定性较高的场景（性能其实可以抛开不谈）。后者偏重业务相关，定制化诉求高，改动较频繁的场景。
// 2.缓存这件事一般都是高度抽象，全业务通用，基本不会改动的东西，所以一般也是采用代理模式，让业务开发从缓存代码的重复劳动中解放出来。但如果当前业务的缓存实现需要特殊化定制，需要揉入业务属性，那么就该采用装饰者模式。因为其定制性强，其他业务也用不着，而且业务是频繁变动的，所以改动的可能也大，相对于动代，装饰者在调整（修改和重组）代码这件事上显得更灵活。

InputStream in = new FileInputStream("/user/wangzheng/test.txt");
InputStream bin = new BufferedInputStream(in);
byte[] data = new byte[128];
while (bin.read(data) != -1) {
  //...
}

// 初看上面的代码，我们会觉得 Java IO 的用法比较麻烦，需要先创建一个 FileInputStream 对象，然后再传递给 BufferedInputStream 对象来使用。我在想，Java IO 为什么不设计一个继承 FileInputStream 并且支持缓存的 BufferedFileInputStream 类呢？这样我们就可以像下面的代码中这样，直接创建一个 BufferedFileInputStream 类对象，打开文件读取数据，用起来岂不是更加简单？

InputStream bin = new BufferedFileInputStream("/user/wangzheng/test.txt");
byte[] data = new byte[128];
while (bin.read(data) != -1) {
  //...
}

