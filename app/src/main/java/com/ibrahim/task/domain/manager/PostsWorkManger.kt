package com.ibrahim.task.domain.manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ibrahim.task.data.model.RequestPost
import com.ibrahim.task.domain.useCases.AddUseCase
import com.ibrahim.task.domain.useCases.DeleteUseCase
import com.ibrahim.task.domain.useCases.UpdateUseCase
import com.ibrahim.task.presentation.utils.ACTION
import com.ibrahim.task.presentation.utils.BODY
import com.ibrahim.task.presentation.utils.CREATE
import com.ibrahim.task.presentation.utils.DELETE
import com.ibrahim.task.presentation.utils.ID
import com.ibrahim.task.presentation.utils.TITLE
import com.ibrahim.task.presentation.utils.UPDATE
import com.ibrahim.task.presentation.utils.USER_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi

@HiltWorker
class PostsWorkManger @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val addUseCase: AddUseCase,
    @Assisted private val deleteUseCase: DeleteUseCase,
    @Assisted private val updateUseCase: UpdateUseCase
) : Worker(appContext, workerParams) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun doWork(): Result {
        val action = inputData.getString(ACTION)
        val id = inputData.getInt(ID, 0)
        val userID = inputData.getInt(USER_ID, 0)
        val title = inputData.getString(TITLE)
        val body = inputData.getString(BODY)

        when (action) {
            UPDATE -> {
                updateUseCase(
                    RequestPost(
                        id, title, body, userID
                    )
                )
            }
            DELETE -> {
                deleteUseCase(
                    id
                )
            }
            CREATE -> {
                addUseCase(
                    RequestPost(
                        id, title, body, userID
                    )
                )
            }
        }
        return Result.success()
    }
}