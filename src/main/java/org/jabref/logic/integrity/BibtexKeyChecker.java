package org.jabref.logic.integrity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jabref.logic.integrity.IntegrityCheck.Checker;
import org.jabref.logic.l10n.Localization;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.FieldName;
import org.jabref.model.strings.StringUtil;

/**
 * Currently only checks the key if there is an author, year, and title present.
 */
public class BibtexKeyChecker implements Checker {

    @Override
    public List<IntegrityMessage> check(BibEntry entry) {
        Optional<String> author = entry.getField(FieldName.AUTHOR);
        Optional<String> title = entry.getField(FieldName.TITLE);
        Optional<String> year = entry.getField(FieldName.YEAR);
        if (!author.isPresent() || !title.isPresent() || !year.isPresent()) {
            return Collections.emptyList();
        }

        //Creates a template of how the bibtex key should like
        String bibkeypattern = entry.getField(FieldName.AUTHOR).toString() +
                entry.getField(FieldName.YEAR).toString() +
                entry.getField(FieldName.TITLE).toString().toUpperCase();

        //System.out.println(bibkeypattern);
        //if the generated key wasn't equal to the template, it's wrong
        if(!entry.toString().equals(bibkeypattern)) {
            return Collections.singletonList(new IntegrityMessage(
                    Localization.lang("wrong format: expected AutorYearTITLE"), entry, BibEntry.KEY_FIELD));
        }
        //

        if (StringUtil.isBlank(entry.getCiteKeyOptional())) {
            String authorTitleYear = entry.getAuthorTitleYear(100);
            return Collections.singletonList(new IntegrityMessage(
                    Localization.lang("empty BibTeX key") + ": " + authorTitleYear, entry, BibEntry.KEY_FIELD));
        }

        return Collections.emptyList();
    }
}
