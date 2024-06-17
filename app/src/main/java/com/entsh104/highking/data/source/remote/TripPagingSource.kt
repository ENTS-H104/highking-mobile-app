package com.entsh104.highking.data.source.remote

import ApiService
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.entsh104.highking.data.model.TripFilter
import retrofit2.HttpException
import java.io.IOException

class TripPagingSource(private val apiService: ApiService) : PagingSource<Int, TripFilter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TripFilter> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getOpenTripsPaging(page, 8)
            val trips = response.body()?.data ?: emptyList()


            LoadResult.Page(
                data = trips,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (trips.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            Log.e("PagingSource", "IOException: ${exception.message}")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("PagingSource", "HttpException: ${exception.message}")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TripFilter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
