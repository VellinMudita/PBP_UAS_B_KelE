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
public class ProfileTest {

    @Rule
    public ActivityTestRule<MenuActivity> mActivityTestRule = new ActivityTestRule<> ( MenuActivity.class );

    @Test
    public void splashScreenTest() {

        ViewInteraction cardView = onView (
                allOf ( withId ( R.id.cardViewProfile ),
                        childAtPosition (
                                allOf ( withId ( R.id.linear4 ),
                                        childAtPosition (
                                                withClassName ( is ( "android.widget.RelativeLayout" ) ),
                                                3 ) ),
                                1 ) ) );
        cardView.perform ( scrollTo (), click () );

        ViewInteraction textInputEditText3 = onView (
                allOf ( withId ( R.id.txtname ), withText ( "fortuna alya a" ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.name ),
                                        0 ),
                                1 ),
                        isDisplayed () ) );
        textInputEditText3.perform ( replaceText ( "fortuna alya " ) );

        ViewInteraction textInputEditText4 = onView (
                allOf ( withId ( R.id.txtname ), withText ( "fortuna alya " ),
                        childAtPosition (
                                childAtPosition (
                                        withId ( R.id.name ),
                                        1 ),
                                0 ),
                        isDisplayed () ) );
        textInputEditText4.perform ( closeSoftKeyboard () );

        ViewInteraction materialButton2 = onView (
                allOf ( withId ( R.id.save ), withText ( "Save" ),
                        childAtPosition (
                                childAtPosition (
                                        withClassName ( is ( "android.widget.LinearLayout" ) ),
                                        2 ),
                                1 ) ) );
        materialButton2.perform ( scrollTo (), click () );
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
