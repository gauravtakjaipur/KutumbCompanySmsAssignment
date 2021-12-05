package com.kutumb.smsassignment.data.messageRepo

import androidx.paging.PagingSource
import com.kutumb.smsassignment.data.modelClasses.SmsMessageData

/**
 * PagingSource that loads data from a content provider.
 */
class SmsMessagePagingSource(val repo: SmsMessageRepository) : PagingSource<Int, SmsMessageData>() {

    // the initial load size for the first page may be different from the requested size
    var initialLoadSize: Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SmsMessageData> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1

            if (params.key == null)
            {
                initialLoadSize = params.loadSize
            }

            // work out the offset into the database to retrieve records from the page number,
            // allow for a different load size for the first page
            val offsetCalc = {
                if (nextPageNumber == 2)
                    initialLoadSize
                else
                    ((nextPageNumber - 1) * params.loadSize) + (initialLoadSize - params.loadSize)
            }
            val offset = offsetCalc.invoke()

            val messages = repo.getSmsMessages(params.loadSize, offset)
            val count = messages.size

            return LoadResult.Page(
                data = messages,
                prevKey = null, // Only paging forward.
                // assume that if a full page is not loaded, that means the end of the data
                nextKey = if (count < params.loadSize) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}