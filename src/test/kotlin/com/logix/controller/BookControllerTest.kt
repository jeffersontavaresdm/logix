package com.logix.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.logix.dto.BookDTO
import com.logix.service.BookService
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class BookControllerTest {

  private lateinit var mockMvc: MockMvc
  private lateinit var bookService: BookService
  private lateinit var objectMapper: ObjectMapper

  @BeforeEach
  fun setup() {
    bookService = mockk()
    mockMvc = MockMvcBuilders.standaloneSetup(BookController(bookService)).build()
    objectMapper = ObjectMapper()
  }

  @Test
  fun `findById should return book when it exists`() {
    // Given
    val bookId = 1L
    val bookDTO = BookDTO(id = bookId, title = "Test Book", description = "Test Description")
    every { bookService.findById(bookId) } returns bookDTO

    // When/Then
    mockMvc.perform(get("/api/books/$bookId"))
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.description").value("Book found successfully!"))
      .andExpect(jsonPath("$.content.id").value(bookId))
      .andExpect(jsonPath("$.content.title").value(bookDTO.title))
      .andExpect(jsonPath("$.content.description").value(bookDTO.description))

    verify { bookService.findById(bookId) }
  }

  @Test
  fun `findAll should return list of books`() {
    // Given
    val books = listOf(
      BookDTO(id = 1L, title = "Book 1", description = "Description 1"),
      BookDTO(id = 2L, title = "Book 2", description = "Description 2")
    )
    every { bookService.getBooks() } returns books

    // When/Then
    mockMvc.perform(get("/api/books"))
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.description").value("Books found successfully!"))
      .andExpect(jsonPath("$.content").isArray)
      .andExpect(jsonPath("$.content.length()").value(2))
      .andExpect(jsonPath("$.content[0].title").value(books[0].title))
      .andExpect(jsonPath("$.content[1].title").value(books[1].title))

    verify { bookService.getBooks() }
  }

  @Test
  fun `create should create new book`() {
    // Given
    val bookDTO = BookDTO(title = "New Book", description = "New Description")
    val createdBook = bookDTO.copy(id = 1L)
    every { bookService.create(bookDTO) } returns createdBook

    // When/Then
    mockMvc.perform(
      post("/api/books")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(bookDTO))
    )
      .andExpect(status().isCreated)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.description").value("Book created successfully!"))
      .andExpect(jsonPath("$.content.id").value(createdBook.id))
      .andExpect(jsonPath("$.content.title").value(createdBook.title))
      .andExpect(jsonPath("$.content.description").value(createdBook.description))

    verify { bookService.create(bookDTO) }
  }

  @Test
  fun `update should update existing book`() {
    // Given
    val bookId = 1L
    val bookDTO = BookDTO(title = "Updated Book", description = "Updated Description")
    val updatedBook = bookDTO.copy(id = bookId)
    every { bookService.updateById(bookId, bookDTO) } returns updatedBook

    // When/Then
    mockMvc.perform(
      put("/api/books/$bookId")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(bookDTO))
    )
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.description").value("Book with id $bookId has updated"))
      .andExpect(jsonPath("$.content.id").value(updatedBook.id))
      .andExpect(jsonPath("$.content.title").value(updatedBook.title))
      .andExpect(jsonPath("$.content.description").value(updatedBook.description))

    verify { bookService.updateById(bookId, bookDTO) }
  }

  @Test
  fun `delete should remove book`() {
    // Given
    val bookId = 1L
    every { bookService.delete(bookId) } just Runs

    // When/Then
    mockMvc.perform(delete("/api/books/$bookId"))
      .andExpect(status().isNoContent)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.description").value("Book deleted successfully!"))

    verify { bookService.delete(bookId) }
  }
} 