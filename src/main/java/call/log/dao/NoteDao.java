package call.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import call.log.entity.Note;

public interface NoteDao extends JpaRepository<Note, Long> {

}
