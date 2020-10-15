// 假设我们在开发一个 OA 系统（办公自动化系统）。公司的组织结构包含部门和员工两种数据类型。其中，部门又可以包含子部门和员工。在数据库中的表结构如下所示：

// 这个例子的代码结构跟上一个例子的很相似，代码实现我直接贴在了下面，你可以对比着看一下。其中，HumanResource 是部门类（Department）和员工类（Employee）抽象出来的父类，为的是能统一薪资的处理逻辑。Demo 中的代码负责从数据库中读取数据并在内存中构建组织架构图。

public abstract class HumanResource {
    protected long id;
    protected double salary;
  
    public HumanResource(long id) {
      this.id = id;
    }
  
    public long getId() {
      return id;
    }
  
    public abstract double calculateSalary();
  }
  
  public class Employee extends HumanResource {
    public Employee(long id, double salary) {
      super(id);
      this.salary = salary;
    }
  
    @Override
    public double calculateSalary() {
      return salary;
    }
  }
  
  public class Department extends HumanResource {
    private List<HumanResource> subNodes = new ArrayList<>();
  
    public Department(long id) {
      super(id);
    }
  
    @Override
    public double calculateSalary() {
      double totalSalary = 0;
      for (HumanResource hr : subNodes) {
        totalSalary += hr.calculateSalary();
      }
      this.salary = totalSalary;
      return totalSalary;
    }
  
    public void addSubNode(HumanResource hr) {
      subNodes.add(hr);
    }
  }
  
  // 构建组织架构的代码
  public class Demo {
    private static final long ORGANIZATION_ROOT_ID = 1001;
    private DepartmentRepo departmentRepo; // 依赖注入
    private EmployeeRepo employeeRepo; // 依赖注入
  
    public void buildOrganization() {
      Department rootDepartment = new Department(ORGANIZATION_ROOT_ID);
      buildOrganization(rootDepartment);
    }
  
    private void buildOrganization(Department department) {
      List<Long> subDepartmentIds = departmentRepo.getSubDepartmentIds(department.getId());
      for (Long subDepartmentId : subDepartmentIds) {
        Department subDepartment = new Department(subDepartmentId);
        department.addSubNode(subDepartment);
        buildOrganization(subDepartment);
      }
      List<Long> employeeIds = employeeRepo.getDepartmentEmployeeIds(department.getId());
      for (Long employeeId : employeeIds) {
        double salary = employeeRepo.getEmployeeSalary(employeeId);
        department.addSubNode(new Employee(employeeId, salary));
      }
    }
  }