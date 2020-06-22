function showPreloader() {
    let preloaderHtml = `<div id='preloader' class='offset-5 loadingio-spinner-spinner-bktd5cbe8lt'>
                            <div class='ldio-x75mdke3og'>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                            </div>
                         </div>`;
    $('#main').append(preloaderHtml);
}

function removePreloader() {
    $('#preloader').remove();
}