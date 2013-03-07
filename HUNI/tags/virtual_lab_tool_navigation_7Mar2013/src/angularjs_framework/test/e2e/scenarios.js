'use strict';

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('virtualab App', function() {

  it('should redirect index.html to index.html#/projects', function() {
    browser().navigateTo('../../app/index.html');
    expect(browser().location().url()).toBe('/projects');
  });


  describe('Project list view', function() {

    beforeEach(function() {
      browser().navigateTo('../../app/index.html#/projects');
    });


    it('should filter the project list as user types into the search box', function() {
      expect(repeater('.projects li').count()).toBe(20);

      input('query').enter('nexus');
      expect(repeater('.projects li').count()).toBe(1);

      input('query').enter('motorola');
      expect(repeater('.projects li').count()).toBe(8);
    });


    it('should be possible to control project order via the drop down select box', function() {
      input('query').enter('tablet'); //let's narrow the dataset to make the test assertions shorter

      expect(repeater('.projects li', 'Project List').column('project.name')).
          toEqual(["Motorola XOOM\u2122 with Wi-Fi",
                   "MOTOROLA XOOM\u2122"]);

      select('orderProp').option('Alphabetical');

      expect(repeater('.projects li', 'Project List').column('project.name')).
          toEqual(["MOTOROLA XOOM\u2122",
                   "Motorola XOOM\u2122 with Wi-Fi"]);
    });


    it('should render project specific links', function() {
      input('query').enter('nexus');
      element('.projects li a').click();
      expect(browser().location().url()).toBe('/projects/nexus-s');
    });
  });


  describe('Project detail view', function() {

    beforeEach(function() {
      browser().navigateTo('../../app/index.html#/projects/nexus-s');
    });


    it('should display nexus-s page', function() {
      expect(binding('project.name')).toBe('Nexus S');
    });


    it('should display the first project image as the main project image', function() {
      expect(element('img.project').attr('src')).toBe('img/projects/nexus-s.0.jpg');
    });


    it('should swap main image if a thumbnail image is clicked on', function() {
      element('.project-thumbs li:nth-child(3) img').click();
      expect(element('img.project').attr('src')).toBe('img/projects/nexus-s.2.jpg');

      element('.project-thumbs li:nth-child(1) img').click();
      expect(element('img.project').attr('src')).toBe('img/projects/nexus-s.0.jpg');
    });
  });
});
