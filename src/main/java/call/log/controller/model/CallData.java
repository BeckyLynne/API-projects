package call.log.controller.model;

import java.util.HashSet;
import java.util.Set;

import call.log.entity.Child;
import call.log.entity.Note;
import call.log.entity.PhoneCall;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CallData {

	private Long callId;
	private String callDate;
	private String callTime;
	private boolean wasAnswered;
	private boolean calledBack;
	private String timeCallReturned;

	private Set<NoteData> noteData = new HashSet<>();
	private Set<ChildData> childData = new HashSet<>();

	public CallData(PhoneCall phoneCall) {
		callId = phoneCall.getCallId();
		callDate = phoneCall.getCallDate();
		callTime = phoneCall.getCallTime();
		wasAnswered = phoneCall.isWasAnswered();
		calledBack = phoneCall.isCalledBack();
		timeCallReturned = phoneCall.getTimeCallReturned();

		for (Child child: phoneCall.getChildren()) {
			this.childData.add(new ChildData(child));
		}

		for (Note note : phoneCall.getNotes()) {
			this.noteData.add(new NoteData(note));
		}
	}

	@Data
	@NoArgsConstructor
	public static class NoteData {
		private Long noteId;
		private String additionalNotes;
		private int importance;

		public NoteData(Note note) {
			noteId = note.getNoteId();
			additionalNotes = note.getAdditionalNotes();
			importance = note.getImportance();
		}
	}

	@Data
	@NoArgsConstructor
	public static class ChildData {
		private Long childId;
		private String childName;
		private int childAge;
		private String mood;
		private boolean wantedToTalk;

		public ChildData(Child child) {
			childId = child.getChildId();
			childName = child.getChildName();
			childAge = child.getChildAge();
			mood = child.getMood();
			wantedToTalk = child.isWantedToTalk();
		}
	}
}

