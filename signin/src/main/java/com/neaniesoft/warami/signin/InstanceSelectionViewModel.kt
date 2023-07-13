package com.neaniesoft.warami.signin

import androidx.lifecycle.ViewModel
import com.neaniesoft.warami.domain.usecases.GetInstancesDownloadUrlUseCase
import me.tatarka.inject.annotations.Inject

@Inject
@SignInScope
class InstanceSelectionViewModel(private val getInstancesDownloadUrl: GetInstancesDownloadUrlUseCase) : ViewModel() {

}
