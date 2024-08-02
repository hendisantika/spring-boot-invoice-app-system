package com.hendisantika.view.pdf;

import com.hendisantika.entity.Invoice;
import com.hendisantika.entity.ItemInvoice;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 10.45
 */
@Component("invoice/view")
public class InvoicePdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        Invoice invoice = (Invoice) model.get("invoice");

        MessageSourceAccessor message = getMessageSourceAccessor();

        // Customer details
        PdfPTable table = new PdfPTable(1);
        table.setSpacingAfter(20);
        PdfPCell cell = null;
        cell = new PdfPCell(new Phrase(message.getMessage("text.invoice.view.data.client")));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);
        table.addCell(cell);
        table.addCell(invoice.getClient().getFirstName() + " " + invoice.getClient().getLastName());
        table.addCell(invoice.getClient().getEmail());

        // Invoice Details
        PdfPTable table2 = new PdfPTable(1);
        table2.setSpacingAfter(20);
        cell = new PdfPCell(new Phrase(message.getMessage("text.invoice.view.data.invoice")));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setPadding(8f);
        table2.addCell(cell);
        table2.addCell(message.getMessage("text.client.invoice.folio") + ": " + invoice.getId());
        table2.addCell(message.getMessage("text.client.invoice.description") + ": " + invoice.getDescription());
        table2.addCell(message.getMessage("text.client.invoice.date") + ": " + invoice.getCreateAt());

        // Products Table
        PdfPTable table3 = new PdfPTable(4);
        table3.setWidths(new float[]{3.5f, 1, 1, 1});
        table3.addCell(message.getMessage("text.invoice.form.item.name"));
        table3.addCell(message.getMessage("text.invoice.form.item.price"));
        table3.addCell(message.getMessage("text.invoice.form.item.quantity"));
        table3.addCell(message.getMessage("text.invoice.form.item.total"));

        for (ItemInvoice item : invoice.getItems()) {
            table3.addCell(item.getProduct().getName());
            table3.addCell(item.getProduct().getPrice().toString());
            cell = new PdfPCell(new Phrase(item.getAmount().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table3.addCell(cell);
            table3.addCell(item.calculateImport().toString());
        }

        cell = new PdfPCell(new Phrase(message.getMessage("text.invoice.form.total")));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        table3.addCell(cell);
        table3.addCell(invoice.getTotal().toString());

        document.add(table);
        document.add(table2);
        document.add(table3);
    }
}
