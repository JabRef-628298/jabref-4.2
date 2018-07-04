package org.jabref.logic.integrity;

import java.util.Calendar;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.jabref.logic.l10n.Localization;
import org.jabref.model.strings.StringUtil;

public class YearChecker implements ValueChecker {

    private static final Predicate<String> CONTAINS_FOUR_DIGIT = Pattern.compile("([^0-9]|^)[0-9]{4}([^0-9]|$)")
            .asPredicate();
    private static final Predicate<String> ENDS_WITH_FOUR_DIGIT = Pattern.compile("[0-9]{4}$").asPredicate();
    private static final String PUNCTUATION_MARKS = "[(){},.;!?<>%&$]";


    /**
     * Checks, if the number String contains a four digit year and ends with it.
     * Official bibtex spec:
     * Generally it should consist of four numerals, such as 1984, although the standard styles
     * can handle any year whose last four nonpunctuation characters are numerals, such as ‘(about 1984)’.
     * Source: http://ftp.fernuni-hagen.de/ftp-dir/pub/mirrors/www.ctan.org/biblio/bibtex/base/btxdoc.pdf
     */
    @Override
    public Optional<String> checkValue(String value) {

        //Creates a java calendar
        Calendar cal = Calendar.getInstance();

        //setLenient must be false at first
        cal.setLenient(false);

        //Verifies if there is a year in value and if this year exists
        if ((value != null) && !value.equals("")) {
            cal.set(Calendar.YEAR, Integer.parseInt(value));
            try {
                cal.get(Calendar.YEAR);

            } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                //If dont exists, show de message
                return Optional.of(Localization.lang("invalid year"));
            }
        }

        if (StringUtil.isBlank(value)) {
            return Optional.empty();
        }
        if (!CONTAINS_FOUR_DIGIT.test(value.trim())) {
            return Optional.of(Localization.lang("should contain a four digit number"));
        }

        if (!ENDS_WITH_FOUR_DIGIT.test(value.replaceAll(PUNCTUATION_MARKS, ""))) {
            return Optional.of(Localization.lang("last four nonpunctuation characters should be numerals"));
        }

        return Optional.empty();
    }
}
