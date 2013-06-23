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

	  });

	  /*
	   * About Page
	   */
	  describe('about', function() {

	    beforeEach(function() {
	      browser().navigateTo('#/about');
	    });

	    it('should render data provider block when user navigates to /about', function() {
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

