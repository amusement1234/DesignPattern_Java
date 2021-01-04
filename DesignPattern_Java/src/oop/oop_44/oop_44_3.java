// 工厂方法（Factory Method）
public interface IRuleConfigParserFactory {
    IRuleConfigParser createParser();
  }
  
  public class JsonRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
      return new JsonRuleConfigParser();
    }
  }
  
  public class XmlRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
      return new XmlRuleConfigParser();
    }
  }
  
  public class YamlRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
      return new YamlRuleConfigParser();
    }
  }
  
  public class PropertiesRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
      return new PropertiesRuleConfigParser();
    }
  }
//   这样当我们新增一种 parser 的时候，只需要新增一个实现了 IRuleConfigParserFactory 接口的 Factory 类即可。所以，工厂方法模式比起简单工厂模式更加符合开闭原则。


public class RuleConfigSource {
    public RuleConfig load(String ruleConfigFilePath) {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);

        IRuleConfigParserFactory parserFactory = null;
        if ("json".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new JsonRuleConfigParserFactory();
        } else if ("xml".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new XmlRuleConfigParserFactory();
        } else if ("yaml".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new YamlRuleConfigParserFactory();
        } else if ("properties".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new PropertiesRuleConfigParserFactory();
        } else {
            throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
        }
        IRuleConfigParser parser = parserFactory.createParser();

        String configText = "";
        //从ruleConfigFilePath文件中读取配置文本到configText中
        RuleConfig ruleConfig = parser.parse(configText);
        return ruleConfig;
    }

    private String getFileExtension(String filePath) {
        //...解析文件名获取扩展名，比如rule.json，返回json
        return "json";
    }
}
// 从上面的代码实现来看，工厂类对象的创建逻辑又耦合进了 load() 函数中，跟我们最初的代码版本非常相似，引入工厂方法非但没有解决问题，反倒让设计变得更加复杂了。那怎么来解决这个问题呢？
  


//   我们可以为工厂类再创建一个简单工厂，也就是工厂的工厂，用来创建工厂类对象。
public class RuleConfigSource {
    public RuleConfig load(String ruleConfigFilePath) {
      String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
  
      IRuleConfigParserFactory parserFactory = RuleConfigParserFactoryMap.getParserFactory(ruleConfigFileExtension);
      if (parserFactory == null) {
        throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
      }
      IRuleConfigParser parser = parserFactory.createParser();
  
      String configText = "";
      //从ruleConfigFilePath文件中读取配置文本到configText中
      RuleConfig ruleConfig = parser.parse(configText);
      return ruleConfig;
    }
  
    private String getFileExtension(String filePath) {
      //...解析文件名获取扩展名，比如rule.json，返回json
      return "json";
    }
  }
  
  //因为工厂类只包含方法，不包含成员变量，完全可以复用，
  //不需要每次都创建新的工厂类对象，所以，简单工厂模式的第二种实现思路更加合适。
  public class RuleConfigParserFactoryMap { //工厂的工厂
      private static final Map<String, IRuleConfigParserFactory> cachedFactories = new HashMap<>();

      static {
          cachedFactories.put("json", new JsonRuleConfigParserFactory());
          cachedFactories.put("xml", new XmlRuleConfigParserFactory());
          cachedFactories.put("yaml", new YamlRuleConfigParserFactory());
          cachedFactories.put("properties", new PropertiesRuleConfigParserFactory());
      }

      public static IRuleConfigParserFactory getParserFactory(String type) {
          if (type == null || type.isEmpty()) {
              return null;
          }
          IRuleConfigParserFactory parserFactory = cachedFactories.get(type.toLowerCase());
          return parserFactory;
      }
  }

//   那什么时候该用工厂方法模式，而非简单工厂模式呢？
//   基于这个设计思想，当对象的创建逻辑比较复杂，不只是简单的 new 一下就可以，而是要组合其他类对象，做各种初始化操作的时候，我们推荐使用工厂方法模式，将复杂的创建逻辑拆分到多个工厂类中，让每个工厂类都不至于过于复杂。

