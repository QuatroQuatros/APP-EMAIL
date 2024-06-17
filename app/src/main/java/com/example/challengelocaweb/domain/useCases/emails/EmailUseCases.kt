package com.example.challengelocaweb.domain.useCases.emails

data class EmailUseCases(
    val getEmails: GetEmails,
    val deleteEmail: DeleteEmail,
    val updateEmail: UpdateEmail,
    val favoritesEmails: GetFavoritesEmails,
    val markAsRead: MarkAsRead,
    val markAsUnread: MarkAsUnread,
    val markAsSpam: MarkAsSpam,
    val markAsSecure: MarkAsSecure


)
