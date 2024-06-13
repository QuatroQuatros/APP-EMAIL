package com.example.challengelocaweb.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.challengelocaweb.domain.model.Article
import com.example.challengelocaweb.util.Constansts

class NewsPagingSource(

    private val newsAPI: NewsAPI,
    private val sources: String


): PagingSource<Int, Article>()
{
    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try{
            val response = newsAPI.getNews(
                page = page,
                sources = sources
            )
            totalNewsCount += response.articles.size
            val articles = response.articles.distinctBy { it.title }
            LoadResult.Page(
                data = articles,
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

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}