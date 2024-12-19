package com.mustafatoktas.kirktakla.di

import android.content.Context
import com.mustafatoktas.usecase_app.AppUseCases
import com.mustafatoktas.usecase_app.GetOSName
import com.mustafatoktas.usecase_app.GetPhoneModel
import com.mustafatoktas.usecase_app.GetString
import com.mustafatoktas.usecase_app.GetVersionNumber
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppUseCaseModule {

    @Provides
    @Singleton
    fun provideAppUseCases(@ApplicationContext context: Context): AppUseCases =
        AppUseCases(
            getString = GetString(context),
            getVersionName = GetVersionNumber(context),
            getOSName = GetOSName(context),
            getPhoneModel = GetPhoneModel(),
        )
}
