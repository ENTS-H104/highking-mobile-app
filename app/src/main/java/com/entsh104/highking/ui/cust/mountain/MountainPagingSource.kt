package com.entsh104.highking.ui.cust.mountain

import ApiService
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.entsh104.highking.data.model.MountainResponse
import kotlinx.coroutines.runBlocking

class MountainPagingSource(private val apiService: ApiService, //get token
) : PagingSource<Int, List<MountainResponse>>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
    override fun getRefreshKey(state: PagingState<Int, List<MountainResponse>>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, List<MountainResponse>> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMountains(position, params.loadSize)
            LoadResult.Page(
                data = listOf(responseData.body()!!.data),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.body()?.data?.isEmpty() == true) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}