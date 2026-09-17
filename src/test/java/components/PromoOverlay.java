package components;

import screens.Common;
import utils.PromoOverlayHandler;

public class PromoOverlay extends Common {

    public boolean dismissIfDisplayed() {
        return PromoOverlayHandler.dismissIfPresent(driver);
    }
}