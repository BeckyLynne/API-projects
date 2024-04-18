package call.log.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long childId;
	
	private String childName;
	
	@EqualsAndHashCode.Exclude
	private int childAge;
	
	@EqualsAndHashCode.Exclude
	private String mood;
	
	@EqualsAndHashCode.Exclude
	private boolean wantedToTalk;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "call_id", nullable = false)
	private PhoneCall phoneCall;

}
