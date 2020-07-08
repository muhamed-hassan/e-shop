package com.cairoshop.it;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class UserControllerIT extends BaseCommonControllerIT {

    // admin
    public void testEdit_WhenPayloadIsValid_ThenReturn204() throws Exception {
        testDataModification(null,null,null);
    }

    //admin
    protected void testGetById_WhenDataFound_ThenReturn200AndData() throws Exception {
        testDataRetrievalToReturnExistedData(null, null, null);
    }

    //admin or customer
    protected void testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData() throws Exception {
        testDataRetrievalToReturnExistedData(null, null, null);
    }

}
