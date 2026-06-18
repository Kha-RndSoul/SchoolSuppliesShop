package offlinesigner.view;

import offlinesigner.model.OfflineSignerUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OfflineSignToolFrame extends JFrame {

    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField txtFolderPath;
    private JButton btnChooseFolder;
    private JButton btnGenerateKeys;

    private JTextField txtOrderHash;
    private JTextField txtKeyPath;
    private JButton btnChoosePrivateKey;
    private JButton btnSign;
    private JTextField txtSignature;
    private JButton btnCopySignature;

    public OfflineSignToolFrame() {
        setTitle("Công cụ Ký số Offline");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 300));
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tạo khóa", buildTabTaoKhoa());
        tabbedPane.addTab("Ký đơn hàng", buildTabKyDonHang());

        setContentPane(tabbedPane);
        pack();

        registerListeners();
    }

    private JPanel buildTabTaoKhoa() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblHint = new JLabel("Chọn nơi lưu trữ 2 file khóa :");
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(lblHint, gbc);

        txtFolderPath = new JTextField();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        panel.add(txtFolderPath, gbc);

        btnChooseFolder = new JButton("Chọn thư mục");
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(btnChooseFolder, gbc);

        btnGenerateKeys = new JButton("Tạo khóa");
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(16, 4, 4, 4);
        panel.add(btnGenerateKeys, gbc);

        return panel;
    }

    private JPanel buildTabKyDonHang() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel lblOrderHash = new JLabel("1. Mã xác thực đơn hàng (Order Hash) :");
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblOrderHash, gbc);

        txtOrderHash = new JTextField();
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(txtOrderHash, gbc);

        JLabel lblKeyPath = new JLabel("2. Chọn file private key :");
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 4, 4, 4);
        panel.add(lblKeyPath, gbc);

        txtKeyPath = new JTextField();
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(4, 4, 4, 4);
        panel.add(txtKeyPath, gbc);

        btnChoosePrivateKey = new JButton("Chọn file");
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(btnChoosePrivateKey, gbc);

        btnSign = new JButton("Ký");
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 4, 4, 4);
        panel.add(btnSign, gbc);

        JLabel lblSignature = new JLabel("3. Kết quả chữ ký :");
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 4, 4, 4);
        panel.add(lblSignature, gbc);

        txtSignature = new JTextField();
        txtSignature.setEditable(false);
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 4, 4, 4);
        panel.add(txtSignature, gbc);

        btnCopySignature = new JButton("Sao chép chữ ký");
        gbc.gridx = 1; gbc.gridy = 6;
        gbc.weightx = 0;
        panel.add(btnCopySignature, gbc);

        return panel;
    }

    private void registerListeners() {

        btnChooseFolder.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Chọn thư mục lưu cặp khóa");
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                txtFolderPath.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        btnGenerateKeys.addActionListener(e -> {
            String folderPathStr = txtFolderPath.getText().trim();
            if (folderPathStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn thư mục lưu trữ trước!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Path outputDir = Paths.get(folderPathStr);
                String keyTimeStamp = OfflineSignerUtil.generateKeyPair(outputDir);
                String message = String.format(
                        "Tạo cặp khóa PublicKey_%s.key và PrivateKey_%s.key thành công!",
                        keyTimeStamp, keyTimeStamp);
                JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi tạo khóa: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnChoosePrivateKey.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogTitle("Chọn file Private Key (*.key)");
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                txtKeyPath.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        btnSign.addActionListener(e -> {
            String orderHash = txtOrderHash.getText().trim();
            String keyPathStr = txtKeyPath.getText().trim();

            if (orderHash.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng dán mã xác thực đơn hàng (Order Hash) vào!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (keyPathStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn đường dẫn đến file private_key.key!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String signature = OfflineSignerUtil.signOrderHash(orderHash, Paths.get(keyPathStr));
                txtSignature.setText(signature);
                JOptionPane.showMessageDialog(this,
                        "Ký số đơn hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi trong quá trình ký số: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnCopySignature.addActionListener(e -> {
            String signature = txtSignature.getText().trim();
            if (signature.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Chưa có chữ ký để copy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            StringSelection sel = new StringSelection(signature);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(sel, null);
            JOptionPane.showMessageDialog(this,
                    "Đã copy chữ ký số vào bộ nhớ tạm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}