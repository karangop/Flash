package cl.karangop.flash.views.main.finder;

/**
 * Created by karan_000 on 08-03-2017.
 */

public interface FinderCallback {

    void success();
    void error(String error);
    void notFound();
}
