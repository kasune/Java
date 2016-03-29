package src;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jun 1, 2011
 * Time: 5:31:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class BtnSearch extends JButton implements Command {

        Mediator med;

        BtnSearch(ActionListener al, Mediator m) {
            super("Search");
            addActionListener(al);
            med = m;
            med.registerSearch(this);
        }

        public void execute() {
            med.search();
        }

}
