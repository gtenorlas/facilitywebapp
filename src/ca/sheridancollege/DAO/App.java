package ca.sheridancollege.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import ca.sheridancollege.beans.Booking;
import ca.sheridancollege.beans.Court;
import ca.sheridancollege.beans.Facility;
/**
 * 
 * @author MAGS
 *
 */
public class App {
	public String DEST;
	public  String REPORTFINAL;
	public String HEADER;
	public String FOOTER;

	public static int totalPageNum = 0;
	private Facility facility;
	private ArrayList<Booking> bookings;
	private String username;
	
	
	public App(Facility facility, String username, ArrayList<Booking> bookings) {
		 this.facility=facility;
		 this.username=username.toUpperCase().trim();
		 this.bookings = bookings;
		 DEST = "/images/Report.pdf";
		 REPORTFINAL = "/images/Report.pdf";
		 String image ="<c:url value=\"/images/logomini.jpg\" />";
		 HEADER = "<table width=\"100%\" border=\"0\"><tr><td style=\"color:#008ecc;\"><img src=\"http://mags.website/images/logomini.jpg\" alt='Book2Ball' width=\"25\" height=\"25\"/>Book2Ball</td><td align=\"right\" style=\"padding-right:-20;\">"+facility.getFacilityName().trim()+"</td></tr></table>";
		 FOOTER = "<table width=\"100%\" border=\"0\"><tr><td>Printed on October 2, 2018 by "+ username.toUpperCase() +"</td><td align=\"right\" style=\"padding-right:-20;\">Page 1 of 4</td></tr></table>";


	}
	
	public class HeaderFooter extends PdfPageEventHelper {
		protected ElementList header;
		protected ElementList footer;
		private int currentPageNum;

		public HeaderFooter() throws IOException {
			header = XMLWorkerHelper.parseToElementList(HEADER, null);
			footer = XMLWorkerHelper.parseToElementList(FOOTER, null);
			this.currentPageNum = 0;
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			try {
				ColumnText ct = new ColumnText(writer.getDirectContent());
				ct.setSimpleColumn(new Rectangle(36, 770, 559, 720));
				
				for (Element e : header) {
					ct.addElement(e);

				}
				ct.go();
				ct.setSimpleColumn(new Rectangle(36, 10, 559, 32));
				// change the footer
				LocalDateTime currentDate = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMMM dd, yyyy 'at' h:mm a");
				String formatCurrentDateTime = currentDate.format(formatter);
				FOOTER = "<table width=\"100%\" border=\"0\"><tr><td>Printed on " + formatCurrentDateTime + " by "
						+ username + "</td>";

				// change the page number
				this.currentPageNum = writer.getPageNumber(); // get the current page number
				FOOTER += "<td align=\"right\">Page " + currentPageNum + " of " + totalPageNum + "</td></tr></table>";
				footer = XMLWorkerHelper.parseToElementList(FOOTER, null);
				for (Element e : footer) {
					ct.addElement(e);
				}
				ct.go();
			} catch (DocumentException de) {
				throw new ExceptionConverter(de);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		/*
		 * return the total number of pages
		 */
		public int getTotalPage() {
			return this.currentPageNum;
		}

	}

	public void main() throws IOException, DocumentException {

		// create a dummy pdf file to get the page number
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		totalPageNum = this.createPdf(DEST);

		// create the final pdf file with the correct page number
		file = new File(REPORTFINAL);
		file.getParentFile().mkdirs();
		this.createPdf(REPORTFINAL);
		System.out.println("Done create FILE: " + REPORTFINAL);

	}

	public int createPdf(String filename) throws IOException, DocumentException {
		System.out.println("Trying to create new pdf");
		Font ParagraphTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
		Font headerFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 9, BaseColor.BLACK);
		Font cellFont = FontFactory.getFont("Times Roman", 8, BaseColor.BLACK);

		// step 1
		// margin left, right, top, bottom
		Document document = new Document(PageSize.LETTER, 36, 36, 72, 72);
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
		System.out.println("pdfwriter stantiated");
		writer.setPageEvent(new HeaderFooter());
		// step 3
		document.open();
		System.out.println("document open");
		document.newPage();

		System.out.println("new page");

		// add paragraph title to page
		Paragraph reportTitle = new Paragraph("Booking Report", ParagraphTitleFont);
		reportTitle.setAlignment(1);
		reportTitle.setSpacingAfter(10);
		document.add(reportTitle);

		// add table
		PdfPTable table = new PdfPTable(6);
	    table.setTotalWidth(PageSize.LETTER.getWidth()-80);
	    table.setLockedWidth(true);
		table.setWidths(new int[] { 8, 8, 4, 8, 8,  6});

		PdfPCell c1 = new PdfPCell(new Phrase("Court", headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Customer", headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Status", headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Start", headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("End", headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Cost", headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		table.setHeaderRows(1); // specify the number of rows relative to header

		System.out.println("before datetimeformatter");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E yyyy-MM-dd h:mm a");
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		double totalCost=0;
		System.out.println("before the court loop");
		

			System.out.println("in the court loop");
			for (Booking eachBooking: bookings) {
				System.out.println("in the booking loop");
				table.addCell(new Phrase(eachBooking.getCourt().getCourtNumber() + " - "+ eachBooking.getCourt().getCourtName(), cellFont));
				table.addCell(new Phrase(eachBooking.getCustomerName(), cellFont));
				table.addCell(new Phrase(eachBooking.getStatus(), cellFont));
				table.addCell(new Phrase(eachBooking.getStartDateTime().format(formatter), cellFont));
				table.addCell(new Phrase(eachBooking.getEndDateTime().format(formatter), cellFont));
				Phrase cost = (new Phrase(currencyFormat.format(eachBooking.getPayment().getSubTotal()), cellFont));
				totalCost+=eachBooking.getPayment().getSubTotal();
				PdfPCell cell = new PdfPCell(cost);
				cell.setHorizontalAlignment(2);
				table.addCell(cell);
				
			}

		PdfPCell cell = new PdfPCell(new Phrase("Total:", headerFont));
		// cell.setPadding(5);
		cell.setColspan(5); // occupy 5 cells horizontally

		table.addCell(cell);
		
		Phrase cost = (new Phrase(currencyFormat.format(totalCost), headerFont));
		PdfPCell cellTotalCost = new PdfPCell(cost);
		cellTotalCost.setHorizontalAlignment(2);
		table.addCell(cellTotalCost);

		document.add(table);

		// close the document

		document.close();
		System.out.println("Done creating pdf");
		return writer.getPageNumber();

	}

}