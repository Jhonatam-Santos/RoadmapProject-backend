package com.roadmap.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class User(
        @Id
        val id: String? = null,
        val name: String,
        val document: String,
        val password: String
)