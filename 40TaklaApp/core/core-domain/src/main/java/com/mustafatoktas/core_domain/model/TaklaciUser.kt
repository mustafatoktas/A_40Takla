package com.mustafatoktas.core_domain.model

import com.google.firebase.Timestamp

data class TaklaciUser(
    val userID: String = String(),
    val isimSoyisim: String? = null,
    val timeStamp: Timestamp? = null
)
