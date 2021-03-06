package nuc.ss.shopping.component;
/**
 * @author：wzk
 * @desc：电商购物平台-购物车移除商品对话框
 */

import nuc.ss.shopping.db.BookDataSet;
import nuc.ss.shopping.entity.Book;
import nuc.ss.shopping.entity.ShoppingCart;
import nuc.ss.shopping.entity.StockException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static nuc.ss.shopping.frame.ShoppingCartFrame.cartModel;


public class CartRemoveDialog extends JDialog {

    public CartRemoveDialog(Frame jf, String title, boolean modal) {
        super(jf, title, modal);
        this.setSize(453, 161);
        this.setLocation(635, 330);
        Box vBox = Box.createVerticalBox();

        //组装书名输入框
        Box bookNameBox = Box.createHorizontalBox();
        JLabel bookNameLabel = new JLabel("请输入你要移除的书名：     ");
        JTextField bookNameField = new JTextField(15);
        bookNameBox.add(bookNameLabel);
        bookNameBox.add(Box.createHorizontalStrut(20));
        bookNameBox.add(bookNameField);

        //组装数量输入框
        Box numBox = Box.createHorizontalBox();
        JLabel numLabel = new JLabel("请输入你要移除的书的数量：");
        JTextField numField = new JTextField(15);
        numBox.add(numLabel);
        numBox.add(Box.createHorizontalStrut(10));
        numBox.add(numField);

        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton removeBtn = new JButton("确认移除");
        btnBox.add(removeBtn);

        vBox.add(Box.createVerticalStrut(10));
        vBox.add(bookNameBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(numBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(btnBox);

        // 为了左右有间距，在vBox外层封装一个水平的Box，添加间隔
        Box hBox = Box.createHorizontalBox();
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(vBox);
        hBox.add(Box.createHorizontalStrut(20));

        this.add(hBox);

        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookDataSet bds = new BookDataSet();
                String name = bookNameField.getText().trim();
                int removeNum = Integer.parseInt(numField.getText());
                List<Book> books = null;
                books = bds.getBooks();
                Book book = null;
                for (int index = 0; index < books.size(); index++) {
                    if (name.equals(books.get(index).getName())){
                        book = books.get(index);
                    }
                }
                boolean flag = false;
                ShoppingCart sc = null;
                try {
                    sc = new ShoppingCart();
                    flag = sc.remove(book,removeNum);
                } catch (StockException stockException) {
                    JOptionPane.showMessageDialog(jf,stockException);
                }
                Object[] colName = {"书名", "数量", "总价"};
                Map<Book, Integer> cartMap = sc.getCarts();

                Object[][] c = new Object[cartMap.size()][3];
                int i = 0;
                for (Map.Entry<Book, Integer> entry : cartMap.entrySet()) {
                    c[i][0] = entry.getKey().getName();
                    c[i][1] = entry.getValue();
                    c[i][2] = entry.getKey().getPrice() * entry.getValue();
                    i++;
                }
                cartModel.setDataVector(c, colName);
                if (flag == true) {
                    JOptionPane.showMessageDialog(jf, "移除成功");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(jf, "移除失败");
                }
            }
        });

    }
}
