package test.barbora;

import components.SearchBar;
import org.testng.Assert;
import org.testng.annotations.Test;
import screens.barbora.SearchScreen;
import test.TestBase;

public class SearchTest extends TestBase {
    @Test
    public void searchFieldShouldAcceptProductQuery() throws InterruptedException {

        SearchBar searchBar = new SearchBar();
        searchBar.enterSearchQuery("pienas");

        Assert.assertEquals(searchBar.getSearchQuery(),
                "pienas", "Search field should contain entered query");
    }

    @Test
    public void searchSuggestionsShouldBeDisplayed() {

        SearchBar searchBar = new SearchBar();
        searchBar.enterSearchQuery("pienas");

        Assert.assertTrue(searchBar.areSuggestionsDisplayed("pienas"),
                "Search suggestions should be displayed");
    }

    @Test
    public void searchResultsShouldBeDisplayedAfterSubmittingQuery() {
        SearchBar searchBar = new SearchBar();
        SearchScreen searchScreen = new SearchScreen();
        searchBar.enterSearchQuery("pienas");
        searchBar.submitSearch();
        Assert.assertTrue(searchScreen.areResultsDisplayed("pienas"), "Search results should be displayed");
    }
}
