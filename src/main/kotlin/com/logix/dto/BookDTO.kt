package com.logix.dto

import com.logix.entity.Book
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

data class BookDTO(
  @Schema(description = "Unique identifier of the book", example = "2")
  val id: Long? = null,

  @Schema(description = "Title of the book", example = "Clean Code")
  val title: String,

  @Schema(description = "description of the book", example = "The book talks about organized code")
  val description: String
) {

  fun toEntity(id: Long): Book {
    return Book(id = id, description = description, title = title)
  }

  fun toEntity(id: Long, updateDateTime: OffsetDateTime): Book {
    return Book(
      id = id,
      description = description,
      title = title,
      updatedAt = updateDateTime
    )
  }

  fun toEntity(): Book {
    return Book(description = description, title = title)
  }
}