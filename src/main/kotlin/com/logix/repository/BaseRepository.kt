package com.logix.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.query.Param

@NoRepositoryBean
interface BaseRepository<T> : JpaRepository<T, Long> {

  @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id")
  fun findByEntityId(@Param("id") id: Long): T?

//  @Transactional
//  fun updateEntityById(@Param("id") id: Long, @Param("entityBody") entityBody: T): T {
//    findByEntityId(id)
//    return save(entityBody!!)
//  }
}