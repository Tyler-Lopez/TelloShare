package com.tlopez.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.tlopez.authPresentation.login.LoginScreen
import com.tlopez.authPresentation.login.LoginViewModel
import com.tlopez.authPresentation.register.RegisterScreen
import com.tlopez.authPresentation.register.RegisterViewModel
import com.tlopez.authPresentation.verifyEmail.VerifyEmailScreen
import com.tlopez.authPresentation.verifyEmail.VerifyEmailViewModel
import com.tlopez.controllerPresentation.ControllerScreen
import com.tlopez.controllerPresentation.ControllerViewModel
import com.tlopez.feedPresentation.feed.FeedViewDelegate
import com.tlopez.feedPresentation.feed.FeedViewModel
import com.tlopez.feedPresentation.flightDetails.FlightDetailsScreen
import com.tlopez.feedPresentation.flightDetails.FlightDetailsViewModel
import com.tlopez.navigation.Screen.*
import com.tlopez.navigation.router.AuthRouter
import com.tlopez.navigation.router.ControllerRouter
import com.tlopez.navigation.router.FeedRouter
import com.tlopez.navigation.router.SettingsRouter
import com.tlopez.navigation.util.selectiveSwipingInOutComposable
import com.tlopez.navigation.util.swipingInOutComposable
import com.tlopez.settingsPresentation.editProfilePicture.EditProfilePictureScreen
import com.tlopez.settingsPresentation.editProfilePicture.EditProfilePictureViewModel
import com.tlopez.settingsPresentation.settings.SettingsScreen
import com.tlopez.settingsPresentation.settings.SettingsViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TelloNavHost(navController: NavHostController) {

    val authRouter = AuthRouter(navController)
    val controllerRouter = ControllerRouter(navController)
    val feedRouter = FeedRouter(navController)
    val settingsRouter = SettingsRouter(navController)

    AnimatedNavHost(
        navController = navController,
        startDestination = Login.route
    ) {
        /** Authorization navigation destinations **/
        selectiveSwipingInOutComposable(route = Login.route) {
            LoginScreen(viewModel = hiltViewModel<LoginViewModel>().also {
                it.attachRouter(authRouter)
            })
        }
        swipingInOutComposable(route = Register.route) {
            RegisterScreen(viewModel = hiltViewModel<RegisterViewModel>().also {
                it.attachRouter(authRouter)
            })
        }
        swipingInOutComposable(
            route = VerifyEmail.route + "?username={username}&password={password}&email={email}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType },
                navArgument("email") {
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) {
            VerifyEmailScreen(viewModel = hiltViewModel<VerifyEmailViewModel>().apply {
                attachRouter(authRouter)
            })
        }

        /** Feed navigation destinations **/
        selectiveSwipingInOutComposable(route = Home.route) {
            FeedViewDelegate(viewModel = hiltViewModel<FeedViewModel>().apply {
                attachRouter(feedRouter)
            })
        }
        swipingInOutComposable(
            route = FlightDetails.route + "?flight_id={flight_id}",
            arguments = listOf(
                navArgument("flight_id") { type = NavType.StringType },
            )
        ) {
            FlightDetailsScreen(viewModel = hiltViewModel<FlightDetailsViewModel>().apply {
                attachRouter(feedRouter)
            })
        }

        /** Settings navigation destinations **/
        swipingInOutComposable(route = Settings.route) {
            SettingsScreen(viewModel = hiltViewModel<SettingsViewModel>().apply {
                attachRouter(settingsRouter)
            })
        }
        swipingInOutComposable(route = EditProfilePicture.route) {
            EditProfilePictureScreen(viewModel = hiltViewModel<EditProfilePictureViewModel>().apply {
                attachRouter(settingsRouter)
            })
        }

        /** Controller navigation destinations **/
        swipingInOutComposable(route = Controller.route) {
            ControllerScreen(viewModel = hiltViewModel<ControllerViewModel>().apply {
                attachRouter(controllerRouter)
            })
        }
    }
}