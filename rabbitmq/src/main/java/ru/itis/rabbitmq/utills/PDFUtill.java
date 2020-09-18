package ru.itis.rabbitmq.utills;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.IOException;

public class PDFUtill {
    private static  String DEST = "results/chapter01/";


    public static void createPdf(String name) throws IOException {
        DEST = DEST + name + ".pdf";

        File file = new File(DEST);

        file.getParentFile().mkdirs();

        PdfWriter writer = new PdfWriter(DEST);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        document.add(new Paragraph(name));

        document.close();
    }
}
