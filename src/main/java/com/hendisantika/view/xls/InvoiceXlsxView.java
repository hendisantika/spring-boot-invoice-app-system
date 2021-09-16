package com.hendisantika.view.xls;

import com.hendisantika.entity.Invoice;
import com.hendisantika.entity.ItemInvoice;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 10.54
 */
@Component("invoice/view.xlsx")
public class InvoiceXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        MessageSourceAccessor message = getMessageSourceAccessor();

        response.setHeader("Content-Disposition", "attachment; filename=\"invoice_view.xlsx\"");
        Invoice invoice = (Invoice) model.get("invoice");
        Sheet sheet = workbook.createSheet("Factura Spring");

        sheet.createRow(0).createCell(0).setCellValue(message.getMessage("text.invoice.view.data.client"));
        sheet.createRow(1).createCell(0).setCellValue(invoice.getClient().getFirstName() + " " + invoice.getClient().getLastName());
        sheet.createRow(2).createCell(0).setCellValue(invoice.getClient().getEmail());

        sheet.createRow(4).createCell(0).setCellValue(message.getMessage("text.invoice.view.data.invoice"));
        sheet.createRow(5).createCell(0).setCellValue(message.getMessage("text.client.view.folio") + ": " + invoice.getId());
        sheet.createRow(6).createCell(0).setCellValue(message.getMessage("text.client.view.description") + ": " + invoice.getDescription());
        sheet.createRow(7).createCell(0).setCellValue(message.getMessage("text.client.view.date") + ": " + invoice.getCreateAt());

        CellStyle theaderStyle = workbook.createCellStyle();
        theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
        theaderStyle.setBorderTop(BorderStyle.MEDIUM);
        theaderStyle.setBorderRight(BorderStyle.MEDIUM);
        theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
        theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
        theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle tbodyStyle = workbook.createCellStyle();
        tbodyStyle.setBorderBottom(BorderStyle.THIN);
        tbodyStyle.setBorderTop(BorderStyle.THIN);
        tbodyStyle.setBorderRight(BorderStyle.THIN);
        tbodyStyle.setBorderLeft(BorderStyle.THIN);

        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue(message.getMessage("text.invoice.form.item.name"));
        header.createCell(1).setCellValue(message.getMessage("text.invoice.form.item.price"));
        header.createCell(2).setCellValue(message.getMessage("text.invoice.form.item.quantity"));
        header.createCell(3).setCellValue(message.getMessage("text.invoice.form.item.total"));

        header.getCell(0).setCellStyle(theaderStyle);
        header.getCell(1).setCellStyle(theaderStyle);
        header.getCell(2).setCellStyle(theaderStyle);
        header.getCell(3).setCellStyle(theaderStyle);

        int rownum = 10;

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);

        for (ItemInvoice item : invoice.getItems()) {
            row = sheet.createRow(rownum++);

            cell = row.createCell(0);
            cell.setCellValue(item.getProduct().getName());
            cell.setCellStyle(tbodyStyle);

            cell = row.createCell(1);
            cell.setCellValue(item.getProduct().getPrice());
            cell.setCellStyle(tbodyStyle);

            cell = row.createCell(2);
            cell.setCellValue(item.getAmount());
            cell.setCellStyle(tbodyStyle);

            cell = row.createCell(3);
            cell.setCellValue(item.calculateImport());
            cell.setCellStyle(tbodyStyle);
        }

        Row finaltotal = sheet.createRow(rownum);
        cell = finaltotal.createCell(2);
        cell.setCellValue(message.getMessage("text.invoice.form.total"));
        cell.setCellStyle(tbodyStyle);

        cell = finaltotal.createCell(3);
        cell.setCellValue(invoice.getTotal());
        cell.setCellStyle(tbodyStyle);

    }
}
