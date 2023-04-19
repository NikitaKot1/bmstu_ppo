package bl.entities

import java.time.LocalDateTime

data class Comment(var id: ULong, var date: LocalDateTime, var text: String, var autor: Consumer)
