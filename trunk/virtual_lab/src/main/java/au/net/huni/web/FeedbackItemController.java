package au.net.huni.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import au.com.bytecode.opencsv.CSVWriter;
import au.net.huni.io.CountingOutputStream;
import au.net.huni.model.FeedbackItem;
import au.net.huni.model.Rating;

// Access by default is restricted to the ADMIN role within the console webapp.
// This is over-ridden by annotations in this file.
// See webmvc-config.xml
@Controller
@RooWebScaffold(path = "feedbackitems", formBackingObject = FeedbackItem.class)
@RooWebJson(jsonObject = FeedbackItem.class)
public class FeedbackItemController {

    private static final String SHORT_DATE_FORMAT_STYLE = DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale());
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(SHORT_DATE_FORMAT_STYLE);

	@RequestMapping(value = "/console/feedbackitems", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid FeedbackItem feedbackItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, feedbackItem);
            return "feedbackitems/create";
        }
        uiModel.asMap().clear();
//        Calendar currentDate = Calendar.getInstance();
//        feedbackItem.setFeedbackDate(currentDate);
//        String ipAddress = locateVisitor(httpServletRequest);
//        feedbackItem.setVisitorIpAddress(ipAddress);
        feedbackItem.persist();
        return "redirect:/console/feedbackitems/" + encodeUrlPathSegment(feedbackItem.getId().toString(), httpServletRequest);
    }

	protected String locateVisitor(HttpServletRequest httpServletRequest) {
		String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
		return ipAddress;
	}

    @RequestMapping(value = "/console/feedbackitems/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        FeedbackItem feedbackItem = FeedbackItem.findFeedbackItem(id);
        feedbackItem.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/console/feedbackitems";
    }

    @RequestMapping(value = "/console/feedbackitems", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid FeedbackItem feedbackItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, feedbackItem);
            return "feedbackitems/update";
        }
        uiModel.asMap().clear();
        feedbackItem.merge();
        return "redirect:/console/feedbackitems/" + encodeUrlPathSegment(feedbackItem.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/console/feedbackitems", params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new FeedbackItem());
        return "feedbackitems/create";
    }

    @RequestMapping(value = "/console/feedbackitems/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("feedbackitem", FeedbackItem.findFeedbackItem(id));
        uiModel.addAttribute("itemId", id);
        return "feedbackitems/show";
    }

    @RequestMapping(value = "/console/feedbackitems", produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("feedbackitems", FeedbackItem.findFeedbackItemEntries(firstResult, sizeNo));
            float nrOfPages = (float) FeedbackItem.countFeedbackItems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("feedbackitems", findAllFeedbackItems());
        }
        addDateTimeFormatPatterns(uiModel);
        return "feedbackitems/list";
    }

    @RequestMapping(value = "/console/feedbackitems/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, FeedbackItem.findFeedbackItem(id));
        return "feedbackitems/update";
    }

    void populateEditForm(Model uiModel, FeedbackItem feedbackItem) {
        uiModel.addAttribute("feedbackItem", feedbackItem);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("ratings", Arrays.asList(Rating.values()));
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("feedbackItem_feedbackdate_date_format", SHORT_DATE_FORMAT_STYLE);
    }

	// Allow access for VL web app.
	@PreAuthorize("isAnonymous()")
	@RequestMapping(value = "/rest/feedbackitems", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, HttpServletRequest httpServletRequest) {
        FeedbackItem feedbackItem = FeedbackItem.fromJsonToFeedbackItem(json);
        Calendar currentDate = Calendar.getInstance();
        feedbackItem.setFeedbackDate(currentDate);
        String ipAddress = locateVisitor(httpServletRequest);
        feedbackItem.setVisitorIpAddress(ipAddress);
        feedbackItem.persist();
        HttpHeaders headers = createJsonHeader();
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/rest/feedbackitems/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json, HttpServletRequest httpServletRequest) {
	       Calendar currentDate = Calendar.getInstance();
	        String ipAddress = locateVisitor(httpServletRequest);
	    for (FeedbackItem feedbackItem: FeedbackItem.fromJsonArrayToFeedbackItems(json)) {
	 	        feedbackItem.setFeedbackDate(currentDate);
	 	        feedbackItem.setVisitorIpAddress(ipAddress);
	 	        feedbackItem.persist();
        }
        HttpHeaders headers = createJsonHeader();
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/rest/feedbackitems/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        FeedbackItem feedbackItem = FeedbackItem.findFeedbackItem(id);
        HttpHeaders headers = createJsonHeader();
        if (feedbackItem == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        feedbackItem.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/feedbackitems", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = createJsonHeader();
        List<FeedbackItem> result = findAllFeedbackItems();
        return new ResponseEntity<String>(FeedbackItem.toJsonArray(result), headers, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/rest/feedbackitems/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        FeedbackItem feedbackItem = FeedbackItem.findFeedbackItem(id);
        HttpHeaders headers = createJsonHeader();
        if (feedbackItem == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(feedbackItem.toJson(), headers, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/rest/feedbackitems", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = createJsonHeader();
        FeedbackItem feedbackItem = FeedbackItem.fromJsonToFeedbackItem(json);
        if (feedbackItem.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/feedbackitems/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = createJsonHeader();
        for (FeedbackItem feedbackItem: FeedbackItem.fromJsonArrayToFeedbackItems(json)) {
            if (feedbackItem.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/console/feedbackitems/export", method = RequestMethod.GET,  produces = "text/csv")
    @ResponseBody
    public void exportCsv(HttpServletResponse httpServletResponse) {
		PrintWriter responseWriter = null;
		CSVWriter countingCsvWriter = null;
		CSVWriter csvWriter = null;
		try {
	        List<FeedbackItem> feedbackItems = findAllFeedbackItems();

	        CountingOutputStream countingOutputStream = new CountingOutputStream();
	        Writer countingWriter = new BufferedWriter(new OutputStreamWriter(countingOutputStream, "UTF-8"));			
	        countingCsvWriter = new CSVWriter(countingWriter, ',');
			for (FeedbackItem feedbackItem : feedbackItems) {
			     writeRow(countingCsvWriter, feedbackItem);
	        }
			countingCsvWriter.flush();
	        int contentLength = countingOutputStream.getCount();
	        		
	        applyHeaders(httpServletResponse, contentLength);
	        
			responseWriter = httpServletResponse.getWriter();
			csvWriter = new CSVWriter(responseWriter, ',');
	        for (FeedbackItem feedbackItem : feedbackItems) {
			     writeRow(csvWriter, feedbackItem);
	        }
			csvWriter.flush();
			
		} catch (IOException ioException) {
	        throw new RuntimeException("Export CSV failed due to IO error. ", ioException);
		} finally {
			if (responseWriter != null) {
		        responseWriter.flush(); 
		        responseWriter.close();
			}
			if (countingCsvWriter != null) {
			     try {
			    	 countingCsvWriter.close();
				} catch (IOException ioException) {
					throw new RuntimeException ("failed to close csv counting writer", ioException);
				}
			}
			if (csvWriter != null) {
			     try {
					csvWriter.close();
				} catch (IOException ioException) {
					throw new RuntimeException ("failed to close csv writer", ioException);
				}
			}
		}
    }

	protected void applyHeaders(HttpServletResponse httpServletResponse,
			int contentLength) {
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentLength(contentLength);
		httpServletResponse.setContentType("text/csv; charset=utf-8");
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		httpServletResponse.addHeader("Content-Disposition", "attachment; filename=\"HuNI_feedback.csv\"");
	}

	protected void writeRow(CSVWriter countingCsvWriter, FeedbackItem feedbackItem) {
		String[] entries = new String[] {
				feedbackItem.getContext() == null ? "null" : feedbackItem.getContext(),
				feedbackItem.getRating() == null ? "null" : feedbackItem.getRating().toString(),
				feedbackItem.getComment() == null ? "null" : feedbackItem.getComment(),
				feedbackItem.getFeedbackDate() == null ? "null" : DATE_FORMATTER.format(feedbackItem.getFeedbackDate().getTime()),
				feedbackItem.getVisitorIpAddress() == null ? "null" : feedbackItem.getVisitorIpAddress() };
		countingCsvWriter.writeNext(entries);
	}

	// Wrap in a protected method so we can stub it out in tests.
	protected List<FeedbackItem> findAllFeedbackItems() {
		return FeedbackItem.findAllFeedbackItems();
	}

	protected HttpHeaders createJsonHeader() {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
		return headers;
	}

}
