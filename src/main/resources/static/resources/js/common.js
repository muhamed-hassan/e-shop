function getItems(itemAction, requestUrl) {
    showPreloader();
    $("#items_container").remove();
    sendAuthorizedRequest(requestUrl, 'GET')
    .done(function (data, textStatus, jqXHR) {
        let itemsJson = data.items;
        let itemsHtml = `<div class='row'>`;
        for (var index = 0; index < itemsJson.length; index++) {
            let item = itemsJson[index];
            let itemActionButton = "";
            if (requestUrl.includes("users")) {
                itemActionButton += `<button id='user_${item.id}' type='button' class='btn btn-sm btn-warning' onclick='changeUserState(${item.id},${!item.active})'>${item.active ? itemAction[1] : itemAction[0]}</button>`;
            } else {
                switch (itemAction) {
                    case "Edit":
                        itemActionButton += `<button type='button' class='btn btn-sm btn-warning' onclick='preEditItem("${requestUrl.substring(0, requestUrl.lastIndexOf('?'))}/${item.id}")'>${itemAction}</button>`;
                        break;
                    case "Delete":
                        itemActionButton += `<button type='button' class='btn btn-sm btn-danger' data-toggle='modal' data-target='#confirm_delete_dialogue' onclick='preDeleteItem("${requestUrl.substring(0, requestUrl.lastIndexOf('?'))}/${item.id}")'>${itemAction}</button>`;
                        break;
                }
            }

            let viewDetailsBtn = !requestUrl.includes('vendors') && !requestUrl.includes('categories') ?
                    `<button type='button' class='btn btn-sm btn-secondary' onclick='getDetails("${requestUrl.substring(0, requestUrl.lastIndexOf('?'))}/${item.id}")'>View</button>`
                    :
                    "";
            itemsHtml += `<div class='col-md-4'>
                            <div class='card mb-4 shadow-sm'>
                                <div class='card-body'>
                                    <p class='card-text text-center'>${item.name}</p>
                                        <div class='d-flex justify-content-between align-items-center'>
                                            <div class='btn-group'>
                                                ${viewDetailsBtn}
                                                ${itemActionButton}
                                            </div>
                                        </div>
                                </div>
                            </div>
                        </div>`;
        }

        itemsHtml += "</div>";
        let paginator = createPaginator(data.allSavedItemsCount);
        let finalContent = `<div id='items_container'>${itemsHtml}</div>
                            ${paginator}`;        
        if ($('#products_search_bar').length > 0) {
            $("#content").append(finalContent);
        } else {
            $("#content").html(finalContent);
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showMessage('Failed to fetch data', 'danger');
    }).always(function() {
        removePreloader();
        clearMessagesSection();
    });
}

function getProductDetails(requestUrl) {
    sendAuthorizedRequest(requestUrl, 'GET')
    .then(function(data, textStatus, jqXHR) {
            return $.when(sendAuthorizedRequest('/vendors/' + data.vendorId, 'GET'),
                            sendAuthorizedRequest('/categories/' + data.categoryId, 'GET'),
                            $.Deferred().resolve(data));
        }, function(jqXHR, textStatus, errorThrown) {
            throw new Error(errorThrown);
    }).then(function(vendorResponse, categoryResponse, product) {
            let itemDetailsHtml = "<div class='row'>";
            Object.keys(product).forEach(function(key) {
                if (key != 'id' && key != 'active' && key != 'imageUploaded') {
                    switch (key) {
                        case 'vendorId':
                            itemDetailsHtml += generateItemDetailHtml('vendor', vendorResponse[0].name);
                            break;
                        case 'categoryId':
                            itemDetailsHtml += generateItemDetailHtml('category', categoryResponse[0].name);
                            break;
                        default:
                            itemDetailsHtml += generateItemDetailHtml(key, product[key]);
                    }
                }
            });
            let imageSrc;
            if (product.imageUploaded) {
                imageSrc = `/products/images/${product.id}`;
            } else {
                imageSrc = `resources/images/empty.jpg`;
            }
            itemDetailsHtml += `    <img id='image_of_product' src='${imageSrc}' width='500' height='333'>
                                </div>`;
            $("#content").html(itemDetailsHtml);
    }, function(errorThrown) {
        showMessage('Failed to load the details', 'danger');
    }).always(function() {
        removePreloader();
        clearMessagesSection();
    });
}

function getUserDetails(requestUrl) {
    sendAuthorizedRequest(requestUrl, 'GET')
    .done(function (data, textStatus, jqXHR) {
        let itemDetailsHtml = `<div class='row'>`;
        Object.keys(data).forEach(function(key) {
            if (key != 'id' && key != 'active') {
                itemDetailsHtml += generateItemDetailHtml(key, data[key]);
            }
        });
        itemDetailsHtml += `</div>`;
        $("#content").html(itemDetailsHtml);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showMessage('Failed to load the details', 'danger');
    }).always(function() { 
        removePreloader();
        clearMessagesSection();
    });
}

function getDetails(requestUrl) {
    showPreloader();
    if (requestUrl.includes("products")) {
        getProductDetails(requestUrl);
    } else if (requestUrl.includes("users")) {
        getUserDetails(requestUrl);
    }
}


/* ********************************************************************************************************************************** */

/**
 <nav>
  <ul class="pagination justify-content-center">
    <li class="page-item disabled">
      <a class="page-link" href="#" tabindex="-1">Previous</a>
    </li>
    <li class="page-item"><a class="page-link" href="#">1</a></li>
    <li class="page-item active">
      <a class="page-link" href="#">2 <span class="sr-only">(current)</span></a>
    </li>
    <li class="page-item"><a class="page-link" href="#">3</a></li>
    <li class="page-item">
      <a class="page-link" href="#">Next</a>
    </li>
  </ul>
</nav>
 */
const PAGE_SIZE = 9;
let currentPage, lastPage;

function createPaginator(allItemsCount) {
    currentPage = 1;
    let noOfPageLinks = Math.ceil(allItemsCount / PAGE_SIZE);
    let paginator = `<nav>
                        <ul id='paginator' class="pagination justify-content-center">
                            <li id='paginator_previous' class="page-item disabled">
                                <a class="page-link" href="#" tabindex="-1">Previous</a>
                            </li>
                            <li id='paginator_1' class="page-item active">
                                <a id='paginator_1_link' class="page-link" href="#" onclick='moveTo(1)'>1<span class="sr-only">(current)</span></a>
                            </li>`;
    for (let pageLink = 2; pageLink <= noOfPageLinks; pageLink++) {
        paginator += `<li id='paginator_${pageLink}' class="page-item"><a class="page-link" href="#" onclick='moveTo(${pageLink})'>${pageLink}</a></li>`;
    }
    paginator += `<li id='paginator_next' class="page-item ${noOfPageLinks == 1 ? 'disabled' : ''}">
    <a class="page-link" href="#">Next</a>
</li>
</ul>
</nav>`;
    return paginator;
}

function moveTo(targetPage) {
    // if targetPage != currentPage => then proceed
    // remove class `active` from `paginator_${currentPage}`
    // remove htmlContent of `a` element with id `paginator_${currentPage}_link`
    // make pageLink active of targetPage using this id `paginator_${targetPage}`
    // add htmlContent to `a` element with id `paginator_${targetPage}_link` => `${targetPage}<span class="sr-only">(current)</span>`
    // if the targetPage is the lastPage, then disable `next` btn, and enable `previous` button
    // else if the targetPage is the 1, then disable `previous` btn and enable `next` button
}

function previous() {
    // calculate targetPage = currentPage - 1
    // remove class `active` from `paginator_${currentPage}`
    // remove htmlContent of `a` element with id `paginator_${currentPage}_link`
    // make pageLink active of targetPage using this id `paginator_${targetPage}`
    // add htmlContent to `a` element with id `paginator_${targetPage}_link` => `${targetPage}<span class="sr-only">(current)</span>`
    // if the targetPage is the lastPage, then disable `next` btn, and enable `previous` button
    // else if the targetPage is the 1, then disable `previous` btn and enable `next` button
}

function next() {
    // calculate targetPage = currentPage + 1
    // remove class `active` from `paginator_${currentPage}`
    // remove htmlContent of `a` element with id `paginator_${currentPage}_link`
    // make pageLink active of targetPage using this id `paginator_${targetPage}`
    // add htmlContent to `a` element with id `paginator_${targetPage}_link` => `${targetPage}<span class="sr-only">(current)</span>`
    // if the targetPage is the lastPage, then disable `next` btn, and enable `previous` button
    // else if the targetPage is the 1, then disable `previous` btn and enable `next` button
}















