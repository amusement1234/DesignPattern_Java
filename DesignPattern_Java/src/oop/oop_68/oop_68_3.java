public abstract class ResourceFile {
    protected String filePath;
    public ResourceFile(String filePath) {
      this.filePath = filePath;
    }
    abstract public void accept(Extractor extractor);
  }
  
  public class PdfFile extends ResourceFile {
    public PdfFile(String filePath) {
      super(filePath);
    }
  
    @Override
    public void accept(Extractor extractor) {
      extractor.extract2txt(this);
    }
  
    //...
  }
  
  //...PPTFile、WordFile跟PdfFile类似，这里就省略了...
  //...Extractor代码不变...
  
  public class ToolApplication {
    public static void main(String[] args) {
      Extractor extractor = new Extractor();
      List<ResourceFile> resourceFiles = listAllResourceFiles(args[0]);
      for (ResourceFile resourceFile : resourceFiles) {
        resourceFile.accept(extractor);
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