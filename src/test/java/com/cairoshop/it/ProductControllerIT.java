package com.cairoshop.it;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class ProductControllerIT
            extends BaseControllerIT {

    //admin
    public void testAdd_WhenPayloadIsValid_ThenSaveItAndReturnTtsLocationWith201() throws Exception {
        testAddingDataWithValidPayloadAndAuthorizedUser(null, null,null);
    }

    // admin
    public void testEdit_WhenPayloadIsValid_ThenReturn204() throws Exception {
        //testDataModificationWithInvalidPayloadAndAuthorizedUser(null,null,null);
    }

    //admin
    public void testRemove_WhenItemExists_ThenRemoveItAndReturn204() {
        testDataRemoval(null,null);
    }

    //admin or customer
    public void testGetById_WhenDataFound_ThenReturnDataWith200() throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(null, null, null);
    }

    //admin or customer
    public void testGetAllItemsByPagination_WhenDataExists_ThenReturnDataWith200() throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(null, null, null);
    }

    //customer
    public void testGetSortableFields_WhenDataExists_ThenReturnDataWith200() {

    }

    //customer
    public void testSearchByProductName_WhenDataExists_ThenReturnDataWith200() {

    }

    public void testUploadImage_WhenProductExist_ThenUpdateItAndReturn200() {

    }

    public void testDownloadImage_WhenProductExist_ThenReturnImageStreamWith200() {

    }

}
