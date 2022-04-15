package com.mahoote.springbootsms.models.entities

import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(
        name = "users_user_id_seq",
        allocationSize = 1
    )
    @Column(name = "user_id")
    val id: Long? = null,

    @Column(name = "phone_nr")
    val phoneNumber: String
        )
