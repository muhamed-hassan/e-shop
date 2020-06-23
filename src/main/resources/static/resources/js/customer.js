function searchProducts(event) {
    event.preventDefault(); //prevent default action
  
    $("#items_container").remove();
    getItems("", "/products"); //view mode
  
    //reset search
  }
  
  function showProductsSearchBar() {
      let productsSearchBar = `<form id='products_search_bar' onsubmit='searchProducts(event)'>
                                  <div class='row'>
                                      <div class='col-md-12 mb-3'>
                                          <input class='form-control w-100' type='text' placeholder='Search' aria-label='Search'>
                                      </div>
                                  </div>
                                  <div class='row'>
                                      <div class='offset-4 col-md-2 mb-3'>
                                          <div>Sort direction</div>
                                      <div class='custom-control custom-radio'>
                                          <input id='asc' name='asc' type='radio' class='custom-control-input' checked required>
                                          <label class='custom-control-label' for='asc'>ASC</label>
                                      </div>
                                      <div class='custom-control custom-radio'>
                                          <input id='desc' name='desc' type='radio' class='custom-control-input' required>
                                          <label class='custom-control-label' for='desc'>DESC</label>
                                      </div>
                                  </div>
                                  <div class='col-md-4 mb-3'>
                                      <label for='state'>Sort by</label>
                                      <select class='custom-select d-block w-50' id='sort_by' required>
                                          <option value=''>Choose...</option>
                                      </select>
                                  </div>
                              </form>`;
      $("#main").html(productsSearchBar);
  }