package com.logix.service

import com.logix.dto.BookDTO
import com.logix.entity.Book
import com.logix.exception.BookNotFoundException
import com.logix.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(private val repository: BookRepository) {

  fun findById(id: Long): BookDTO {
    val book = repository.findByEntityId(id) ?: throw BookNotFoundException(id)
    return BookDTO(title = book.title, description = book.description ?: "")
  }

  fun getBooks(): List<BookDTO> {
    return repository
      .findAll()
      .map { book: Book -> BookDTO(title = book.title, description = book.description ?: "") }
  }

  fun updateById(id: Long, bookDTO: BookDTO): BookDTO {
    findById(id)
    val updatedBook = repository.save(bookDTO.toEntity(id))
    return BookDTO(title = updatedBook.title, description = updatedBook.description ?: "")
  }

  fun create(bookDTO: BookDTO): BookDTO {
    return repository.save(bookDTO.toEntity()).toDTO()
  }

  fun delete(id: Long) {
    val book = repository.findById(id).orElseThrow { BookNotFoundException() }
    repository.delete(book)
  }
}