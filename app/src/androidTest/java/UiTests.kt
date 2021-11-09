//import android.content.Context
//import android.util.Log
//import androidx.fragment.app.testing.launchFragmentInContainer
//import androidx.lifecycle.Lifecycle
//
///**
// *
// * UI Test
// *
// * Instrumented test, which will execute on an Android device.
// *
// * @author Kaan Osmanağaoğlu
// * @see [Testing documentation](http://d.android.com/tools/testing)
// */
//@RunWith(AndroidJUnit4::class)
//class UiTests : SpaceXFragment() {
//    @Test
//    fun launchFragment() {
//        val frag = launchFragmentInContainer<SpaceXFragment>()
//        frag.moveToState(Lifecycle.State.STARTED)
//
//        onView(withId(R.id.newsList))
//            .perform(ViewActions.click())
//            .perform(ViewActions.swipeLeft())
//            .perform(ViewActions.swipeDown())
//            .check { view, _ ->
//                assertEquals(view.id, R.id.newsList)
//                Log.d("XXX", "view.id= ${view.id}")
//                Log.d("XXX", "contentDescription: ${view.contentDescription}")
//            }
//    }
//
//    @Test
//    fun checkPortraitImages() {
//        val frag = launchFragmentInContainer<SpaceXFragment>()
//        frag.moveToState(Lifecycle.State.STARTED)
//
//        onView(withId(R.id.newsImagePortrait))
//            .perform(ViewActions.click())
//            .check(matches(isDisplayed()))
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun useAppContext() {
//        val appContext: Context = ApplicationProvider.getApplicationContext()
//        assertEquals("kaan.tabcorp.nine", appContext.packageName)
//    }
//
////    @Test
////    fun checkLandscapeImages() {
////        val frag = launchFragmentInContainer<RecipesFragment>()
////        frag.moveToState(Lifecycle.State.STARTED)
////
////        onView(allOf(withId(R.id.recipeImageLandscape), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
////            .perform(ViewActions.click())
////            .check(matches(not(isCompletelyDisplayed()))) // TODO: Bug, matches(isDisplayed) or anything similar for multiple images not working for comparison.
////    }
//}