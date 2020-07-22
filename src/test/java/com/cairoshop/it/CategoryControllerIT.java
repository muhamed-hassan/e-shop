package com.cairoshop.it;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class CategoryControllerIT
            extends BaseProductClassificationControllerIT {

    //admin
    protected void testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation() throws Exception {
        testAddingDataWithValidPayloadAndAuthorizedUser(null, null,null);
    }

    // admin
    public void testEdit_WhenPayloadIsValid_ThenReturn204() throws Exception {
        //testDataModificationWithInvalidPayloadAndAuthorizedUser(null,null,null);
    }

    //admin
    protected void testRemove_WhenItemExists_ThenRemoveItAndReturn204() {
        testDataRemoval(null,null);
    }

    //admin or customer
    protected void testGetById_WhenDataFound_ThenReturn200AndData() throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(null, null, null);
    }

    //admin or customer
    protected void testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData() throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(null, null, null);
    }

    // admin or customer
    public void testGetAll_WhenDataExists_ThenReturn200WithData() throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(null, null, null);
    }

}
