package com.example.challengelocaweb.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.model.Source

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

    private fun generateMockEmails(page: Int, pageSize: Int): List<Email> {
        return List(pageSize) { index ->
            Email(
                author = "Author ${(page - 1) * pageSize + index + 1}",
                content = "Content ${(page - 1) * pageSize + index + 1}",
                description = "Description ${(page - 1) * pageSize + index + 1}",
                publishedAt = "2024-06-13T00:00:00Z",
                source = Source(1.toString(), "Source Name"),
                title = "Title ${(page - 1) * pageSize + index + 1}",
                url = "https://example.com/email${(page - 1) * pageSize + index + 1}",
                urlToImage = "https://example.com/image${(page - 1) * pageSize + index + 1}.jpg"
            )
        }
    }
}
