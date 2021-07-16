package com.example.inz20.DTO

data class ToDo (
    var id: Long = -1,
    var name: String = "",
    var text: String = "",
    var createdAt: Long = System.currentTimeMillis(),
)