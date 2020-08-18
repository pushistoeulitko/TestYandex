package pagemodels;

public enum Language {
    RUSSIAN("Русский", "ru"),
    ENGLISH("English", "en");

    private final String searchLanguage;
    private final String address;

    Language(String searchLanguage, String address) {
        this.searchLanguage = searchLanguage;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getSearchLanguage() {
        return searchLanguage;
    }
}
