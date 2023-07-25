package com.roadmap.controller

import com.roadmap.entity.User
import com.roadmap.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class Controller(val repository: UserRepository) {

    @PostMapping
    fun create(@RequestBody user: User) = ResponseEntity.ok(repository.save(user))

    @GetMapping
    fun read() = ResponseEntity.ok(repository.findAll())

    @PutMapping("/{name}")
    fun update(@PathVariable name: String, @RequestBody user: User): ResponseEntity<User> {
        val userDBOptional = repository.findByName(name)
        val toSave = userDBOptional
            .orElseThrow { RuntimeException("User name: $name not found!") }
            .copy(name = user.name, document = user.document, password = user.password)
        return ResponseEntity.ok(repository.save(toSave))
    }

    @DeleteMapping("/{name}")
    fun delete(@PathVariable name: String) = repository
        .findByName(name)
        .ifPresent { repository.delete(it) }
}