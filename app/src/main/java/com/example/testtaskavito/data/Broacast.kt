package com.example.testtaskavito.data

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

// TODO: Не успел заменить. выглядит костыльно. для отлова ошибок и вывода их на экран в тосте.
object Broacast {

    val errorUpdates = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    fun pushError(string: String) {
        errorUpdates.tryEmit(string)
    }
}
