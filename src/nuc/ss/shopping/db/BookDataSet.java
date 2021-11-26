package nuc.ss.shopping.db;
/**
 * @author：wzk
 * @desc：电商购物平台book管理类
 */

import nuc.ss.shopping.entity.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookDataSet implements Serializable{
    private static final long serialVersionUID = 1L;
    public static List<Book> books = new ArrayList<Book>();
    static {
        books = new BookDataSet().getBooks();
    }

    public List<Book> getBooks() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(DatabaseConfig.BOOK_FILE_PATH);
            ois = new ObjectInputStream(fis);
            if (DatabaseConfig.BOOK_FILE_PATH.length() == 0) {
                fis.close();
                return books;
            } else {
                ArrayList<Book> books = (ArrayList<Book>) ois.readObject();
                ois.close();
                fis.close();
                return books;
            }
        } catch (EOFException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public boolean addBook(Book book) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DatabaseConfig.BOOK_FILE_PATH));
        books.add(book);
        oos.writeObject(books);
        oos.close();
        return true;
    }
}