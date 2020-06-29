const PAGE_SIZE = 9;
let currentPage = 1, lastPage, currentRequestUrl, currentItemAction;

function resetPaginator() {
    currentPage = 1;
    lastPage = null;
    currentRequestUrl = null;
    currentItemAction = null;
    $('#paginator').remove();
}

function createPaginator(allItemsCount, requestUrl, itemAction) {
    currentRequestUrl = requestUrl;
    currentItemAction = itemAction;
    let noOfPageLinks = Math.ceil(allItemsCount / PAGE_SIZE);
    lastPage = noOfPageLinks; 
    let paginator = `<nav id='paginator'> <br /><br />
                        <ul class="pagination justify-content-center">
                            <li id='paginator_previous' class="page-item disabled">
                                <a id='paginator_previous_link' href="#" class="page-link" onclick='previous()' tabindex="-1">Previous</a>
                            </li>
                            <li id='paginator_1' class="page-item active">
                                <a id='paginator_1_link' class="page-link" href="#" onclick='moveTo(1)'>1<span class="sr-only">(current)</span></a>
                            </li>`;
    for (let pageLink = 2; pageLink <= noOfPageLinks; pageLink++) {
        paginator += `<li id='paginator_${pageLink}' class="page-item"><a class="page-link" href="#" onclick='moveTo(${pageLink})'>${pageLink}</a></li>`;
    }
    paginator += `          <li id='paginator_next' class="page-item ${noOfPageLinks == 1 ? 'disabled' : ''}">
                                <a id='paginator_next_link' href="#" class="page-link" onclick='next()'>Next</a>
                            </li>
                        </ul>
                    </nav>`;
    return paginator;
}

function moveTo(targetPage) {
    if (targetPage != currentPage) {
        adjustPaginatorActiveLink(currentPage, targetPage);
        if (targetPage == lastPage) {
            onLastPage();
        } else if (targetPage == 1) {
            onFirstPage();
        }
        getMoreItems(targetPage);
    }
}

function previous() {
    let targetPage = currentPage - 1;
    adjustPaginatorActiveLink(currentPage, targetPage);
    if (targetPage == 1) {
        onFirstPage();
    }
    getMoreItems(targetPage);
}

function next() {
    let targetPage = currentPage + 1;
    adjustPaginatorActiveLink(currentPage, targetPage);
    if (targetPage == lastPage) {
        onLastPage();
    } 
    getMoreItems(targetPage);
}

function adjustPaginatorActiveLink(currentPage, targetPage) {
    $(`#paginator_${currentPage}`).removeClass('active');
    $(`#paginator_${currentPage}_link`).html(currentPage);
    $(`#paginator_${targetPage}`).addClass('active');
    $(`#paginator_${targetPage}_link`).html(`${targetPage}<span class="sr-only">(current)</span>`);
}

function onLastPage() {
    $(`#paginator_next`).addClass('disabled');
    $('#paginator_next_link').attr('tabindex', -1);
    $(`#paginator_previous`).removeClass('disabled');
    $('#paginator_previous_link').removeAttr('tabindex');
}

function onFirstPage() {
    $(`#paginator_next`).removeClass('disabled');
    $('#paginator_next_link').removeAttr('tabindex');
    $(`#paginator_previous`).addClass('disabled');
    $('#paginator_previous_link').attr('tabindex', -1);
}

function getMoreItems(targetPage) {
    currentPage = targetPage;
    currentRequestUrl = currentRequestUrl.replace(/(start-position=)[0-9]+/g, `start-position=${targetPage - 1}`);
    getItems(currentItemAction, currentRequestUrl);
}











