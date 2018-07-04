//by:Tiago

package org.jabref.logic.importer.fileformat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import org.jabref.logic.importer.ImportFormatPreferences;
import org.jabref.logic.importer.Importer;
import org.jabref.logic.importer.ParserResult;
import org.jabref.logic.util.FileType;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.FieldName;
import org.jabref.model.util.FileUpdateMonitor;

public class CSVImporter extends Importer {

    private final ImportFormatPreferences importFormatPreferences;
    private final FileUpdateMonitor fileMonitor;

    public CSVImporter(ImportFormatPreferences importFormatPreferences, FileUpdateMonitor fileMonitor) {
        this.importFormatPreferences = importFormatPreferences;
        this.fileMonitor = fileMonitor;
    }

    @Override
    public String getName() {
        return "CSV";
    }

    @Override
    public FileType getFileType() {
        return FileType.CSV;
    }

    @Override
    public String getDescription() {
        return "This importer exists to enable `--importToOpen someEntry.csv`\n";
    }

    @Override
    public String getId() {
        return "csv";
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader reader) {
        Objects.requireNonNull(reader);
        return true;
    }

    //Imports de entries of the CSV file to database
    @Override
    public ParserResult importDatabase(BufferedReader reader) throws IOException {
        Objects.requireNonNull(reader);

        StringTokenizer st = null;
        List<BibEntry> results = new LinkedList<>();

        String str;

        //loop while there is entries [lines] in the CSV file
        while ((str = reader.readLine()) != null) {

            //fixes that it's gonna be a book
            BibEntry b = new BibEntry("book");

            //split the str in substrings with the separator ','
            st = new StringTokenizer(str, ",");

            //get the first token of this line
            String entry = st.nextToken();
            //set it to the correspondent field
            b.setField(FieldName.TITLE, entry);

            //repeat...
            entry = st.nextToken();
            b.setField(FieldName.AUTHOR, entry);

            entry = st.nextToken();
            b.setField(FieldName.YEAR, entry);

            entry = st.nextToken();
            b.setField(FieldName.PUBLISHER, entry);

            entry = st.nextToken();
            b.setField(FieldName.SERIES, entry);

            entry = st.nextToken();
            b.setField(FieldName.ISBN, entry);

            entry = st.nextToken();
            b.setField(FieldName.KEYWORDS, entry);

            entry = st.nextToken();
            b.setField(FieldName.NOTE, entry);

            //add to the results
            results.add(b);
        }

        //parse to the database
        return new ParserResult(results);

    }


}
