package com.example.fetchrewardsexercise.presentation

import androidx.lifecycle.ViewModel
import com.example.fetchrewardsexercise.domain.use_cases.GetItemListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class ListFragmentViewModel @Inject constructor(
    getItemListUseCase: GetItemListUseCase
): ViewModel() {

    //Flow that connects the viewModel and Use case
    val listFlow = getItemListUseCase()

}