package com.glsebastiany.bakingapp.view.main;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.core.repository.DecoratedRecipesRepository;
import com.glsebastiany.bakingapp.view.recipe.RecipeActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource idlingResource;

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Before
    public void decorateRepository() {
        Intents.init();
        DecoratedRecipesRepository decoratedRecipesRepository = (DecoratedRecipesRepository) mActivityTestRule.getActivity().recipesRepository;
        idlingResource = decoratedRecipesRepository.getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        Intents.release();
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

    @Test
    public void mainActivity_shouldShowRecipes() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_recipes),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction textView = getFirstRecipeTV();
        textView.check(matches(isDisplayed()));

    }

    @Test
    public void recipeClick_shouldGoToRecipes() {
        ViewInteraction textView = getFirstRecipeTV();

        textView.perform(click());


        intended(AllOf.allOf(
                hasComponent(RecipeActivity.class.getName()),
                hasExtraWithKey(RecipeActivity.EXTRA_RECIPE)));


    }

    private ViewInteraction getFirstRecipeTV() {
        return onView(
                    allOf(withId(R.id.tv_ingredients), withText("Nutella Pie"),
                            childAtPosition(
                                    childAtPosition(
                                            IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                            0),
                                    0),
                            isDisplayed()));
    }
}
