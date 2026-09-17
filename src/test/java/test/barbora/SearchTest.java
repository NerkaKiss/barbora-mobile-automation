package test.barbora;

import components.PromoOverlay;
import components.SearchBar;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.barbora.SearchScreen;
import test.TestBase;

public class SearchTest extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void prepareSearchState() {
        new PromoOverlay().dismissIfDisplayed();
    }

    @Test(groups = "smoke")
    public void searchFieldShouldAcceptProductQuery() {

        SearchBar searchBar = new SearchBar();
        searchBar.enterSearchQuery("pienas");

        Assert.assertEquals(searchBar.getSearchQuery(),
                "pienas", "Search field should contain entered query");
    }

    @Test(groups = "smoke")
    public void searchSuggestionsShouldBeDisplayed() {

        SearchBar searchBar = new SearchBar();
        searchBar.enterSearchQuery("pienas");

        Assert.assertTrue(searchBar.areSuggestionsDisplayed("pienas"),
                "Search suggestions should be displayed");
    }

    @Test(groups = "regression")
    public void searchResultsShouldBeDisplayedAfterSubmittingQuery() {
        SearchBar searchBar = new SearchBar();
        SearchScreen searchScreen = new SearchScreen();
        searchBar.enterSearchQuery("pienas");
        searchBar.submitSearch();
        Assert.assertTrue(searchScreen.areResultsDisplayed("pienas"), "Search results should be displayed");
    }
}
