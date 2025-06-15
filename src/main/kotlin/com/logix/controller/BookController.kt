package com.logix.controller

import com.logix.dto.ApiResponse
import com.logix.dto.BookDTO
import com.logix.dto.asResponse
import com.logix.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
class BookController(private val service: BookService) {

  @GetMapping("/{id}")
  fun findById(@PathVariable id: Long): ResponseEntity<ApiResponse<BookDTO>> = ResponseEntity
    .ok(ApiResponse.success("Book with id $id found successfully!", service.findById(id)))

  @GetMapping
  fun findAll(): ResponseEntity<ApiResponse<List<BookDTO>>> = ResponseEntity
    .ok(ApiResponse.success("Books found successfully!!", service.getBooks()))

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  fun update(@PathVariable("id") id: Long, @RequestBody bookDTO: BookDTO): ResponseEntity<ApiResponse<BookDTO>> =
    ApiResponse
      .success("Book with id $id has updated", service.updateById(id, bookDTO))
      .asResponse(HttpStatus.OK)

  @PostMapping
  fun create(@RequestBody bookDTO: BookDTO): ResponseEntity<ApiResponse<BookDTO>> = ApiResponse
    .success("Book created successfully!!!", service.create(bookDTO))
    .asResponse(HttpStatus.CREATED)

  @DeleteMapping("/{id}")
  fun delete(@PathVariable("id") bookId: Long): ResponseEntity<ApiResponse<Unit>> {
    return ApiResponse
      .success(description = "Book deleted successfully!!!", content = service.delete(bookId))
      .asResponse(HttpStatus.NO_CONTENT)
  }
}
