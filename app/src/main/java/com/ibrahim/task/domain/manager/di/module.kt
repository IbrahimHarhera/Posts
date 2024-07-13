package com.ibrahim.task.domain.manager.di

import com.ibrahim.task.domain.manager.IWorkManager
import com.ibrahim.task.domain.manager.WorkMangerImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class module {
    @Binds
    abstract fun bindWorker(repository: WorkMangerImp): IWorkManager
}