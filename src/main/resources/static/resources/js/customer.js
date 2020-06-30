function searchProducts(event) {
    event.preventDefault(); 
    resetPaginator();

    let productNameKeyword = $('#product_name').val(); // only valid names allowed => add validations later
    let sortDirection = $("input[name='sort_direction']:checked").val();
    let sortBy = $('#sort_by').children("option:selected").val();  
    
    $("#items_container").html('');
    getItems("", `/products?name=${productNameKeyword}&start-position=0&sort-by=${sortBy}&sort-direction=${sortDirection}`); //view mode
}

function showProductsSearchBar() {
    showPreloader();
    sendAuthorizedRequest('/products/sortable-fields', GET)
    .done(function(data, textStatus, jqXHR) {
        let sortableFields = data.map(sortableField => `<option value='${sortableField}'>${sortableField}</option>`).join('');
        let productsSearchBar = `<form id='products_search_bar' onsubmit='searchProducts(event)'>
                                    <div class='row'>
                                        <div class='col-md-12 mb-3'>
                                            <input id='product_name' class='form-control w-100' type='text' placeholder='Search products' aria-label='Search products'>
                                        </div>
                                    </div>
                                    <div class='row'>
                                        <div class='offset-4 col-md-2 mb-3'>
                                            <div>Sort direction</div>                                        
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="sort_direction" id="asc" value="ASC" checked>
                                                    <label class="form-check-label" for="asc">ASC</label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="sort_direction" id="desc" value="DESC">
                                                    <label class="form-check-label" for="desc">DESC</label>
                                                </div>
                                        </div>
                                        <div class='col-md-4 mb-3'>
                                            <label for='state'>Sort by</label>
                                            <select class='custom-select d-block w-50' id='sort_by' required>
                                                <option value=''>Choose...</option>
                                                ${sortableFields}
                                            </select>
                                        </div>
                                    </div>                                    
                                </form><br /><br />`;                              
        $("#content").html(productsSearchBar);         
    }).fail(function(errorThrown) {
        showMessage(errorThrown, 'danger');
    }).always(function() {
        removePreloader();
    });       
}