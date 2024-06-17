package com.example.challengelocaweb.domain.useCases.emails

data class EmailUseCases(
    val getEmails: GetEmails,
    val getEmailsWithAttachments: GetEmailsWithAttachments,
    val deleteEmail: DeleteEmail,
    val updateEmail: UpdateEmail,
    val spamEmails: GetSpamEmails,
    val favoritesEmails: GetFavoritesEmails,
    val markAsRead: MarkAsRead,
    val markAsUnread: MarkAsUnread,
    val markAsSpam: MarkAsSpam,
    val markAsNotSpam: MarkAsNotSpam,
    val markAsSecure: MarkAsSecure,
    val getUnreadEmailCount: GetUnreadEmailCount


)
