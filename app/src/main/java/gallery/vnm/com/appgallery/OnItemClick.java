package gallery.vnm.com.appgallery;

public interface OnItemClick<T> {
    void onClick(T item, int position);
}
