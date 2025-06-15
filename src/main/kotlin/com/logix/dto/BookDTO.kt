package com.logix.dto

import com.logix.entity.Book

data class BookDTO(val title: String, val description: String) {

  fun toEntity(id: Long): Book {
    return Book(id = id, description = description, title = title)
  }

  fun toEntity(): Book {
    return Book(description = description, title = title)
  }
}