import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.model.EmailWithAttachments

class MockEmailPagingSource : PagingSource<Int, Email>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Email> {
        val page = params.key ?: 1
        val pageSize = params.loadSize
        val emails = generateMockEmails(page, pageSize)
        return LoadResult.Page(
            data = emails,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (emails.isEmpty()) null else page + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Email>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    fun generateMockEmails(page: Int, pageSize: Int): List<Email> {
        return List(pageSize) { index ->
            val id = (page - 1) * pageSize + index + 1
            val name = generateRandomName()
            Email(
                id = id,
                author = name,
                sender = generateRandomEmailAddress(name),
                recipient = generateRandomEmailAddress(generateRandomName()),
                cc = generateRandomEmailAddress(generateRandomName()),
                bcc = generateRandomEmailAddress(generateRandomName()),
                content = generateRandomContent(),
                description = generateRandomDescription(),
                publishedAt = "2024-06-13T00:00:00Z",
                title = generateRandomTitle(),
                url = "https://example.com/email$id",
                urlToImage = generateRandomImageUrl(id, name)
                //attachments = generateRandomAttachments()
            )
        }
    }

    private fun generateRandomName(): String {
        val firstNames = listOf("John", "Jane", "Alex", "Emily", "Chris", "Katie")
        val lastNames = listOf("Doe", "Smith", "Johnson", "Williams", "Brown", "Jones")
        return "${firstNames.random()} ${lastNames.random()}"
    }

    private fun generateRandomEmailAddress(name: String): String {
        val domains = listOf("example.com", "test.com", "mock.com")
        return "${name.replace(" ", ".").toLowerCase()}@${domains.random()}"
    }

    private fun generateRandomContent(): String {
        val contents = listOf(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Vestibulum consectetur felis non odio varius, ut tempor ligula posuere.",
            "Pellentesque habitant morbi tristique senectus et netus et malesuada fames.",
            "Curabitur aliquet quam id dui posuere blandit.",
            "Donec sollicitudin molestie malesuada."
        )
        return contents.random()
    }

    private fun generateRandomDescription(): String {
        val descriptions = listOf(
            "Important information regarding your account.",
            "Upcoming event details you should know.",
            "Weekly newsletter and updates.",
            "Exclusive offers just for you.",
            "Reminder: Your subscription is ending soon."
        )
        return descriptions.random()
    }

    private fun generateRandomTitle(): String {
        val titles = listOf(
            "Your Account Update",
            "Event Invitation",
            "Weekly Newsletter",
            "Special Offers Inside",
            "Subscription Reminder"
        )
        return titles.random()
    }

    private fun generateRandomImageUrl(id: Int, name: String): String {
        return "https://ui-avatars.com/api/?background=random&name=$name&id=$id"
    }

    private fun generateRandomAttachments(): List<String> {
        val attachments = listOf(
            "attachment1.pdf",
            "attachment2.docx",
            "attachment3.jpg",
            "attachment4.png"
        )
        return attachments.shuffled().take((1..4).random())
    }
}
