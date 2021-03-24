package bll;

import dal.FacadeDAL;
import dal.IFacadeDAL;

/**
 * @author Kuba
 * @date 3/24/2021 1:31 PM
 */
public class FacadeBLL implements IFacadeBLL{
    private static FacadeBLL facadeBLL;
    private IFacadeDAL facadeDAL = FacadeDAL.getInstance();

    public static IFacadeBLL getInstance(){
        if(facadeBLL==null)
            facadeBLL = new FacadeBLL();
        return facadeBLL;
    }

    private FacadeBLL() {
    }

    @Override
    public boolean establishedConnection() {
        return facadeDAL.establishedConnection();
    }
}
