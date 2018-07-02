//https://www.callicoder.com/spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate/
//https://docs.spring.io/autorepo/docs/spring-data-jpa/current/api/org/springframework/data/jpa/repository/support/SimpleJpaRepository.html

package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

	@Autowired
	NoteRepository noteRepository;

	// Get All Notes
	@GetMapping("/notes")
	public List<Note> getAllNotes() {
		return noteRepository.findAll();
		/*
		 * It calls JpaRepository’s findAll() method to retrieve all the notes from the
		 * database and returns the entire list. Also, The @GetMapping("/notes")
		 * annotation is a short form of @RequestMapping(value="/notes",
		 * method=RequestMethod.GET)
		 */
	}

	// Create a new Note
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		return noteRepository.save(note);
		/*
		 * The @RequestBody annotation is used to bind the request body with a method
		 * parameter. The @Valid annotation makes sure that the request body is valid.
		 * Remember, we had marked Note’s title and content with @NotBlank annotation in
		 * the Note model? If the request body doesn’t have a title or a content, then
		 * spring will return a 400 BadRequest error to the client.
		 */
	}

	// Get a Single Note
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value = "id") Long noteId) {
		return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		/*
		 * In the above method, we are throwing a ResourceNotFoundException whenever a
		 * Note with the given id is not found. This will cause Spring Boot to return a
		 * 404 Not Found error to the client (Remember, we had added
		 * a @ResponseStatus(value = HttpStatus.NOT_FOUND) annotation to the
		 * ResourceNotFoundException class).
		 */
	}

	// Update a Note
	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails) {

		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());

		Note updatedNote = noteRepository.save(note);
		return updatedNote;
	}

	// Delete a Note
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

		noteRepository.delete(note);

		return ResponseEntity.ok().build();
	}
}

/*for postman testing
 * {
 * 
 * "id": 1, "title": "my titlte", "content": "the content is blank",
 * "createdAt": 1234234324, "updatedAt": 142342342 }
 */