package com.kobylynskyiv.teammeet.data.db

import org.koin.dsl.module

val databaseModule = module {
    single { Database() }
}