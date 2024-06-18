package com.example.challengelocaweb.domain.useCases.emails

data class EmailUseCases(
    val getEmails: GetEmails,
    val getSendEmails: GetSendEmails,
    val getEmailsWithAttachments: GetEmailsWithAttachments,
    val sendEmail: SendEmail,
    val deleteEmail: DeleteEmail,
    val updateEmail: UpdateEmail,
    val draftEmails: GetDraftEmails,
    val spamEmails: GetSpamEmails,
    val favoritesEmails: GetFavoritesEmails,
    val markAsRead: MarkAsRead,
    val markAsUnread: MarkAsUnread,
    val markAsSpam: MarkAsSpam,
    val markAsNotSpam: MarkAsNotSpam,
    val markAsSecure: MarkAsSecure,
    val getUnreadEmailCount: GetUnreadEmailCount,
    val uploadFile: UploadFile


)
