package offlinesigner.view;

import offlinesigner.model.OfflineSignerUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OfflineSignToolFrame extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
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
        this.setContentPane(panel1);
        this.setTitle("Công cụ Ký số Offline");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setMinimumSize(new java.awt.Dimension(500, 300));

        //Nút chọn thư mục lưu khóa
        btnChooseFolder.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Chỉ cho chọn thư mục
            chooser.setDialogTitle("Chọn thư mục lưu cặp khóa");

            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = chooser.getSelectedFile();
                txtFolderPath.setText(selectedFolder.getAbsolutePath());
            }
        });

        // Nút tạo khóa
        btnGenerateKeys.addActionListener(e -> {
            String folderPathStr = txtFolderPath.getText().trim();
            if (folderPathStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn thư mục lưu trữ trước!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Path outputDir = Paths.get(folderPathStr);

                OfflineSignerUtil.generateKeyPair(outputDir);
                JOptionPane.showMessageDialog(this, "Tạo cặp khóa (Public/Private Key) thành công tại:\n" + folderPathStr, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tạo khóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Nút chọn file private key
        btnChoosePrivateKey.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Chỉ cho chọn file
            chooser.setDialogTitle("Chọn file Private Key (*.key)");

            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                txtKeyPath.setText(selectedFile.getAbsolutePath());
            }
        });

        // Nút ký
        btnSign.addActionListener(e -> {
            String orderHash = txtOrderHash.getText().trim();
            String keyPathStr = txtKeyPath.getText().trim();

            if (orderHash.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng dán mã xác thực đơn hàng (Order Hash) vào!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (keyPathStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đường dẫn đến file private_key.key!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Path privateKeyPath = Paths.get(keyPathStr);

                String signature = OfflineSignerUtil.signOrderHash(orderHash, privateKeyPath);

                txtSignature.setText(signature);
                JOptionPane.showMessageDialog(this, "Ký số đơn hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi trong quá trình ký số: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Nút copy chữ ký
        btnCopySignature.addActionListener(e -> {
            String signature = txtSignature.getText().trim();
            if (signature.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa có chữ ký để copy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            StringSelection stringSelection = new StringSelection(signature);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            JOptionPane.showMessageDialog(this, "Đã copy chữ ký số vào bộ nhớ tạm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}