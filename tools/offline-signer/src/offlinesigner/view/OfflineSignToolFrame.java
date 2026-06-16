package offlinesigner.view;

import offlinesigner.model.OfflineSignerUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;

public class OfflineSignToolFrame extends JFrame {

    private JTextArea orderHashArea;
    private JTextArea signatureArea;
    private JLabel privateKeyLabel;
    private File privateKeyFile;

    public OfflineSignToolFrame() {
        setTitle("Tool ký đơn hàng offline");
        setSize(760, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Tạo khóa", createKeyPanel());
        tabs.addTab("Ký đơn hàng", createSignPanel());

        add(tabs);
    }

    private JPanel createKeyPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea guide = new JTextArea();
        guide.setEditable(false);
        guide.setLineWrap(true);
        guide.setWrapStyleWord(true);
        guide.setText(
                "Chức năng này tạo cặp khóa DSA:\n\n" +
                        "- PrivateKey_ddMMyyyy_HHmmss.key: giữ trên máy để ký đơn hàng.\n" +
                        "- PublicKey_ddMMyyyy_HHmmss.key: upload lên website để đăng ký.\n\n" +
                        "Lưu ý: Không gửi file PrivateKey cho bất kỳ ai."
        );

        JButton generateButton = new JButton("Tạo cặp khóa");
        generateButton.addActionListener(e -> generateKeys());

        panel.add(guide, BorderLayout.CENTER);
        panel.add(generateButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSignPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        orderHashArea = new JTextArea(5, 60);
        orderHashArea.setLineWrap(true);
        orderHashArea.setWrapStyleWord(true);

        signatureArea = new JTextArea(6, 60);
        signatureArea.setLineWrap(true);
        signatureArea.setWrapStyleWord(true);
        signatureArea.setEditable(false);

        privateKeyLabel = new JLabel("Chưa chọn private key");

        JButton chooseKeyButton = new JButton("Chọn PrivateKey.key");
        chooseKeyButton.addActionListener(e -> choosePrivateKey());

        JButton signButton = new JButton("Ký số");
        signButton.addActionListener(e -> signOrder());

        JButton copyButton = new JButton("Copy chữ ký");
        copyButton.addActionListener(e -> copySignature());

        JPanel hashPanel = new JPanel(new BorderLayout(5, 5));
        hashPanel.add(new JLabel("Dán mã xác thực đơn hàng từ website:"), BorderLayout.NORTH);
        hashPanel.add(new JScrollPane(orderHashArea), BorderLayout.CENTER);

        JPanel keyPanel = new JPanel(new BorderLayout(5, 5));
        keyPanel.add(privateKeyLabel, BorderLayout.CENTER);
        keyPanel.add(chooseKeyButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(signButton);
        buttonPanel.add(copyButton);

        JPanel signaturePanel = new JPanel(new BorderLayout(5, 5));
        signaturePanel.add(keyPanel, BorderLayout.NORTH);
        signaturePanel.add(buttonPanel, BorderLayout.CENTER);
        signaturePanel.add(new JScrollPane(signatureArea), BorderLayout.SOUTH);

        panel.add(hashPanel, BorderLayout.NORTH);
        panel.add(signaturePanel, BorderLayout.CENTER);

        return panel;
    }

    private void generateKeys() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Chọn thư mục lưu khóa");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int result = chooser.showSaveDialog(this);
            if (result != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File dir = chooser.getSelectedFile();
            OfflineSignerUtil.generateKeyPair(dir.toPath());

            JOptionPane.showMessageDialog(this,
                    "Tạo khóa thành công!\n\n" +
                            "Đã tạo 2 file trong thư mục bạn chọn:\n" +
                            "- PrivateKey_ddMMyyyy_HHmmss.key\n" +
                            "- PublicKey_ddMMyyyy_HHmmss.key\n\n" +
                            "Upload file PublicKey lên website.\n" +
                            "Giữ file PrivateKey trên máy để ký đơn.",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi tạo khóa: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void choosePrivateKey() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn file PrivateKey.key");

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            privateKeyFile = chooser.getSelectedFile();
            privateKeyLabel.setText("Đã chọn: " + privateKeyFile.getAbsolutePath());
        }
    }

    private void signOrder() {
        try {
            String orderHash = orderHashArea.getText().trim();

            if (orderHash.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng dán mã xác thực đơn hàng.",
                        "Thiếu dữ liệu",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (privateKeyFile == null) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn file PrivateKey.key.",
                        "Thiếu private key",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String signatureBase64 = OfflineSignerUtil.signOrderHash(
                    orderHash,
                    privateKeyFile.toPath()
            );

            signatureArea.setText(signatureBase64);

            JOptionPane.showMessageDialog(this,
                    "Ký đơn hàng thành công.\nHãy copy chữ ký và dán lại vào website.",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi ký đơn hàng: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void copySignature() {
        String signature = signatureArea.getText().trim();

        if (signature.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Chưa có chữ ký để copy.",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new StringSelection(signature), null);

        JOptionPane.showMessageDialog(this,
                "Đã copy chữ ký.",
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
    }
}