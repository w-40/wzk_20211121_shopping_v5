package nuc.ss.shopping.db;
/**
 * @author：wzk
 * @desc：电商购物平台user管理类
 */

import nuc.ss.shopping.entity.Book;
import nuc.ss.shopping.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDataSet {
    private static final long serialVersionUID = 1L;
    //    public static Set<User> users = new HashSet<User>();
//
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        UserDataSet.users = users;
//    }
    //public static List<Book> books = new BookDataSet().getBooks();
    public static List<User> users = new ArrayList<User>();
    static {
        users = new UserDataSet().getUsers();
    }

    public List<User> getUsers() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(DatabaseConfig.USER_FILE_PATH);
            ois = new ObjectInputStream(fis);
            if (DatabaseConfig.USER_FILE_PATH.length() == 0) {
                fis.close();
                return users;
            } else {
                ArrayList<User> users = (ArrayList<User>) ois.readObject();
                ois.close();
                fis.close();
                return users;
            }
        } catch (EOFException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void setUsers(List<User> users) {
        UserDataSet.users = users;
    }

    public boolean addUser(User user) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DatabaseConfig.USER_FILE_PATH));
        users.add(user);
        oos.writeObject(users);
        oos.close();
        return true;
    }
}
