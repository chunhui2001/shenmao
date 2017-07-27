import { A4loginPage } from './app.po';

describe('a4login App', () => {
  let page: A4loginPage;

  beforeEach(() => {
    page = new A4loginPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
