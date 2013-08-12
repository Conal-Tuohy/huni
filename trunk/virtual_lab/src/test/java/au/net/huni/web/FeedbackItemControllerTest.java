package au.net.huni.web;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import au.net.huni.model.FeedbackItem;

public class FeedbackItemControllerTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();

	
	@Test
	public void testExportCsvForNoOutput() throws IOException {
		
		final OutputStream outputStream = new ByteArrayOutputStream();
		final List<FeedbackItem> feedbackItems = new ArrayList<FeedbackItem>();

		final HttpServletResponse httpServletResponse = context.mock(HttpServletResponse.class);
		context.checking(new Expectations() {{
		    oneOf (httpServletResponse).setCharacterEncoding("UTF-8"); 
		    oneOf (httpServletResponse).setContentLength(0); 
		    oneOf (httpServletResponse).setContentType("text/csv; charset=utf-8"); 
		    oneOf (httpServletResponse).setStatus(200); 
		    oneOf (httpServletResponse).addHeader("Content-Disposition", "attachment; filename=\"HuNI_feedback.csv\"");
		    oneOf (httpServletResponse).getWriter(); will(returnValue(new PrintWriter(outputStream)));
		}});
		
		FeedbackItemController controller = new FeedbackItemController() {
			protected List<FeedbackItem> findAllFeedbackItems() {
				return feedbackItems;
			}
		};
		
		controller.exportCsv(httpServletResponse);
	}
	
	@Test
	public void testExportCsvForSomeOutputHasContentLength() throws IOException {
		
		final OutputStream outputStream = new ByteArrayOutputStream();
		final List<FeedbackItem> feedbackItems = new ArrayList<FeedbackItem>();
		feedbackItems.add(new FeedbackItem()); // CSV = "null","null","null","null","null"\n

		final HttpServletResponse httpServletResponse = context.mock(HttpServletResponse.class);
		context.checking(new Expectations() {{
		    oneOf (httpServletResponse).setCharacterEncoding("UTF-8"); 
		    oneOf (httpServletResponse).setContentLength(35); 
		    oneOf (httpServletResponse).setContentType("text/csv; charset=utf-8"); 
		    oneOf (httpServletResponse).setStatus(200); 
		    oneOf (httpServletResponse).addHeader("Content-Disposition", "attachment; filename=\"HuNI_feedback.csv\"");
		    oneOf (httpServletResponse).getWriter(); will(returnValue(new PrintWriter(outputStream)));
		}});
		
		FeedbackItemController controller = new FeedbackItemController() {
			protected List<FeedbackItem> findAllFeedbackItems() {
				return feedbackItems;
			}
		};
		
		controller.exportCsv(httpServletResponse);
	}
	
	@Test
	public void testExportCsvForSomeOutputHasExpectedContent() throws IOException {
		
		final OutputStream outputStream = new ByteArrayOutputStream();
		final List<FeedbackItem> feedbackItems = new ArrayList<FeedbackItem>();
		feedbackItems.add(new FeedbackItem()); // CSV = "null","null","null","null","null"\n

		final HttpServletResponse httpServletResponse = context.mock(HttpServletResponse.class);
		context.checking(new Expectations() {{
		    oneOf (httpServletResponse).setCharacterEncoding("UTF-8"); 
		    oneOf (httpServletResponse).setContentLength(35); 
		    oneOf (httpServletResponse).setContentType("text/csv; charset=utf-8"); 
		    oneOf (httpServletResponse).setStatus(200); 
		    oneOf (httpServletResponse).addHeader("Content-Disposition", "attachment; filename=\"HuNI_feedback.csv\"");
		    oneOf (httpServletResponse).getWriter(); will(returnValue(new PrintWriter(outputStream)));
		}});
		
		FeedbackItemController controller = new FeedbackItemController() {
			protected List<FeedbackItem> findAllFeedbackItems() {
				return feedbackItems;
			}
		};
		
		controller.exportCsv(httpServletResponse);
		assertEquals("Has content of value", "\"null\",\"null\",\"null\",\"null\",\"null\"\n", outputStream.toString());
	}

}
