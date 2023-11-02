package dev.catsuperberg.e_commerce_exercise.client.presentation.node

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.operation.singleTop
import dev.catsuperberg.e_commerce_exercise.client.domain.model.Item
import dev.catsuperberg.e_commerce_exercise.client.domain.service.IAuthState
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.AuthScreen
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.ItemEditScreen
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.ManagerStoreFrontScreen
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.OrderFormScreen
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.OrdersScreen
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.StoreFrontScreen
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.auth.IAuthViewModel
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.item.edit.IItemEditViewModel
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.manager.store.front.IManagerStoreFrontViewModel
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.order.form.IOrderFormViewModel
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.orders.IOrdersViewModel
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.store.front.IStoreFrontViewModel
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class MainNode(
    buildContext: BuildContext,
    private val authState: IAuthState,
    private val backStack: BackStack<NavTarget> = BackStack(
        initialElement = NavTarget.StoreFrontScreen,
        savedStateMap = buildContext.savedStateMap,
    ),
) : ParentNode<MainNode.NavTarget>(
    navModel = backStack,
    buildContext = buildContext,
), KoinScopeComponent {
    override val scope: Scope by lazy { createScope(this) }

    sealed class NavTarget : Parcelable {
        @Parcelize
        object StoreFrontScreen : NavTarget()

        @Parcelize
        class OrderFormScreen(val item: Item) : NavTarget()

        @Parcelize
        object AuthScreen : NavTarget()

        @Parcelize
        class ItemEditScreen(val item: Item?) : NavTarget()

        @Parcelize
        object OrdersScreen : NavTarget()
    }

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node =
        when (navTarget) {
            is NavTarget.StoreFrontScreen -> screenNode(buildContext) {
                val signedIn = authState.signedIn.collectAsState()
                if(signedIn.value) {
                    val callbacks = IManagerStoreFrontViewModel.NavCallbacks(
                        onOrdersScreen = { backStack.push(NavTarget.OrdersScreen) },
                        onEditItem = { item -> backStack.push(NavTarget.ItemEditScreen(item)) },
                    )
                    ManagerStoreFrontScreen(get { parametersOf(callbacks) })
                }
                else {
                    val callbacks = IStoreFrontViewModel.NavCallbacks(
                        onBuyItem = { item -> backStack.push(NavTarget.OrderFormScreen(item)) },
                        onAuth = { backStack.push(NavTarget.AuthScreen) }
                    )
                    StoreFrontScreen(get { parametersOf(callbacks) })
                }
            }
            is NavTarget.OrderFormScreen -> screenNode(buildContext) {
                val callbacks = IOrderFormViewModel.NavCallbacks(
                    onOrderOpened = { backStack.pop() }
                )
                OrderFormScreen(get { parametersOf(callbacks, navTarget.item) })
            }
            is NavTarget.AuthScreen -> screenNode(buildContext) {
                val callbacks = IAuthViewModel.NavCallbacks( onSuccess = { backStack.pop() })
                AuthScreen(get { parametersOf(callbacks) })
            }
            is NavTarget.ItemEditScreen -> screenNode(buildContext) {
                val callbacks = IItemEditViewModel.NavCallbacks( onSuccess = { backStack.pop() })
                ItemEditScreen(get { parametersOf(callbacks, navTarget.item) })
            }
            is NavTarget.OrdersScreen -> screenNode(buildContext) {
                val callbacks = IOrdersViewModel.NavCallbacks( onBack = { backStack.pop() })
                OrdersScreen(get { parametersOf(callbacks) })
            }
        }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }

    override fun onChildFinished(child: Node) {
        backStack.singleTop(NavTarget.StoreFrontScreen)
    }
}
