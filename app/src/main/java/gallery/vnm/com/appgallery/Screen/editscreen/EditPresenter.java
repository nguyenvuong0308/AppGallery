package gallery.vnm.com.appgallery.Screen.editscreen;

public class EditPresenter implements EditScreenContract.Presenter {
    private EditScreenContract.View mView;

    public EditPresenter(EditScreenContract.View mView) {
        this.mView = mView;
    }
}
