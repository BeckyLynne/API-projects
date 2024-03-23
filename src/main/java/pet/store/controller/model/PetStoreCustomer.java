package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreCustomer {

	private Long customerId;
	private String lastName;
	private String firstName;
	private String customerEmail;
	//private Set<PetStore> petStores = new HashSet<>();
	
	 public PetStoreCustomer(Customer customer) {
	      this.customerId = customer.getCustomerId();
	      this.firstName = customer.getFirstName();
	      this.lastName = customer.getLastName();
	      this.customerEmail = customer.getCustomerEmail();
	    
		}
}
