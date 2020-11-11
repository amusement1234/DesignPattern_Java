
public class Character {//文字
    private char c;

    private Font font;
    private int size;
    private int colorRGB;

    public Character(char c, Font font, int size, int colorRGB) {
        this.c = c;
        this.font = font;
        this.size = size;
        this.colorRGB = colorRGB;
    }
}

public class Editor {
    private List<Character> chars = new ArrayList<>();

    public void appendCharacter(char c, Font font, int size, int colorRGB) {
        Character character = new Character(c, font, size, colorRGB);
        chars.add(character);
    }
}

//   在文本编辑器中，我们每敲一个文字，都会调用 Editor 类中的 appendCharacter() 方法，创建一个新的 Character 对象，保存到 chars 数组中。如果一个文本文件中，有上万、十几万、几十万的文字，那我们就要在内存中存储这么多 Character 对象。那有没有办法可以节省一点内存呢？

//   实际上，在一个文本文件中，用到的字体格式不会太多，毕竟不大可能有人把每个文字都设置成不同的格式。所以，对于字体格式，我们可以将它设计成享元，让不同的文字共享使用。按照这个设计思路，我们对上面的代码进行重构。重构后的代码如下所示：

public class CharacterStyle {
    private Font font;
    private int size;
    private int colorRGB;

    public CharacterStyle(Font font, int size, int colorRGB) {
        this.font = font;
        this.size = size;
        this.colorRGB = colorRGB;
    }

    @Override
    public boolean equals(Object o) {
        CharacterStyle otherStyle = (CharacterStyle) o;
        return font.equals(otherStyle.font) && size == otherStyle.size && colorRGB == otherStyle.colorRGB;
    }
}

public class CharacterStyleFactory {
    private static final List<CharacterStyle> styles = new ArrayList<>();

    public static CharacterStyle getStyle(Font font, int size, int colorRGB) {
        CharacterStyle newStyle = new CharacterStyle(font, size, colorRGB);
        for (CharacterStyle style : styles) {
            if (style.equals(newStyle)) {
                return style;
            }
        }
        styles.add(newStyle);
        return newStyle;
    }
}

public class Character {
    private char c;
    private CharacterStyle style;

    public Character(char c, CharacterStyle style) {
        this.c = c;
        this.style = style;
    }
}

public class Editor {
    private List<Character> chars = new ArrayList<>();

    public void appendCharacter(char c, Font font, int size, int colorRGB) {
        Character character = new Character(c, CharacterStyleFactory.getStyle(font, size, colorRGB));
        chars.add(character);
    }
}

// 我们先来看享元模式跟单例的区别。
// 在单例模式中，一个类只能创建一个对象，而在享元模式中，一个类可以创建多个对象，每个对象被多处代码引用共享。实际上，享元模式有点类似于之前讲到的单例的变体：多例。
// 应用享元模式是为了对象复用，节省内存，而应用多例模式是为了限制对象的个数。

// 我们再来看享元模式跟缓存的区别。
// 在享元模式的实现中，我们通过工厂类来“缓存”已经创建好的对象。这里的“缓存”实际上是“存储”的意思，跟我们平时所说的“数据库缓存”“CPU 缓存”“MemCache 缓存”是两回事。我们平时所讲的缓存，主要是为了提高访问效率，而非复用。

// 最后我们来看享元模式跟对象池的区别。
// 池化技术中的“复用”可以理解为“重复使用”，主要目的是节省时间（比如从数据库池中取一个连接，不需要重新创建）。在任意时刻，每一个对象、连接、线程，并不会被多处使用，而是被一个使用者独占，当使用完成之后，放回到池中，再由其他使用者重复利用。
// 享元模式中的“复用”可以理解为“共享使用”，在整个生命周期中，都是被所有使用者共享的，主要目的是节省空间。

