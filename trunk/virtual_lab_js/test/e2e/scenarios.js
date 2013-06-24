'use strict';

/*
 * https://docs.google.com/document/d/1HYnKnLxhxUYygPqIFiyibI3PsgicGENeHINCN-B_rdw/edit
 */
	
/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

//describe('my app', function() {
//
//  beforeEach(function() {
//    browser().navigateTo('../../app/index.html');
//  });
//
//
//  it('should automatically redirect to /view1 when location hash/fragment is empty', function() {
//    expect(browser().location().url()).toBe("/view1");
//  });
//
//
//  describe('view1', function() {
//
//    beforeEach(function() {
//      browser().navigateTo('#/view1');
//    });
//
//
//    it('should render view1 when user navigates to /view1', function() {
//      expect(element('[ng-view] p:first').text()).
//        toMatch(/partial for view 1/);
//    });
//
//  });
//
//
//  describe('view2', function() {
//
//    beforeEach(function() {
//      browser().navigateTo('#/view2');
//    });
//
//
//    it('should render view2 when user navigates to /view2', function() {
//      expect(element('[ng-view] p:first').text()).
//        toMatch(/partial for view 2/);
//    });
//
//  });
//});

describe('HuNI Virtual Lab', function() {

	  beforeEach(function() {
	    browser().navigateTo('../../app/index.html');
	  });


	  it('should automatically redirect to /landing when location hash/fragment is empty', function() {
	    expect(browser().location().url()).toBe("/landing");
	  });

	  /*
	   * Landing Page
	   */
	  describe('landing', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/landing');	      
	    });

	    it('should render data provider block when user navigates to /landing', function() {
	      expect(element('[ng-view] h5:first').text()).
	        toMatch(/The HuNI Data Providers/);
	    });

	    it('should render feedback title when user navigates to /landing', function() {
	      expect(element('#feedbackModalLabel').text()).
	        toMatch(/Feedback/);
	    });

	    it('should render location path when user navigates to /landing and clicks feedback button.', function() {
	    	element("[data-target='#feedbackModal']").click();
	    	expect(element('#feedbackContext').text()).toMatch(/Context\: \/landing/);
	    });

	    it('should render feedback button green when user navigates to /landing and clicks feedback button and provides feedback.', function() {
	    	var feedbackButton = element("[data-target='#feedbackModal']");
	    	feedbackButton.click();
	    	var submitFeedbackButton = element("[data-target='#feedbackModal']");
	    	submitFeedbackButton.click();
	    	expect(submitFeedbackButton.prop('class')).toMatch(/btn-success/);
	    });

	  });

	  /*
	   * About Page
	   */
	  describe('about', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/about');
	    });

	    it('should render about block when user navigates to /about', function() {
	      expect(element('[ng-view] h2:first').text()).
	        toMatch(/About HuNI/);
	    });

	    it('should render feedback title when user navigates to /about', function() {
	      expect(element('#feedbackModalLabel').text()).
	        toMatch(/Feedback/);
	    });

	    it('should render location path when user navigates to /about and clicks feedback button.', function() {
	    	element("[data-target='#feedbackModal']").click();
	    	expect(element('#feedbackContext').text()).toMatch(/Context\: \/about/);
	    });

	  });

	  /*
	   * Members Partners Page
	   */
	  describe('members-partners', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/members-partners');
	    });

	    it('should render members partners block when user navigates to /members-partners', function() {
	      expect(element('[ng-view] h2:first').text()).
	        toMatch(/Members and Partners/);
	    });

	    it('should render feedback title modal when user navigates to /members-partners', function() {
	      expect(element('#feedbackModalLabel').text()).
	        toMatch(/Feedback/);
	    });

	    it('should render location path when user navigates to /members-partners and clicks feedback button.', function() {
	    	element("[data-target='#feedbackModal']").click();
	    	expect(element('#feedbackContext').text()).toMatch(/Context\: \/members-partners/);
	    });

	  });

	  /*
	   * Contact Page
	   */
	  describe('contact', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/contact');
	    });

	    it('should render contact block when user navigates to /contact', function() {
	      expect(element('[ng-view] h2:first').text()).
	        toMatch(/Contact/);
	    });

	    it('should render feedback title when user navigates to /contact', function() {
	      expect(element('#feedbackModalLabel').text()).
	        toMatch(/Feedback/);
	    });

	    it('should render location path when user navigates to /contact and clicks feedback button.', function() {
	    	element("[data-target='#feedbackModal']").click();
	    	expect(element('#feedbackContext').text()).toMatch(/Context\: \/contact/);
	    });

	  });

	  /*
	   * Browse Search Page
	   */
	  describe('advanced-search', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/advanced-search');
	    });

	    it('should render advanced-search block when user navigates to /advanced-search', function() {
	      expect(element('[ng-view] h2:first').text()).
	        toMatch(/Advanced Search/);
	    });

	    it('should render feedback title when user navigates to /advanced-search', function() {
	      expect(element('#feedbackModalLabel').text()).
	        toMatch(/Feedback/);
	    });

	    it('should render location path when user navigates to /advanced-search and clicks feedback button.', function() {
	    	element("[data-target='#feedbackModal']").click();
	    	expect(element('#feedbackContext').text()).toMatch(/Context\: \/advanced-search/);
	    });

	  });

	  /*
	   * Workspace Page
	   * had to disable the workspace controller to get this to work.
	   * TODO RR Reenable later.
	   */
	  describe('workspace', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/workspace');
	    });

	    it('should render workspace block when user navigates to /workspace', function() {
	      expect(element('[ng-view] h2:first').text()).
	        toMatch(/Workspace/);
	    });

	    it('should render feedback title when user navigates to /workspace', function() {
	      expect(element('#feedbackModalLabel').text()).
	        toMatch(/Feedback/);
	    });

	    it('should render location path when user navigates to /workspace and clicks feedback button.', function() {
	    	element("[data-target='#feedbackModal']").click();
	    	expect(element('#feedbackContext').text()).toMatch(/Context\: \/workspace/);
	    });

	  });

	  /*
	   * HuNI Aggregate Page
	   */
	  describe('The HuNI Aggregate', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/huni-aggregate');
	    });

	    it('should render huni-aggregate block when user navigates to /huni-aggregate', function() {
	      expect(element('[ng-view] h2:first').text()).
	        toMatch(/The HuNI Aggregate/);
	    });

	    it('should render feedback title when user navigates to /huni-aggregate', function() {
	      expect(element('#feedbackModalLabel').text()).
	        toMatch(/Feedback/);
	    });

	    it('should render location path when user navigates to /huni-aggregate and clicks feedback button.', function() {
	    	element("[data-target='#feedbackModal']").click();
	    	expect(element('#feedbackContext').text()).toMatch(/Context\: \/huni-aggregate/);
	    });

	  });

	  /*
	   * Data Provider Page
	   */
	  describe('The Data Providers', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/dataset-directory');
	    });

	    it('should render dataset-directory block when user navigates to /dataset-directory', function() {
	      expect(element('[ng-view] h2:first').text()).
	        toMatch(/Data Providers/);
	    });

	    it('should render feedback title when user navigates to /dataset-directory', function() {
	      expect(element('#feedbackModalLabel').text()).
	        toMatch(/Feedback/);
	    });

	    it('should render location path when user navigates to /dataset-directory and clicks feedback button.', function() {
	    	element("[data-target='#feedbackModal']").click();
	    	expect(element('#feedbackContext').text()).toMatch(/Context\: \/dataset-directory/);
	    });

	  });

	  /*
	   * Humanities Data Sources Page
	   */
	  describe('Humanities Data Sources', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/humanities-datasources');
	    });

	    it('should render humanities-datasources block when user navigates to /humanities-datasources', function() {
	      expect(element('[ng-view] h2:first').text()).
	        toMatch(/Humanities Data Sources/);
	    });

	    it('should render feedback title when user navigates to /humanities-datasources', function() {
	      expect(element('#feedbackModalLabel').text()).
	        toMatch(/Feedback/);
	    });

	    it('should render location path when user navigates to /humanities-datasources and clicks feedback button.', function() {
	    	element("[data-target='#feedbackModal']").click();
	    	expect(element('#feedbackContext').text()).toMatch(/Context\: \/humanities-datasources/);
	    });

	  });

	  /*
	   * Help Page
	   */
	  describe('Help', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/help');
	    });

	    it('should render help block when user navigates to /help', function() {
	      expect(element('[ng-view] h2:first').text()).
	        toMatch(/Help/);
	    });

	    it('should render feedback title when user navigates to /help', function() {
	      expect(element('#feedbackModalLabel').text()).
	        toMatch(/Feedback/);
	    });

	    it('should render location path when user navigates to /help and clicks feedback button.', function() {
	    	element("[data-target='#feedbackModal']").click();
	    	expect(element('#feedbackContext').text()).toMatch(/Context\: \/help/);
	    });

	  });
	  	  
//	  describe('Buzz Client', function() {
//		  it('should filter results', function() {
//		    input('user').enter('jacksparrow');
//		    element(':button').click();
//		    expect(repeater('ul li').count()).toEqual(10);
//		    input('filterText').enter('Bees');
//		    expect(repeater('ul li').count()).toEqual(1);
//		  });
//		  });

//	  describe('contact', function() {
//
//	    beforeEach(function() {
//	      browser().navigateTo('#/contact');
//	    });
//
//
//	    it('should render contact when user navigates to /contact', function() {
//	      expect(element('[ng-view] p:first').text()).
//	        toMatch(/contact.html/);
//	    });
//
//	  });
	});

