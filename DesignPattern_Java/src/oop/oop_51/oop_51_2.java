// 前面我们反复提到，适配器模式的应用场景是“接口不兼容”。那在实际的开发中，什么情况下才会出现接口不兼容呢？我建议你先自己思考一下这个问题，然后再来看我下面的总结 。

// 1. 封装有缺陷的接口设计

public class CD { //这个类来自外部sdk，我们无权修改它的代码
    //...
    public static void staticFunction1() { //... }
    
    public void uglyNamingFunction2() { //... }
  
    public void tooManyParamsFunction3(int paramA, int paramB, ...) { //... }
    
     public void lowPerformanceFunction4() { //... }
  }
  
  // 使用适配器模式进行重构
  public class ITarget {
    void function1();
    void function2();
    void fucntion3(ParamsWrapperDefinition paramsWrapper);
    void function4();
    //...
  }
  // 注意：适配器类的命名不一定非得末尾带Adaptor
  public class CDAdaptor extends CD implements ITarget {
    //...
    public void function1() {
       super.staticFunction1();
    }
    
    public void function2() {
      super.uglyNamingFucntion2();
    }
    
    public void function3(ParamsWrapperDefinition paramsWrapper) {
       super.tooManyParamsFunction3(paramsWrapper.getParamA(), ...);
    }
    
    public void function4() {
      //...reimplement it...
    }
  }