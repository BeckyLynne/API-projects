package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;

@Data
@NoArgsConstructor
public class PetStoreEmployee {
	private Long employeeId;
	private Long petStoreId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;
	//private String employeeName = employeeFirstName + employeeLastName;
	
	public PetStoreEmployee(Employee employee) {
	      this.employeeId = employee.getEmployeeId();
	      this.employeeFirstName = employee.getEmployeeFirstName();
	      this.employeeLastName = employee.getEmployeeLastName();
	      this.employeePhone = employee.getEmployeePhone();
	      this.employeeJobTitle = employee.getEmployeeJobTitle();
	    }
}
