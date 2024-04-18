package call.log.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import call.log.controller.model.CallData;
import call.log.controller.model.CallData.ChildData;
import call.log.controller.model.CallData.NoteData;
import call.log.dao.CallDao;
import call.log.dao.ChildDao;
import call.log.dao.NoteDao;
import call.log.entity.Child;
import call.log.entity.Note;
import call.log.entity.PhoneCall;

@Service
public class CallService {

	@Autowired
	private CallDao callDao;

	@Autowired
	private NoteDao noteDao;

	@Autowired
	private ChildDao childDao;

	@Transactional(readOnly = false)
	public CallData saveCall(CallData callData) {
		Long callId = callData.getCallId();
		PhoneCall phoneCall = findOrCreateCall(callId);
		copyCallFields(phoneCall, callData);
		return new CallData(callDao.save(phoneCall));
	}

	private static void copyCallFields(PhoneCall phoneCall, CallData callData) {
		phoneCall.setCallDate(callData.getCallDate());
		phoneCall.setCallTime(callData.getCallTime());
		phoneCall.setWasAnswered(callData.isWasAnswered());
		phoneCall.setCalledBack(callData.isCalledBack());
		phoneCall.setTimeCallReturned(callData.getTimeCallReturned());
		phoneCall.setCallId(callData.getCallId());
	
	}

	
	private PhoneCall findOrCreateCall(Long callId) {
		PhoneCall phoneCall;

		if (Objects.isNull(callId)) {
			phoneCall = new PhoneCall();
		} else {
			phoneCall = findCallById(callId);
		}
		return phoneCall;
	}
	
	
	public PhoneCall findCallById(Long callId) {
		return callDao.findById(callId)
				.orElseThrow(() -> new NoSuchElementException("Call with ID=" + callId + " does not exist."));
	}

	@Transactional(readOnly = false)
	public NoteData saveNote(Long callId, NoteData noteData) {
		PhoneCall phoneCall = findCallById(callId);
		Long noteId = noteData.getNoteId();
		Note note = findOrCreateNote(noteId, callId);

		copyNoteFields(note, noteData);

		note.getPhoneCalls().add(phoneCall);
		phoneCall.getNotes().add(note);

		Note dbNote = noteDao.save(note);
		return new NoteData(dbNote);
	}

	private Note findOrCreateNote(Long noteId, Long callId) {
		Note note;
		
		if (Objects.isNull(noteId)) {
			note = new Note();
		} else {
			note = findNoteById(noteId, callId);
		}
		return note;
	}

	private void copyNoteFields(Note note, NoteData noteData) {
		note.setNoteId(noteData.getNoteId());
		note.setAdditionalNotes(noteData.getAdditionalNotes());
		note.setImportance(noteData.getImportance());
	
	}

	private Note findNoteById(Long noteId, Long callId) {
		Note note = noteDao.findById(noteId)
				.orElseThrow(() -> new NoSuchElementException(
						"Note with ID=" + noteId + " does not exist."));

		boolean foundNote = false;
		
		for(PhoneCall phoneCall : note.getPhoneCalls()) {
			if(phoneCall.getCallId().equals(callId)) {
				foundNote = true;
				break;
			}
		}
		
		if(!foundNote) {
			throw new IllegalArgumentException("That's not the correct note for this call.");			
		}
		return note;
	}

	@Transactional(readOnly = false)
	public ChildData saveChild(Long callId, ChildData childData) {
		PhoneCall phoneCall = findCallById(callId);
		Long childId = childData.getChildId();
		Child child = findOrCreateChild(childId, callId);

		copyChildFields(child, childData);

		child.setPhoneCall(phoneCall);
		phoneCall.getChildren().add(child);

		Child dbChild = childDao.save(child);
		return new ChildData(dbChild);
	}

	private Child findOrCreateChild(Long childId, Long callId) {
	   if(childId == null) {
		   return new Child();
	   }else {
		   return findChildById(callId, childId);
	   }
	}
	
	private Child findChildById(Long callId, Long childId) {
		Child child = childDao.findById(childId)
				.orElseThrow(() -> new NoSuchElementException("Child not found"));

		if(child.getPhoneCall().getCallId() != callId) {
			throw new IllegalArgumentException("Child was not on this call");
		}
		return child;
	}

	private void copyChildFields(Child child, ChildData childData) {
		child.setChildId(childData.getChildId());
		child.setChildName(childData.getChildName());
		child.setChildAge(childData.getChildAge());
		child.setMood(childData.getMood());
		child.setWantedToTalk(childData.isWantedToTalk());
	}

	@Transactional(readOnly = true)
	public List<CallData> retrieveAllCalls() {

		List<PhoneCall> phoneCalls = callDao.findAll();
		List<CallData> results = new LinkedList<>();

		for (PhoneCall phoneCall : phoneCalls) {
			CallData cd = new CallData(phoneCall);

			cd.getChildData().clear();
			cd.getNoteData().clear();

			results.add(cd);
		}
		return results;

	}

	@Transactional(readOnly = true)
	public CallData retrieveCallById(Long callId) {
		PhoneCall phoneCall = findCallById(callId);
		return new CallData(phoneCall);
	}

	@Transactional(readOnly = false)
	public void deleteCallById(Long callId) {
		PhoneCall phoneCall = findCallById(callId);
		callDao.delete(phoneCall);
	}
	@Transactional(readOnly = true)
	public List<NoteData> retrieveAllNotes() {
		
		List<Note> notes = noteDao.findAll();
		List<NoteData> results = new LinkedList<>();
		
		for(Note note : notes) {
		NoteData nd = new NoteData(note);
		
		results.add(nd);
		}
		return results;
	}
	


}
