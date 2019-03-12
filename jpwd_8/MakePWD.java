import javax.swing.*;
import java.awt.*;

/**
 *  This class intend to create the password consist
 *  a sequnce of non-repeating printable characters
 */
public class MakePWD {

    private static javax.swing.JTextField passwordLength = new javax.swing.JTextField("88", 3);
    private static javax.swing.JTextArea madePassword;
    private static javax.swing.JPopupMenu jTextPopup;
    private static javax.swing.JLabel actualValue = new javax.swing.JLabel();
    private static javax.swing.JLabel pwdValue
            = new javax.swing.JLabel("CHARSET :", javax.swing.SwingConstants.RIGHT);
    private static String defaultLabelTXT = "     Enter the password length : ";
    private static javax.swing.JButton passwordMake
            = new javax.swing.JButton("<html><font color=RED><i>to</i></font> MAKE");
    private static javax.swing.JButton resetPassword
            = new javax.swing.JButton("<html><font color=BLUE>RESET</font>");
    private static long makeID = 0;
    private static String originStr, workStr;

    /**
     * JText area clear by the user
     */
    private static javax.swing.Action clearTextArea;

    /**
     * JText area copy to the clipboard
     */
    private static javax.swing.Action copySelectedText;

    /**
     * see http://docs.oracle.com/javase/tutorial/uiswing/components/frame.html
     */
    private static void createAndShowGUI() {

        originStr = buildLine("!\"#$%&\'()*+,-./0123456789<=>"
                + "?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^"
                + "a}~bcdefghijklmnopqrstuvwxyz{");
        workStr = originStr;
        JFrame mainWindow = new JFrame("Make Password");
        JPanel mainFrame = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel passwordPanel = new JPanel();
        JPanel buttonPanel = new JPanel(new BorderLayout());

        pwdValue.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        actualValue.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        actualValue.setText(defaultLabelTXT);
        lengthPanel.add(actualValue);

        passwordLength.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLength.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        passwordLength.addActionListener(MakePWD::actionHandle);
        lengthPanel.add(passwordLength);
        topPanel.add(lengthPanel, BorderLayout.CENTER);

        jTextPopup = new JPopupMenu();
        actionsMake();
        jTextPopup.add(copySelectedText);
        jTextPopup.add(clearTextArea);
        copySelectedText.setEnabled(false);

        madePassword = new JTextArea(3, 40);
        madePassword.setEditable(true);
        madePassword.setLineWrap(true);
        madePassword.setWrapStyleWord(true);
        madePassword.setMargin(new Insets(3, 3, 3, 3));
        madePassword.setBackground(Color.darkGray);
        madePassword.setForeground(Color.white);
        madePassword.setCaretColor(mainWindow.getForeground());
        madePassword.setText(originStr);
        madePassword.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        madePassword.setLineWrap(true);
        passwordMake.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        passwordMake.setBackground(Color.WHITE);
        passwordMake.addActionListener(MakePWD::actionHandle);
        resetPassword.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        resetPassword.setBackground(Color.WHITE);
        resetPassword.addActionListener(MakePWD::actionHandle);
        topPanel.add(resetPassword, BorderLayout.EAST);
        pwdValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        buttonPanel.add(pwdValue,BorderLayout.NORTH);
        buttonPanel.add(passwordMake,BorderLayout.CENTER);

        madePassword.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger() && (e.getButton() == 3)) {
                    jTextPopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (e.getButton() == 3) {
                    jTextPopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        passwordPanel.add(buttonPanel);
        JScrollPane centerPane = new JScrollPane(madePassword);
        centerPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        passwordPanel.add(centerPane);
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(passwordPanel, BorderLayout.CENTER);
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainFrame.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setContentPane(mainFrame);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setResizable(false);
        mainWindow.setLocation((screenSize.width - mainWindow.getWidth()) / 2,
                (screenSize.height - mainWindow.getHeight()) / 2);
        mainWindow.setVisible(true);
    }

    /**
     * Input action handle
     * @param a action
     */
    private static void actionHandle(java.awt.event.ActionEvent a) {
    	
        if (a.getActionCommand().contains("MAKE")||
        	a.getActionCommand().matches("[^0-9]*[12]?[0-9]{1,2}[^0-9]*")) {
            String stringLength = passwordLength.getText();
            try {
                int pLength = Integer.parseInt(stringLength);
                actualValue.setText(defaultLabelTXT);
                passwordLength.setText(String.valueOf(pLength));
                pwdValue.setText("PASSWORD :");
                madePassword.setText(makePwd(pLength));
                madePassword.setEditable(false);
                clearTextArea.setEnabled(false);
                copySelectedText.setEnabled(true);
                //passwordMake.requestFocus();
                passwordMake.requestFocus();
                makeID = a.getWhen();
            } catch (NumberFormatException e1) {
                actualValue.setText("Text type error, please try again");
                resetUI(originStr.length());
            }
        } else {
            resetUI(originStr.length());
            actualValue.setText(defaultLabelTXT);
        }
    }

    /**
     * Reset to the clear source frame
     * @param pLength  password lenght
     */
    private static void resetUI(int pLength) {
        makeID = 0;
        madePassword.setText(originStr);
        passwordLength.setText(String.valueOf(pLength));
        pwdValue.setText(" CHARSET :");
        madePassword.setEditable(true);
        clearTextArea.setEnabled(true);
        copySelectedText.setEnabled(true);
        passwordLength.requestFocus();
        passwordLength.selectAll();
    }

    /**
     * The password line maker
     * @param pwdLgt password lenght
     * @return
     */
    private static String makePwd(int pwdLgt) {

        if (makeID == 0) {
            workStr = buildLine(madePassword.getText());

            if (workStr.length() > pwdLgt && pwdLgt == originStr.length()) {
                pwdLgt = workStr.length();
                passwordLength.setText(String.valueOf(pwdLgt));
            }
        }

        if (pwdLgt > workStr.length()) {
            pwdLgt = workStr.length();
            passwordLength.setText(String.valueOf(pwdLgt));
        }

        return new java.util.Random().ints(0, workStr.length())
                .boxed().distinct().limit(pwdLgt)
                .map(workStr::charAt)
                .collect(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append).toString();
    }

    /**
     * The original line prepare to output
     * @param str
     * @return
     */
    private static String buildLine(String str) {
        return str.chars().distinct().sorted()
                .collect(StringBuilder::new,
                        (sb, ch) -> sb.append(String.valueOf((char) ch)),
                        StringBuilder::append).toString();
    }

    /**
     * The popup menu actions create
     * see http://math.hws.edu/eck/cs124/javanotes7/c13/s3.html
     */
    static private void actionsMake() {
        clearTextArea = new javax.swing.AbstractAction("<html><i><b color=red>C</i></b>lear") {

			@Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                madePassword.setText("");
            }
        };
        clearTextArea.putValue(javax.swing.Action.SHORT_DESCRIPTION, "TextArea clear");

        copySelectedText = new javax.swing.AbstractAction("<html><i><b color=red>C</i></b>opy") {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String txtArea = madePassword.getSelectedText();
                java.awt.datatransfer.Clipboard clipboard
                        = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new java.awt.datatransfer.StringSelection(txtArea), null);
            }

        };
        copySelectedText.putValue(javax.swing.Action.SHORT_DESCRIPTION, "TextArea copy to the clipboard");
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(MakePWD::createAndShowGUI);
    }
}
