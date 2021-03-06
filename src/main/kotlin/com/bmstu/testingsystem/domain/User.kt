package com.bmstu.testingsystem.domain

import java.util.*
import javax.persistence.*
import javax.persistence.JoinColumn
import javax.persistence.GeneratedValue




@Entity
@Table(name = "users")
data class User (

        var username: String,

        var email: String,

        var password: String

) {
    @GeneratedValue
    @Id
    var id: UUID = UUID.randomUUID()

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="person_id")
    var person: Person = Person()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="user_id", referencedColumnName="id")
    val exams: MutableList<Exam> = arrayListOf()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="user_id", referencedColumnName="id")
    val results: MutableList<ExamResult> = arrayListOf()

    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.USER

}

