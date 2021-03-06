package ba.klika.androidtemplate.ui.landing.login

import androidx.lifecycle.MutableLiveData
import ba.klika.androidtemplate.data.session.Credentials
import ba.klika.androidtemplate.data.session.SessionRepository
import ba.klika.androidtemplate.scheduling.SchedulingProvider
import ba.klika.androidtemplate.ui.base.SimpleNavigationAction
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.base.viewmodel.SingleLiveEvent
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class LoginViewModel
@Inject constructor(
    private val sessionRepository: SessionRepository,
    schedulingProvider: SchedulingProvider
) : BaseViewModel(schedulingProvider) {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val navigationTrigger = SingleLiveEvent<SimpleNavigationAction>()
    val toastMessage = MutableLiveData<String>()

    fun onLoginClick() {
        sessionRepository.logIn(
                Credentials(username.value!!, password.value!!)
        ).asIOCall().subscribe(
                { navigationTrigger.postValue(SimpleNavigationAction.NEXT) },
                { toastMessage.postValue("Failed") }
        ).disposeOnClear()
    }
}