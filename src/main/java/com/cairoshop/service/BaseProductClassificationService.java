package com.cairoshop.service;

import java.util.List;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseProductClassificationService<NDTO, SDDTO, SBDTO, T> extends BaseService<NDTO, SDDTO, SBDTO, T>{

    void edit(SDDTO sddto);

    List<SBDTO> getAll();

}
