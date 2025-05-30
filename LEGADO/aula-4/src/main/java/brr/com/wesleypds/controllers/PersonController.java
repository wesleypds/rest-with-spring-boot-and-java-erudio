package brr.com.wesleypds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import brr.com.wesleypds.data.vo.PersonVO;
import brr.com.wesleypds.services.PersonService;
import brr.com.wesleypds.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "api/people/v1")
@Tag(name = "People", description = "Endpoits for Managing People")
public class PersonController {

        @Autowired
        private PersonService personService;

        @PostMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                        MediaType.APPLICATION_YAML }, consumes = { MediaType.APPLICATION_JSON,
                                        MediaType.APPLICATION_XML,
                                        MediaType.APPLICATION_YAML })
        @Operation(summary = "Create a person", description = "Create a person", tags = { "People" }, responses = {
                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
        public PersonVO create(@RequestBody PersonVO person) {
                return personService.create(person);
        }

        @PutMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                        MediaType.APPLICATION_YAML }, consumes = { MediaType.APPLICATION_JSON,
                                        MediaType.APPLICATION_XML,
                                        MediaType.APPLICATION_YAML })
        @Operation(summary = "Update a person", description = "Update a person", tags = { "People" }, responses = {
                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
        public PersonVO update(@RequestBody PersonVO person) {
                return personService.update(person);
        }

        @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                        MediaType.APPLICATION_YAML })
        @Operation(summary = "Find a person", description = "Find a person", tags = { "People" }, responses = {
                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
        public PersonVO findById(@PathVariable Long id) {
                return personService.findById(id);
        }

        @GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
        @Operation(summary = "Find all people", description = "Find all people", tags = { "People" }, responses = {
                        @ApiResponse(description = "Success", responseCode = "200", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))
                        }),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
        public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        @RequestParam(value = "direction", defaultValue = "asc") String direction) {
                
                var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
                Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

                return ResponseEntity.ok(personService.findAll(pageable));
        }

        @GetMapping(value = "/find-people-by-name/{firstName}" ,produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YAML })
        @Operation(summary = "Find people by name", description = "Find people by name", tags = { "People" }, responses = {
                        @ApiResponse(description = "Success", responseCode = "200", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))
                        }),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
        public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findPeopleByName(
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        @RequestParam(value = "direction", defaultValue = "asc") String direction,
                        @PathVariable String firstName) {
                
                var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
                Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

                return ResponseEntity.ok(personService.findPeopleByName(firstName, pageable));
        }

        @PatchMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                        MediaType.APPLICATION_YAML })
        @Operation(summary = "Disabling a person", description = "Disabling a person", tags = {
                        "People" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
                                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
                                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                        })
        public PersonVO disablePerson(@PathVariable Long id) {
                return personService.disablePerson(id);
        }

        @DeleteMapping(value = "/{id}")
        @Operation(summary = "Delete a person", description = "Delete a person", tags = { "People" }, responses = {
                        @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
        public ResponseEntity<?> delete(@PathVariable Long id) {
                personService.delete(id);
                return ResponseEntity.noContent().build();
        }

}
