// 52 | 门面模式：如何设计合理的接口粒度以兼顾接口的易用性和通用性？

// 门面模式，也叫外观模式，英文全称是 Facade Design Pattern。在 GoF 的《设计模式》一书中，门面模式是这样定义的：门面模式为子系统提供一组统一的接口，定义一组高层接口让子系统更易用。

// 假设有一个系统 A，提供了 a、b、c、d 四个接口。系统 B 完成某个业务功能，需要调用 A 系统的 a、b、d 接口。利用门面模式，我们提供一个包裹 a、b、d 接口调用的门面接口 x，给系统 B 直接使用。

// 1. 解决易用性问题

// 门面模式可以用来封装系统的底层实现，隐藏系统的复杂性，提供一组更加简单易用、更高层的接口。

// 2. 解决性能问题

// 我们通过将多个接口调用替换为一个门面接口调用，减少网络通信成本，提高 App 客户端的响应速度。

// 3. 解决分布式事务问题


// 重点回顾

// 接口粒度设计得太大，太小都不好。太大会导致接口不可复用，太小会导致接口不易用。在实际的开发中，接口的可复用性和易用性需要“微妙”的权衡。针对这个问题，我的一个基本的处理原则是，尽量保持接口的可复用性，但针对特殊情况，允许提供冗余的门面接口，来提供更易用的接口。


// 评论：

// 适配器模式与门面模式的区别：
// 适配器是做接口转换，解决的是原接口和目标接口不匹配的问题。
// 门面模式做接口整合，解决的是多接口调用带来的问题。