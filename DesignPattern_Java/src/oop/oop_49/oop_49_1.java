// 桥接模式

// 代理模式是一个类与另一个类的组合，桥接模式是一组类和另外一组类的组合

// 桥接模式，也叫作桥梁模式，英文是 Bridge Design Pattern。这个模式可以说是 23 种设计模式中最难理解的模式之一了。

// 很多设计模式都是试图将庞大的类拆分成更细小的类，然后再通过某种更合理的结构组装在一起。

Class.forName("com.mysql.jdbc.Driver");//加载及注册JDBC驱动程序
String url="jdbc:mysql://localhost:3306/sample_db?user=root&password=your_password";Connection con=DriverManager.getConnection(url);Statement stmt=con.createStatement()；String query="select * from test";ResultSet rs=stmt.executeQuery(query);while(rs.next()){rs.getString(1);rs.getInt(2);}
// 如果我们想要把 MySQL 数据库换成 Oracle 数据库，只需要把第一行代码中的 com.mysql.jdbc.Driver 换成 oracle.jdbc.driver.OracleDriver 就可以了。

// 不管是改代码还是改配置，在项目中，从一个数据库切换到另一种数据库，都只需要改动很少的代码，或者完全不需要改动代码，那如此优雅的数据库切换是如何实现的呢？
// 源码之下无秘密。要弄清楚这个问题，我们先从 com.mysql.jdbc.Driver 这个类的代码看起。我摘抄了部分相关代码，放到了这里，你可以看一下。

package com.mysql.jdbc;

import java.sql.SQLException;

public class Driver extends NonRegisteringDriver implements java.sql.Driver {
    static {
        try {
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    /**
     * Construct a new driver and register it with DriverManager
     * @throws SQLException if a database error occurs.
     */
    public Driver() throws SQLException {
        // Required for Class.forName().newInstance()
    }
}

// 结合 com.mysql.jdbc.Driver 的代码实现，我们可以发现，当执行 Class.forName(“com.mysql.jdbc.Driver”) 这条语句的时候，实际上是做了两件事情。
// 第一件事情是要求 JVM 查找并加载指定的 Driver 类，
// 第二件事情是执行该类的静态代码，也就是将 MySQL Driver 注册到 DriverManager 类中。