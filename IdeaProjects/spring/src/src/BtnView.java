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
 * Time: 5:28:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class BtnView extends JButton implements Command {

    Mediator med;

    BtnView(ActionListener al, Mediator m) {
        super("View");
        addActionListener(al);
        med = m;
        med.registerView(this);
    }
 
    public void execute() {
        med.view();
    }

}
