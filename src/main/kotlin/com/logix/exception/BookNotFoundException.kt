package com.logix.exception

class BookNotFoundException : NotFoundException {
  constructor() : super("BOOK_NOT_FOUND", "Book not found")
  constructor(id: Long) : super("BOOK_NOT_FOUND", "Book with ID $id not found")
  constructor(code: String, message: String) : super(code, message)
}
