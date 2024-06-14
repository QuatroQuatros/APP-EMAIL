package com.example.challengelocaweb.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.challengelocaweb.domain.model.Email


class EmailPagingSource(

    private val emailAPI: EmailAPI,
    private val sources: String,
    private val searchTerm: String


): PagingSource<Int, Email>()
{


    override fun getRefreshKey(state: PagingState<Int, Email>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Email> {
        val page = params.key ?: 1
        return try{
            val response = emailAPI.getEmails(
                page = page,
                sources = sources
            )
            totalNewsCount += response.emails.size
            //val emails = response.emails.distinctBy { it.title }
            val emails = response.emails.filter { email ->
                email.title.contains(searchTerm, ignoreCase = true)
            }
            LoadResult.Page(
                data = emails,
                prevKey = null,
                nextKey = if (totalNewsCount == response.totalResults) null else page + 1
            )




        }catch (e: Exception){
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }


}