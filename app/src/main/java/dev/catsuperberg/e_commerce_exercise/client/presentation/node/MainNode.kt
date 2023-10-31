package dev.catsuperberg.e_commerce_exercise.client.presentation.node

import android.os.Parcelable
import androidx.compose.runtime.Composable
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
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.MainScreen
import dev.catsuperberg.e_commerce_exercise.client.presentation.ui.OrderFormScreen
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.main.IMainViewModel
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.order.form.IOrderFormViewModel
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class MainNode(
    buildContext: BuildContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        initialElement = NavTarget.MainScreen,
        savedStateMap = buildContext.savedStateMap,
    ),
) : ParentNode<MainNode.NavTarget>(
    navModel = backStack,
    buildContext = buildContext,
), KoinScopeComponent {
    override val scope: Scope by lazy { createScope(this) }

    sealed class NavTarget : Parcelable {
        @Parcelize
        object MainScreen : NavTarget()

        @Parcelize
        class OrderFormScreen(val item: Item) : NavTarget()

        @Parcelize
        object AuthScreen : NavTarget()

        @Parcelize
        object OrdersScreen : NavTarget()

        @Parcelize
        class ItemEditScreen(val itemId: String) : NavTarget()
    }

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node =
        when (navTarget) {
            is NavTarget.MainScreen -> screenNode(buildContext) {
                val callbacks = IMainViewModel.NavCallbacks(
                    onBuyItem = { item -> backStack.push(NavTarget.OrderFormScreen(item)) }
                )
                MainScreen(get {parametersOf(callbacks)})
            }
            is NavTarget.OrderFormScreen -> screenNode(buildContext) {
                val callbacks = IOrderFormViewModel.NavCallbacks(
                    onOrderOpened = { backStack.pop() }
                )
                OrderFormScreen(get { parametersOf(callbacks, navTarget.item) })
            }
            else -> screenNode(buildContext) { MainScreen(get()) }
        }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }

    override fun onChildFinished(child: Node) {
        backStack.singleTop(NavTarget.MainScreen)
    }
}
