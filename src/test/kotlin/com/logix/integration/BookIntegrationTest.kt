package com.logix.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.logix.dto.BookDTO
import com.logix.entity.Book
import com.logix.repository.BookRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.OffsetDateTime

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class BookIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        @Container
        private val postgres = PostgreSQLContainer<Nothing>("postgres:15-alpine").apply {
            withDatabaseName("testdb")
            withUsername("test")
            withPassword("test")
        }

        @JvmStatic
        @DynamicPropertySource
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }

    @BeforeEach
    fun setup() {
        bookRepository.deleteAll()
    }

    @Test
    fun `should create and retrieve book`() {
        // Given
        val bookDTO = BookDTO(title = "Test Book", description = "Test Description")

        // When/Then - Create
        val createdBookResponse = mockMvc.perform(
            post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.content.title").value(bookDTO.title))
            .andExpect(jsonPath("$.content.description").value(bookDTO.description))
            .andReturn()

        val createdBook = objectMapper.readTree(createdBookResponse.response.contentAsString)
            .path("content")
        val bookId = createdBook.path("id").asLong()

        // When/Then - Retrieve
        mockMvc.perform(get("/api/books/$bookId"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.content.id").value(bookId))
            .andExpect(jsonPath("$.content.title").value(bookDTO.title))
            .andExpect(jsonPath("$.content.description").value(bookDTO.description))
    }

    @Test
    fun `should update book`() {
        // Given
        val book = Book(
            title = "Original Title",
            description = "Original Description",
            createDateTime = OffsetDateTime.now(),
            updateDateTime = OffsetDateTime.now()
        )
        val savedBook = bookRepository.save(book)
        val updatedDTO = BookDTO(title = "Updated Title", description = "Updated Description")

        // When/Then
        mockMvc.perform(
            put("/api/books/${savedBook.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.content.id").value(savedBook.id))
            .andExpect(jsonPath("$.content.title").value(updatedDTO.title))
            .andExpect(jsonPath("$.content.description").value(updatedDTO.description))
    }

    @Test
    fun `should delete book`() {
        // Given
        val book = Book(
            title = "Test Book",
            description = "Test Description",
            createDateTime = OffsetDateTime.now(),
            updateDateTime = OffsetDateTime.now()
        )
        val savedBook = bookRepository.save(book)

        // When/Then
        mockMvc.perform(delete("/api/books/${savedBook.id}"))
            .andExpect(status().isNoContent)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.description").value("Book deleted successfully!"))

        // Verify book is actually deleted
        mockMvc.perform(get("/api/books/${savedBook.id}"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should list all books`() {
        // Given
        val books = listOf(
            Book(
                title = "Book 1",
                description = "Description 1",
                createDateTime = OffsetDateTime.now(),
                updateDateTime = OffsetDateTime.now()
            ),
            Book(
                title = "Book 2",
                description = "Description 2",
                createDateTime = OffsetDateTime.now(),
                updateDateTime = OffsetDateTime.now()
            )
        )
        bookRepository.saveAll(books)

        // When/Then
        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.content").isArray)
            .andExpect(jsonPath("$.content.length()").value(2))
            .andExpect(jsonPath("$.content[0].title").value(books[0].title))
            .andExpect(jsonPath("$.content[1].title").value(books[1].title))
    }
} 