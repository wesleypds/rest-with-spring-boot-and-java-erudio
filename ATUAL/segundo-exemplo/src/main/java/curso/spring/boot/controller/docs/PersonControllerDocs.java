package curso.spring.boot.controller.docs;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import curso.spring.boot.model.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface PersonControllerDocs {

    @Operation(summary = "Find all people",
        tags = "People",
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                    )
                }),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                    @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                    @RequestParam(name = "field", defaultValue = "id") String field);

    @Operation(summary = "Find all people by name",
        tags = "People",
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                    )
                }),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findPeopleByName(@PathVariable(name = "firstName") String firstName,
                                                    @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                    @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                    @RequestParam(name = "field", defaultValue = "id") String field);

    @Operation(summary = "Find a person",
        tags = "People",
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
        }
    )
    PersonDTO findById(Long id);

    @Operation(summary = "Create a person",
        tags = "People",
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
        }
    )
    PersonDTO create(PersonDTO model);

    @Operation(summary = "Update a person",
        tags = "People",
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
        }
    )
    PersonDTO update(PersonDTO model);

    @Operation(summary = "Disable a person",
        tags = "People",
        responses = {
            @ApiResponse(
                description = "Success", 
                responseCode = "200", 
                content = @Content(schema = @Schema(implementation = PersonDTO.class))),
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
        }
    )
    PersonDTO disablePerson(Long id);

    @Operation(summary = "Delete a person",
        tags = "People",
        responses = {
            @ApiResponse(description = "No content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<?> delete(Long id);

}