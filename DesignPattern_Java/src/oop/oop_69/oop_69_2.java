
public abstract class ResourceFile {
    protected String filePath;
    public ResourceFile(String filePath) {
      this.filePath = filePath;
    }
    public abstract ResourceFileType getType();
  }
  
  public class PdfFile extends ResourceFile {
    public PdfFile(String filePath) {
      super(filePath);
    }
  
    @Override
    public ResourceFileType getType() {
      return ResourceFileType.PDF;
    }
  
    //...
  }
  
  //...PPTFile/WordFile跟PdfFile代码结构类似，此处省略...
  
  public interface Extractor {
    void extract2txt(ResourceFile resourceFile);
  }
  
  public class PdfExtractor implements Extractor {
    @Override
    public void extract2txt(ResourceFile resourceFile) {
      //...
    }
  }
  
  //...PPTExtractor/WordExtractor跟PdfExtractor代码结构类似，此处省略...
  
  public class ExtractorFactory {
    private static final Map<ResourceFileType, Extractor> extractors = new HashMap<>();
    static {
      extractors.put(ResourceFileType.PDF, new PdfExtractor());
      extractors.put(ResourceFileType.PPT, new PPTExtractor());
      extractors.put(ResourceFileType.WORD, new WordExtractor());
    }
  
    public static Extractor getExtractor(ResourceFileType type) {
      return extractors.get(type);
    }
  }
  
  public class ToolApplication {
    public static void main(String[] args) {
      List<ResourceFile> resourceFiles = listAllResourceFiles(args[0]);
      for (ResourceFile resourceFile : resourceFiles) {
        Extractor extractor = ExtractorFactory.getExtractor(resourceFile.getType());
        extractor.extract2txt(resourceFile);
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