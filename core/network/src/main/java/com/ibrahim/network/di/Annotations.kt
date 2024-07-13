package com.ibrahim.network.di

import javax.inject.Qualifier

object Annotations{

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class CoreNetwork
}
