package com.example.randomnumberapp.numbers.sl

import com.example.randomnumberapp.main.sl.Core
import com.example.randomnumberapp.main.sl.Module
import com.example.randomnumberapp.numbers.data.BaseNumbersRepository
import com.example.randomnumberapp.numbers.data.DataToDomainErrorHandler
import com.example.randomnumberapp.numbers.data.MapperNumberDataToDomain
import com.example.randomnumberapp.numbers.data.RequestDataHandler
import com.example.randomnumberapp.numbers.data.cache.MapperNumberDataToCache
import com.example.randomnumberapp.numbers.data.cache.NumbersCacheDataSource
import com.example.randomnumberapp.numbers.data.cloud.NumberService
import com.example.randomnumberapp.numbers.data.cloud.NumbersCloudDataSource
import com.example.randomnumberapp.numbers.domain.ErrorHandler
import com.example.randomnumberapp.numbers.domain.NumberFactToNumberUi
import com.example.randomnumberapp.numbers.domain.NumbersInteractor
import com.example.randomnumberapp.numbers.domain.RequestHandler
import com.example.randomnumberapp.numbers.presentation.CurrentStateCommunication
import com.example.randomnumberapp.numbers.presentation.HandleNumbersRequest
import com.example.randomnumberapp.numbers.presentation.HistoryListCommunication
import com.example.randomnumberapp.numbers.presentation.NumberResultMapper
import com.example.randomnumberapp.numbers.presentation.NumbersCommunications
import com.example.randomnumberapp.numbers.presentation.NumbersViewModel
import com.example.randomnumberapp.numbers.presentation.ProgressCommunication

class NumbersModule(private val core: Core) : Module<NumbersViewModel> {
    override fun viewModel(): NumbersViewModel {
        val communications = NumbersCommunications.Base(
            ProgressCommunication.Base(),
            CurrentStateCommunication.Base(),
            HistoryListCommunication.Base()
        )
        val cacheDataSource = NumbersCacheDataSource.Base(
            core.provideDataBase().getNumbersDao(),
            MapperNumberDataToCache()
        )
        val repository = BaseNumbersRepository(
            NumbersCloudDataSource.Base(
                core.service(NumberService::class.java)
            ),
            cacheDataSource,
            MapperNumberDataToDomain(),
            RequestDataHandler.Base(
                DataToDomainErrorHandler(),
                MapperNumberDataToDomain(),
                cacheDataSource,
            ),
        )
        return NumbersViewModel(
            HandleNumbersRequest.Base(
                core.provideDispatchers(),
                communications,
                NumberResultMapper(communications, NumberFactToNumberUi())
            ),
            communications,
            NumbersInteractor.Base(
                repository,
                RequestHandler.Base(
                    repository,
                    ErrorHandler.Base(core),
                )
            ),
            core
        )
    }
}