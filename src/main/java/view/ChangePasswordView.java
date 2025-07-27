package view;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePwState;
import interface_adapter.change_password.ChangeViewModel;
import interface_adapter.change_password.MakePasswordChange.MakePasswordChangeController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Change‑Password UI — shows current user and accepts a new password.
 * <p>If the user has a security question configured, clicking <em>Submit</em>
 * pops up a dialog to collect the answer. On success the controller is invoked;
 * on cancel the operation is aborted.</p>
 */
public class ChangePasswordView extends JPanel
        implements ActionListener, PropertyChangeListener {

    /* ---------------- Dependencies ---------------- */
    private final ChangeViewModel vm;
    private MakePasswordChangeController controller;

    /* ---------------- Swing widgets ---------------- */
    private final JLabel         currentUserLbl = new JLabel();
    private final JPasswordField newPwFld       = new JPasswordField(15);
    private final JLabel         newPwErrLbl    = new JLabel();
    private final JButton        submitBtn      = new JButton("Submit");

    /** Whether a security‑question answer is required (comes from ViewModel). */
    private boolean needSecurityAnswer = false;

    public static final String VIEW_NAME = "change password";

    public ChangePasswordView(ChangeViewModel vm) {
        this.vm = vm;
        this.vm.addPropertyChangeListener(this);
        buildUi();
        syncFromState(vm.getState());
    }

    /* ---------------- UI construction ---------------- */
    private void buildUi() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Change Password", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        /* Current user row */
        JPanel userRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userRow.add(new JLabel("Current User: "));
        userRow.add(currentUserLbl);
        add(userRow);

        /* New password row */
        add(makeRow("New Password", newPwFld, newPwErrLbl));

        /* Submit button */
        JPanel btnRow = new JPanel();
        btnRow.add(submitBtn);
        add(btnRow);

        submitBtn.addActionListener(this);

        /* Bind new password field to ViewModel */
        docBind(newPwFld, ChangePwState::setPassword);
    }

    private JPanel makeRow(String label, JComponent field, JLabel errorLabel) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel(label + ":"));
        p.add(field);
        if (errorLabel != null) {
            errorLabel.setForeground(Color.RED);
            p.add(errorLabel);
        }
        return p;
    }

    /* ---------------- Document binding helper ---------------- */
    private interface Mutator { void apply(ChangePwState s, String value); }
    private void docBind(JTextComponent comp, Mutator m) {
        comp.getDocument().addDocumentListener(new DocumentListener() {
            void sync() {
                ChangePwState st = vm.getState();
                String val = comp instanceof JPasswordField ?
                        new String(((JPasswordField) comp).getPassword()) :
                        comp.getText();
                m.apply(st, val);
                vm.setState(st);
            }
            public void insertUpdate(DocumentEvent e) { sync(); }
            public void removeUpdate(DocumentEvent e) { sync(); }
            public void changedUpdate(DocumentEvent e) { sync(); }
        });
    }

    // TODO BLANK?
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn && controller != null) {
            ChangePwState st = vm.getState();

            String newPwd = st.getPassword();
            String user   = st.getUsername();

            boolean needverification = st.isVerification();
            needSecurityAnswer = needverification;

            if (needSecurityAnswer) {
                String answer = JOptionPane.showInputDialog(
                        this,
                        st.getSecurityQuestion(),
                        "Enter Security Question Answer:",
                        JOptionPane.QUESTION_MESSAGE);
                controller.execute(user, newPwd , (String) answer.trim());
            } else {
                controller.execute(user, newPwd , null);
            }
        }
    }

    /* ---------------- ViewModel sync ---------------- */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            syncFromState((ChangePwState) evt.getNewValue());
        }
    }

    private void syncFromState(ChangePwState s) {
        currentUserLbl.setText(s.getUsername());
        newPwFld.setText(s.getPassword());
        newPwErrLbl.setText(s.getChangeError());
        this.needSecurityAnswer = s.isVerification();
    }

    /* ---------------- Wiring ---------------- */
    public void setController(MakePasswordChangeController c) { this.controller = c; }
    public String getViewName() { return VIEW_NAME; }
}
