package call.log.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import call.log.controller.model.CallData;
import call.log.controller.model.CallData.ChildData;
import call.log.controller.model.CallData.NoteData;
import call.log.service.CallService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/call")
@Slf4j
public class CallController {

	@Autowired
	private CallService callService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CallData callData(@RequestBody CallData callData) {
		log.info("Creating call {}", callData);
		return callService.saveCall(callData);
	}

	@GetMapping
	public List<CallData> retrieveAllCalls() {
		log.info("Retrieve all calls.");
		return callService.retrieveAllCalls();
	}

	@PutMapping("/{callId}")
	public CallData updateCall(@PathVariable Long callId, @RequestBody CallData callData) {
		callData.setCallId(callId);
		log.info("Updating call {}", callData);
		return callService.saveCall(callData);
	}
	
	@GetMapping("/{callId}")
	public CallData retrieveCallById(@PathVariable Long callId) {

		log.info("Retrieving call with ID={}", callId);
		return callService.retrieveCallById(callId);
	}

	@DeleteMapping("/{callId}")
	public Map<String, String> deleteCallById(@PathVariable Long callId) {
		log.info("Deleting call with ID={}", callId);
		callService.deleteCallById(callId);
		return Map.of("message", "Deletion of call with ID = " + callId + " was successful.");
	}

	@PostMapping("/{callId}/note")
	@ResponseStatus(code = HttpStatus.CREATED)
	public NoteData insertNote(@PathVariable Long callId, @RequestBody NoteData noteData) {
		log.info("Creating note {} for call {}", noteData, callId);
		return callService.saveNote(callId, noteData);
	}

	@GetMapping("/note")
	public List<NoteData> retrieveAllNotes() {
		log.info("Retrieving all notes");
		return callService.retrieveAllNotes();
	}

	@PutMapping("/{callId}/note/{noteId}")
	public NoteData updateNote(@PathVariable Long noteId, @PathVariable Long callId, @RequestBody NoteData noteData) {
		noteData.setNoteId(noteId);
		noteData.setNoteId(noteId);
		log.info("Updating note {} for call{}", noteData, callId);
		return callService.saveNote(callId, noteData);
	}

	@PostMapping("/{callId}/child")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ChildData addChild(@PathVariable Long callId, @RequestBody ChildData childData) {
		log.info("Creating child {} on call {}", childData, callId);
		return callService.saveChild(callId, childData);
	}

}