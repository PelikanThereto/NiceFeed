package com.joshuacerdenia.android.nicefeed.work

import android.content.Context
import androidx.work.*
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import java.util.concurrent.TimeUnit

class SweeperWorker(
    context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {

    private val repository = NiceFeedRepository.get()

    override fun doWork(): Result {
        repository.deleteLeftoverItems()
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "com.joshuacerdenia.android.nicefeed.work.SweeperWorker"

        fun setup(context: Context) {
            val request = PeriodicWorkRequest.Builder(
                SweeperWorker::class.java,
                3,
                TimeUnit.DAYS
            ).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}