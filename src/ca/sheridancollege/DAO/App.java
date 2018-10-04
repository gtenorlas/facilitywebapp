package ca.sheridancollege.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

public class App {
	public static final String DEST = "/images/html_header_footer.pdf";
	public static final String REPORTFINAL = "/images/html_header_footer.pdf";
	public static final String HEADER = "<table width=\"100%\" border=\"0\"><tr><td><img src=\"/images/logomini.jpg\" width=25 height=25 alt=\"Book2Ball\" /></td><td align=\"right\">Hershey Centre</td></tr></table>";
	public static  String FOOTER = "<table width=\"100%\" border=\"0\"><tr><td>Printed on October 2, 2018 by Gene Tenorlas</td><td align=\"right\">Page 1 of 4</td></tr></table>";
	public static int totalPageNum=0;
	
	public class HeaderFooter extends PdfPageEventHelper {
		protected ElementList header;
		protected ElementList footer;
		private int currentPageNum;

		public HeaderFooter() throws IOException {
			header = XMLWorkerHelper.parseToElementList(HEADER, null);
			footer = XMLWorkerHelper.parseToElementList(FOOTER, null);
			this.currentPageNum=0;
		}


		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			try {
				ColumnText ct = new ColumnText(writer.getDirectContent());
				//ct.setSimpleColumn(new Rectangle(36, 832, 559, 810));
				//ct.setSimpleColumn(new Rectangle(36, 832, 559, 750)); //FOR A4 WITH IMAGE
				ct.setSimpleColumn(new Rectangle(36, 770, 559, 730));
				//Image image = Image.getInstance("results/events/logomini.png");
				//image.scaleToFit(25, 25);
		        //ct.addElement(image);
				for (Element e : header) {
					ct.addElement(e);

				}
				ct.go();
				ct.setSimpleColumn(new Rectangle(36, 10, 559, 32));
				//change the footer
				LocalDateTime currentDate = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMMM dd, yyyy 'at' hh:mm a");
				String formatCurrentDateTime = currentDate.format(formatter);
				String username="Gene Tenorlas";
				FOOTER = "<table width=\"100%\" border=\"0\"><tr><td>Printed on "+formatCurrentDateTime+ " by " + username +"</td>";
				
				//change the page number
				this.currentPageNum = writer.getPageNumber(); //get the current page number
				FOOTER+="<td align=\"right\">Page "+currentPageNum + " of " + totalPageNum+"</td></tr></table>";
				footer=XMLWorkerHelper.parseToElementList(FOOTER, null);
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
		//create a dummy pdf file to get the page number
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		totalPageNum=new App().createPdf(DEST);
		
		//create the final pdf file with the correct page number
		file = new File(REPORTFINAL);
		file.getParentFile().mkdirs();
		new App().createPdf(REPORTFINAL);
		System.out.println("Done create FILE: "+REPORTFINAL);
		
	}

	public int createPdf(String filename) throws IOException, DocumentException {
		System.out.println("Trying to create new pdf");
		Font ParagraphTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
		Font headerFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 9, BaseColor.BLACK);
		Font cellFont = FontFactory.getFont("Times Roman", 8, BaseColor.BLACK);
		  
		// step 1
		//margin left, right, top, bottom
		//Document document = new Document(PageSize.A4, 36, 36, 72, 72);
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
		
		//add paragraph title to page
		Paragraph reportTitle=new Paragraph("Booking Report", ParagraphTitleFont);
		reportTitle.setAlignment(1);
		reportTitle.setSpacingAfter(10);
		document.add(reportTitle);

		// add table
		PdfPTable table = new PdfPTable(6);
		table.setTotalWidth(45);;
		table.setWidths(new int[]{6, 6, 3, 8, 8, 4});

		PdfPCell c1 = new PdfPCell(new Phrase("Court",headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Customer",headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Status",headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Start Date/Time",headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("End Date/Time",headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Cost",headerFont));
		c1.setHorizontalAlignment(1);
		table.addCell(c1);

		table.setHeaderRows(1); // specify the number of rows relative to header


		// step 4
		for (int i = 0; i < 50; i++) {
			table.addCell(new Phrase("1 - Court " + i,cellFont));
			table.addCell(new Phrase("Customer Lastname " + i,cellFont));
			table.addCell(new Phrase("Active " + i,cellFont));
			table.addCell(new Phrase("January 1, 2018 11:05 AM",cellFont));
			table.addCell(new Phrase("January 1, 2018 11:05 AM",cellFont));
			Phrase cost=(new Phrase("$"+i+".00",cellFont));
			PdfPCell cell = new PdfPCell(cost);
			cell.setHorizontalAlignment(2);
			table.addCell(cell);
		}
		

	
	
		
		PdfPCell cell = new PdfPCell(new Phrase("Total:",headerFont));
        //cell.setPadding(5);
		cell.setColspan(5); //occupy 5 cells horizontally
		
		table.addCell(cell);;
		Phrase cost=(new Phrase("$9998.00",headerFont));
		PdfPCell cellTotalCost = new PdfPCell(cost);
		table.addCell(cellTotalCost);;
		
		document.add(table);
		
	     //add image
		//Image image = Image.getInstance("results/events/logomini.png");
		//image.scaleToFit(25, 25);
        //document.add(image);
	    
		//close the  document
		
		
		document.close();
		System.out.println("Done creating pdf");
		return writer.getPageNumber();
	
	}

}