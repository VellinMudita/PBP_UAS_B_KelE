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
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<> ( MainActivity.class );

    @Test
    public void mainActivityTest() {
        ViewInteraction materialButton = onView (
                allOf ( withId ( R.id.buttonLogin ), withText ( "Login" ),
                        childAtPosition (
                                childAtPosition (
                                        withClassName ( is ( "android.widget.ScrollView" ) ),
                                        0 ),
                                4 ) ) );
        materialButton.perform ( scrollTo (), click () );

        ViewInteraction textInputEditText = onView (
                allOf ( withId ( R.id.txtemail ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.emails ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText.perform ( replaceText ( "fortunaalya@gmail" ), closeSoftKeyboard () );

        ViewInteraction textInputEditText2 = onView (
                allOf ( withId ( R.id.txtpassword ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.pass ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText2.perform ( replaceText ( "pass" ), closeSoftKeyboard () );

        ViewInteraction materialButton2 = onView (
                allOf ( withId ( R.id.buttonLogin ), withText ( "Login" ),
                        childAtPosition (
                                childAtPosition (
                                        withClassName ( is ( "android.widget.ScrollView" ) ),
                                        0 ),
                                4 ) ) );
        materialButton2.perform ( scrollTo (), click () );

        ViewInteraction textInputEditText3 = onView (
                allOf ( withId ( R.id.txtemail ), withText ( "fortunaalya@gmail" ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.emails ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText3.perform ( replaceText ( "fortunaalya@gmail.com" ) );

        ViewInteraction textInputEditText4 = onView (
                allOf ( withId ( R.id.txtemail ), withText ( "fortunaalya@gmail.com" ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.emails ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText4.perform ( closeSoftKeyboard () );

        ViewInteraction textInputEditText5 = onView (
                allOf ( withId ( R.id.txtpassword ), withText ( "pass" ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.pass ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText5.perform ( replaceText ( "pas" ) );

        ViewInteraction textInputEditText6 = onView (
                allOf ( withId ( R.id.txtpassword ), withText ( "pas" ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.pass ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText6.perform ( closeSoftKeyboard () );

        ViewInteraction materialButton3 = onView (
                allOf ( withId ( R.id.buttonLogin ), withText ( "Login" ),
                        childAtPosition (
                                childAtPosition (
                                        withClassName ( is ( "android.widget.ScrollView" ) ),
                                        0 ),
                                4 ) ) );
        materialButton3.perform ( scrollTo (), click () );

        ViewInteraction textInputEditText7 = onView (
                allOf ( withId ( R.id.txtpassword ), withText ( "pas" ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.pass ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText7.perform ( click () );

        ViewInteraction textInputEditText8 = onView (
                allOf ( withId ( R.id.txtpassword ), withText ( "pas" ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.pass ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText8.perform ( replaceText ( "pass" ) );

        ViewInteraction textInputEditText9 = onView (
                allOf ( withId ( R.id.txtpassword ), withText ( "pass" ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.pass ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText9.perform ( closeSoftKeyboard () );

        ViewInteraction materialButton4 = onView (
                allOf ( withId ( R.id.buttonLogin ), withText ( "Login" ),
                        childAtPosition (
                                childAtPosition (
                                        withClassName ( is ( "android.widget.ScrollView" ) ),
                                        0 ),
                                4 ) ) );
        materialButton4.perform ( scrollTo (), click () );
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
