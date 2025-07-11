package fastcsv;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import de.siegmar.csvbenchmark.CsvConstants;
import de.siegmar.csvbenchmark.ICsvReader;
import de.siegmar.csvbenchmark.ICsvWriter;
import de.siegmar.csvbenchmark.util.InfiniteDataReader;
import de.siegmar.fastcsv.reader.CloseableIterator;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRecord;
import de.siegmar.fastcsv.writer.CsvWriter;
import de.siegmar.fastcsv.writer.LineDelimiter;

public final class Factory {

    private Factory() {
    }

    public static ICsvReader reader() {
        return new ICsvReader() {

            private final CloseableIterator<CsvRecord> iterator = CsvReader.builder()
                .skipEmptyLines(false)
                .allowMissingFields(true)
                .ofCsvRecord(new InfiniteDataReader(CsvConstants.DATA))
                .iterator();

            @Override
            public List<String> readRecord() {
                return iterator.next().getFields();
            }

            @Override
            public void close() throws IOException {
                iterator.close();
            }

        };
    }

    public static ICsvReader readerMulti() {
        return new ICsvReader() {

            private final CloseableIterator<CsvRecord> iterator = CsvReader.builder()
                .fieldSeparator(CsvConstants.MULTI_SEPARATOR)
                .skipEmptyLines(false)
                .allowMissingFields(true)
                .ofCsvRecord(new InfiniteDataReader(CsvConstants.MULTI_DATA))
                .iterator();

            @Override
            public List<String> readRecord() {
                return iterator.next().getFields();
            }

            @Override
            public void close() throws IOException {
                iterator.close();
            }

        };
    }

    public static ICsvWriter writer(final Writer writer) {
        return new ICsvWriter() {

            private final CsvWriter csvWriter = CsvWriter.builder()
                .lineDelimiter(LineDelimiter.LF)
                .build(writer);

            @Override
            public void writeRecord(final List<String> fields) {
                csvWriter.writeRecord(fields);
            }

            @Override
            public void close() throws IOException {
                csvWriter.close();
            }

        };
    }

}
