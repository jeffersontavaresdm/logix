package com.logix.entity

import com.logix.dto.BookDTO
import jakarta.persistence.*

@Entity
@Table(name = "book")
class Book(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  var title: String,
  val description: String? = null
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
    return BookDTO(title = this.title, description = this.description ?: "")
  }
}
