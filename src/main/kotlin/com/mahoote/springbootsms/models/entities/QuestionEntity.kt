package com.mahoote.springbootsms.models.entities

import javax.persistence.*

@Entity
@Table(name = "questions")
data class QuestionEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_question_id_seq")
    @SequenceGenerator(
        name = "questions_question_id_seq",
        allocationSize = 1
    )
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "question")
    val question: String,

    @Column(name = "key_word")
    val keyWord: String
        )