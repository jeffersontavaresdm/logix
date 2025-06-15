package com.logix.repository

import com.logix.entity.Book
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : BaseRepository<Book>
