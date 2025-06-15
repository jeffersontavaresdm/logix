package com.logix.service

import com.logix.dto.BookDTO
import com.logix.entity.Book
import com.logix.exception.BookNotFoundException
import com.logix.repository.BookRepository
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BookServiceTest {

  private lateinit var bookRepository: BookRepository
  private lateinit var bookService: BookService

  @BeforeEach
  fun setup() {
    bookRepository = mockk()
    bookService = BookService(bookRepository)
  }

  @Test
  fun `findById should return book when it exists`() {
    // Given
    val bookId = 1L
    val book = Book(
      id = bookId,
      title = "Test Book",
      description = "Test Description",
      createdAt = OffsetDateTime.now(),
      updatedAt = OffsetDateTime.now()
    )

    every { bookRepository.findByEntityId(bookId) } returns book

    // When
    val result = bookService.findById(bookId)

    // Then
    assertNotNull(result)
    assertEquals(bookId, result.id)
    assertEquals(book.title, result.title)
    assertEquals(book.description, result.description)
    verify { bookRepository.findByEntityId(bookId) }
  }

  @Test
  fun `findById should throw BookNotFoundException when book does not exist`() {
    // Given
    val bookId = 1L
    every { bookRepository.findByEntityId(bookId) } returns null

    // When/Then
    assertThrows<BookNotFoundException> { bookService.findById(bookId) }
    verify { bookRepository.findByEntityId(bookId) }
  }

  @Test
  fun `getBooks should return all books`() {
    // Given
    val books = listOf(
      Book(
        id = 1L,
        title = "Book 1",
        description = "Description 1",
        createdAt = OffsetDateTime.now(),
        updatedAt = OffsetDateTime.now()
      ),
      Book(
        id = 2L,
        title = "Book 2",
        description = "Description 2",
        createdAt = OffsetDateTime.now(),
        updatedAt = OffsetDateTime.now()
      )
    )
    every { bookRepository.findAll() } returns books

    // When
    val result = bookService.getBooks()

    // Then
    assertEquals(2, result.size)
    assertEquals(books[0].title, result[0].title)
    assertEquals(books[1].title, result[1].title)
    verify { bookRepository.findAll() }
  }

  @Test
  fun `create should save and return new book`() {
    // Given
    val bookDTO = BookDTO(title = "New Book", description = "New Description")
    val savedBook = Book(
      id = 1L,
      title = bookDTO.title,
      description = bookDTO.description,
      createdAt = OffsetDateTime.now(),
      updatedAt = OffsetDateTime.now()
    )
    every { bookRepository.save(any()) } returns savedBook

    // When
    val result = bookService.create(bookDTO)

    // Then
    assertNotNull(result)
    assertEquals(savedBook.id, result.id)
    assertEquals(savedBook.title, result.title)
    assertEquals(savedBook.description, result.description)
    verify { bookRepository.save(any()) }
  }

  @Test
  fun `updateById should update and return book`() {
    // Given
    val bookId = 1L
    val bookDTO = BookDTO(title = "Updated book", description = "Updated Description")
    val existingBook = Book(
      id = bookId,
      title = "Old Title",
      description = "Old Description",
      createdAt = OffsetDateTime.now(),
      updatedAt = OffsetDateTime.now()
    )
    val updatedBook = Book(
      id = bookId,
      title = bookDTO.title,
      description = bookDTO.description,
      createdAt = existingBook.createdAt,
      updatedAt = OffsetDateTime.now()
    )
    every { bookRepository.findByEntityId(bookId) } returns existingBook
    every { bookRepository.save(any()) } returns updatedBook

    // When
    val result = bookService.updateById(bookId, bookDTO)

    // Then
    assertNotNull(result)
    assertEquals(bookId, result.id)
    assertEquals(bookDTO.title, result.title)
    assertEquals(bookDTO.description, result.description)
    verify { bookRepository.findByEntityId(bookId) }
    verify { bookRepository.save(any()) }
  }

  @Test
  fun `delete should remove book when it exists`() {
    // Given
    val bookId = 1L
    val book = Book(
      id = bookId,
      title = "Test Book",
      description = "Test Description",
      createdAt = OffsetDateTime.now(),
      updatedAt = OffsetDateTime.now()
    )
    every { bookRepository.findByEntityId(bookId) } returns book
    every { bookRepository.delete(any()) } just Runs

    // When
    bookService.delete(bookId)

    // Then
    verify { bookRepository.findByEntityId(bookId) }
    verify { bookRepository.delete(book) }
  }

  @Test
  fun `delete should throw BookNotFoundException when book does not exist`() {
    // Given
    val bookId = 1L
    every { bookRepository.findByEntityId(bookId) } returns null

    // When/Then
    assertThrows<BookNotFoundException> { bookService.delete(bookId) }
    verify { bookRepository.findByEntityId(bookId) }
  }
} 