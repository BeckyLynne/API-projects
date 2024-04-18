package call.log.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class PhoneCall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long callId;
	
	private String callDate;
	private String callTime;
	private boolean wasAnswered;
	private boolean calledBack;
	private String timeCallReturned;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "phoneCall", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Child> children = new HashSet<>();

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "call_note",
			joinColumns = @JoinColumn(name = "call_id"),
			inverseJoinColumns = @JoinColumn(name = "note_id"))
	private Set<Note> notes = new HashSet<>();
	
}
