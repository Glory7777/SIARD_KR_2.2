package ch.admin.bar.siard2.api.utli;

import java.util.regex.Pattern;

public class SearchUtil {

    private final String term;

    public SearchUtil(String term) {
        this.term = term;
    }

    public boolean matches(String value) {
        return term != null
                && value != null
                && !term.isEmpty()
                && !value.isEmpty()
                && Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(value).find();
    }
}
