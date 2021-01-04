// 抽象工厂（Abstract Factory）


// 针对规则配置的解析器：基于接口IRuleConfigParser
// JsonRuleConfigParser
// XmlRuleConfigParser
// YamlRuleConfigParser
// PropertiesRuleConfigParser

// 针对系统配置的解析器：基于接口ISystemConfigParser
// JsonSystemConfigParser
// XmlSystemConfigParser
// YamlSystemConfigParser
// PropertiesSystemConfigParser



// 我们可以让一个工厂负责创建多个不同类型的对象（IRuleConfigParser、ISystemConfigParser 等），而不是只创建一种 parser 对象。这样就可以有效地减少工厂类的个数。具体的代码实现如下所示：
public interface IConfigParserFactory {
    IRuleConfigParser createRuleParser();
    ISystemConfigParser createSystemParser();
    //此处可以扩展新的parser类型，比如IBizConfigParser
  }
  
  public class JsonConfigParserFactory implements IConfigParserFactory {
    @Override
    public IRuleConfigParser createRuleParser() {
      return new JsonRuleConfigParser();
    }
  
    @Override
    public ISystemConfigParser createSystemParser() {
      return new JsonSystemConfigParser();
    }
  }
  
  public class XmlConfigParserFactory implements IConfigParserFactory {
    @Override
    public IRuleConfigParser createRuleParser() {
      return new XmlRuleConfigParser();
    }
  
    @Override
    public ISystemConfigParser createSystemParser() {
      return new XmlSystemConfigParser();
    }
  }
  
  // 省略YamlConfigParserFactory和PropertiesConfigParserFactory代码


//   这也是判断要不要使用工厂模式的最本质的参考标准。
//   1.封装变化：创建逻辑有可能变化，封装成工厂类之后，创建逻辑的变更对调用者透明。
//   2.代码复用：创建代码抽离到独立的工厂类之后可以复用。
//   3.隔离复杂性：封装复杂的创建逻辑，调用者无需了解如何创建对象。
//   4.控制复杂度：将创建代码抽离出来，让原本的函数或类职责更单一，代码更简洁。

