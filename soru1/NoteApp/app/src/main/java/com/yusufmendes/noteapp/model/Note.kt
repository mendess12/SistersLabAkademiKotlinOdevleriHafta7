package com.yusufmendes.noteapp.model

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class Note(
    var noteId: String? = "",
    var noteTitle: String? = "",
    var noteDetail: String? = ""
) :
    Parcelable
