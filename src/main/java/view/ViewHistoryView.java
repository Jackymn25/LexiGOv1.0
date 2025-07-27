package view;

import interface_adapter.view_history.ViewHistoryState;
import interface_adapter.view_history.ViewHistoryViewModel;
import use_case.viewhistory.ViewHistoryEntryData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViewHistoryView extends JPanel implements PropertyChangeListener {

    /* ── 依赖 ───────────────────────────── */
    private final ViewHistoryViewModel vm;

    /* ── Swing 组件 ─────────────────────── */
    private final JLabel title = new JLabel(ViewHistoryViewModel.TITLE_LABEL, SwingConstants.CENTER);

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"#", "Date", "Words Learned"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);

    private final JLabel summaryLabel = new JLabel();

    /* ── 构造 ───────────────────────────── */
    public ViewHistoryView(ViewHistoryViewModel vm) {
        this.vm = vm;
        this.vm.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.add(summaryLabel, BorderLayout.WEST);
        add(south, BorderLayout.SOUTH);

        // 初始化 UI
        updateFromState(vm.getState());
    }

    /* ── ViewModel → View 同步 ───────────────────────── */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            ViewHistoryState s = (ViewHistoryState) evt.getNewValue();
            SwingUtilities.invokeLater(() -> updateFromState(s));
        }
    }

    private void updateFromState(ViewHistoryState s) {
        // 刷新标题
        title.setText(ViewHistoryViewModel.TITLE_LABEL + " — " + s.getCurrentUser());

        // 刷新表格
        tableModel.setRowCount(0);
        List<ViewHistoryEntryData> sessions = s.getSessions();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (int i = 0; i < sessions.size(); i++) {
            ViewHistoryEntryData entry = sessions.get(i);
            String dateStr;
            int words;
            try {
                //TODO 111
                dateStr = entry.getEndTime();
                words = entry.getWordsCount();
            } catch (Exception e) {
                // 若 DTO 定义不同，则回退使用 toString()
                dateStr = entry.toString();
                words = 0;
            }
            tableModel.addRow(new Object[]{i + 1, dateStr, words});
        }

        // 刷新汇总
        summaryLabel.setText(String.format("%s %d    %s %d",
                ViewHistoryViewModel.SESSIONS_LABEL, s.getTotalSessions(),
                ViewHistoryViewModel.WORDS_LABEL, s.getTotalWords()));

        // 显示错误
        if (s.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, s.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
