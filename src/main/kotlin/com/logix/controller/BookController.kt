package com.logix.controller

import com.logix.dto.ApiResponse
import com.logix.dto.BookDTO
import com.logix.dto.asResponse
import com.logix.service.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerResponse

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Endpoints for managing books")
class BookController(private val service: BookService) {

  @Operation(summary = "Find book by ID", description = "Returns a single book by its ID")
  @SwaggerResponse(
    responseCode = "200",
    description = "Book found",
    content = [Content(schema = Schema(implementation = BookDTO::class))]
  )
  @GetMapping("/{id}")
  fun findById(
    @Parameter(description = "ID of the book to retrieve") @PathVariable id: Long
  ): ResponseEntity<ApiResponse<BookDTO>> = ApiResponse
    .success(description = "Book found successfully!", content = service.findById(id))
    .asResponse(HttpStatus.OK)

  @Operation(summary = "List all books", description = "Returns a list of books")
  @GetMapping
  fun findAll(): ResponseEntity<ApiResponse<List<BookDTO>>> {
    val books = service.getBooks()
    val description = if (books.isNotEmpty()) "Books found successfully!" else "List of books is empty"

    return ApiResponse
      .success(description = description, content = books)
      .asResponse(HttpStatus.OK)
  }

  @Operation(summary = "Update a book")
  @PutMapping("/{id}")
  fun update(
    @Parameter(description = "ID of the book to update") @PathVariable("id") id: Long,
    @RequestBody bookDTO: BookDTO
  ): ResponseEntity<ApiResponse<BookDTO>> = ApiResponse
    .success(description = "Book with id $id has updated", content = service.updateById(id, bookDTO))
    .asResponse(HttpStatus.OK)

  @Operation(summary = "Create a new book")
  @PostMapping
  fun create(@RequestBody bookDTO: BookDTO): ResponseEntity<ApiResponse<BookDTO>> = ApiResponse
    .success(description = "Book created successfully!", content = service.create(bookDTO))
    .asResponse(HttpStatus.CREATED)

  @Operation(summary = "Delete a book by ID")
  @DeleteMapping("/{id}")
  fun delete(
    @Parameter(description = "ID of the book to delete") @PathVariable("id") bookId: Long
  ): ResponseEntity<ApiResponse<Unit>> = ApiResponse
    .success(description = "Book deleted successfully!", content = service.delete(bookId))
    .asResponse(HttpStatus.NO_CONTENT)
}
