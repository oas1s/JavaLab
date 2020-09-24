package ru.itis.documents.utills;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IPdfTextLocation;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.pdfcleanup.autosweep.CompositeCleanupStrategy;
import com.itextpdf.pdfcleanup.autosweep.PdfAutoSweep;
import com.itextpdf.pdfcleanup.autosweep.RegexBasedCleanupStrategy;

import java.io.File;

public class ReplaceStream2 {
    private static  String DEST = "results/chapter01/";
    public static  String SRC = "sources/";


    public static void manipulatePdf(String name, String source) throws Exception {
        SRC = SRC + source + ".pdf";
        DEST = DEST + name + " " + source + ".pdf";
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        CompositeCleanupStrategy strategy = new CompositeCleanupStrategy();
        strategy.add(new RegexBasedCleanupStrategy("Example").setRedactionColor(null));

        PdfWriter writer = new PdfWriter(DEST);
        writer.setCompressionLevel(0);
        PdfDocument pdf = new PdfDocument(new PdfReader(SRC), writer);

        // sweep
        PdfAutoSweep autoSweep = new PdfAutoSweep(strategy);
        autoSweep.cleanUp(pdf);

        for (IPdfTextLocation location : strategy.getResultantLocations()) {
            PdfPage page = pdf.getPage(location.getPageNumber() + 1);
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(), page.getDocument());
            Canvas canvas = new Canvas(pdfCanvas, pdf, location.getRectangle());
            canvas.add(new Paragraph(name).setFontSize(8));
        }
            pdf.close();
        }
}