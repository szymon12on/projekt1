package pl.samba.lms.raport.api;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.stereotype.Service;
import pl.samba.lms.raport.StudentRaportData;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaportGenerator {

    public ByteArrayOutputStream createPdf(String nazwaPrzedmiotu, String kodGrupy, List<StudentRaportData> studentDataList) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Przedmiot: " + nazwaPrzedmiotu));
            document.add(new Paragraph("Kod grupy: " + kodGrupy + "\n\n"));

            Table table = new Table(UnitValue.createPercentArray(new float[]{50, 30, 20})).useAllAvailableWidth();
            table.addCell(new Cell().add(new Paragraph("Imie i Nazwisko")).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("Oceny Czastkowe")).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("Ocena Koncowa")).setTextAlignment(TextAlignment.CENTER));

            for (StudentRaportData student : studentDataList) {
                String fullName = student.getImie() + " " + student.getNazwisko();
                String partialGrades = student.getOcenyCzastkowe().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
                String finalGrade = student.getOcenaKoncowa() != null ? student.getOcenaKoncowa().toString() : "";

                table.addCell(new Cell().add(new Paragraph(fullName)));
                table.addCell(new Cell().add(new Paragraph(partialGrades)));
                table.addCell(new Cell().add(new Paragraph(finalGrade)));
            }

            document.add(table);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date());
            Paragraph dateParagraph = new Paragraph("Raport wygenerowano: " + date)
                    .setFontSize(10)
                    .setItalic()
                    .setTextAlignment(TextAlignment.RIGHT);

            document.add(dateParagraph);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream;
    }
}