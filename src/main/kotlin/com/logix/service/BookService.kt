package com.logix.service

import com.logix.dto.BookDTO
import com.logix.entity.Book
import com.logix.exception.BookNotFoundException
import com.logix.repository.BookRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class BookService(private val repository: BookRepository) {

  fun findById(id: Long): BookDTO {
    val book = repository.findByEntityId(id) ?: throw BookNotFoundException(id)
    return book.toDTO()
  }

  fun getBooks(): List<BookDTO> {
    return repository
      .findAll()
      .map { book: Book -> book.toDTO() }
  }

  fun updateById(id: Long, bookDTO: BookDTO): BookDTO {
    val existingBook = repository.findByEntityId(id) ?: throw BookNotFoundException(id)
    val updatedBook = repository.save(bookDTO.toEntity(id = id, updatedAt = OffsetDateTime.now()))
    return updatedBook.toDTO()
  }

  fun create(bookDTO: BookDTO): BookDTO {
    val book = repository.save(bookDTO.toEntity())
    return book.toDTO()
  }

  fun delete(id: Long) {
    val book = repository.findByEntityId(id) ?: throw BookNotFoundException(id)
    repository.delete(book)
  }
}