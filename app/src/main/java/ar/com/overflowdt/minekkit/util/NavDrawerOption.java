package ar.com.overflowdt.minekkit.util;

/**
 * Created by Fede on 24/12/2014.
 */
public class NavDrawerOption {

    private String optionID;
    private String optionTitle;
    private Integer iconID;

    public NavDrawerOption(String optionID, String optionTitle, int iconID) {
        this.optionID = optionID;
        this.optionTitle = optionTitle;
        this.iconID = iconID;
    }

    public NavDrawerOption(String optionID, String optionTitle) {
        this.optionID = optionID;
        this.optionTitle = optionTitle;
        this.iconID = null;
    }

    public String getTitle() {
        return optionTitle;
    }

    public int getIconResource() {
        return iconID;
    }

    public String getOptionID() {
        return this.optionID;
    }
}
