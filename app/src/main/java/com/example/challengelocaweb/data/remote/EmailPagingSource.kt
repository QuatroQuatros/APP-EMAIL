package com.example.challengelocaweb.data.remote

import MockEmailPagingSource
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.challengelocaweb.domain.dao.EmailDao
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.util.NetworkUtils
import javax.inject.Inject


class EmailPagingSource @Inject constructor(
    private val emailDao: EmailDao,
    private val networkUtils: NetworkUtils
) : PagingSource<Int, Email>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Email> {
        return try {
            val cachedEmails = emailDao.getEmails()

            if (networkUtils.isNetworkAvailable() && cachedEmails.isEmpty()) {
                val page = params.key ?: 1
                val pageSize = params.loadSize
                val emails = generateMockEmails(page, pageSize)

                emailDao.clearAll()
                emailDao.insertAll(emails)

                LoadResult.Page(
                    data = emails,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (emails.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Page(
                    data = cachedEmails,
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Email>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun generateMockEmails(page: Int, pageSize: Int): List<Email> {
         return MockEmailPagingSource().generateMockEmails(page, pageSize)
    }
}