// 68 | 访问者模式（上）：手把手带你还原访问者模式诞生的思维过程


public abstract class ResourceFile {
    protected String filePath;
  
    public ResourceFile(String filePath) {
      this.filePath = filePath;
    }
  
    public abstract void extract2txt();
  }
  
  public class PPTFile extends ResourceFile {
    public PPTFile(String filePath) {
      super(filePath);
    }
  
    @Override
    public void extract2txt() {
      //...省略一大坨从PPT中抽取文本的代码...
      //...将抽取出来的文本保存在跟filePath同名的.txt文件中...
      System.out.println("Extract PPT.");
    }
  }
  
  public class PdfFile extends ResourceFile {
    public PdfFile(String filePath) {
      super(filePath);
    }
  
    @Override
    public void extract2txt() {
      //...
      System.out.println("Extract PDF.");
    }
  }
  
  public class WordFile extends ResourceFile {
    public WordFile(String filePath) {
      super(filePath);
    }
  
    @Override
    public void extract2txt() {
      //...
      System.out.println("Extract WORD.");
    }
  }
  
  // 运行结果是：
  // Extract PDF.
  // Extract WORD.
  // Extract PPT.
  public class ToolApplication {
    public static void main(String[] args) {
      List<ResourceFile> resourceFiles = listAllResourceFiles(args[0]);
      for (ResourceFile resourceFile : resourceFiles) {
        resourceFile.extract2txt();
      }
    }
  
    private static List<ResourceFile> listAllResourceFiles(String resourceDirectory) {
      List<ResourceFile> resourceFiles = new ArrayList<>();
      //...根据后缀(pdf/ppt/word)由工厂方法创建不同的类对象(PdfFile/PPTFile/WordFile)
      resourceFiles.add(new PdfFile("a.pdf"));
      resourceFiles.add(new WordFile("b.word"));
      resourceFiles.add(new PPTFile("c.ppt"));
      return resourceFiles;
    }
  }