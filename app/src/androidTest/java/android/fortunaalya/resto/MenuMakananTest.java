package android.fortunaalya.resto;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MenuMakananTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<> ( MainActivity.class );

    @Test
    public void splashScreenTest3() {
        ViewInteraction textInputEditText = onView (
                allOf ( withId ( R.id.txtemail ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.emails ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText.perform ( replaceText ( "fortunaalya@gmail.com" ), closeSoftKeyboard () );

        ViewInteraction textInputEditText2 = onView (
                allOf ( withId ( R.id.txtpassword ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.pass ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText2.perform ( replaceText ( "pass" ), closeSoftKeyboard () );

        ViewInteraction materialButton = onView (
                allOf ( withId ( R.id.buttonLogin ), withText ( "Login" ),
                        childAtPosition (
                                childAtPosition (
                                        withClassName ( is ( "android.widget.ScrollView" ) ),
                                        0 ),
                                4 ) ) );
        materialButton.perform ( scrollTo (), click () );

        ViewInteraction cardView = onView (
                allOf ( withId ( R.id.cardViewMenuMakanan ),
                        childAtPosition (
                                allOf ( withId ( R.id.linear2 ),
                                        childAtPosition (
                                                withClassName ( is ( "android.widget.RelativeLayout" ) ),
                                                1 ) ),
                                0 ) ) );
        cardView.perform ( scrollTo (), click () );

        ViewInteraction appCompatButton = onView (
                allOf ( withId ( android.R.id.button1 ), withText ( "Yes" ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.buttonPanel ),
                                        0 ),
                                3 ) ) );
        appCompatButton.perform ( scrollTo (), click () );

        ViewInteraction materialButton2 = onView (
                allOf ( withId ( R.id.buttonBack ), withText ( "Back" ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( android.R.id.content ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        materialButton2.perform ( click () );
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View> () {
            @Override
            public void describeTo(Description description) {
                description.appendText ( "Child at position " + position + " in parent " );
                parentMatcher.describeTo ( description );
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent ();
                return parent instanceof ViewGroup && parentMatcher.matches ( parent )
                        && view.equals ( (( ViewGroup ) parent).getChildAt ( position ) );
            }
        };
    }
}
