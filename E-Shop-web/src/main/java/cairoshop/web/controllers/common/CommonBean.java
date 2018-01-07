package cairoshop.web.controllers.common;

import cairoshop.web.controllers.common.pagination.Paginator;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

public class CommonBean {

    @ManagedProperty("#{paginator}")
    private Paginator paginator;

    @Inject
    private ContentChanger contentChanger;

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public ContentChanger getContentChanger() {
        return contentChanger;
    }

}
