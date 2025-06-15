package com.logix.entity

import com.logix.dto.BookDTO
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "book")
class Book(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  var title: String,
  val description: String? = null,
  val createdAt: OffsetDateTime = OffsetDateTime.now(),
  val updatedAt: OffsetDateTime = OffsetDateTime.now(),
) {

  init {
    formatTitle(this.title)
  }

  private final fun formatTitle(title: String) {
    title
      .trim()
      .lowercase()
      .replaceFirstChar { character -> character.uppercase() }
  }

  fun toDTO(): BookDTO {
    return BookDTO(
      id = this.id,
      title = this.title,
      description = this.description ?: ""
    )
  }

  fun toDTO(id: Long): BookDTO {
    return BookDTO(id = id, title = this.title, description = this.description ?: "")
  }
}
